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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osamayastal.easycare.Model.Classes.Complain;
import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Complains;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Model.Rootes.Complain_root;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.AboutUs;
import com.osamayastal.easycare.fragments.Basket;
import com.osamayastal.easycare.fragments.EditProfile;
import com.osamayastal.easycare.fragments.Home;
import com.osamayastal.easycare.fragments.LoginFrag;
import com.osamayastal.easycare.fragments.MyOrders;
import com.osamayastal.easycare.fragments.MyPlace;
import com.osamayastal.easycare.fragments.Notifications;
import com.osamayastal.easycare.fragments.Profile;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static FragmentManager fragmentManager;
    private Fragment fragment;
    private Fragment home_frag, myOrders_frag, myPlace_frag, basket_frag, profile_frag;
    public static Toolbar toolbar;
public static  FragmentTransaction transaction;
    public static BottomNavigationView bottom_navigation;
    public static LinearLayout linear_bottom;
private TextView langu;
    User_info user_info;
  public static   int item_select;
  public static  FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout=findViewById(R.id.mainContainer);
/******************************Test Fore user_Login*************************************/
       user_info=new User_info(this);
        if (user_info.getId()==null){
           user root=new user();
           root.Get_Token(this);
        }else {
            Bascket_root root=new Bascket_root();
            root.GetItemCount(this);
        }

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
         langu = navigation.findViewById(R.id.language);
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
        if (user_info.getId()==null){
            logout.setVisibility(View.GONE);
        }

        /********************Bottom nav view**********************/
//        item_select=R.id.home;
        linear_bottom = findViewById(R.id.linear_bottom);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        home_frag = new Home();
        myOrders_frag = new MyOrders();
        myPlace_frag = new MyPlace();
        basket_frag = new Basket();
        profile_frag = new Profile();
        fragment = home_frag;
        getLanguge();
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        item_select=R.id.home;
                        fragment = home_frag;
                        toolbar.setBackground(getDrawable(R.drawable.bg_circle_midblue));
                        break;
                    case R.id.my_orders:
                        item_select=R.id.my_orders;
                        if (user_info.getId()==null){
                            LoginAlert();
                        }else {
                            fragment = myOrders_frag;
                            toolbar.setBackground(getDrawable(R.drawable.bg_circle_blue_gradiant));
                        }

                        break;
                    case R.id.my_place:
                        item_select=R.id.my_place;

                            fragment = myPlace_frag;
                            toolbar.setBackground(getDrawable(R.drawable.bg_circle_darkblue));

                        break;
                    case R.id.basket:
                        item_select=R.id.basket;

                        if (user_info.getId()==null){
                            LoginAlert();
                        }else {
                            fragment = basket_frag;
                            toolbar.setBackground(getDrawable(R.drawable.bg_circle_blue_gradiant));
                        }
                        break;
                    case R.id.profile:
                        item_select=R.id.profile;

                        if (user_info.getId()==null){
                            LoginAlert();
                        }else {
                            fragment = profile_frag;
                            toolbar.setBackground(getDrawable(R.drawable.bg_circle_blue_gradiant));
                        }

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

    @Override
    protected void onResume() {
        super.onResume();
        bottom_navigation.setSelectedItemId(item_select);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public static void SetVisibillty(int visibl){
        toolbar.setVisibility(visibl);
        linear_bottom.setVisibility(visibl);

       if (visibl==View.VISIBLE){
           frameLayout.setPadding(0, 0, 0, 50);
       }else {
           frameLayout.setPadding(0, 0, 0, 0);

       }


    }
    public void LoginAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("قم بتسجيل الدخول ")
                .setPositiveButton("تسجيل الدخول", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }
    public void switchFGM(Fragment fragment){
        MainActivity.transaction = getSupportFragmentManager().beginTransaction();
        MainActivity. transaction.replace(R.id.mainContainer, fragment);
        MainActivity. transaction.commit();
    }
    @Override
    public void onClick(View v) {
        AppPop appPop=new AppPop();

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
                SetVisibillty(View.GONE);

                break;
            case R.id.drawer_report_tv:

              PostADDComplain_pop(this);
                break;
            case R.id.drawer_call_tv:

                break;
            case R.id.drawer_wallet_tv:

                break;
            case R.id.edit_profile_tv:

                if (user_info.getId()==null){
                    LoginAlert();
                }else {
                    switchFGM(new Profile());
                }
                break;
            case R.id.user_img:

                break;
            case R.id.user_name_tv:

                break;
            case R.id.language:
               if (new User_info(this).getLanguage().equals("en")){
                  ChangeLanguge_pop("ar", this, new chang() {
                      @Override
                      public void onSave(Boolean v) {

                          if (v) langu.setText("Ar");

                      }
                  });
               }else {
                   ChangeLanguge_pop("en", this, new chang() {
                       @Override
                       public void onSave(Boolean v) {
                           if (v)  langu.setText("En");

                       }
                   });


               }
               getLanguge();

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
public  interface chang{
       void onSave(Boolean v);
}

    public Boolean ChangeLanguge_pop(final String languge, final Context mcontext, final chang chang_){
        final Boolean[] change = {false};
        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_language, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new User_info(languge,mcontext);
                mBottomSheetDialog.dismiss();
                chang_.onSave(true);



            }
        });

        Button cancel=sheetView.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                chang_.onSave(false);


            }
        });

