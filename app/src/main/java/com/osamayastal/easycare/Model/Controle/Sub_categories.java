package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Pagenation;
import com.osamayastal.easycare.Model.Classes.Sub_categorie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sub_categories {
    private String message;
    private int status_code;
    private Sub_categorie items;

    public Sub_categories(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        try {
            message=jsonObject.getString("message");
            status_code=jsonObject.getInt("status_code");


            items=new Sub_categorie(jsonObject.getJSONObject("items"));
//            sub_categorie.Sub_categorie_details(jsonObject.getJSONObject("items"));

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

    public Sub_categorie getItems() {
        return items;
    }

    public void setItems(Sub_categorie items) {
        this.items = items;
    }
}
