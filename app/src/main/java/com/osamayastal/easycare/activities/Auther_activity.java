package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.osamayastal.easycare.R;

public class Auther_activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auther_activity);
    }
    public static   FragmentTransaction transaction;
    public static Fragment fragment=null;
    public void switchFGM(Fragment fragment){
       transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment);
       transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragment!=null){
            switchFGM(fragment);
        }
    }
}
