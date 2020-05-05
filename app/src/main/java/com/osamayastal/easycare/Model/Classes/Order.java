package com.osamayastal.easycare.Model.Classes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.osamayastal.easycare.Model.Classes.Basket.categories_basket;
import com.osamayastal.easycare.Model.Classes.Provider.Provider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {


private Provider provider_id;
    private String _id,createAt,user_id,address,time,date,Order_no;
    private boolean isUpfront;
    private double lat,lng,provider_Total,Admin_Total,Remain,Total;
    private int upfrontAmount,PaymentType,locationType,StatusId;
private Long dateLong;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Order(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }


        try {
            provider_id=new Provider(jsonObject.getJSONObject("provider_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            _id=jsonObject.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            createAt=jsonObject.getString("createAt");
//          dateLong=Instant.parse("2020-10-31T00:00:00Z").toEpochMilli();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            user_id=jsonObject.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            address=jsonObject.getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            time=jsonObject.getString("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            date=jsonObject.getString("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Order_no=jsonObject.getString("Order_no");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lat=jsonObject.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lng=jsonObject.getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            provider_Total=jsonObject.getDouble("provider_Total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Admin_Total=jsonObject.getDouble("Admin_Total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Remain=jsonObject.getDouble("Remain");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Total=jsonObject.getDouble("Total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            upfrontAmount=jsonObject.getInt("upfrontAmount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            PaymentType=jsonObject.getInt("PaymentType");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            locationType=jsonObject.getInt("locationType");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            StatusId=jsonObject.getInt("StatusId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            isUpfront=jsonObject.getBoolean("isUpfront");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Long getDateLong() {
        return dateLong;
    }

    public void setDateLong(Long dateLong) {
        this.dateLong = dateLong;
    }

    public Provider getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Provider provider_id) {
        this.provider_id = provider_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder_no() {
        return Order_no;
    }

    public void setOrder_no(String order_no) {
        Order_no = order_no;
    }

    public boolean isUpfront() {
        return isUpfront;
    }

    public void setUpfront(boolean upfront) {
        isUpfront = upfront;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getProvider_Total() {
        return provider_Total;
    }

    public void setProvider_Total(double provider_Total) {
        this.provider_Total = provider_Total;
    }

    public double getAdmin_Total() {
        return Admin_Total;
    }

    public void setAdmin_Total(double admin_Total) {
        Admin_Total = admin_Total;
    }

    public double getRemain() {
        return Remain;
    }

    public void setRemain(double remain) {
        Remain = remain;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public int getUpfrontAmount() {
        return upfrontAmount;
    }

    public void setUpfrontAmount(int upfrontAmount) {
        this.upfrontAmount = upfrontAmount;
    }

    public int getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(int paymentType) {
        PaymentType = paymentType;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }
}
