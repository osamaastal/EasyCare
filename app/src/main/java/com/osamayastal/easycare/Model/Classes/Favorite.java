package com.osamayastal.easycare.Model.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favorite {
    private String _id,user_id,createAt;
    private Search provider_id;

    public Favorite(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }


        try {
            _id=jsonObject.getString("_id");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            user_id=jsonObject.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            createAt=jsonObject.getString("createAt");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            provider_id=new Search(jsonObject.getJSONObject("provider_id"),null);
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Search getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Search provider_id) {
        this.provider_id = provider_id;
    }
}
