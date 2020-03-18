package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.Welcome1;
import com.osamayastal.easycare.fragments.Welcome2;
import com.osamayastal.easycare.fragments.Welcome3;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager mPager;
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
    }

    private void init() {
        mPager = findViewById(R.id.viewPager);
        mPager.setAdapter( new MyPagerAdapter(getSupportFragmentManager()));
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
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
