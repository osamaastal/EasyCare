package com.osamayastal.easycare.Model.Controle;

import org.json.JSONObject;

public class Coupon {

    private String messageAr,messageEn;
    private boolean status;
    private int statusCode;
    private  Double tax=0.0,total_price=0.0,total_discount=0.0,final_total=0.0;

    public Coupon(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        messageAr = jsonObject.optString("messageAr");
        messageEn = jsonObject.optString("messageEn");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
        tax = jsonObject.optDouble("tax");
        total_price = jsonObject.optDouble("total_price");
        total_discount = jsonObject.optDouble("total_discount");
        final_total = jsonObject.optDouble("final_total");


    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public Double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(Double total_discount) {
        this.total_discount = total_discount;
    }

    public Double getFinal_total() {
        return final_total;
    }

    public void setFinal_total(Double final_total) {
        this.final_total = final_total;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
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

}
