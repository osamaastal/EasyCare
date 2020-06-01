package com.osamayastal.easycare.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Adapters.Provider_service_adapter;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Provider;
import com.osamayastal.easycare.Model.Rootes.ProviderDetails_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Most_rate extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onResume() {
        super.onResume();
        setLocale(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service__single);



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
    private void Loading() {


        ProviderDetails_root root=new ProviderDetails_root();
        root.GetALL_Mor_rate(this, 0, new ProviderDetails_root.AppProv_Listener() {
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
        title.setText(getString(R.string.more_rates));

        /******************Actions*******************/
        back.setOnClickListener(this);
        searchList=new ArrayList<>();
        adapter=new Provider_service_adapter(this, searchList, new Provider_service_adapter.Selected_item() {


            @Override
            public void Onselcted(com.osamayastal.easycare.Model.Classes.Provider.Provider provider) {
                Intent intent=new Intent(Most_rate.this, ServiceProfiderDetails.class);
                intent.putExtra("provider_id",provider.get_id());
                startActivity(intent);
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
