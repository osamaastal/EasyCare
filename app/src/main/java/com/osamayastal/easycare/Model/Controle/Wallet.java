package com.osamayastal.easycare.Model.Controle;

import android.content.Context;
import android.util.Log;

import com.osamayastal.easycare.Model.Const.User_info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private String message;
    private int status_code;
    private List<com.osamayastal.easycare.Model.Classes.Wallet> items;
private Boolean status;
    public Wallet(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();

        try {
            message=jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            status=jsonObject.getBoolean("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            status_code=jsonObject.getInt("status_code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray= null;
        try {
            jsonArray = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try {
                items.add(new com.osamayastal.easycare.Model.Classes.Wallet(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<com.osamayastal.easycare.Model.Classes.Wallet> getItems() {
        return items;
    }

    public void setItems(List<com.osamayastal.easycare.Model.Classes.Wallet> items) {
        this.items = items;
    }
}
