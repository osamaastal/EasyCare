package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Classes.TopRatedProviders;
import com.osamayastal.easycare.Model.Classes.TopRequestedProviders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private String message;
    private int status_code;
    private List<TopRequestedProviders> topRequestedProviders;
    private List<TopRequestedProviders> topRatedProviders;
    private List<Categorie> categories;

    public Home(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        topRequestedProviders=new ArrayList<>();
        topRatedProviders=new ArrayList<>();
        categories=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");
            JSONArray jsonArray=jsonObject.getJSONArray("topRequestedProviders");
            JSONArray jsonArray1=jsonObject.getJSONArray("topRatedProviders");
            JSONArray jsonArray2=jsonObject.getJSONArray("categories");

            for(int i=0;i<jsonArray.length();i++){
               topRequestedProviders.add(new TopRequestedProviders(jsonArray.getJSONObject(i)));
            }

            for(int i=0;i<jsonArray1.length();i++){
                topRatedProviders.add(new TopRequestedProviders(jsonArray.getJSONObject(i)));
            }

            for(int i=0;i<jsonArray2.length();i++){
                categories.add(new Categorie(jsonArray2.getJSONObject(i)));
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

    public List<TopRequestedProviders> getTopRequestedProviders() {
        return topRequestedProviders;
    }

    public void setTopRequestedProviders(List<TopRequestedProviders> topRequestedProviders) {
        this.topRequestedProviders = topRequestedProviders;
    }

    public List<TopRequestedProviders> getTopRatedProviders() {
        return topRatedProviders;
    }

    public void setTopRatedProviders(List<TopRequestedProviders> topRatedProviders) {
        this.topRatedProviders = topRatedProviders;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
}
