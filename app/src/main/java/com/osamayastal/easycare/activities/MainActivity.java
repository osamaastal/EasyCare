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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.AboutUs;
import com.osamayastal.easycare.fragments.Basket;
import com.osamayastal.easycare.fragments.EditProfile;
import com.osamayastal.easycare.fragments.Home;
import com.osamayastal.easycare.fragments.MyOrders;
import com.osamayastal.easycare.fragments.MyPlace;
import com.osamayastal.easycare.fragments.Notifications;
import com.osamayastal.easycare.fragments.Profile;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static FragmentManager fragmentManager;
    private Fragment fragment;
    private Fragment home_frag, myOrders_frag, myPlace_frag, basket_frag, profile_frag;
    private Toolbar toolbar;
public static  FragmentTransaction transaction;
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
        View navigation = findViewById(R.id.nav_view);
        TextView drawer_offers_tv = navigation.findViewById(R.id.drawer_offers_tv);
        TextView drawer_points_tv = navigation.findViewById(R.id.drawer_points_tv);
        TextView drawer_chat_tv = navigation.findViewById(R.id.drawer_chat_tv);
        TextView drawer_notification_tv = navigation.findViewById(R.id.drawer_notification_tv);
        TextView drawer_settings_tv = navigation.findViewById(R.id.drawer_settings_tv);
        TextView drawer_about_tv = navigation.findViewById(R.id.drawer_about_tv);
        TextView drawer_report_tv = navigation.findViewById(R.id.drawer_report_tv);
        TextView drawer_call_tv = navigation.findViewById(R.id.drawer_call_tv);
        TextView drawer_wallet_tv = navigation.findViewById(R.id.drawer_wallet_tv);
        TextView edit_profile_tv = navigation.findViewById(R.id.edit_profile_tv);
        TextView user_name_tv = navigation.findViewById(R.id.user_name_tv);
        CircleImageView user_img = navigation.findViewById(R.id.user_img);
        TextView langu = navigation.findViewById(R.id.language);
        ImageButton logout = navigation.findViewById(R.id.logout_btn);
        langu.setOnClickListener(this);
        logout.setOnClickListener(this);
        drawer_offers_tv.setOnClickListener(this);
        drawer_points_tv.setOnClickListener(this);
        drawer_chat_tv.setOnClickListener(this);
        drawer_notification_tv.setOnClickListener(this);
        drawer_settings_tv.setOnClickListener(this);
        drawer_about_tv.setOnClickListener(this);
        drawer_report_tv.setOnClickListener(this);
        drawer_call_tv.setOnClickListener(this);
        drawer_wallet_tv.setOnClickListener(this);
        edit_profile_tv.setOnClickListener(this);
        user_name_tv.setOnClickListener(this);
        user_img.setOnClickListener(this);
        /********************Bottom nav view**********************/
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
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
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainContainer, fragment).commit();
                return true;
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainContainer, fragment);
        transaction.commit();
        bottom_navigation.setSelectedItemId(R.id.home);


    }

    public void switchFGM(Fragment fragment){
        MainActivity.transaction = getSupportFragmentManager().beginTransaction();
        MainActivity. transaction.replace(R.id.mainContainer, fragment);
        MainActivity. transaction.commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.drawer_offers_tv:

                break;
            case R.id.drawer_points_tv:
                Toast.makeText(this,"point" ,Toast.LENGTH_SHORT).show();

                break;
            case R.id.drawer_chat_tv:
                Toast.makeText(this,"chat",Toast.LENGTH_SHORT).show();

                break;
            case R.id.drawer_notification_tv:
                switchFGM(new Notifications());
                break;
            case R.id.drawer_settings_tv:

                break;
            case R.id.drawer_about_tv:
                switchFGM(new AboutUs());

                break;
            case R.id.drawer_report_tv:

                break;
            case R.id.drawer_call_tv:

                break;
            case R.id.drawer_wallet_tv:

                break;
            case R.id.edit_profile_tv:
switchFGM(new EditProfile());
                break;
            case R.id.user_img:

                break;
            case R.id.user_name_tv:

                break;
            case R.id.language:
                Toast.makeText(this,"langauge",Toast.LENGTH_SHORT).show();

                break;
            case R.id.logout_btn:
                    user user=new user();
                    user.Post_Logout(this, new user.user_Listener() {
                        @Override
                        public void onSuccess(users new_account) {
                            if (new User_info(MainActivity.this).getLanguage().equals("en")){
                                Toast.makeText(MainActivity.this,new_account.getMessageEn(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this,new_account.getMessageAr(),Toast.LENGTH_SHORT).show();
                            }
                            if (!new_account.isStatus()){
                                return;
                            }
                            new User_info(new User(),MainActivity.this);
                           finish();
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                    break;

        }
    }
}
