package com.osamayastal.easycare.Model.Controle;

import com.osamayastal.easycare.Model.Classes.User;

import org.json.JSONException;
import org.json.JSONObject;


public class users {

    private User items;
    private String message;
    private boolean status;
    private int statusCode;

    public User getItems() {
        return items;
    }

    public void setItems(User items) {
        this.items = items;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
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

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public users(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        message = jsonObject.optString("message");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
        items = new User(jsonObject.optJSONObject("items"));

    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("items", items.toJsonObject());
            jsonObject.put("message", message);
            jsonObject.put("status", status);
            jsonObject.put("status_code", statusCode);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}