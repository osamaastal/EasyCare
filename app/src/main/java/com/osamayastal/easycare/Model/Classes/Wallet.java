package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Wallet {
    private String _id,no,user_id,to,type,createAt;
private Double amount=0.0;
    public Wallet(JSONObject provider) {
        if (provider==null){
            return;
        }
        try {
            _id=provider.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            no=provider.getString("no");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            user_id=provider.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            to=provider.getString("to");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            amount=provider.getDouble("amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            type=provider.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            createAt=provider.getString("createAt");
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
