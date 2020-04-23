package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Adapters.Provider_adapter;
import com.osamayastal.easycare.Adapters.Provider_service_adapter;
import com.osamayastal.easycare.Adapters.Provider_servicies_adapter;
import com.osamayastal.easycare.Adapters.Search_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Categories;
import com.osamayastal.easycare.Model.Controle.Provider;
import com.osamayastal.easycare.Model.Controle.Provider_Details;
import com.osamayastal.easycare.Model.Controle.Search;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.Model.Rootes.ProviderDetails_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

public class Service_Single extends AppCompatActivity implements View.OnClickListener {

    public static Categorie categorie=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__single);
        init();
        Loading();
    }

    private void Loading() {

        if (new User_info(this).getLanguage().equals("en")){
            title.setText(categorie.getEnName());
        }else {
            title.setText(categorie.getArName());
        }
        ProviderDetails_root root=new ProviderDetails_root();
        root.GetALL_pro_By_cat(this, categorie.get_id(), 0, new ProviderDetails_root.AppProv_Listener() {
            @Override
            public void onSuccess(Provider prov) {
                progressBar.setVisibility(View.GONE);
                searchList.clear();
                searchList.addAll(prov.getItems());
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
    TextView title;
    List<com.osamayastal.easycare.Model.Classes.Provider.Provider> searchList;
    Provider_service_adapter adapter;
    private void init() {
        progressBar=findViewById(R.id.progress);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
        title=findViewById(R.id.title);
        /******************Actions*******************/
        back.setOnClickListener(this);
        searchList=new ArrayList<>();
        adapter=new Provider_service_adapter(this, searchList, new Provider_service_adapter.Selected_item() {


            @Override
            public void Onselcted(com.osamayastal.easycare.Model.Classes.Provider.Provider provider) {
                ServiceProfiderDetails.provider_id= provider.get_id();
                startActivity(new Intent(Service_Single.this,ServiceProfiderDetails.class));
                finish();
            }
        });
        RV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
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
