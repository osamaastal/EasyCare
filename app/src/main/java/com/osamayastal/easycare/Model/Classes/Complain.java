package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Complain {
    private String _id,user_id,category,title,details,dt_date;

    public Complain(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            _id=jsonObject.getString("_id");
            user_id=jsonObject.getString("user_id");
            category=jsonObject.getString("category");
            title=jsonObject.getString("title");
            details=jsonObject.getString("details");
            dt_date=jsonObject.getString("dt_date");


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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDt_date() {
        return dt_date;
    }

    public void setDt_date(String dt_date) {
        this.dt_date = dt_date;
    }
}
