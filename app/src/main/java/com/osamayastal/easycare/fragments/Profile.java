package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;


public class Profile extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        Loading_data();
        return view;
    }

    private void Loading_data() {
        user user=new user();
//        user.egetprofile
    }

    private TextView email,phone,city,favorit_nb,order_nb;
    private Button logout_btn;
    private ImageView edit_btn;
    private void init(View view) {
        email=view.findViewById(R.id.emial_tv);
        phone=view.findViewById(R.id.phone_tv);
        city =view.findViewById(R.id.city_tv);
        favorit_nb=view.findViewById(R.id.favorit_nb);
        order_nb=view.findViewById(R.id.order_nb);
        logout_btn=view.findViewById(R.id.logout_btn);
        edit_btn=view.findViewById(R.id.edit_btn);
        /*****************************Actions****************************/
        logout_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout_btn:
                user user=new user();
                user.Post_Logout(getContext(), new user.user_Listener() {
                    @Override
                    public void onSuccess(users new_account) {
                        new User_info(new User(),getContext());
                        getActivity().finish();
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
        }
    }
}
