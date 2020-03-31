package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.osamayastal.easycare.Model.Classes.Wellcom;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.WelcomeActivity;
import com.squareup.picasso.Picasso;

public class Welcome1 extends Fragment {


    public Wellcom welcom=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_welcome1, container, false);
        init(view);
        return view;
    }

    private    TextView main,sub;
    private ImageView icon;

    private void init(View view) {
        main = view.findViewById(R.id.main_tv);
        sub = view.findViewById(R.id.sub_tv);
        icon= view.findViewById(R.id.icon);


        /******************************************/
       if (welcom!=null){

          if (new User_info(getContext()).getLanguage().equals("en")){
              main.setText(welcom.getTitle());
              sub.setText(welcom.getDescription());
          }else {
              main.setText(welcom.getTitleAr());
              sub.setText(welcom.getDescriptionAr());
          }
           Picasso.with(getContext())
                   .load(welcom.getIcon())
                   .into(icon);
       }
    }

}
