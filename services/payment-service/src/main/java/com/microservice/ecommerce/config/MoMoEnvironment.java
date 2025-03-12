package com.microservice.ecommerce.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    11/03/2025 at 1:11 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
@Getter
@Setter
@NoArgsConstructor
public class MoMoEnvironment {

    private PartnerInfo partnerInfo;
    private MoMoEndpoint endpoints;
    private String target;

    @Autowired
    public MoMoEnvironment(MoMoEndpoint endpoints, PartnerInfo partnerInfo, @Value("${DEV_MOMO_ENV}") String target) {
        this.endpoints = endpoints;
        this.partnerInfo = partnerInfo;
        this.target = target;
    }

    /**
     *
     * @param target String target name ("dev" or "prod")
     * @return
     * @throws IllegalArgumentException
     */
    public static MoMoEnvironment selectEnv(String target) throws IllegalArgumentException {
        switch(target) {
            case "dev":
                return selectEnv(EnvTarget.DEV);
            case "prod":
                return selectEnv(EnvTarget.PROD);
            default:
                throw new IllegalArgumentException("MoMo doesnt provide other environment: dev and prod");
        }
    }

    /**
     * Select appropriate environment to run processes
     * Create and modify your environment.properties file appropriately
     *
     * @param target EnvTarget (choose DEV or PROD)
     * @return
     */
    public static MoMoEnvironment selectEnv(EnvTarget target) {
        try (InputStream input = MoMoEnvironment.class.getClassLoader().getResourceAsStream("environment.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            switch (target) {
                case DEV:
                    MoMoEndpoint devEndpoint = new MoMoEndpoint(prop.getProperty("DEV_MOMO_ENDPOINT"),
                            prop.getProperty("CREATE_URL"),
                            prop.getProperty("REFUND_URL"),
                            prop.getProperty("QUERY_URL"),
                            prop.getProperty("CONFIRM_URL"),
                            prop.getProperty("TOKEN_PAY_URL"),
                            prop.getProperty("TOKEN_BIND_URL"),
                            prop.getProperty("TOKEN_INQUIRY_URL"),
                            prop.getProperty("TOKEN_DELETE_URL"));
                    PartnerInfo devInfo = new PartnerInfo(prop.getProperty("DEV_ACCESS_KEY"), prop.getProperty("DEV_PARTNER_CODE"), prop.getProperty("DEV_SECRET_KEY"));
                    MoMoEnvironment dev = new MoMoEnvironment(devEndpoint, devInfo, target.getTarget());
                    return dev;
                case PROD:
                    MoMoEndpoint prodEndpoint = new MoMoEndpoint(prop.getProperty("PROD_MOMO_ENDPOINT"),
                            prop.getProperty("CREATE_URL"),
                            prop.getProperty("REFUND_URL"),
                            prop.getProperty("QUERY_URL"),
                            prop.getProperty("CONFIRM_URL"),
                            prop.getProperty("TOKEN_PAY_URL"),
                            prop.getProperty("TOKEN_BIND_URL"),
                            prop.getProperty("TOKEN_INQUIRY_URL"),
                            prop.getProperty("TOKEN_DELETE_URL"));                    PartnerInfo prodInfo = new PartnerInfo(prop.getProperty("PROD_PARTNER_CODE"), prop.getProperty("PROD_ACCESS_KEY"), prop.getProperty("PROD_SECRET_KEY"));
                    MoMoEnvironment prod = new MoMoEnvironment(prodEndpoint, prodInfo, target.getTarget());
                    return prod;
                default:
                    throw new IllegalArgumentException("MoMo doesnt provide other environment: dev and prod");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @AllArgsConstructor
    public enum EnvTarget {
        DEV("development"), PROD("production");

        @Getter
        private String target;
    }

    public enum ProcessType {
        PAY_GATE, APP_IN_APP, PAY_POS, PAY_QUERY_STATUS, PAY_REFUND, PAY_CONFIRM;

        public String getSubDir(Properties prop) {
            return prop.getProperty(this.toString());
        }
    }


}