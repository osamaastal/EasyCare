package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Notification {
    private String _id, fromId, title, msg, user_id, body_parms, dt_date,fromName;
    private Boolean isRead;
    private int type;

    public Notification(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }
        _id = jsonObject.optString("_id");
        fromId = jsonObject.optString("fromId");
        fromName = jsonObject.optString("fromName");
        title = jsonObject.optString("title");
        msg = jsonObject.optString("msg");
        try {
            user_id = jsonObject.optString("user_id");
            body_parms = jsonObject.optString("body_parms");
        } catch (Exception e) {
            e.printStackTrace();
        }
        type = jsonObject.getInt("type");

        dt_date = jsonObject.optString("dt_date");
        isRead = jsonObject.getBoolean("isRead");
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody_parms() {
        return body_parms;
    }

    public void setBody_parms(String body_parms) {
        this.body_parms = body_parms;
    }

    public String getDt_date() {
        return dt_date;
    }

    public void setDt_date(String dt_date) {
        this.dt_date = dt_date;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }


}
