package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Classes.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Provider_Details {
    private String message;
    private int status_code;
    private Boolean status;
    private Provider providerDetails;
    private List<Product> Products;
    private List<Categorie> Services;

    public Provider_Details(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        Products=new ArrayList<>();
        Services=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            status=jsonObject.getBoolean("status");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");
            providerDetails=new Provider(jsonObject.getJSONObject("items").getJSONObject("providerDetails"));

            JSONArray jsonArray=jsonObject.getJSONObject("items").getJSONArray("Services");
            JSONArray jsonArray1=jsonObject.getJSONObject("items").getJSONArray("Products");


            for(int i=0;i<jsonArray.length();i++){
                Services.add(new Categorie(jsonArray.getJSONObject(i)));
            }

            for(int i=0;i<jsonArray1.length();i++){
                Products.add(new Product(jsonArray1.getJSONObject(i)));
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Provider getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(Provider providerDetails) {
        this.providerDetails = providerDetails;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public List<Categorie> getServices() {
        return Services;
    }

    public void setServices(List<Categorie> services) {
        Services = services;
    }
}
