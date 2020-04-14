package com.osamayastal.easycare.Model.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sub_servic {
    private String _id,provider_id,category_id;
    private boolean isActive;
    private Double price;
    private Sub_categorie subCategory_id;
    private List<Size> sizeList;
    private List<Sub_servic> car_list;
private String car_name;
    public Sub_servic(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        sizeList=new ArrayList<>();
        try {
            _id=jsonObject.getString("_id");
            provider_id=jsonObject.getString("provider_id");
            price=jsonObject.getDouble("price");
            category_id=jsonObject.getString("category_id");
            subCategory_id=new Sub_categorie(jsonObject.getJSONObject("subCategory_id"));
            isActive=jsonObject.getBoolean("isActive");
            JSONArray jsonArray=jsonObject.getJSONArray("sizes");
            for (int i=0;i<jsonArray.length();i++){
                sizeList.add(new Size(jsonArray.getJSONObject(i)));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public List<Sub_servic> getCar_list() {
        return car_list;
    }

    public void setCar_list(List<Sub_servic> car_list) {
        this.car_list = car_list;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Sub_categorie getSubCategory_id() {
        return subCategory_id;
    }

    public void setSubCategory_id(Sub_categorie subCategory_id) {
        this.subCategory_id = subCategory_id;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
    }
}
