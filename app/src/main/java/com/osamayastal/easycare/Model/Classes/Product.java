package com.osamayastal.easycare.Model.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String _id,name,description,image,by_user_id,createat;
    private  Double price,discountPrice;
    private int upFront;
    private boolean isUpFront,status;
    private List<String> images;

    public Product(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        images=new ArrayList<>();
        try {
            _id=jsonObject.getString("_id");
            name=jsonObject.getString("name");
            description=jsonObject.getString("description");
            image=jsonObject.getString("image");
            by_user_id=jsonObject.getString("by_user_id");
            by_user_id=jsonObject.getString("by_user_id");
            createat=jsonObject.getString("createat");

            isUpFront=jsonObject.getBoolean("isUpFront");
            status=jsonObject.getBoolean("status");
            upFront=jsonObject.getInt("upFront");
            price=jsonObject.getDouble("price");
            discountPrice=jsonObject.getDouble("discountPrice");

            JSONArray jsonArray=jsonObject.getJSONArray("images");
            for (int i=0;i<jsonArray.length();i++){
                images.add(jsonArray.getString(i));
            }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBy_user_id() {
        return by_user_id;
    }

    public void setBy_user_id(String by_user_id) {
        this.by_user_id = by_user_id;
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getUpFront() {
        return upFront;
    }

    public void setUpFront(int upFront) {
        this.upFront = upFront;
    }

    public boolean isUpFront() {
        return isUpFront;
    }

    public void setUpFront(boolean upFront) {
        isUpFront = upFront;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
