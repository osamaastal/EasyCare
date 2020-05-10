package com.osamayastal.easycare.Model.Classes;

import android.content.Context;

import com.osamayastal.easycare.Model.Const.User_info;

import org.json.JSONException;
import org.json.JSONObject;

public class City {
    private String _id,arName,enName;
    private Context mcontext;
    public City(JSONObject provider,Context mcontext) {
        if (provider==null){
            return;
        }
        this.mcontext=mcontext;
        try {
            _id=provider.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            arName=provider.getString("arName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            enName=provider.getString("enName");
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

    @Override
    public String toString() {
        if (new User_info(mcontext).getLanguage().equals("en")){
            return enName;
        }else {
            return arName;

        }
    }
}
