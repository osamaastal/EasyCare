package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.StaticPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Wellcom {
    private String message;
    private int status_code;
    private boolean status;
    private List<com.osamayastal.easycare.Model.Classes.Wellcom> items;

    public Wellcom(JSONObject jsonObject) {
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
                items.add(new com.osamayastal.easycare.Model.Classes.Wellcom(jsonArray.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<com.osamayastal.easycare.Model.Classes.Wellcom> getItems() {
        return items;
    }

    public void setItems(List<com.osamayastal.easycare.Model.Classes.Wellcom> items) {
        this.items = items;
    }
}
