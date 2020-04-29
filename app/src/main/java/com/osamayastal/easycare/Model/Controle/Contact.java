package com.osamayastal.easycare.Model.Controle;

import android.content.Context;
import android.util.Log;

import com.osamayastal.easycare.Model.Const.User_info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String message;
    private int status_code;
    private List<com.osamayastal.easycare.Model.Classes.Contact> items;
private Boolean status;
    public Contact(JSONObject jsonObject, Context mcontext) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            status=jsonObject.getBoolean("status");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                items.add(new com.osamayastal.easycare.Model.Classes.Contact(jsonArray.getJSONObject(i)));

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setItems(List<com.osamayastal.easycare.Model.Classes.Contact> items) {
        this.items = items;
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

    public List<com.osamayastal.easycare.Model.Classes.Contact> getItems() {
        return items;
    }
}
