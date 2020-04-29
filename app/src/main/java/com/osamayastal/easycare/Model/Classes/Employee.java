package com.osamayastal.easycare.Model.Classes;

import com.osamayastal.easycare.Model.Classes.Provider.Provider;

import org.json.JSONException;
import org.json.JSONObject;

public class Employee {
    private String _id,createAt,full_name,email,phone_number,password,image,fcmToken,address;
    private boolean isBlock;
    private Provider provider_id;

    public Employee(JSONObject provider) {
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
            full_name=provider.getString("full_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            email=provider.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            phone_number=provider.getString("phone_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            password=provider.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            fcmToken=provider.getString("fcmToken");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            address=provider.getString("address");
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
            isBlock=provider.getBoolean("isBlock");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {

            provider_id=new Provider(provider.getJSONObject("provider_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public Provider getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Provider provider_id) {
        this.provider_id = provider_id;
    }
}
