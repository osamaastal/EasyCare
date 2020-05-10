package com.osamayastal.easycare.Model.Classes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Favorite_get {
    private String _id,createAt,user_id;
    private Search provider_id;

    public Favorite_get(JSONObject provider) {
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
            user_id=provider.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            provider_id=new Search(provider.getJSONObject("provider_id"),null);
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Search getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Search provider_id) {
        this.provider_id = provider_id;
    }
}
