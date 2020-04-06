package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Size {
    private String _id,arName,enName,size_id;
    private Double price;

    public Size(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            _id=jsonObject.getString("_id");
            arName=jsonObject.getString("arName");
            enName=jsonObject.getString("enName");
            size_id=jsonObject.getString("size_id");
            price=jsonObject.getDouble("price");



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getSize_id() {
        return size_id;
    }

    public void setSize_id(String size_id) {
        this.size_id = size_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
