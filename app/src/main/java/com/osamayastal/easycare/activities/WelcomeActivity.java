package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osamayastal.easycare.Model.Classes.Wellcom;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Rootes.Wellcom_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.Welcome1;
import com.osamayastal.easycare.fragments.Welcome2;
import com.osamayastal.easycare.fragments.Welcome3;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private TextView skip,en,ar;
    private ImageView valid_en,valid_ar;
    private final Fragment[] PAGES = new Fragment[]{
            new Welcome1(),
            new Welcome2(),
            new Welcome3()
    };
    private List<Wellcom> wellcomList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();


    }

    private void init() {
        mPager = findViewById(R.id.viewPager);
        skip = findViewById(R.id.skip);
        en = findViewById(R.id.en_tv);
        ar = findViewById(R.id.ar_tv);
        valid_ar = findViewById(R.id.valid_ar);
        valid_en = findViewById(R.id.valid_en);



        /********************************Actions****************************************/
        skip.setOnClickListener(this);
        ar.setOnClickListener(this);
        en.setOnClickListener(this);

        wellcomList=new ArrayList<>();

        Loading();

    }

    private void Loading() {
        languge();
        Wellcom_root root=new Wellcom_root();
        root.Get_WellcomPages(this, new Wellcom_root.Wellcom_Listener() {
            @Override
            public void onSuccess(com.osamayastal.easycare.Model.Controle.Wellcom wellcom) {
                wellcomList.addAll(wellcom.getItems());
                mPager.setAdapter( new MyPagerAdapter(getSupportFragmentManager()));
                CircleIndicator indicator = findViewById(R.id.indicator);
                indicator.setViewPager(mPager);
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
    public void onClick(View view) {

        MainActivity mainActivity=new MainActivity();
        switch (view.getId()){
            case R.id.skip:
                User_info user_info=new User_info();
                user_info.DO_INTRO(WelcomeActivity.this);
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.ar_tv:

          mainActivity.ChangeLanguge_pop("ar", this, new MainActivity.chang() {
              @Override
              public void onSave(Boolean v) {
                  if (v){
                      valid_ar.setVisibility(View.VISIBLE);
                      valid_en.setVisibility(View.GONE);
                  }
              }
          });




                break;
            case R.id.en_tv:

               if(mainActivity.ChangeLanguge_pop("en", this, new MainActivity.chang() {
                   @Override
                   public void onSave(Boolean v) {
                       if (v){
                           valid_en.setVisibility(View.VISIBLE);
                           valid_ar.setVisibility(View.GONE);
                       }
                   }
               }));

                break;
        }
    }
private void languge(){
    if (new User_info(this).getLanguage().equals("en")){
        valid_en.setVisibility(View.VISIBLE);
        valid_ar.setVisibility(View.GONE);
    }else {
        valid_ar.setVisibility(View.VISIBLE);
        valid_en.setVisibility(View.GONE);
    }
}
    public class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
//            return PAGES[position];
            findViewById(R.id.progress).setVisibility(View.GONE);
            Welcome1 fragment=new Welcome1();
           fragment.welcom=wellcomList.get(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return wellcomList.size();
        }

    }
}
