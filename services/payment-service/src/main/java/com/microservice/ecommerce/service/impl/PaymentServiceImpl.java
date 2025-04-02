package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.client.OrderResponse;
import com.microservice.ecommerce.client.PaymentClient;
import com.microservice.ecommerce.config.ConfigVNPay;
import com.microservice.ecommerce.config.MoMoEnvironment;
import com.microservice.ecommerce.constant.PaymentMethod;
import com.microservice.ecommerce.constant.RequestType;
import com.microservice.ecommerce.message.PaymentConfirmation;
import com.microservice.ecommerce.message.PaymentEvent;
import com.microservice.ecommerce.message.PaymentProducer;
import com.microservice.ecommerce.model.dto.request.PaymentRequest;
import com.microservice.ecommerce.model.entity.Payment;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.mapper.PaymentMapper;
import com.microservice.ecommerce.model.momo.PaymentResponse;
import com.microservice.ecommerce.repository.PaymentRepository;
import com.microservice.ecommerce.service.PaymentService;
import com.microservice.ecommerce.util.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 1:47 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;

    PaymentMapper paymentMapper;

    PaymentProducer paymentProducer;

    PaymentClient paymentClient;

    private static final String MOMO_SUCCESS_STATUS = "0";
    private static final String VNPAY_SUCCESS_STATUS = "00";


    @Override
    public GlobalResponse<com.microservice.ecommerce.model.dto.response.PaymentResponse> saveMoMoPayment(PaymentRequest request) throws Exception {
        LogUtils.init();
        UUID paymentId = UUID.randomUUID();
        String requestId = paymentId.toString();
        String orderId = request.orderId().toString();
        Long transId = 2L;
        long amount = request.amount().longValue() * 1000;

        String partnerClientId = "partnerClientId";
        String orderInfo = "Pay With MoMo";
        String returnURL = "http://localhost:8086/api/v1/payments/callback";
        String notifyURL = "http://localhost:8086/api/v1/payments/notify";
        String callbackToken = "callbackToken";
        String token = "";

        MoMoEnvironment environment = MoMoEnvironment.selectEnv("dev");


//      Remember to change the IDs at enviroment.properties file

        /***
         * create payment with capture momo wallet
         */
        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId,
                Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET,
                Boolean.TRUE
        );

        com.microservice.ecommerce.model.dto.response.PaymentResponse paymentResponse = savePayment(request, paymentId);
        paymentResponse.setPaymentUrl(captureWalletMoMoResponse.getPayUrl());
        paymentResponse.setAppUrl(captureWalletMoMoResponse.getApplink());
        if (captureWalletMoMoResponse != null && captureWalletMoMoResponse.getResultCode() == 0) {
            log.info("Thanh toán thành công với MoMo. Mã giao dịch: {}", captureWalletMoMoResponse.getTransId());
            return new GlobalResponse<>(Status.SUCCESS, paymentResponse);
        } else {
            log.error("Thanh toán thất bại. Mã lỗi: {}", captureWalletMoMoResponse != null ? captureWalletMoMoResponse.getResultCode() : "null");
            return new GlobalResponse<>(Status.ERROR, paymentResponse);
        }
    }

    @Override
    public GlobalResponse<com.microservice.ecommerce.model.dto.response.PaymentResponse> saveVNPayPayment(PaymentRequest request,
                                                                                                          HttpServletRequest httpServletRequest) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "order-type";
        long amount = (long) (request.amount() * 100000);
        String bankCode = request.bankCode();

        String vnp_TxnRef = ConfigVNPay.getRandomNumber(8);
        String orderInfor = request.orderId().toString();
        String vnp_IpAddr = ConfigVNPay.getIpAddress(httpServletRequest);
        String vnp_TmnCode = ConfigVNPay.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.language();
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", ConfigVNPay.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVNPay.hmacSHA512(ConfigVNPay.vnp_SecretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVNPay.vnp_PayUrl + "?" + queryUrl;

        com.microservice.ecommerce.model.dto.response.PaymentResponse paymentResponse = savePayment(request, UUID.randomUUID());
        paymentResponse.setPaymentUrl(paymentUrl);

        return new GlobalResponse<>(
                Status.SUCCESS,
                paymentResponse
        );
    }

    @Override
    public GlobalResponse<com.microservice.ecommerce.model.dto.response.PaymentResponse> savePayment(PaymentRequest request) {
        Payment payment = paymentMapper.toPayment(request);
        payment.setId(UUID.randomUUID());

        payment = paymentRepository.save(payment);

        return new GlobalResponse<>(
                Status.SUCCESS,
                paymentMapper.toPaymentResponse(payment)
        );

    }

    @Override
    public GlobalResponse<String> paymentConfirmation(Map<String, String> requestParams) {
        String momo_status = requestParams.get("resultCode");
        String vnpay_status = requestParams.get("vnp_TransactionStatus");

        if (isSuccess(momo_status, MOMO_SUCCESS_STATUS)) {
            return handleSuccessPayment(requestParams, PaymentMethod.MOMO, "orderId", "amount");
        } else if (isCanceled(momo_status, MOMO_SUCCESS_STATUS)) {
            return handleCanceledPayment(requestParams, PaymentMethod.MOMO, "orderId");
        }

        if (isSuccess(vnpay_status, VNPAY_SUCCESS_STATUS)) {
            return handleSuccessPayment(requestParams, PaymentMethod.VN_PAY, "vnp_OrderInfo", "vnp_Amount");
        } else if (isCanceled(vnpay_status, VNPAY_SUCCESS_STATUS)) {
            return handleCanceledPayment(requestParams, PaymentMethod.VN_PAY, "vnp_OrderInfo");
        }

        return new GlobalResponse<>(Status.ERROR, "Thanh toán thất bại");
    }

    private com.microservice.ecommerce.model.dto.response.PaymentResponse savePayment(PaymentRequest request, UUID paymentId){
        Payment payment = paymentMapper.toPayment(request);
        payment.setId(paymentId);

        payment = paymentRepository.save(payment);

        return paymentMapper.toPaymentResponse(payment);
    }

    private GlobalResponse<String> processPaymentSuccess(String orderId, PaymentMethod method, Long amount) {
        OrderResponse response = paymentClient.findOrderById(UUID.fromString(orderId)).getBody().data();

        PaymentConfirmation confirmation = new PaymentConfirmation(
                "hle646698@gmail.com",
                "Le Hong Anh",
                amount,
                response.reference()
        );

        paymentProducer.sendPaymentConfirmation(confirmation);
        paymentProducer.sendOrderCallBack(new PaymentEvent(
                UUID.fromString(orderId),
                method,
                "PAID"
        ));

        return new GlobalResponse<>(Status.SUCCESS, "Thanh toán thành công");
    }

    /**
     * Kiểm tra trạng thái thanh toán thành công
     */
    private boolean isSuccess(String status, String successCode) {
        return status != null && status.equals(successCode);
    }

    /**
     * Kiểm tra trạng thái thanh toán thất bại
     */
    private boolean isCanceled(String status, String successCode) {
        return status != null && !status.equals(successCode);
    }

    /**
     * Xử lý thanh toán thành công
     */
    private GlobalResponse<String> handleSuccessPayment(Map<String, String> requestParams, PaymentMethod method, String orderIdKey, String amountKey) {
        String orderId = requestParams.get(orderIdKey);
        String amountStr = requestParams.get(amountKey);

        if (orderId == null || amountStr == null) {
            return new GlobalResponse<>(Status.ERROR, "Thiếu thông tin thanh toán");
        }

        Long amount = Long.parseLong(amountStr);
        return processPaymentSuccess(orderId, method, amount);
    }

    private GlobalResponse<String> handleCanceledPayment(Map<String, String> requestParams, PaymentMethod method, String orderIdKey) {
        String orderId = requestParams.get(orderIdKey);

        if (orderId == null) {
            return new GlobalResponse<>(Status.ERROR, "Thiếu thông tin đơn hàng");
        }

        paymentProducer.sendOrderCallBack(new PaymentEvent(
                UUID.fromString(orderId),
                method,
                "CANCELED"
        ));

        return new GlobalResponse<>(Status.ERROR, "Thanh toán thất bại");
    }
}
