package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;

public class Logo extends AppCompatActivity {

    private static final long SPLASH_TIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User_info user_info=new User_info();
                if (user_info.INTRO(Logo.this)){
                    startActivity(new Intent(Logo.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(Logo.this,WelcomeActivity.class));

                }

            }
        }, SPLASH_TIME);
    }
}
