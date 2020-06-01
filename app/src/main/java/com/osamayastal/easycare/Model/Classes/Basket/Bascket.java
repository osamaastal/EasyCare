package com.osamayastal.easycare.Model.Classes.Basket;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Employee;
import com.osamayastal.easycare.Model.Classes.Payment;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider.Provider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Bascket implements Serializable {

    private List<Product> products;
    private List<categories_basket> categories;
    private List<Payment> payment_id;
private Provider provider;
private Categorie categorie;
private JSONObject mjsonObject=null;

//for OrderDetails
    private String time,date,Order_no,createAt;
    private int PaymentType,locationType,StatusId;
    private Double total_price=0.0,total_discount=0.0,final_total=0.0,tax=0.0;
    private Employee employee_id;
private Long dateLong;
    public Bascket() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bascket(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        mjsonObject=jsonObject;
        try {
            createAt=jsonObject.getString("createAt");
//            dateLong= Instant.parse("2020-10-31T00:00:00Z").toEpochMilli();
        } catch (JSONException e) {
            e.printStackTrace();
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
            total_price=jsonObject.getDouble("Total");
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
            employee_id=new Employee(jsonObject.getJSONObject("employee_id"));
            Log.d("parameters",jsonObject.getJSONObject("employee_id").toString());

        } catch (Exception e) {
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

    public JSONObject getMjsonObject() {
        return mjsonObject;
    }

    public void setMjsonObject(JSONObject mjsonObject) {
        this.mjsonObject = mjsonObject;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public Double getTotal_discount() {
        return total_discount;
    }

    public Double getFinal_total() {
        return final_total;
    }

    public void setFinal_total(Double final_total) {
        this.final_total = final_total;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public void setTotal_discount(Double total_discount) {
        this.total_discount = total_discount;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Long getDateLong() {
        return dateLong;
    }

    public void setDateLong(Long dateLong) {
        this.dateLong = dateLong;
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

    public Employee getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Employee employee_id) {
        this.employee_id = employee_id;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
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
