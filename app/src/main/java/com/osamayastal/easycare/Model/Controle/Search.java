package com.osamayastal.easycare.Model.Controle;

import android.content.Context;
import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Pagenation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private String message;
    private int status_code;
    private Pagenation pagenation;
    private List<com.osamayastal.easycare.Model.Classes.Search> items;

    public Search(JSONObject jsonObject, Context context) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");
            pagenation=new Pagenation(jsonObject.getJSONObject("pagenation"));
            JSONArray jsonArray2=jsonObject.getJSONArray("items");


            for(int i=0;i<jsonArray2.length();i++){
                items.add(new com.osamayastal.easycare.Model.Classes.Search(jsonArray2.getJSONObject(i),context));
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

    public Pagenation getPagenation() {
        return pagenation;
    }

    public void setPagenation(Pagenation pagenation) {
        this.pagenation = pagenation;
    }

    public List<com.osamayastal.easycare.Model.Classes.Search> getItems() {
        return items;
    }

    public void setItems(List<com.osamayastal.easycare.Model.Classes.Search> items) {
        this.items = items;
    }
}
