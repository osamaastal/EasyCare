package com.osamayastal.easycare.Model.Classes.Provider;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Sub_servic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Provider_Details {
    private String _id,createAt,name,email,address,phone_number,password,image;
    private Categorie category_id;
    private City city_id;
    private boolean isBlock;
    private double lat,lng,profitPercentage,orderPercentage;
    private float rate;

    private List<Product> Products;
    private List<Sub_servic> Sub_services;
    private List<ProviderSetting> providerSetting;

    public Provider_Details(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        Products=new ArrayList<>();
        Sub_services=new ArrayList<>();
        providerSetting=new ArrayList<>();
        try {
            _id=jsonObject.getString("_id");
            createAt=jsonObject.getString("createAt");
            name=jsonObject.getString("name");
            email=jsonObject.getString("email");
            address=jsonObject.getString("address");
            phone_number=jsonObject.getString("phone_number");
            category_id=new Categorie(jsonObject.getJSONObject("category_id"));

            rate=jsonObject.getInt("rate");

            isBlock=jsonObject.getBoolean("isBlock");

            lat=jsonObject.getDouble("lat");
            lng=jsonObject.getDouble("lng");
            profitPercentage=jsonObject.getDouble("profitPercentage");
            orderPercentage=jsonObject.getDouble("orderPercentage");
            city_id=new City(jsonObject.getJSONObject("city_id"));
            password=jsonObject.getString("password");


            JSONArray jsonArray=jsonObject.getJSONArray("subServicesList");
            JSONArray jsonArray1=jsonObject.getJSONArray("providerProduct");
            JSONArray jsonArray2=jsonObject.getJSONArray("providerSetting");


            for(int i=0;i<jsonArray.length();i++){
                Sub_services.add(new Sub_servic(jsonArray.getJSONObject(i)));
            }

            for(int i=0;i<jsonArray1.length();i++){
                Products.add(new Product(jsonArray1.getJSONObject(i)));
            }

            for(int i=0;i<jsonArray2.length();i++){
                providerSetting.add(new ProviderSetting(jsonArray2.getJSONObject(i)));
            }

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Categorie getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Categorie category_id) {
        this.category_id = category_id;
    }

    public City getCity_id() {
        return city_id;
    }

    public void setCity_id(City city_id) {
        this.city_id = city_id;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
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

    public double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public double getOrderPercentage() {
        return orderPercentage;
    }

    public void setOrderPercentage(double orderPercentage) {
        this.orderPercentage = orderPercentage;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public List<Sub_servic> getSub_services() {
        return Sub_services;
    }

    public void setSub_services(List<Sub_servic> sub_services) {
        Sub_services = sub_services;
    }

    public List<ProviderSetting> getProviderSetting() {
        return providerSetting;
    }

    public void setProviderSetting(List<ProviderSetting> providerSetting) {
        this.providerSetting = providerSetting;
    }
}
