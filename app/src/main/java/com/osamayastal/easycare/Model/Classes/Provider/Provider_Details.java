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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            createAt=jsonObject.getString("createAt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name=jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            image=jsonObject.getString("image").replace("http", "https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            email=jsonObject.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            address=jsonObject.getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            phone_number=jsonObject.getString("phone_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            category_id=new Categorie(jsonObject.getJSONObject("category_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            rate=jsonObject.getInt("rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            isBlock=jsonObject.getBoolean("isBlock");
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
            profitPercentage=jsonObject.getDouble("profitPercentage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            orderPercentage=jsonObject.getDouble("orderPercentage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            city_id=new City(jsonObject.getJSONObject("city_id"),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            password=jsonObject.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONArray jsonArray= null;
        try {
            jsonArray = jsonObject.getJSONArray("subServicesList");
            for(int i=0;i<jsonArray.length();i++){
                Sub_services.add(new Sub_servic(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray1= null;
        try {
            jsonArray1 = jsonObject.getJSONArray("providerProduct");
            for(int i=0;i<jsonArray1.length();i++){
                Products.add(new Product(jsonArray1.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray2= null;
        try {
            jsonArray2 = jsonObject.getJSONArray("providerSetting");

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
