package com.osamayastal.easycare.Model.Controle;

import org.json.JSONObject;

import java.io.Serializable;

public class Getpayment implements Serializable {

    private String  items,payment_order_id,order_no,successRedirectURL,errorRedirectURL;
    private Double amount;
    private String messageAr,messageEn;
    private boolean status;
    private int statusCode;
    public Getpayment(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        messageAr = jsonObject.optString("messageAr");
        messageEn = jsonObject.optString("messageEn");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
        items = jsonObject.optString("items");
        amount = jsonObject.optDouble("amount");
        payment_order_id = jsonObject.optString("payment_order_id");
        order_no = jsonObject.optString("order_no");
        successRedirectURL = jsonObject.optString("successRedirectURL");
        errorRedirectURL = jsonObject.optString("errorRedirectURL");


    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getPayment_order_id() {
        return payment_order_id;
    }

    public void setPayment_order_id(String payment_order_id) {
        this.payment_order_id = payment_order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSuccessRedirectURL() {
        return successRedirectURL;
    }

    public void setSuccessRedirectURL(String successRedirectURL) {
        this.successRedirectURL = successRedirectURL;
    }

    public String getErrorRedirectURL() {
        return errorRedirectURL;
    }

    public void setErrorRedirectURL(String errorRedirectURL) {
        this.errorRedirectURL = errorRedirectURL;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMessageAr() {
        return messageAr;
    }

    public void setMessageAr(String messageAr) {
        this.messageAr = messageAr;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
