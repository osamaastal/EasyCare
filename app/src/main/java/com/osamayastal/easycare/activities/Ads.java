package com.osamayastal.easycare.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Adapters.AdsAdapter;
import com.osamayastal.easycare.Adapters.CardAdapter;
import com.osamayastal.easycare.Adapters.Provider_service_adapter;
import com.osamayastal.easycare.Model.Classes.Slider;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Provider;
import com.osamayastal.easycare.Model.Rootes.ProviderDetails_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Ads extends AppCompatActivity implements View.OnClickListener {

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
        root.GetALL_ADS(this, 0, new ProviderDetails_root.AppADS_Listener() {


            @Override
            public void onSuccess(com.osamayastal.easycare.Model.Controle.Ads ads) {
                progressBar.setVisibility(View.GONE);
                sliderList.clear();
                sliderList.addAll(ads.getItems());
                Slide_adapter.notifyDataSetChanged();
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
    private List<Slider> sliderList;
    private AdsAdapter Slide_adapter;
    Context mcontext=Ads.this;
    private void init() {
        progressBar=findViewById(R.id.progress);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
        title=findViewById(R.id.title);
        title.setText(getString(R.string.menu_last_offers));

        /******************Actions*******************/
        back.setOnClickListener(this);
        sliderList=new ArrayList<>();
        Slide_adapter=new AdsAdapter(this, sliderList, new AdsAdapter.Selected_item() {
            @Override
            public void Onselcted(Slider Slider) {


                    if (Slider.getAds_for().equals("2")){
                        Intent intent=new Intent(mcontext, ServiceProfiderDetails.class);
                        intent.putExtra("provider_id",Slider.getStore_id());
                        mcontext.startActivity(intent);
                        finish();
                    }
                    if (Slider.getAds_for().equals("3")) {
                        if (!Slider.getUrl().isEmpty()) {
                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Slider.getUrl()));
                            mcontext.startActivity(myIntent);
                            finish();
                        }
                    }
                }

        });
        RV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        RV.setAdapter(Slide_adapter);

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
