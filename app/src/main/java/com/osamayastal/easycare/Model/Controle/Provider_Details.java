package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Classes.ProviderSetting;
import com.osamayastal.easycare.Model.Classes.Provider_map;
import com.osamayastal.easycare.Model.Classes.Slider;
import com.osamayastal.easycare.Model.Classes.Sub_servic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Provider_Details {
    private String message;
    private int status_code;
    private Boolean status;
    private com.osamayastal.easycare.Model.Classes.Provider_Details providerDetails;


    public Provider_Details(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }


        try {
            message=jsonObject.getString("message");
            status=jsonObject.getBoolean("status");
            Log.d("items",jsonObject.getJSONObject("items").toString());
            status_code=jsonObject.getInt("status_code");
            try{
                providerDetails=new com.osamayastal.easycare.Model.Classes.Provider_Details(jsonObject.getJSONObject("items"));
            } catch (JSONException e) {
                e.printStackTrace();
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public com.osamayastal.easycare.Model.Classes.Provider_Details getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(com.osamayastal.easycare.Model.Classes.Provider_Details providerDetails) {
        this.providerDetails = providerDetails;
    }
}
