package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Categorie implements Serializable {
    private String _id,arName,enName,image;
    private String color;
    private boolean isActive;

    public Categorie(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            _id=jsonObject.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            arName=jsonObject.getString("arName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            enName=jsonObject.getString("enName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            image=jsonObject.getString("image");
            if (!image.contains("https")){
                image=image.replace("http", "https");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            isActive=jsonObject.getBoolean("isActive");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {

            color= jsonObject.getString("color");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
