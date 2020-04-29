package com.osamayastal.easycare.Model.Classes.Basket;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Payment;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider.Provider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bascket implements Serializable {

    private List<Product> products;
    private List<categories_basket> categories;
    private List<Payment> payment_id;
private Provider provider;
private Categorie categorie;


//for Order
    private String time,date,Order_no;
    private int PaymentType,locationType,StatusId;
    private Double Total;

    public Bascket() {
    }

    public Bascket(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        categories=new ArrayList<>();
        products=new ArrayList<>();
        payment_id=new ArrayList<>();

       try {
           provider=new Provider(jsonObject);
       } catch (Exception e) {
           e.printStackTrace();
       }
        try {
            categorie=new Categorie(jsonObject.getJSONObject("type_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray= null;
        try {
            jsonArray = jsonObject.getJSONArray("categories");
            for(int i=0;i<jsonArray.length();i++){
                categories.add(new categories_basket(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            JSONArray jsonArray1=jsonObject.getJSONArray("products");
            for(int i=0;i<jsonArray1.length();i++){
                products.add(new Product(jsonArray1.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray3= null;
        try {
            jsonArray3 = jsonObject.getJSONArray("payment_id");
            for(int i=0;i<jsonArray3.length();i++){
                payment_id.add(new Payment(jsonArray3.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void OrderDetails(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
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
            Total=jsonObject.getDouble("Total");
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
        try{
            provider=new Provider(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        categories=new ArrayList<>();
        try {

            categorie=new Categorie(jsonObject.getJSONObject("type_id"));

            JSONArray jsonArray=jsonObject.getJSONArray("categories");
            for(int i=0;i<jsonArray.length();i++){
                categories.add(new categories_basket(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        products=new ArrayList<>();
        JSONArray jsonArray1= null;
        try {
            jsonArray1 = jsonObject.getJSONArray("products");
            for(int i=0;i<jsonArray1.length();i++){
                products.add(new Product(jsonArray1.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        payment_id=new ArrayList<>();
        JSONArray jsonArray3= null;
        try {
            jsonArray3 = jsonObject.getJSONArray("payment_id");
            for(int i=0;i<jsonArray3.length();i++){
                payment_id.add(new Payment(jsonArray3.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<Payment> getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(List<Payment> payment_id) {
        this.payment_id = payment_id;
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

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<categories_basket> getCategories() {
        return categories;
    }

    public void setCategories(List<categories_basket> categories) {
        this.categories = categories;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
