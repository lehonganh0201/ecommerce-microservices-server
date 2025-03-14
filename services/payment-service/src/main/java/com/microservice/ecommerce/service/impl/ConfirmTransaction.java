package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.config.MoMoEnvironment;
import com.microservice.ecommerce.constant.ConfirmRequestType;
import com.microservice.ecommerce.constant.Language;
import com.microservice.ecommerce.constant.Parameter;
import com.microservice.ecommerce.exception.MoMoException;
import com.microservice.ecommerce.model.momo.ConfirmRequest;
import com.microservice.ecommerce.model.momo.ConfirmResponse;
import com.microservice.ecommerce.model.momo.HttpResponse;
import com.microservice.ecommerce.service.AbstractProcess;
import com.microservice.ecommerce.util.Encoder;
import com.microservice.ecommerce.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 5:54 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
public class ConfirmTransaction extends AbstractProcess<ConfirmRequest, ConfirmResponse> {

    @Autowired
    public ConfirmTransaction(MoMoEnvironment environment) {
        super(environment);
    }

    public static ConfirmResponse process(MoMoEnvironment env, String orderId, String requestId, String amount, ConfirmRequestType requestType, String description) throws Exception {
        try {
            ConfirmTransaction m2Processor = new ConfirmTransaction(env);

            ConfirmRequest request = m2Processor.createConfirmTransactionRequest(orderId, requestId, amount, requestType, description);
            ConfirmResponse response = m2Processor.execute(request);

            return response;
        } catch (Exception exception) {
            LogUtils.error("[ConfirmTransactionProcess] "+ exception);
        }
        return null;
    }

    @Override
    public ConfirmResponse execute(ConfirmRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, ConfirmRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getEndpoints().getConfirmUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[ConfirmTransactionResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            ConfirmResponse confirmResponse = getGson().fromJson(response.getData(), ConfirmResponse.class);
            String responserawData =   Parameter.ORDER_ID + "=" + confirmResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + confirmResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + confirmResponse.getResultCode();

            LogUtils.info("[ConfirmTransactionResponse] rawData: " + responserawData);

            return confirmResponse;

        } catch (Exception exception) {
            LogUtils.error("[ConfirmTransactionResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params confirm MoMo Request");
        }
    }

    public ConfirmRequest createConfirmTransactionRequest(String orderId, String requestId, String amount, ConfirmRequestType requestType, String description) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.AMOUNT).append("=").append(amount).append("&")
                    .append(Parameter.DESCRIPTION).append("=").append(description).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.REQUEST_TYPE).append("=").append(requestType.getConfirmRequestType())
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[ConfirmRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new ConfirmRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, Long.valueOf(amount), "", ConfirmRequestType.CAPTURE, signRequest);
        } catch (Exception e) {
            LogUtils.error("[ConfirmResponse] "+ e);
        }

        return null;
    }
}