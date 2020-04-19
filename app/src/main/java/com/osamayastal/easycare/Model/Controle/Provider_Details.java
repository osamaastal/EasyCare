package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider.ProviderSetting;
import com.osamayastal.easycare.Model.Classes.Search;
import com.osamayastal.easycare.Model.Classes.Sub_servic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Provider_Details {
    private String message;
    private int status_code;
    private Boolean status;
    private com.osamayastal.easycare.Model.Classes.Search providerDetails;
    private List<Product> productList;
    private List<Categorie> categorieList;
    private List<Sub_servic> servicList;
    private List<ProviderSetting> providerSettingList;

    public Provider_Details(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        servicList=new ArrayList<>();
        categorieList=new ArrayList<>();
        productList=new ArrayList<>();
        providerSettingList=new ArrayList<>();

        try {
            message=jsonObject.getString("message");
            status=jsonObject.getBoolean("status");
            Log.d("items",jsonObject.getJSONObject("items").toString());
            status_code=jsonObject.getInt("status_code");
            JSONObject items=jsonObject.getJSONObject("items");
            providerDetails= new Search(items); //.getJSONObject("providerDetails")
            JSONArray jsonArray=items.getJSONArray("subServicesList");
            JSONArray jsonArray1=items.getJSONArray("providerProduct");
            JSONArray jsonArray2=items.getJSONArray("providerSetting");
            JSONArray jsonArray3=items.getJSONArray("category_id");
            for (int i=0;i<jsonArray.length();i++){
                servicList.add(new Sub_servic(jsonArray.getJSONObject(i)));
            }
            for (int i=0;i<jsonArray1.length();i++){
                productList.add(new Product(jsonArray1.getJSONObject(i)));
            }
            for (int i=0;i<jsonArray2.length();i++){
                providerSettingList.add(new ProviderSetting(jsonArray2.getJSONObject(i)));
            }
            for (int i=0;i<jsonArray3.length();i++){
                categorieList.add(new Categorie(jsonArray3.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<ProviderSetting> getProviderSettingList() {
        return providerSettingList;
    }

    public void setProviderSettingList(List<ProviderSetting> providerSettingList) {
        this.providerSettingList = providerSettingList;
    }

    public List<Categorie> getCategorieList() {
        return categorieList;
    }

    public void setCategorieList(List<Categorie> categorieList) {
        this.categorieList = categorieList;
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

    public Search getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(Search providerDetails) {
        this.providerDetails = providerDetails;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Sub_servic> getServicList() {
        return servicList;
    }

    public void setServicList(List<Sub_servic> servicList) {
        this.servicList = servicList;
    }
}
