package com.osamayastal.easycare.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.osamayastal.easycare.R;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /************************* Drawer*******************/
        /** TODO ***************************DRAWER new Library********************************/
        Toolbar toolbar = findViewById(R.id.toolBar);
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

    }
}