return change[0];
    }
    public void PostADDComplain_pop(final Context mcontext){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_contact_us, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        ImageButton whatup,phone,email;
        final EditText title,details;
        final Spinner categorie;
        whatup=sheetView.findViewById(R.id.whatup);
        phone=sheetView.findViewById(R.id.phone);
        email=sheetView.findViewById(R.id.email);
        whatup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    PackageManager packageManager =mcontext.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone="+ Server_info.phone +"&text=" + URLEncoder.encode(" ", "UTF-8");

                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        mcontext.startActivity(i);
                    }else {
                        Toast.makeText(mcontext, mcontext.getString(R.string.no_whatsapp), Toast.LENGTH_SHORT);
                    }
                } catch(Exception e) {
                    Log.e("ERROR WHATSAPP",e.toString());

                }
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                String[] TO ={Server_info.email};
                intent.putExtra(Intent.EXTRA_EMAIL,TO );
                intent.putExtra(Intent.EXTRA_SUBJECT, "استفسار");
                intent.putExtra(Intent.EXTRA_TEXT, "");

                mcontext.startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_PHONE_NUMBER,Server_info.phone );

                mcontext.startActivity(Intent.createChooser(intent, "call phone"));
            }
        });
        title=sheetView.findViewById(R.id.title);
        details=sheetView.findViewById(R.id.details);
        categorie=sheetView.findViewById(R.id.categories_spinner);

        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().isEmpty()){
                    title.setError(title.getHint());
                    return;
                }
                if (details.getText().toString().isEmpty()){
                    details.setError(details.getHint());
                    return;
                }
                Complain complain=new Complain(null);
                complain.setCategory(categorie.getSelectedItem().toString());
                complain.setTitle(title.getText().toString());
                complain.setTitle(details.getText().toString());
                Complain_root root=new Complain_root();
                root.PostComplain(mcontext, complain, new Complain_root.complain_Listener() {
                    @Override
                    public void onSuccess(Complains complain) {
                        if (complain.getStatus_code()==200){
                            if (new User_info(mcontext).getLanguage().equals("en")){
                                Toast.makeText(mcontext,complain.getMessageEn(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(mcontext,complain.getMessageAr(),Toast.LENGTH_SHORT).show();

                            }
                            mBottomSheetDialog.dismiss();
                        }
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });



            }
        });


    }

    private void getLanguge() {
        if (new User_info(this).getLanguage().equals("en")){
           langu.setText("En");
        }else {
            langu.setText("Ar");


        }
    }
}
