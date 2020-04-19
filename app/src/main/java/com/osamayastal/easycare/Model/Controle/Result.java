package com.osamayastal.easycare.Model.Controle;

import com.osamayastal.easycare.Model.Classes.User;

import org.json.JSONObject;

public class Result {

    private String messageAr,messageEn;
    private boolean status;
    private int statusCode;
    public Result(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        messageAr = jsonObject.optString("messageAr");
        messageEn = jsonObject.optString("messageEn");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");


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
