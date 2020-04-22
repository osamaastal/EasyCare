package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.osamayastal.easycare.Adapters.Categories_adapter;
import com.osamayastal.easycare.Adapters.Provider_servicies_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Controle.Categories;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

public class AllServices extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);
        init();
        Loading();
    }

    private void Loading() {
        Categories_root root=new Categories_root();
        root.GetCategories(this, new Categories_root.cat_Listener() {
            @Override
            public void onSuccess(Categories catego) {
                progressBar.setVisibility(View.GONE);
                categories.clear();
                categories.addAll(catego.getItems());
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
    List<Categorie> categories;
    Provider_servicies_adapter adapter;
    TextView title;
    private void init() {
        progressBar=findViewById(R.id.progress);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
        title=findViewById(R.id.title);
       /******************Actions*******************/
        back.setOnClickListener(this);
        categories=new ArrayList<>();
        adapter=new Provider_servicies_adapter(this, categories, new Provider_servicies_adapter.Selected_item() {
            @Override
            public void Onselcted(Categorie categorie) {
                Service_Single.categorie = categorie;
                startActivity(new Intent(AllServices.this, Service_Single.class));
                finish();
            }
        });
        RV.setLayoutManager(new GridLayoutManager(this,3));
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
