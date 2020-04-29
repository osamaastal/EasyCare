package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment {
    private String _id,id,arName,enName;
    public Payment(JSONObject provider) {
        if (provider==null){
            return;
        }
        try {
            _id=provider.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            id=provider.getString("id");
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
