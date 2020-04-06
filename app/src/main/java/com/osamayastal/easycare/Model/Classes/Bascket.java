package com.osamayastal.easycare.Model.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bascket {
    private String provider;

    private List<Product> items;

    public Bascket(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {
            provider=jsonObject.getString("provider");

            JSONArray jsonArray2=jsonObject.getJSONArray("productsOrservice");


            for(int i=0;i<jsonArray2.length();i++){
                items.add(new Product(jsonArray2.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
