package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.LoginFrag;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        setLocale(this);
    }
    public static FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        User_info user_info=new User_info(this);

        if (user_info.getId()==null || !user_info.CONF_phone(this)){
            switchFGM(new LoginFrag());
        }else {
            Log.d("users_id",user_info.getId());

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();


//            if (user_info.CONF_phone(this))
//            {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();
//            }
//            else {
////                startActivity(new Intent(LoginActivity.this, ConfCode.class));
////                finish();
//            }

        }



        new com.osamayastal.easycare.Model.Classes.GPS(this).
                turnGPSOn(new com.osamayastal.easycare.Model.Classes.GPS.onGpsListener() {
                    @Override
                    public void gpsStatus(boolean isGPSEnable) {
                        // turn on GPS
                        isGPS = isGPSEnable;
//                        Toast.makeText(Logo.this, "isGPS = " +isGPS, Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void setLocale(Context context ){
        User_info user_info;
        user_info = new User_info(context);
        String language=user_info.getLanguage();
        Locale locale = new Locale(language);
        Configuration config = new Configuration(getResources().getConfiguration());
        Locale.setDefault(locale);
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
//        Toast.makeText(this, "Language: "+ Locale.getDefault().getLanguage() , Toast.LENGTH_SHORT).show();
    }
    private Boolean isGPS;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 15) {
                isGPS = true; // flag maintain before get location
            }
        }
    }




    public void switchFGM(Fragment fragment){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.LoginContainer, fragment);
        transaction.commit();
    }
}
