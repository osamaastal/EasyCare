package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Search {
    private String _id,createAt,name,email,address,phone_number,password,image,fcmToken;
    private Categorie category_id;
    private City city_id;
    private boolean isBlock;
    private double lat,lng,profitPercentage,orderPercentage;
    private float rate;

    public Search(JSONObject provider) {
        if (provider==null){
            return;
        }
        try {
            _id=provider.getString("_id");
            createAt=provider.getString("createAt");
            name=provider.getString("name");
            email=provider.getString("email");
            address=provider.getString("address");
            phone_number=provider.getString("phone_number");
            password=provider.getString("password");
            category_id=new Categorie(provider.getJSONObject("category_id"));
            city_id=new City(provider.getJSONObject("city_id"));
            image=provider.getString("image");
            fcmToken=provider.getString("fcmToken");
            rate=provider.getInt("rate");

            isBlock=provider.getBoolean("isBlock");

            lat=provider.getDouble("lat");
            lng=provider.getDouble("lng");
            profitPercentage=provider.getDouble("profitPercentage");
            orderPercentage=provider.getDouble("orderPercentage");
        } catch (JSONException e) {
            e.printStackTrace();
        }



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
