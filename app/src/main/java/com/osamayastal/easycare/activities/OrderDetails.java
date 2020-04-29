package com.osamayastal.easycare.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osamayastal.easycare.Adapters.Basket_adapter;
import com.osamayastal.easycare.Model.Classes.Basket.Bascket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.Message.Messages;
import com.osamayastal.easycare.Model.Classes.Message.User;
import com.osamayastal.easycare.Model.Classes.Order;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Order_Details;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Order_root;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.defaults.drawabletoolbox.DrawableBuilder;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_details);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        order_id=bundle.getString("order_id");

        init();
        Loading();
    }
private String order_id=null;
    private void Loading() {
       if (order_id!=null){
           findViewById(R.id.linear_wait).setVisibility(View.VISIBLE);
           Order_root root=new Order_root();
           root.GetOrderDetails(mcontext, order_id, new Order_root.GetOrderDetailsListener() {
               @RequiresApi(api = Build.VERSION_CODES.O)
               @Override
               public void onSuccess(Order_Details order_details) {
                   findViewById(R.id.linear_wait).setVisibility(View.GONE);
                   order=order_details.getItems().get(0);
                   FetchData();
               }

               @Override
               public void onStart() {

               }

               @Override
               public void onFailure(String msg) {

               }
           });
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getdate(String date_){
        Log.e("date_", date_.substring(0, date_.indexOf("T")));
        return date_.substring(0, date_.indexOf("T"));
    }
    private void makeDrawable(int color, View view, int corner) {
        Drawable drawable = new DrawableBuilder()
                .rectangle()
                .solidColor(color)//0xffe67e22
//                .height(90)
//                .width(90)
                .cornerRadii(corner, corner, corner, corner)// pixel
                // top-left  top-right  bottom-right   bottom-left
                .build();
        view.setBackground(drawable);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void FetchData() {
        if (order!=null){
            name.setText(order.getProvider().getName());
if (new User_info(mcontext).getLanguage().equals("en")){
    payment.setText(order.getPayment_id().get(0).getEnName());
}else {
    payment.setText(order.getPayment_id().get(0).getArName());

}
    id.setText(order.getOrder_no());
            id_top.setText(order.getOrder_no());
            date.setText(getdate(order.getDate()));
            time.setText(order.getTime());
            price.setText(String.format("%.2f",order.getTotal()));
            if (new User_info(mcontext).getLanguage().equals("en")){
                type.setText(order.getCategorie().getEnName());
            }else {
                type.setText(order.getCategorie().getArName());
            }
            try{
                String color=order.getCategorie().getColor();
                makeDrawable(Color.parseColor(color),type,18);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Picasso.with(mcontext)
                        .load(order.getProvider().getImage())
                        .into(Img);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("order.getStatusId()",order.getStatusId()+"");
            switch (order.getStatusId()){
                case 1://1- Pending
                    order_status.setImageDrawable(mcontext.getDrawable(R.drawable.ic_order_type_waiting));
                save.setText(mcontext.getString(R.string.cancel_order));
                save.setBackground(mcontext.getDrawable(R.drawable.bg_req_orange_30dp));
                break;
                case 2://2- Accepted
                case 3:// 3- OnProgress
                    order_status.setImageDrawable(mcontext.getDrawable(R.drawable.ic_order_type_current));
                    save.setText(mcontext.getString(R.string.contact_with_provider));
                    save.setBackground(mcontext.getDrawable(R.drawable.bg_req_purple_30dp));
                    break;
                case 4://  4- Finished
                    order_status.setImageDrawable(mcontext.getDrawable(R.drawable.ic_order_type_finished));
                    save.setText(mcontext.getString(R.string.conf_finish_order));
                    save.setBackground(mcontext.getDrawable(R.drawable.bg_req_green_gradiant_30dp));
                    break;

                case 5:// 5- Cancled
                    order_status.setImageDrawable(mcontext.getDrawable(R.drawable.ic_order_type_canceled));
                    save.setVisibility(View.GONE);
                    break;
            }
            /************************************RV*****************************************/
            List<Bascket> bascketList=new ArrayList<>();
            bascketList.add(order);
            adapter=new Basket_adapter(mcontext, bascketList, new Basket_adapter.Selected_item() {
                @Override
                public void Onselcted(Car_servece car_servece) {

                }
            });
            RV.setAdapter(adapter);
            adapter.isOrder=true;
            adapter.notifyDataSetChanged();
        }
    }
ImageButton back;
    Bascket order=null;
    Basket_adapter adapter;
    ImageView Img;
    ImageView order_status;
    TextView name,date,time,type,id,id_top,price,payment;
RecyclerView RV;
Button save;
    Context mcontext=OrderDetails.this;

    private void init() {
        name = findViewById(R.id.userName_tv);
        payment = findViewById(R.id.payment_way_tv);
        Img = findViewById(R.id.userImg);
        order_status = findViewById(R.id.order_status);
        date= findViewById(R.id.date_tv);
        time= findViewById(R.id.time_tv);
        type= findViewById(R.id.type_tv);
        id= findViewById(R.id.order_id);
        id_top= findViewById(R.id.order_id_top);
        price= findViewById(R.id.price);
        RV= findViewById(R.id.RV);
        save= findViewById(R.id.save_btn);
        back= findViewById(R.id.back_btn);
        type.setVisibility(View.VISIBLE);
        order_status.setVisibility(View.VISIBLE);
        /*****************************Actions*********************/
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        RV.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));

    }
    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.save_btn:
        switch (order.getStatusId()){
            case 1://1- Pending
                Order_root root=new Order_root();
                root.CancelOrder(mcontext, order_id, new Order_root.PostOrderListener() {
                    @Override
                    public void onSuccess(Result result) {
                        try {
                            if (new User_info(mcontext).getLanguage().equals("en")){
                                Toast.makeText(mcontext,result.getMessageEn(),Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(mcontext,result.getMessageAr(),Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Loading();
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
            case 2://2- Accepted
            case 3:// 3- OnProgress
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference reference=database.getReference().child("chat").child(order_id);
                Messages messages=new Messages();
                messages.setDriver(new User("5e8ca63507f8d278acbe0999",
                        "Osama Youcef",
                        "https://res.cloudinary.com/dkos8ethw/image/upload/v1586275893/m1glent9ytswjpl9mtx0.jpg"));
                messages.setUser(new User(new User_info(mcontext).getId(),
                        new User_info(mcontext).getName(),
                        new User_info(mcontext).getImag()));
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("driver",messages.getDriver());
                parameters.put("user",messages.getUser());
                reference.updateChildren(parameters)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("driver",null);
                            bundle.putSerializable("order_id",order_id);
                            Intent intent=new Intent(mcontext, Chat.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });


                break;

            case 4://  4- Finished

                break;

            case 5:// 5- Cancled

                break;
        }
        break;
    case R.id.back_btn:
        finish();
        break;
}
    }
}
