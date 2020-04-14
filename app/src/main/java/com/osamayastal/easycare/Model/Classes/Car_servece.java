package com.osamayastal.easycare.Model.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Car_servece {

    private String size_id,providerSubCategory_id,car_name,category_id;

    public Car_servece() {
    }

    public String getSize_id() {
        return size_id;
    }

    public void setSize_id(String size_id) {
        this.size_id = size_id;
    }

    public String getProviderSubCategory_id() {
        return providerSubCategory_id;
    }

    public void setProviderSubCategory_id(String providerSubCategory_id) {
        this.providerSubCategory_id = providerSubCategory_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
