package com.osamayastal.easycare.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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

public class Messages extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        init();
        Loading();
    }
    private void Loading() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference().child("chat");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                findViewById(R.id.progress).setVisibility(View.GONE);
                com.osamayastal.easycare.Model.Classes.Message.Messages  messages=
                        dataSnapshot.getValue(com.osamayastal.easycare.Model.Classes.Message.Messages.class);

                Log.d("dataSnapshot",dataSnapshot.toString());

              try {
                  if (messages.getUser().get_id().equals(new User_info(mcontext).getId())){
                      messages.setOrder_id(dataSnapshot.getKey());
                      messageList.add(messages);
                      adapter.notifyDataSetChanged();
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
        adapter=new Message_adapter(mcontext, messageList, new Message_adapter.Selected_item() {
            @Override
            public void Onselcted(com.osamayastal.easycare.Model.Classes.Message.Messages messages) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("driver",messages.getDriver());
                bundle.putString("order_id",messages.getOrder_id());
                Intent intent=new Intent(mcontext, Chat.class);
                intent.putExtras(bundle);
                startActivity(intent);
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