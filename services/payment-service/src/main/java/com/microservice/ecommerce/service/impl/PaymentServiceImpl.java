package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.config.ConfigVNPay;
import com.microservice.ecommerce.config.MoMoEnvironment;
import com.microservice.ecommerce.constant.RequestType;
import com.microservice.ecommerce.model.dto.request.PaymentVNPayRequest;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.momo.PaymentRequest;
import com.microservice.ecommerce.model.momo.PaymentResponse;
import com.microservice.ecommerce.service.PaymentService;
import com.microservice.ecommerce.util.LogUtils;
import com.microservice.ecommerce.util.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    @Override
    public GlobalResponse<PaymentResponse> saveMoMoPayment(PaymentRequest request) throws Exception {
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        Long transId = 2L;
        long amount = 50000;

        String partnerClientId = "partnerClientId";
        String orderInfo = "Pay With MoMo";
        String returnURL = "https://google.com.vn";
        String notifyURL = "https://google.com.vn";
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
        if (captureWalletMoMoResponse != null && captureWalletMoMoResponse.getResultCode() == 0) {
            log.info("Thanh toán thành công với MoMo. Mã giao dịch: {}", captureWalletMoMoResponse.getTransId());
            return new GlobalResponse<>(Status.SUCCESS, captureWalletMoMoResponse);
        } else {
            log.error("Thanh toán thất bại. Mã lỗi: {}", captureWalletMoMoResponse != null ? captureWalletMoMoResponse.getResultCode() : "null");
            return new GlobalResponse<>(Status.ERROR, captureWalletMoMoResponse);
        }
    }

    @Override
    public GlobalResponse<Map<String, String>> saveVNPayPayment(PaymentVNPayRequest request,
                                                                HttpServletRequest httpServletRequest) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = request.amount() * 100;
        String bankCode = request.bankCode();

        String vnp_TxnRef = ConfigVNPay.getRandomNumber(8);
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
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.language();
        vnp_Params.put("vnp_Locale", (locate != null && !locate.isEmpty()) ? locate : "vn");

        vnp_Params.put("vnp_ReturnUrl", ConfigVNPay.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append('&');
                hashData.append('&');
            }
        }

        if (query.length() > 0) {
            query.setLength(query.length() - 1);
            hashData.setLength(hashData.length() - 1);
        }

        String vnp_SecureHash = ConfigVNPay.hmacSHA512(ConfigVNPay.vnp_SecretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = ConfigVNPay.vnp_PayUrl + "?" + query;

        Map<String, String> response = new HashMap<>();
        response.put("code", "00");
        response.put("message", "success");
        response.put("data", paymentUrl);

        return new GlobalResponse<>(
                Status.SUCCESS,
                response
        );
    }
}
