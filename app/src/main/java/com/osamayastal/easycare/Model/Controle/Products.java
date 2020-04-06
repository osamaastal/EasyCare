package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Pagenation;
import com.osamayastal.easycare.Model.Classes.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private String message;
    private int status_code;
    private Pagenation pagenation;
    private List<Product> items;

    public Products(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");
            pagenation=new Pagenation(jsonObject.getJSONObject("pagenation"));
            JSONArray jsonArray2=jsonObject.getJSONArray("items");


            for(int i=0;i<jsonArray2.length();i++){
                items.add(new com.osamayastal.easycare.Model.Classes.Product(jsonArray2.getJSONObject(i)));
            }

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

    public Pagenation getPagenation() {
        return pagenation;
    }

    public void setPagenation(Pagenation pagenation) {
        this.pagenation = pagenation;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
