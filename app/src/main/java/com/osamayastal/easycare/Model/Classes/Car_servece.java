package com.osamayastal.easycare.Model.Classes;

import com.osamayastal.easycare.Model.Classes.Basket.Service_for_basket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Car_servece {

    private String size_id="",providerSubCategory_id="",car_name,category_id;
private Double total=0.0;
Service_for_basket service_for_basket;
    public Car_servece() {
    }
public JSONObject Order_JSON(String provider_id,List<Car_servece> car_serveces)  {
        JSONObject jsonObject=new JSONObject();

    if(car_serveces != null && car_serveces.size() > 0){
        JSONArray itemsJsonArray = new JSONArray();
        for(Car_servece itemsElement : car_serveces){
            try {
                itemsJsonArray.put(itemsElement.toJsonObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jsonObject.put("services", itemsJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    try {
        jsonObject.put("provider_id",provider_id);
    } catch (JSONException e) {
        e.printStackTrace();
    }

    return jsonObject;
}

    private JSONObject toJsonObject() throws JSONException {
        providerSubCategory_id=null;
        for (Sub_service sub:service_for_basket.getSub_serviceList()
             ) {
            if (providerSubCategory_id==null){
                providerSubCategory_id=sub.getProvider_subCategory_id();
            }else {
                providerSubCategory_id=providerSubCategory_id+","+sub.getProvider_subCategory_id();

            }
        }
        size_id=service_for_basket.getSize().getSize_id();

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("car_name",car_name);
        jsonObject.put("providerSubCategory_id",providerSubCategory_id);
        jsonObject.put("size_id",size_id);
        jsonObject.put("category_id",category_id);
return jsonObject;
    }

    public Service_for_basket getService_for_basket() {
        return service_for_basket;
    }

    public void setService_for_basket(Service_for_basket service_for_basket) {
        this.service_for_basket = service_for_basket;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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
