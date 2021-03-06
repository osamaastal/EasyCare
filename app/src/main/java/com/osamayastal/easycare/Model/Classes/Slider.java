package com.osamayastal.easycare.Model.Classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Slider {
    private String _id,ads_for,expiry_date,image,url,store_id,product_id,by,titleEn,titleAr,descriptionEn,descriptionAr;
    private boolean is_ads_redirect_to_store,is_ads_have_expiry_date,isApprove;

    public Slider(JSONObject provider) {
        if (provider==null){
            return;
        }
        Log.d("Response", provider.toString());
        try {
            _id=provider.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            ads_for=provider.getString("ads_for");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            expiry_date=provider.getString("expiry_date");
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
            url=provider.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            store_id=provider.getString("store_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product_id=provider.getString("product_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            titleEn=provider.getString("titleEn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            titleAr=provider.getString("titleAr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            descriptionEn=provider.getString("descriptionEn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            descriptionAr=provider.getString("descriptionAr");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            is_ads_redirect_to_store=provider.getBoolean("is_ads_redirect_to_store");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            is_ads_have_expiry_date=provider.getBoolean("is_ads_have_expiry_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            isApprove=provider.getBoolean("isApprove");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            by=provider.getString("by");


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAds_for() {
        return ads_for;
    }

    public void setAds_for(String ads_for) {
        this.ads_for = ads_for;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public boolean isIs_ads_redirect_to_store() {
        return is_ads_redirect_to_store;
    }

    public void setIs_ads_redirect_to_store(boolean is_ads_redirect_to_store) {
        this.is_ads_redirect_to_store = is_ads_redirect_to_store;
    }

    public boolean isIs_ads_have_expiry_date() {
        return is_ads_have_expiry_date;
    }

    public void setIs_ads_have_expiry_date(boolean is_ads_have_expiry_date) {
        this.is_ads_have_expiry_date = is_ads_have_expiry_date;
    }

    public boolean isApprove() {
        return isApprove;
    }

    public void setApprove(boolean approve) {
        isApprove = approve;
    }
}
