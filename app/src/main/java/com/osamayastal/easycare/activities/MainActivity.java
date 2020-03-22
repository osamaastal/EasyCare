package com.osamayastal.easycare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.Basket;
import com.osamayastal.easycare.fragments.Home;
import com.osamayastal.easycare.fragments.MyOrders;
import com.osamayastal.easycare.fragments.MyPlace;
import com.osamayastal.easycare.fragments.Profile;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation;
    private static FragmentManager fragmentManager;
    private Fragment fragment;
    private Fragment home_frag, myOrders_frag, myPlace_frag, basket_frag, profile_frag;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /************************* Drawer*******************/
         toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu_);
//        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_));
        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withDragDistance(250)
                .withRootViewScale(1f)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.navigation_content)
                .inject();
        /********************Bottom nav view**********************/
        bottom_navigation = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        home_frag = new Home();
        myOrders_frag = new MyOrders();
        myPlace_frag = new MyPlace();
        basket_frag = new Basket();
        profile_frag = new Profile();
        fragment = home_frag;

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        fragment = home_frag;
                        toolbar.setBackground(getDrawable(R.drawable.bg_circle_midblue));
                        break;
                    case R.id.my_orders:
                        fragment = myOrders_frag;
                        toolbar.setBackground(getDrawable(R.drawable.bg_circle_blue_gradiant));
                        break;
                    case R.id.my_place:
                        fragment = myPlace_frag;
                        toolbar.setBackground(getDrawable(R.drawable.bg_circle_darkblue));
                        break;
                    case R.id.basket:
                        fragment = basket_frag;
                        toolbar.setBackground(getDrawable(R.drawable.bg_circle_blue_gradiant));
                        break;
                    case R.id.profile:
                        fragment = profile_frag;
                        toolbar.setBackground(getDrawable(R.drawable.bg_circle_blue_gradiant));
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainContainer, fragment).commit();
                return true;
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainContainer, fragment);
        transaction.commit();
        bottom_navigation.setSelectedItemId(R.id.home);


    }
}
