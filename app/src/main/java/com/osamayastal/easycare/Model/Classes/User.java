package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;


/************************* Mo’min J.Abusaada *************************/
//
//	point.java


public class User {

    private String id;
    private String password;
    private String gender;
    private String address;
    private String city;
    private String city_id;
    private String createAt;
    private String email;
    private String fullName;
    private String image;
    private boolean isBlock;
    private boolean isVerify;
    private Double lat;
    private Double lng;
    private String phoneNumber;
    private String token;
    private String verifyCode;
    private Long wallet = Long.valueOf(0);
    private boolean isEnableNotifications;
    private int favoritCount,orderCount;

    public int getFavoritCount() {
        return favoritCount;
    }

    public void setFavoritCount(int favoritCount) {
        this.favoritCount = favoritCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreateAt() {
        return this.createAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public boolean isIsBlock() {
        return this.isBlock;
    }

    public void setIsVerify(boolean isVerify) {
        this.isVerify = isVerify;
    }

    public boolean isIsVerify() {
        return this.isVerify;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Long getWallet() {
        return this.wallet;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public User() {
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public User(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        id = jsonObject.optString("_id");

        try {
            gender = jsonObject.optString("gender");
        } catch (Exception e) {
            e.printStackTrace();
        }
        password = jsonObject.optString("password");
        address = jsonObject.optString("address");
        createAt = jsonObject.optString("createAt");
        email = jsonObject.optString("email");
        fullName = jsonObject.optString("full_name");
        image=jsonObject.optString("image");
        if (!image.contains("https")){
            image=image.replace("http", "https");
        }
        isBlock = jsonObject.optBoolean("isBlock");
        isVerify = jsonObject.optBoolean("isVerify");
        lat = jsonObject.optDouble("lat");
        lng = jsonObject.optDouble("lng");
        phoneNumber = jsonObject.optString("phone_number");
        favoritCount = jsonObject.optInt("favoritCount");
        orderCount = jsonObject.optInt("orderCount");
        city_id = jsonObject.optString("city_id");

        try {
            token = jsonObject.optString("token");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        verifyCode = jsonObject.optString("verify_code");
        wallet = jsonObject.optLong("wallet");
        try {
            isEnableNotifications = jsonObject.getBoolean("isEnableNotifications");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("_id", id);
//            jsonObject.put("address", address);
            jsonObject.put("city_id", city_id);
//            jsonObject.put("createAt", createAt);
            jsonObject.put("email", email);
            jsonObject.put("full_name", fullName);
//            jsonObject.put("image", image);
//            jsonObject.put("isBlock", isBlock);
//            jsonObject.put("isVerify", isVerify);
            jsonObject.put("lat", lat);
            jsonObject.put("lng", lng);
//            jsonObject.put("phone_number", phoneNumber);
//            jsonObject.put("token", token);
//            jsonObject.put("verify_code", verifyCode);
//            jsonObject.put("wallet", wallet);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    public boolean isEnableNotifications() {
        return isEnableNotifications;
    }

    public void setEnableNotifications(boolean enableNotifications) {
        isEnableNotifications = enableNotifications;
    }
}