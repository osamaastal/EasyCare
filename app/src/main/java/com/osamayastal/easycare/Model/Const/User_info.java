package com.osamayastal.easycare.Model.Const;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.activities.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class User_info {
    private String language;
    private String id,phone,token,name,email,image,gender,city,cityID,pw,address;
private String lat;
    private String lng;
private long wallet;
private  int point,basket;
    private boolean isEnableNotifications;
private Context mcontext;
    public User_info(Context mcontext) {
        try {
           this.mcontext=mcontext;
            final SharedPreferences sp=mcontext.getSharedPreferences("Login", MODE_PRIVATE);
            id=sp.getString("id",null);
            token=sp.getString("token",null);
            phone=sp.getString("phone","+966 000-0000-00");
            name=sp.getString("name","إسم العميل");
            email=sp.getString("email","mail@email.com");
            image=sp.getString("image","");
            gender=sp.getString("gender","");
            city=sp.getString("city"," ");
            cityID=sp.getString("cityID"," ");
            address=sp.getString("address"," ");
            lat=sp.getString("lat","");
            lng=sp.getString("lng","");
            pw=sp.getString("pw","");
            wallet=sp.getLong("wallet", (long) 0.26);
            point=sp.getInt("point", 0);
            basket=sp.getInt("basket", 0);
            language=sp.getString("language","ar");
            pw=sp.getString("pw","");
            isEnableNotifications=sp.getBoolean("isEnableNotifications",true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User_info(String language, Context context) {
        SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();

        Ed.putString("language", language);

        Ed.commit();

    }
    public void Password(String pw, Context context) {
        SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();

        Ed.putString("pw", pw);

        Ed.commit();

    }
    public void Token(String token, Context context) {
        SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();

        Ed.putString("token", token);

        Ed.commit();

    }
    public void Basket(int nb, Context context) {
        SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();

        Ed.putInt("basket", nb);

        Ed.commit();

    }
    public User_info(User user_, Context context) {
       try {
           SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
           SharedPreferences.Editor Ed=sp.edit();
           Ed.putString("phone",user_.getPhoneNumber() );
           Ed.putString("id",user_.getId());
           Ed.putString("token",user_.getToken());
           Ed.putString("name",user_.getFullName());
           Ed.putString("image",user_.getImage());


           Ed.putString("email",user_.getEmail());
           Ed.putString("city",user_.getCity());
           Ed.putString("cityID",user_.getCity_id());
           Ed.putString("address",user_.getAddress());
           Ed.putLong("wallet",user_.getWallet());
           Ed.putString("lat", String.valueOf(user_.getLat()));
           Ed.putString("lng", String.valueOf(user_.getLng()));
           Ed.putBoolean("isEnableNotifications", user_.isEnableNotifications());

           Ed.commit();
           new User_info(context);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    public User_info(Context context,Boolean logout) {
        SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("phone",null );
        Ed.putString("id",null);
        Ed.putString("token",null);
        Ed.putString("name",null);
        Ed.putString("image",null);
        Ed.putString("email",null);
        Ed.putString("city",null);
        Ed.putLong("wallet", (long) 0.0);
        Ed.putBoolean("conf_phone",false);
        Ed.putInt("basket", 0);


        Ed.commit();

    }
    public void SetLocation(Context context, LatLng mlatLng) {
        try {
            SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();

            Ed.putString("lat", String.valueOf(mlatLng.latitude));
            Ed.putString("lng", String.valueOf(mlatLng.longitude));


            Ed.commit();
            new User_info(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void  Change_isEnableNotifications(Context context,Boolean isEnableNotifications) {
        SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putBoolean("isEnableNotifications",isEnableNotifications);

        Ed.commit();

    }
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public User_info() {

    }

//    public User_info(Context context, points points) {
//        SharedPreferences sp=context.getSharedPreferences("Login", MODE_PRIVATE);
//        SharedPreferences.Editor Ed=sp.edit();
//        Ed.putInt("point",points.getPoints());
//        Ed.putLong("wallet",points.getMoney());
//        Ed.commit();
//    }

    public boolean isEnableNotifications() {
        return isEnableNotifications;
    }

    public void setEnableNotifications(boolean enableNotifications) {
        isEnableNotifications = enableNotifications;
    }

    public int getBasket() {
        return basket;
    }

    public void setBasket(int basket) {
        this.basket = basket;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getImage() {
        return image;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getImag() {
        return image;
    }

    public void setImag(String imag) {
        this.image = imag;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    public Long getWallet() {
        return wallet;
    }

    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public boolean INTRO( Context mcontext) {
        SharedPreferences sp=mcontext.getSharedPreferences("Login", MODE_PRIVATE);
        boolean intro=sp.getBoolean("intro",false);
        if (intro){
            return true;
        }else {
            return false;

        }

    }
    public boolean CONF_phone( Context mcontext) {
        SharedPreferences sp=mcontext.getSharedPreferences("Login", MODE_PRIVATE);
        boolean intro=sp.getBoolean("conf_phone",false);
        if (intro){
            return true;
        }else {
            return false;

        }

    }

    public void DO_CONF_phone( Context mcontext) {
        SharedPreferences sp=mcontext.getSharedPreferences("Login", MODE_PRIVATE);
        boolean intro=sp.getBoolean("conf_phone",false);
        if (!intro){
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putBoolean("conf_phone",true);
            Ed.commit();
        }

    }
    public void DO_INTRO( Context mcontext) {
        SharedPreferences sp=mcontext.getSharedPreferences("Login", MODE_PRIVATE);
        boolean intro=sp.getBoolean("intro",false);
        if (!intro){
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putBoolean("intro",true);
            Ed.commit();
        }

    }
    public User_info(User_info user_, Context mcontext) {
        SharedPreferences sp=mcontext.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("phone",user_.getPhone() );
        Ed.putString("id",user_.getId());
        Ed.putString("token",user_.getToken());
        Ed.putString("name",user_.getName());
        Ed.putString("image",user_.getImag());
        Ed.putString("email",user_.getEmail());
        Ed.putString("gender",user_.getGender());
        Ed.putLong("wallet",user_.getWallet());
        Ed.commit();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        if (mcontext!=null && token==null){
           mcontext.startActivity(new Intent(mcontext, LoginActivity.class));
           return " no token";
        }
        return token;
    }
    public String getToken_home() {

        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
