package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.Welcome1;
import com.osamayastal.easycare.fragments.Welcome2;
import com.osamayastal.easycare.fragments.Welcome3;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private TextView skip,en,ar;
    private ImageView valid_en,valid_ar;
    private final Fragment[] PAGES = new Fragment[]{
            new Welcome1(),
            new Welcome2(),
            new Welcome3()
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

        User_info user_info=new User_info();
        user_info.DO_INTRO(this);
    }

    private void init() {
        mPager = findViewById(R.id.viewPager);
        skip = findViewById(R.id.skip);
        en = findViewById(R.id.en_tv);
        ar = findViewById(R.id.ar_tv);
        valid_ar = findViewById(R.id.valid_ar);
        valid_en = findViewById(R.id.valid_en);

        mPager.setAdapter( new MyPagerAdapter(getSupportFragmentManager()));
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        /********************************Actions****************************************/
        skip.setOnClickListener(this);
        ar.setOnClickListener(this);
        en.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.skip:
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                break;
            case R.id.ar_tv:

                break;
            case R.id.en_tv:

                break;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

    }
}
