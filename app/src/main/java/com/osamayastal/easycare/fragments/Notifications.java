package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.osamayastal.easycare.R;
import com.suke.widget.SwitchButton;


public class Notifications extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notifications, container, false);
        init(view);
        return view;
    }
private ImageButton back;
    SwitchButton switchButton;
    RecyclerView RV;

    private void init(View view) {
      back=view.findViewById(R.id.back_btn);
        switchButton=view.findViewById(R.id.notification_switch);
        RV=view.findViewById(R.id.RV);

    }
}
