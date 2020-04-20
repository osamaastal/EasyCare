package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.LoginFrag;

public class LoginActivity extends AppCompatActivity {

    public static FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        User_info user_info=new User_info(this);

        if (user_info.getId()==null){
            switchFGM(new LoginFrag());
        }else {
            Log.d("users_id",user_info.getId());
            if (user_info.CONF_phone(this))
            {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            else {
                startActivity(new Intent(LoginActivity.this, ConfCode.class));
                finish();
            }

        }



    }

    public void switchFGM(Fragment fragment){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.LoginContainer, fragment);
        transaction.commit();
    }
}
