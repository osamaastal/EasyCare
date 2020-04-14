package com.osamayastal.easycare.Model.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Provider {
    private String _id,createAt,name,email,address,phone_number,password,category_id,image,favorite_id;
    private boolean isBlock;
    private double lat,lng,profitPercentage,orderPercentage;
private float rate;
private List<String> images;
    public Provider(JSONObject provider) {
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
            category_id=provider.getString("category_id");
            favorite_id=provider.getString("favorite_id");

            rate=provider.getInt("rate");

            isBlock=provider.getBoolean("isBlock");

            lat=provider.getDouble("lat");
            lng=provider.getDouble("lng");
            profitPercentage=provider.getDouble("profitPercentage");
            orderPercentage=provider.getDouble("orderPercentage");
            image=provider.getString("image");
            password=provider.getString("password");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        images=new ArrayList<>();
        try {
            JSONArray jsonArray=provider.getJSONArray("images");
            for (int i=0;i<jsonArray.length();i++){
                images.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
public Search toProvider(){
    Search provider=new Search(null);
    provider.set_id(_id);
    provider.setName(name);
    provider.setAddress(address);
    provider.setEmail(email);
    provider.setImage(image);
    provider.setLat(lat);
    provider.setLng(lng);
    provider.setRate(rate);
    provider.setPhone_number(phone_number);
    provider.setFavorite_id(favorite_id);
    provider.setImages(images);
    return provider;
}

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public String getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(String favorite_id) {
        this.favorite_id = favorite_id;
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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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
