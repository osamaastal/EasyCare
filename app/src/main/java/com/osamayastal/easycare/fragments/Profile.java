package com.osamayastal.easycare.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.osamayastal.easycare.activities.LoginActivity;
import com.osamayastal.easycare.activities.MainActivity;


public class Profile extends Fragment implements View.OnClickListener {
    public static User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        User_info user_info=new User_info();

        init(view);
        Loading_data();
        return view;
    }

    private void Loading_data() {
       User_info info=new User_info(getContext());


        user user=new user();
        user.GET_profil(getContext(), new user.user_Listener() {
            @Override
            public void onSuccess(users account) {
                email.setText(account.getItems().getEmail());
                phone.setText(account.getItems().getPhoneNumber());
                city.setText(account.getItems().getAddress());
                favorit_nb.setText(account.getItems().getFavoritCount()+"");
                order_nb.setText(account.getItems().getOrderCount()+"");
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });


    }
private ConstraintLayout favorit,order;
    private TextView email,phone,city,favorit_nb,order_nb;
    private Button changPW_btn;
    private ImageView edit_btn;
    private void init(View view) {
        favorit=view.findViewById(R.id.favorit_btn);
        order=view.findViewById(R.id.order_btn);
        email=view.findViewById(R.id.emial_tv);
        phone=view.findViewById(R.id.phone_tv);
        city =view.findViewById(R.id.city_tv);
        favorit_nb=view.findViewById(R.id.favorit_nb);
        order_nb=view.findViewById(R.id.order_nb);
        changPW_btn=view.findViewById(R.id.logout_btn);
        edit_btn=view.findViewById(R.id.edit_btn);
        /*****************************Actions****************************/
        changPW_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);
        favorit.setOnClickListener(this);
        order.setOnClickListener(this);
    }
    public void switchFGM(Fragment fragment){
        MainActivity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        MainActivity. transaction.replace(R.id.mainContainer, fragment);
        MainActivity. transaction.commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout_btn:
                switchFGM(new ChangePass());
                break;
            case R.id.edit_btn:
                switchFGM(new EditProfile());
                break;
            case R.id.favorit_btn:
                switchFGM(new MyFavorites());
                break;
            case R.id.order_btn:

                break;
        }
    }
}
