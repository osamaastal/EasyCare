package com.osamayastal.easycare.Model.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sub_categorie {
    private String _id,nameAr,nameEn,image,category_id,type_id,provider_id;
    private boolean isActive;
    private List<Size> sizes;
    private List<Sub_service> sub_services;
private String color;
    public Sub_categorie(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            _id=jsonObject.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            nameAr=jsonObject.getString("nameAr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            nameEn=jsonObject.getString("nameEn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            category_id=jsonObject.getString("category_id");
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
    }
    public void Sub_categorie_forOrder(JSONObject jsonObject) {
        sizes=new ArrayList<>();
        sub_services=new ArrayList<>();

        if (jsonObject==null){
            return;
        }

        try {
            _id=jsonObject.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            nameAr=jsonObject.getString("arName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            nameEn=jsonObject.getString("enName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            color=jsonObject.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            type_id=jsonObject.getString("type_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            provider_id=jsonObject.getString("provider_id");
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


        JSONArray jsonArray= null;
        try {
            jsonArray = jsonObject.getJSONArray("sizes");
            for (int i=0;i<jsonArray.length();i++){
                try {
                    sizes.add(new Size(jsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONArray jsonArray1= null;
        try {
            jsonArray1 = jsonObject.getJSONArray("sub_services");
            for (int i=0;i<jsonArray1.length();i++){
                sub_services.add(new Sub_service(jsonArray1.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {

            isActive=jsonObject.getBoolean("isActive");

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

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public List<Sub_service> getSub_services() {
        return sub_services;
    }

    public void setSub_services(List<Sub_service> sub_services) {
        this.sub_services = sub_services;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
