package com.microservice.ecommerce.config;

import com.microservice.ecommerce.util.VNPayUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Configuration
@PropertySource("classpath:environment.properties")
public class ConfigVNPay {

    @Value("${VNP_TMNCODE}")
    private String vnpTmnCode;

    @Value("${VNP_HASHSECRET}")
    private String secretKey;

    @Value("${VNP_PAYURL}")
    private String vnpPayUrl;

    @Value("${VNP_RETURNURL}")
    private String vnpReturnUrl;


    public static String vnp_TmnCode;
    public static String vnp_ReturnUrl;
    public static String vnp_PayUrl;
    public static String vnp_SecretKey;

    @PostConstruct
    public void init() {
        vnp_TmnCode = vnpTmnCode;
        vnp_SecretKey = secretKey;
        vnp_PayUrl = vnpPayUrl;
        vnp_ReturnUrl=vnpReturnUrl;
    }

    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }
}
