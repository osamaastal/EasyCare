package com.osamayastal.easycare.Model.Controle;

import android.content.Context;
import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Const.User_info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class City {
    private String message;
    private int status_code;
    private List<com.osamayastal.easycare.Model.Classes.City> items;
    private List<String> cityList;

    public City(JSONObject jsonObject, Context mcontext) {
        if (jsonObject==null){
            return;
        }

        items=new ArrayList<>();
        cityList=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");
            JSONArray jsonArray2=jsonObject.getJSONArray("items");


            for(int i=0;i<jsonArray2.length();i++){
                items.add(new com.osamayastal.easycare.Model.Classes.City(jsonArray2.getJSONObject(i)));
                if (new User_info(mcontext).getLanguage().equals("en")){
                    cityList.add(new com.osamayastal.easycare.Model.Classes.City(jsonArray2.getJSONObject(i)).getEnName());
                }else {
                    cityList.add(new com.osamayastal.easycare.Model.Classes.City(jsonArray2.getJSONObject(i)).getArName());
                }
           }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
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

    public List<com.osamayastal.easycare.Model.Classes.City> getItems() {
        return items;
    }

    public void setItems(List<com.osamayastal.easycare.Model.Classes.City> items) {
        this.items = items;
    }
}
