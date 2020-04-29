package com.osamayastal.easycare.Model.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private String _id,createAt,name,email,address,phone_number,password,image,fcmToken,favorite_id;
    private Categorie category_id;
    private City city_id;
    private boolean isBlock;
    private double lat,lng,profitPercentage,orderPercentage;
    private float rate;
    private List<String> images;


    public Search(JSONObject provider) {
        if (provider==null){
            return;
        }
        try {
            _id=provider.getString("_id");
            createAt=provider.getString("createAt");



            profitPercentage=provider.getDouble("profitPercentage");
            orderPercentage=provider.getDouble("orderPercentage");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            city_id=new City(provider.getJSONObject("city_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            fcmToken=provider.getString("fcmToken");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            name=provider.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            category_id=new Categorie(provider.getJSONObject("type_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            image=provider.getString("image");
            if (!image.contains("https")){
                image=image.replace("http", "https");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            email=provider.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            address=provider.getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            phone_number=provider.getString("phone_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            password=provider.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try{
            favorite_id=provider.getString("favorite_id");
            Log.d("favorite_id ", favorite_id.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            rate=provider.getInt("rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            isBlock=provider.getBoolean("isBlock");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            lat=provider.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            lng=provider.getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        images=new ArrayList<>();
        try {
            JSONArray jsonArray=provider.getJSONArray("images");
            for (int i=0;i<jsonArray.length();i++){
                images.add(jsonArray.getString(i).replace("http", "https"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(String favorite_id) {
        this.favorite_id = favorite_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Categorie getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Categorie category_id) {
        this.category_id = category_id;
    }

    public City getCity_id() {
        return city_id;
    }

    public void setCity_id(City city_id) {
        this.city_id = city_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public double getOrderPercentage() {
        return orderPercentage;
    }

    public void setOrderPercentage(double orderPercentage) {
        this.orderPercentage = orderPercentage;
    }
}
