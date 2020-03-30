package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Adapters.Provider_servicies_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Controle.Categories;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

public class AllProducts extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        init();
//        Loading();
    }

    private void Loading() {
        Categories_root root=new Categories_root();
        root.GetCategories(this, new Categories_root.cat_Listener() {
            @Override
            public void onSuccess(Categories catego) {
                progressBar.setVisibility(View.GONE);
                productList.clear();
//                categories.addAll(catego.getItems());
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
        adapter=new Product_adapter(this,productList,null);
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

