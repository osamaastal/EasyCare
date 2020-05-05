package com.osamayastal.easycare.Model.Controle;

import com.osamayastal.easycare.Model.Classes.User;

import org.json.JSONException;
import org.json.JSONObject;


public class users {

    private User items=null;
    private String messageAr,messageEn;
    private boolean status;
    private int statusCode;

    public User getItems() {
        return items;
    }

    public void setItems(User items) {
        this.items = items;
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

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public users(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        messageAr = jsonObject.optString("messageAr");
        messageEn = jsonObject.optString("messageEn");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
        items = new User(jsonObject.optJSONObject("items"));

    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */


}