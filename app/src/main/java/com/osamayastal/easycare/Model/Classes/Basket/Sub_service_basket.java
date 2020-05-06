package com.osamayastal.easycare.Model.Classes.Basket;

import com.osamayastal.easycare.Model.Classes.Size;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sub_service_basket implements Serializable {
    private String cart_id,car_name,providerSubCategory_id;
    private List<SubCategory_basket> subCategory_basketList;
private Size size;
    public Sub_service_basket(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            providerSubCategory_id=jsonObject.getString("provider_sub_category_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            cart_id=jsonObject.getString("cart_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            car_name=jsonObject.getString("car_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        subCategory_basketList=new ArrayList<>();
        try {

            size=new Size(jsonObject.getJSONObject("size"));

            JSONArray jsonArray=jsonObject.getJSONArray("SubCategory");
            for (int i=0;i<jsonArray.length();i++){
                subCategory_basketList.add(new SubCategory_basket(jsonArray.getJSONObject(i)));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCart_id() {
        return cart_id;
    }

    public String getProviderSubCategory_id() {
        return providerSubCategory_id;
    }

    public void setProviderSubCategory_id(String providerSubCategory_id) {
        this.providerSubCategory_id = providerSubCategory_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public List<SubCategory_basket> getSubCategory_basketList() {
        return subCategory_basketList;
    }

    public void setSubCategory_basketList(List<SubCategory_basket> subCategory_basketList) {
        this.subCategory_basketList = subCategory_basketList;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
