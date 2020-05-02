package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Favorite;
import com.osamayastal.easycare.Model.Classes.Pagenation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favorites {
    private String messageEn,messageAr;
    private int status_code;
    private Pagenation pagenation;
    private List<com.osamayastal.easycare.Model.Classes.Favorite> items;
//////for post
    private String _id;
    public Favorites(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {


            status_code=jsonObject.getInt("status_code");
            pagenation=new Pagenation(jsonObject.getJSONObject("pagenation"));
            JSONArray jsonArray2=jsonObject.getJSONArray("items");


            for(int i=0;i<jsonArray2.length();i++){
                items.add(new com.osamayastal.easycare.Model.Classes.Favorite(jsonArray2.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            messageAr=jsonObject.getString("messageAr");
            messageEn=jsonObject.getString("messageEn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void Favorites_id(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        try {


            _id = jsonObject.getJSONObject("items").getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getMessageEn() {
        return messageEn;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    public String getMessageAr() {
        return messageAr;
    }

    public void setMessageAr(String messageAr) {
        this.messageAr = messageAr;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public Pagenation getPagenation() {
        return pagenation;
    }

    public void setPagenation(Pagenation pagenation) {
        this.pagenation = pagenation;
    }

    public List<Favorite> getItems() {
        return items;
    }

    public void setItems(List<Favorite> items) {
        this.items = items;
    }
}
