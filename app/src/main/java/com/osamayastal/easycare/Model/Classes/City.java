package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class City {
    private String _id,arName,enName;
    public City(JSONObject provider) {
        if (provider==null){
            return;
        }
        try {
            _id=provider.getString("_id");
            arName=provider.getString("arName");
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
        return arName;
    }
}
