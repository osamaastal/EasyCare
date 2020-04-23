package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.osamayastal.easycare.Adapters.Basket_Products_adapter;
import com.osamayastal.easycare.Adapters.Basket_Service_adapter;
import com.osamayastal.easycare.Model.Classes.Basket.Bascket;
import com.osamayastal.easycare.Popups.OrderPop;
import com.osamayastal.easycare.R;

public class OrderDetails_Create extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details__create);
        init();
        Loading();
    }
    private ImageButton back_btn,date_btn,time_btn;
    private Button save;
    private TextView name,item_nb,sub_tot,discount,tot_price,totale,date_tv,time_tv;
    private RecyclerView service_RV,product_RV;
    public static Bascket bascket=null;
    private Basket_Service_adapter service_adapter;
    private Basket_Products_adapter products_adapter;
    private void init(){
        back_btn=findViewById(R.id.back_btn);
        date_btn=findViewById(R.id.date_btn);
        time_btn=findViewById(R.id.time_btn);
        save=findViewById(R.id.save_btn);
        name=findViewById(R.id.provider_name_tv);
        item_nb=findViewById(R.id.item_nb);
        sub_tot=findViewById(R.id.price_before_discount_tv);
        discount=findViewById(R.id.discount_amount_tv);
        tot_price=findViewById(R.id.total_price_tv);
        totale=findViewById(R.id.total);
        time_tv=findViewById(R.id.time_tv);
        date_tv=findViewById(R.id.date_tv);
        service_RV=findViewById(R.id.RV_services);
        product_RV=findViewById(R.id.RV_products);
     /***************************************************************/
        back_btn.setOnClickListener(this);
        date_btn.setOnClickListener(this);
        time_btn.setOnClickListener(this);
        save.setOnClickListener(this);



    }
private void Loading(){
        if (bascket!=null){
            name.setText(bascket.getProvider().getName());
            item_nb.setText(bascket.getCategories().size()+bascket.getProducts().size()+"");
            date_tv.setText(OrderPop.date);
            time_tv.setText(OrderPop.time);
            service_adapter=new Basket_Service_adapter(this,bascket.getCategories(),bascket.getCategorie(),null);
            service_RV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            service_RV.setAdapter(service_adapter);

            products_adapter=new Basket_Products_adapter(this,bascket.getProducts(),null);
            product_RV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            product_RV.setAdapter(products_adapter);


        }
}
    @Override
    public void onClick(View view) {
        OrderPop pop=new OrderPop(this);
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.date_btn:
                pop.GetDate_pop(new OrderPop.POPLisstenner() {
                    @Override
                    public void ongetResult(String result) {
                        date_tv.setText(result);
                    }
                });
                break;
            case R.id.save_btn:

                break;
            case R.id.time_btn:
                pop.GetTime_pop(new OrderPop.POPLisstenner() {
                    @Override
                    public void ongetResult(String result) {
                        time_tv.setText(result);
                    }
                });

                break;
        }
    }
}
