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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osamayastal.easycare.Adapters.Chat_adapter;
import com.osamayastal.easycare.Model.Classes.Message.Message;
import com.osamayastal.easycare.Model.Classes.Message.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Maps;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        order_id=bundle.getString("order_id");
        driver= (User) bundle.getSerializable("driver");

        init();
        Loading();
    }
    FirebaseDatabase database;
    DatabaseReference reference;
    private void Loading() {
        if (order_id!=null) {
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
            database=FirebaseDatabase.getInstance();
            if (driver==null){
                DatabaseReference reference=database.getReference().child("chat").child(order_id);
                reference.child("driver").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        findViewById(R.id.progress).setVisibility(View.GONE);

                        if (dataSnapshot.exists()){
                            driver=dataSnapshot.getValue(User.class);
                            Driver_info();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else {
                findViewById(R.id.progress).setVisibility(View.GONE);
                Driver_info();
            }

         reference=database.getReference().child("chat").child(order_id);
        reference.child("conversation").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                findViewById(R.id.progress).setVisibility(View.GONE);

                try {
                    com.osamayastal.easycare.Model.Classes.Message.Message  message=
                            dataSnapshot.getValue(com.osamayastal.easycare.Model.Classes.Message.Message.class);

                    message.setUid(dataSnapshot.getKey());
                    Log.d("dataSnapshot",dataSnapshot.toString());
                    messageList.add(message);
                    adapter.notifyDataSetChanged();


                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("isRead_user",true);
                    reference.updateChildren(parameters);
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
    }

    private ImageButton back,send,camra;
    private CircleImageView img;
    TextView name;
    EditText msg;
    RecyclerView RV;
    Context mcontext=Chat.this;
    Chat_adapter adapter;
    List<Message> messageList;
    private void init() {
        back=findViewById(R.id.back_btn);
        send=findViewById(R.id.send_btn);
        camra=findViewById(R.id.camera_btn);
        msg=findViewById(R.id.msg_tv);
        name=findViewById(R.id.name);
        img=findViewById(R.id.Img);
        RV=findViewById(R.id.RV);
        /***************************Actions***********************/
        back.setOnClickListener(this);
        send.setOnClickListener(this);
        camra.setOnClickListener(this);
        messageList=new ArrayList<>();
        adapter=new Chat_adapter(mcontext,messageList);
        RV.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));
        RV.setAdapter(adapter);


    }
private void Driver_info(){

    if (driver!=null){
        Picasso.with(mcontext)
                .load(driver.getImg())
                .into(img);
        name.setText(driver.getName());
    }
}
    private String order_id=null;
    private User driver=null;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.send_btn:
               try {
                   add_message();
               } catch (Exception e) {
                   e.printStackTrace();
               }
                break;
        }
    }

    private void add_message() {
        if (msg.getText().toString().isEmpty()){
            msg.setError(msg.getHint());
            return;
        }
        final String msg_=msg.getText().toString();
        final Message message=new Message(msg_,new Date().getTime());
        Log.d("order_id",reference.toString());
        reference.child("conversation").push().setValue(message)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    msg.setText("");

                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("last_msg",message.getContent());
                    parameters.put("edit_time_long",message.getTime_long());
                    parameters.put("isRead_driver",false);
                    parameters.put("isRead_user",true);
                    reference.updateChildren(parameters);
                }
            }
        });
    }
}
