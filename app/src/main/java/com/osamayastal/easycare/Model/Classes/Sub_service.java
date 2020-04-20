package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Sub_service {
    private String _id,nameAr,nameEn,image,category_id,provider_subCategory_id;
    private boolean isActive;
private Double price;
    public Sub_service(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            _id=jsonObject.getString("_id");
            nameAr=jsonObject.getString("nameAr");
            nameEn=jsonObject.getString("nameEn");
            category_id=jsonObject.getString("category_id");
            provider_subCategory_id=jsonObject.getString("provider_subCategory_id");
            image=jsonObject.getString("image").replace("http", "https");
            price=jsonObject.getDouble("price");

            isActive=jsonObject.getBoolean("isActive");



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getProvider_subCategory_id() {
        return provider_subCategory_id;
    }

    public void setProvider_subCategory_id(String provider_subCategory_id) {
        this.provider_subCategory_id = provider_subCategory_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
