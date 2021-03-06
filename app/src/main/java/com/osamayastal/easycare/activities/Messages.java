package com.osamayastal.easycare.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osamayastal.easycare.Adapters.Chat_adapter;
import com.osamayastal.easycare.Adapters.Message_adapter;
import com.osamayastal.easycare.Model.Classes.Message.Message;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Messages extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messages);
        init();

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
    @Override
    protected void onResume() {
        super.onResume();
        setLocale(this);
        if (FirebaseDatabase.getInstance() != null)
        {
            FirebaseDatabase.getInstance().goOnline();
        }
        Loading();
    }

    private void Loading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progress).setVisibility(View.GONE);
                if (messageList.size()==0)
                findViewById(R.id.no_data).setVisibility(View.VISIBLE);

            }
        }, 2000);

        messageList.clear();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference().child("chat");
        reference.orderByChild("edit_time_long").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


              try {

                  findViewById(R.id.progress).setVisibility(View.GONE);
                  com.osamayastal.easycare.Model.Classes.Message.Messages  messages=
                          dataSnapshot.getValue(com.osamayastal.easycare.Model.Classes.Message.Messages.class);

                  Log.d("dataSnapshot",dataSnapshot.toString());
                  Log.d("dataSnapshot",dataSnapshot.child("isRead_user").toString());

                  if (messages.getUser().get_id().equals(new User_info(mcontext).getId())
                          && !messages.getIs_user_delete()){
                      findViewById(R.id.no_data).setVisibility(View.GONE);
                      messages.setRead_user(dataSnapshot.child("isRead_user").getValue(Boolean.class));
                      messages.setOrder_id(dataSnapshot.getKey());
                      messageList.add(messages);
                      Log.d("messageList",messageList.size()+"");

                      adapter=new Message_adapter(mcontext, messageList, new Message_adapter.Selected_item() {
                          @Override
                          public void Onselcted(com.osamayastal.easycare.Model.Classes.Message.Messages messages) {
                              Bundle bundle = new Bundle();
                              bundle.putSerializable("user",messages.getUser());
                              bundle.putSerializable("driver",messages.getDriver());
                              bundle.putString("order_id",messages.getOrder_id());
                              bundle.putBoolean("is_driver_delete",messages.getIs_driver_delete());
                              Intent intent=new Intent(mcontext, Chat.class);
                              intent.putExtras(bundle);
                              startActivity(intent);
                          }

                          @Override
                          public void ondelete(com.osamayastal.easycare.Model.Classes.Message.Messages messages) {
                              String id=messages.getOrder_id();
                            FirebaseDatabase  database=FirebaseDatabase.getInstance();
                            DatabaseReference  reference=database.getReference().child("chat");
                              reference.child(id).child("is_user_delete").setValue(true);
                              messageList.remove(messages);
                              adapter.notifyDataSetChanged();
                          }
                      });
                      RV.setAdapter(adapter);

                      Handler handler = new Handler();
                      handler.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              if (adapter.getItemCount() > 0) {
                                  RV.smoothScrollToPosition(0);
                              }
                          }
                      }, 300);
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                try {
                    findViewById(R.id.progress).setVisibility(View.GONE);
                    com.osamayastal.easycare.Model.Classes.Message.Messages  messages=
                            dataSnapshot.getValue(com.osamayastal.easycare.Model.Classes.Message.Messages.class);

                    Log.d("dataSnapshot",dataSnapshot.toString());
                    Log.d("dataSnapshot",dataSnapshot.child("isRead_user").toString());

                    if (messages.getUser().get_id().equals(new User_info(mcontext).getId())
                            && !messages.getIs_user_delete()){
                        for (com.osamayastal.easycare.Model.Classes.Message.Messages m:messageList
                             ) {
                            if (m.getOrder_id().equals(dataSnapshot.getKey())){
                                messageList.remove(m);
                            }
                        }
                        messages.setRead_user(dataSnapshot.child("isRead_user").getValue(Boolean.class));
                        messages.setOrder_id(dataSnapshot.getKey());
                        messageList.add(messages);
                        adapter=new Message_adapter(mcontext, messageList, new Message_adapter.Selected_item() {
                            @Override
                            public void Onselcted(com.osamayastal.easycare.Model.Classes.Message.Messages messages) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user",messages.getUser());
                                bundle.putSerializable("driver",messages.getDriver());
                                bundle.putString("order_id",messages.getOrder_id());
                                bundle.putString("order_number",messages.getOrder_number());
                                Intent intent=new Intent(mcontext, Chat.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            @Override
                            public void ondelete(com.osamayastal.easycare.Model.Classes.Message.Messages messages) {
                                String id=messages.getOrder_id();
                                FirebaseDatabase  database=FirebaseDatabase.getInstance();
                                DatabaseReference  reference=database.getReference().child("chat");
                                reference.child(id).child("is_user_delete").setValue(true);
                                messageList.remove(messages);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        RV.setAdapter(adapter);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter.getItemCount() > 0) {
                                    RV.smoothScrollToPosition(0);
                                }
                            }
                        }, 300);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ProgressBar progressBar;
    private ImageButton back;
    RecyclerView RV;
    List<com.osamayastal.easycare.Model.Classes.Message.Messages> messageList;
    Message_adapter adapter;
    Context mcontext=Messages.this;
    private void init() {
        progressBar=findViewById(R.id.progress);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
        /******************Actions*******************/
        back.setOnClickListener(this);
        messageList=new ArrayList<>();

        RV.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                FirebaseDatabase.getInstance().goOffline();

                Messages.this.finish();
                break;
        }
    }



    @Override
    public void onPause() {

        super.onPause();

        if(FirebaseDatabase.getInstance()!=null)
        {
            FirebaseDatabase.getInstance().goOffline();
        }
    }
}