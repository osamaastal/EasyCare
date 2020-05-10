package com.osamayastal.easycare.Model.Classes.Provider;

import com.osamayastal.easycare.Model.Classes.Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Provider implements Serializable {
    private String _id,createAt,name,email,address,phone_number,password,category_id,image,favorite_id;
    private boolean isBlock,isUpfront;
    private double lat,lng,profitPercentage,orderPercentage,upfrontAmount;
private float rate;
private List<String> images;
    public Provider(JSONObject provider) {
        if (provider==null){
            return;
        }
        try {
            _id=provider.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            createAt=provider.getString("createAt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name=provider.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            email=provider.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            address=provider.getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            phone_number=provider.getString("phone_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            category_id=provider.getString("category_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            favorite_id=provider.getString("favorite_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            rate=provider.getInt("rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            isBlock=provider.getBoolean("isBlock");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            lat=provider.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lng=provider.getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            profitPercentage=provider.getDouble("profitPercentage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            orderPercentage=provider.getDouble("orderPercentage");
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
        try {
            password=provider.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            upfrontAmount=provider.getDouble("upfrontAmount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            isUpfront=provider.getBoolean("isUpfront");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        images=new ArrayList<>();
        try {
            JSONArray jsonArray=provider.getJSONArray("images");
            for (int i=0;i<jsonArray.length();i++){
                String img=jsonArray.getString(i);
                    if (!img.contains("https")){
                        img=img.replace("http", "https");
                    }
                images.add(img);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
public Search toProvider(){
    Search provider=new Search(null,null);
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

    public boolean isUpfront() {
        return isUpfront;
    }

    public void setUpfront(boolean upfront) {
        isUpfront = upfront;
    }

    public double getUpfrontAmount() {
        return upfrontAmount;
    }

    public void setUpfrontAmount(double upfrontAmount) {
        this.upfrontAmount = upfrontAmount;
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
