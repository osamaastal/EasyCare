package com.osamayastal.easycare.Model.Controle;

import com.osamayastal.easycare.Model.Classes.Car_servece;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Basket_prices {

    private String message;
    private boolean status;
    private int statusCode;
    private  Double tax=0.0,total_price=0.0,total_discount=0.0,final_total=0.0;

    public Basket_prices(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        message = jsonObject.optString("message");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
        tax = jsonObject.optDouble("tax");
        total_price = jsonObject.optDouble("total_price");
        total_discount = jsonObject.optDouble("total_discount");
        final_total = jsonObject.optDouble("final_total");


    }

    public JSONObject to_JSON(List<String> provider_id)  {
        JSONObject jsonObject=new JSONObject();

        JSONArray itemsJsonArray = new JSONArray();
        for(String itemsElement : provider_id){
            itemsJsonArray.put(itemsElement);
        }
        try {
            jsonObject.put("provider_id", itemsJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public Double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(Double total_discount) {
        this.total_discount = total_discount;
    }

    public Double getFinal_total() {
        return final_total;
    }

    public void setFinal_total(Double final_total) {
        this.final_total = final_total;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }



}
