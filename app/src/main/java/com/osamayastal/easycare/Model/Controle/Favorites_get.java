package com.osamayastal.easycare.Model.Controle;

import com.osamayastal.easycare.Model.Classes.Favorite;
import com.osamayastal.easycare.Model.Classes.Favorite_get;
import com.osamayastal.easycare.Model.Classes.Pagenation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favorites_get {
    private String message;
    private int status_code;
    private Pagenation pagenation;
    private List<Favorite_get> items;

    public Favorites_get(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {


            status_code=jsonObject.getInt("status_code");
            pagenation=new Pagenation(jsonObject.getJSONObject("pagenation"));
            JSONArray jsonArray2=jsonObject.getJSONArray("items");


            for(int i=0;i<jsonArray2.length();i++){
                items.add(new Favorite_get(jsonArray2.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            message=jsonObject.getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Favorite_get> getItems() {
        return items;
    }

    public void setItems(List<Favorite_get> items) {
        this.items = items;
    }
}
