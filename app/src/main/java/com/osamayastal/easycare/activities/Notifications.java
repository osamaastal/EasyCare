package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.osamayastal.easycare.Adapters.Notifications_adapter;
import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Model.Classes.Notification;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Products;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Controle.notification;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.R;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Notifications extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(this);
        setContentView(R.layout.activity_notifications);
        init();
        Loading();
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
    private void Loading() {
        findViewById(R.id.no_data).setVisibility(View.GONE);
        com.osamayastal.easycare.Model.Rootes.Notifications root=new com.osamayastal.easycare.Model.Rootes.Notifications();
        root.Getnotification(Notifications.this, new com.osamayastal.easycare.Model.Rootes.Notifications.notificationListener() {
            @Override
            public void onSuccess(notification show_notif) {
                progressBar.setVisibility(View.GONE);
                notifications.clear();
                notifications.addAll(show_notif.getItems());
                adapter.notifyDataSetChanged();
                if (notifications.size()==0){
                     findViewById(R.id.no_data).setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onSuccess(Result result) {

            }

            @Override
            public void onSuccess(users users) {

            }


        });

    }
    SwitchButton switchButton;
    ProgressBar progressBar;
    private ImageButton back;
    RecyclerView RV;
    List<Notification> notifications;
    Notifications_adapter adapter;
    Context mcontext=Notifications.this;
    private void init() {
        progressBar=findViewById(R.id.progress);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
        switchButton=findViewById(R.id.notification_switch);
        /******************Actions*******************/
        switchButton.setChecked(new User_info(mcontext).isEnableNotifications());
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                com.osamayastal.easycare.Model.Rootes.Notifications root=new com.osamayastal.easycare.Model.Rootes.Notifications();
                root.Putnotification(Notifications.this, isChecked,
                        new com.osamayastal.easycare.Model.Rootes.Notifications.notificationListener() {
                            @Override
                            public void onSuccess(notification show_notif) {

                            }

                            @Override
                            public void onSuccess(Result result) {

                            }

                            @Override
                            public void onSuccess(users users) {
                                if (new User_info(Notifications.this).getLanguage().equals("en")){
                                    Toast.makeText(Notifications.this,users.getMessageEn(),Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Notifications.this,users.getMessageAr(),Toast.LENGTH_SHORT).show();
                                }
                                if (users.isStatus()){
                                    new User_info(users.getItems(),mcontext);
                                }
                            }


                        });

            }
        });
        back.setOnClickListener(this);
        notifications=new ArrayList<>();
        adapter=new Notifications_adapter(this, notifications, new Notifications_adapter.Selected_item() {
            @Override
            public void Onselcted(Notification notification) {
                switch (notification.getType()){//حالات التنبيهات: type
                    //١- طلبات

                    case 1:
                        Intent intent=new Intent(mcontext, OrderDetails.class);
                        intent.putExtra("order_id",notification.getBody_parms());
                        startActivity(intent);

                        break;
                    case 2://٢- تقييمات
                    case 4://٤- مسجات عامة من لوحة التحكم — مابفتح اي شي
                    case 3://٣- كوبونات او عروض — مابفتح اي شي
                                break;

                }

            }
        });
        RV.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));
        RV.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
