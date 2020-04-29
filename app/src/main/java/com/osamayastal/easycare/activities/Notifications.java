package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
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
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.R;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        init();
        Loading();
    }
    private void Loading() {
        com.osamayastal.easycare.Model.Rootes.Notifications root=new com.osamayastal.easycare.Model.Rootes.Notifications();
        root.Getnotification(Notifications.this, new com.osamayastal.easycare.Model.Rootes.Notifications.notificationListener() {
            @Override
            public void onSuccess(notification show_notif) {
                progressBar.setVisibility(View.GONE);
                notifications.clear();
                notifications.addAll(show_notif.getItems());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess(Result result) {

            }

            @Override
            public void onFailure(String msg) {

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
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                com.osamayastal.easycare.Model.Rootes.Notifications root=new com.osamayastal.easycare.Model.Rootes.Notifications();
                root.Putnotification(Notifications.this, isChecked, new com.osamayastal.easycare.Model.Rootes.Notifications.notificationListener() {
                    @Override
                    public void onSuccess(notification show_notif) {

                    }

                    @Override
                    public void onSuccess(Result result) {
                        if (new User_info(Notifications.this).getLanguage().equals("en")){
                            Toast.makeText(Notifications.this,result.getMessageEn(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Notifications.this,result.getMessageAr(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });
        back.setOnClickListener(this);
        notifications=new ArrayList<>();
        adapter=new Notifications_adapter(this,notifications,null);
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
