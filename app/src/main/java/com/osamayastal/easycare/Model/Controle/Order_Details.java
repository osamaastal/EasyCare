package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Basket.Bascket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Order_Details {
    private String message;
    private int status_code;
    private Boolean status;
    private List<com.osamayastal.easycare.Model.Classes.Basket.Bascket> items;
private Double total_price;
    public Order_Details(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            status=jsonObject.getBoolean("status");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");



            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                Bascket order=new Bascket();
                order.OrderDetails(jsonArray.getJSONObject(i));
                items.add(order);
            }

            total_price=jsonObject.getDouble("total_price");

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

    public List<com.osamayastal.easycare.Model.Classes.Basket.Bascket> getItems() {
        return items;
    }

    public void setItems(List<com.osamayastal.easycare.Model.Classes.Basket.Bascket> items) {
        this.items = items;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}