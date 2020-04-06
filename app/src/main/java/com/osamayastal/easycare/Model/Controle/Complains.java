package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Complain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Complains {
    private String messageAr,messageEn;
    private int status_code;
    private List<Complain> items;

    public Complains(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {
            messageAr=jsonObject.getString("messageAr");
            messageEn=jsonObject.getString("messageEn");
            status_code=jsonObject.getInt("status_code");
            JSONArray jsonArray=jsonObject.getJSONArray("items");


            for(int i=0;i<jsonArray.length();i++){
                items.add(new Complain(jsonArray.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<Complain> getItems() {
        return items;
    }

    public void setItems(List<Complain> items) {
        this.items = items;
    }
}
