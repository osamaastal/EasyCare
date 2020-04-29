package com.osamayastal.easycare.Model.Classes.Basket;

import com.osamayastal.easycare.Model.Classes.Basket.Sub_service_basket;
import com.osamayastal.easycare.Model.Classes.Categorie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class categories_basket implements Serializable {
    private Categorie category_id;
    private List<Sub_service_basket> sub_services;
    public categories_basket(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        try {
            category_id=new Categorie(jsonObject.getJSONObject("category_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sub_services=new ArrayList<>();
        try {
            JSONArray jsonArray=jsonObject.getJSONArray("sub_services");
            for (int i=0;i<jsonArray.length();i++){
                sub_services.add(new Sub_service_basket(jsonArray.getJSONObject(i)));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Categorie getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Categorie category_id) {
        this.category_id = category_id;
    }

    public List<Sub_service_basket> getSub_services() {
        return sub_services;
    }

    public void setSub_services(List<Sub_service_basket> sub_services) {
        this.sub_services = sub_services;
    }
}
