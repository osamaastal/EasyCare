package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Adapters.Provider_servicies_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Controle.Provider_Details;
import com.osamayastal.easycare.Model.Rootes.ProviderDetails_root;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceProfiderDetails extends AppCompatActivity implements View.OnClickListener {

    public static Provider provider=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profider_details);
        init();
        Loading();
    }

    private void Loading() {
       if (provider!=null){
           Picasso.with(this)
                   .load(provider.getImage())
                   .into(img_slide);
           /*******/
           Picasso.with(this)
                   .load(provider.getImage())
                   .into(img);
           /*************/
           name.setText(provider.getName());
           address.setText(provider.getAddress());
           ratingBar.setRating(provider.getRate());
           /*******************Loading********************/
           ProviderDetails_root root=new ProviderDetails_root();
           root.GetDetailsListener(this, provider.get_id(), new ProviderDetails_root.DetailsListener() {
               @Override
               public void onSuccess(Provider_Details details) {
                   categorieList.clear();
                   productList.clear();
                   categorieList.addAll(details.getServices());
                   productList.addAll(details.getProducts());
                   serviciesAdapter.notifyDataSetChanged();
                   product_adapter.notifyDataSetChanged();
                   progressBar1.setVisibility(View.GONE);
                   progressBar2.setVisibility(View.GONE);
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

    ProgressBar progressBar1,progressBar2;
    SelectableRoundedImageView img_slide;
    ImageButton like_btn,basket_btn,back_btn;
    CircleImageView img;
    TextView name,address,from,to,type,more_pro,basket_nb;
    RecyclerView prod_RV,serv_RV;
    RatingBar ratingBar;
    List<Product> productList;
    List<Categorie> categorieList;
    Provider_servicies_adapter serviciesAdapter;
    Product_adapter product_adapter;
    private void init() {
        progressBar1=findViewById(R.id.progress1);
        progressBar2=findViewById(R.id.progress2);
        img=findViewById(R.id.provider_Img);
        img_slide=findViewById(R.id.ImgSlider);
        like_btn=findViewById(R.id.like_btn);
        basket_btn=findViewById(R.id.basket_btn);
        back_btn=findViewById(R.id.back_btn);
        name=findViewById(R.id.provider_name_tv);
        address=findViewById(R.id.address_tv);
        from=findViewById(R.id.from_tv);
        to=findViewById(R.id.to_tv);
        type=findViewById(R.id.type_tv);
        more_pro=findViewById(R.id.more_product);
        basket_nb=findViewById(R.id.basket_nb);
        prod_RV=findViewById(R.id.RV_products);
        serv_RV=findViewById(R.id.RV_services);
        ratingBar=findViewById(R.id.ratingBar);
        /*********************************Actions***********************************/
        back_btn.setOnClickListener(this);
        basket_btn.setOnClickListener(this);
        like_btn.setOnClickListener(this);
        more_pro.setOnClickListener(this);
        /*******************************RV******************************************/
        productList=new ArrayList<>();
        categorieList=new ArrayList<>();
        product_adapter=new Product_adapter(this,productList,null);
        serviciesAdapter=new Provider_servicies_adapter(this,categorieList,null);
        serv_RV.setLayoutManager(new GridLayoutManager(this,3));
        prod_RV.setLayoutManager(new GridLayoutManager(this,2));
        serv_RV.setAdapter(serviciesAdapter);
        prod_RV.setAdapter(product_adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.like_btn:
                break;
            case R.id.basket_btn:
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.more_product:
                startActivity(new Intent(ServiceProfiderDetails.this,AllProducts.class));
                break;
        }
    }
}
