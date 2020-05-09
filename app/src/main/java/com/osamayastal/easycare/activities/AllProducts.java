package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Categories;
import com.osamayastal.easycare.Model.Controle.Products;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllProducts extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(this);
        setContentView(R.layout.activity_all_products);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        provider_id=bundle.getString("provider_id");

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
private String provider_id="";
    private void Loading() {
        Categories_root root=new Categories_root();
        root.GetProducts(this, provider_id, 0, new Categories_root.product_Listener() {
            @Override
            public void onSuccess(Products products) {
                progressBar.setVisibility(View.GONE);
                productList.clear();
                productList.addAll(products.getItems());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    ProgressBar progressBar;
    private ImageButton back;
    RecyclerView RV;
    List<Product> productList;
    Product_adapter adapter;
    private void init() {
        progressBar=findViewById(R.id.progress);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
        /******************Actions*******************/
        back.setOnClickListener(this);
        productList=new ArrayList<>();
        adapter=new Product_adapter(this, productList, new Product_adapter.Selected_item() {
            @Override
            public void Onselcted(Product product) {

            }
        });
        RV.setLayoutManager(new GridLayoutManager(this,2));
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

