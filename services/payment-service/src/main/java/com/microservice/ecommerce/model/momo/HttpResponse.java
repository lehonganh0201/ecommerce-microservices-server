package com.microservice.ecommerce.model.momo;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    12/03/2025 at 12:57 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


import okhttp3.Headers;

public class HttpResponse {

    private int status;
    private String data;
    private Headers headers;

    public HttpResponse(int status, String data, Headers headers) {
        this.status = status;
        this.data = data;
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public String toString() {
        return "HttpStatusCode:: " + this.status + ", ResponseBody:: " + this.data;
    }

}