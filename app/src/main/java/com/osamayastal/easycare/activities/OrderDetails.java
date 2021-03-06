package com.osamayastal.easycare.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.google.firebase.iid.FirebaseInstanceId;
import com.osamayastal.easycare.Adapters.Basket_adapter;
import com.osamayastal.easycare.Model.Classes.Basket.Bascket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.Message.Messages;
import com.osamayastal.easycare.Model.Classes.Message.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Getpayment;
import com.osamayastal.easycare.Model.Controle.Order_Details;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Getway;
import com.osamayastal.easycare.Model.Rootes.Order_root;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.Popups.OrderPop;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import top.defaults.drawabletoolbox.DrawableBuilder;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {
    private  int TEN_MINUTES = 30 * 60 * 1000;

    @Override
    protected void onResume() {
        super.onResume();
        setLocale(this);
    }
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
private String order_id=null;
    private Boolean isChatEnabled=false;
    private void Loading() {
       if (order_id!=null){
           findViewById(R.id.linear_wait).setVisibility(View.VISIBLE);
           Order_root root=new Order_root();
           root.GetOrderDetails(mcontext, order_id, new Order_root.GetOrderDetailsListener() {
               @RequiresApi(api = Build.VERSION_CODES.O)
               @Override
               public void onSuccess(final Order_Details order_details) {

                   findViewById(R.id.linear_wait).setVisibility(View.GONE);
                   order=order_details.getItems().get(0);
                   isChatEnabled=order_details.getChatEnabled();
                   price.setText(String.format("%.2f",order_details.getFinal_total()));
                   subtotal.setText(String.format("%.2f", order_details.getTotal_price()));
                   tax.setText(String.format("%.2f", order_details.getTax()));
                   discount.setText(String.format("%.2f", order_details.getTotal_discount()));
                   total.setText(String.format("%.2f", order_details.getFinal_total()));
                   FetchData();

                   if (order.getStatusId()==3 && order.getUpfront() && order_details.getRemain()!=0.0){
                       AppPop pop=new AppPop();
                       pop.Getway_POP(mcontext, mcontext.getString(R.string.there_is_upfront),
                               new AppPop.goListenner() {
                           @Override
                           public void Go() {
                               Getway root=new Getway();
                               root.GoPayment(mcontext, order_details.getRemain(), null, new Getway.GetwayListener() {
                                   @Override
                                   public void onSuccess(Getpayment payment) {
//                                       payment_id=payment.getPayment_order_id();
                                       Intent intent=new Intent(mcontext, GetWayActivity.class);
                                       Bundle bundle = new Bundle();
                                       bundle.putSerializable("payment",payment);
                                       intent.putExtras(bundle);
                                       startActivityForResult(intent, 2);
                                   }

                                   @Override
                                   public void onStart() {

                                   }

                                   @Override
                                   public void onFailure(String msg) {

                                   }
                               });

                           }

                           @Override
                           public void Cancel() {

                           }
                       });
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 2: {
                if (resultCode == Activity.RESULT_OK) {
                    Getway root=new Getway();
                    root.SendUpfront(mcontext, order_id, new Getway.GetwayListener() {
                        @Override
                        public void onSuccess(Getpayment payment) {

                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });

                }
                break;
            }
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
            int payment_type=order.getPaymentType();
if (new User_info(mcontext).getLanguage().equals("en")){
    payment.setText(order.getPayment_id().get(payment_type-1).getEnName());
}else {
    payment.setText(order.getPayment_id().get(payment_type-1).getArName());

}

////////desabel button  rat
            rate.setVisibility(View.GONE);
////////////////////////////////////////////////

            id.setText(order.getOrder_no());

            date.setText(getdate(order.getDate()));
            time.setText(order.getTime());

            nb_service.setText(order.getCategories().size()+"");
            nb_product.setText(order.getProducts().size()+"");

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

                    if (!isChatEnabled){
                        save.setVisibility(View.GONE);
                    }
                    break;
                case 4://  4- Finished
                    order_status.setImageDrawable(mcontext.getDrawable(R.drawable.ic_order_type_finished));
                    save.setText(mcontext.getString(R.string.reOrder));
                    save.setBackground(mcontext.getDrawable(R.drawable.bg_req_pink_30dp));
                    if (order.getRate()){
                        rate.setVisibility(View.GONE);
                    }else {
                        rate.setVisibility(View.VISIBLE);
                    }

                    break;

                case 5:// 5- Cancled
                    order_status.setImageDrawable(mcontext.getDrawable(R.drawable.ic_order_type_canceled));
                    save.setText(mcontext.getString(R.string.reOrder));
                    save.setBackground(mcontext.getDrawable(R.drawable.bg_req_pink_30dp));

                    break;
            }
            /************************************RV*****************************************/
            List<Bascket> bascketList=new ArrayList<>();
            bascketList.add(order);
            adapter=new Basket_adapter(mcontext, bascketList, new Basket_adapter.Selected_item() {
                @Override
                public void OnRefresh(Car_servece car_servece) {

                }

                @Override
                public void Onselcted(int potions) {

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
    TextView name,date,time,nb_service,nb_product,id,id_top,price,payment;
RecyclerView RV;
Button save,rate;

    Context mcontext=OrderDetails.this;
    TextView subtotal,discount,total,tax;
    private void init() {
        tax=findViewById(R.id.tax_amount_tv);
        subtotal=findViewById(R.id.subtotal);
        discount=findViewById(R.id.discount_amount_tv);
        total=findViewById(R.id.total_price_tv);
        name = findViewById(R.id.userName_tv);
        payment = findViewById(R.id.payment_way_tv);
        Img = findViewById(R.id.userImg);
        order_status = findViewById(R.id.order_status);
        date= findViewById(R.id.date_tv);
        time= findViewById(R.id.time_tv);
        nb_product= findViewById(R.id.nb_products);
        nb_service= findViewById(R.id.nb_service);
        id= findViewById(R.id.order_id);
        id_top= findViewById(R.id.order_id_top);
        price= findViewById(R.id.price);
        RV= findViewById(R.id.RV);
        save= findViewById(R.id.save_btn);
        rate= findViewById(R.id.rate_btn);
        back= findViewById(R.id.back_btn);
        order_status.setVisibility(View.VISIBLE);
        /*****************************Actions*********************/
        save.setOnClickListener(this);
        rate.setOnClickListener(this);
        back.setOnClickListener(this);
        RV.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));

    }
    @Override
    public void onClick(View view) {
        final AppPop pop=new AppPop();
switch (view.getId()){
    case R.id.save_btn:
        switch (order.getStatusId()){
            case 1://1- Pending

                OrderPop pop1=new OrderPop(mcontext);
                pop1.Cancel_order_pop(mcontext, new OrderPop.OrderCanelLisstenner() {
                    @Override
                    public void onCancel(String reson) {
                        Order_root root=new Order_root();
                        root.CancelOrder(mcontext, order_id,reson, new Order_root.PostOrderListener() {
                            @Override
                            public void onSuccess(Result result) {
                                try {
                                    String msg=null;
                                    if (new User_info(mcontext).getLanguage().equals("en")){
                                        Toast.makeText(mcontext,result.getMessageEn(),Toast.LENGTH_LONG).show();
                                        msg=result.getMessageEn();
                                    }else {
                                        Toast.makeText(mcontext,result.getMessageAr(),Toast.LENGTH_LONG).show();//
                                        msg=result.getMessageAr();
                                    }

                                    if (result.isStatus()){
                                        Loading();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
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


                break;
            case 2://2- Accepted
            case 3:// 3- OnProgress
// open conversation
                final Messages messages=new Messages();
                //driver
                messages.setDriver(new User(order.getEmployee_id().get_id(),
                        order.getEmployee_id().getFull_name(),
                        order.getEmployee_id().getImage()
                ,order.getEmployee_id().getFcmToken()));
                //user
                User_info user_info=new User_info(mcontext);
                String tokenFCM = FirebaseInstanceId.getInstance().getToken();
                messages.setUser(new User(user_info.getId(),
                        user_info.getName(),
                       user_info.getImag(),
                       tokenFCM));

                Bundle bundle = new Bundle();
                bundle.putSerializable("driver",messages.getDriver());
                bundle.putSerializable("user",messages.getUser());
                bundle.putString("order_id",order_id);
                bundle.putString("order_number",order.getOrder_no());
                Intent intent=new Intent(mcontext, Chat.class);
                intent.putExtras(bundle);
                startActivity(intent);




                break;

            case 4://  4- Finished
            case 5:// 5- Cancled

                Bundle bundle2 = new Bundle();
                bundle2.putInt("bascket_index",0);
                bundle2.putString("order_id",order_id);
                Intent intent2=new Intent(mcontext, OrderDetails_Create.class);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                finish();
                break;
        }
        break;
    case R.id.back_btn:
        finish();
        break;
    case R.id.rate_btn:
        pop.PostRat_pop(mcontext, order_id, new AppPop.goListenner() {
            @Override
            public void Go() {
                rate.setVisibility(View.GONE);
            }

            @Override
            public void Cancel() {

            }
        });
        break;
}
    }
}
