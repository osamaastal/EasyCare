package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;

public class ResetPassword extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        final EditText email=view.findViewById(R.id.email_ed);
        Button save=view.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()){
                    email.setError(email.getHint());
                    return;
                }
                user user=new user();
                user.Post_forget_password(getContext(), email.getText().toString(), new user.user_Listener() {
                    @Override
                    public void onSuccess(users new_account) {
                        if (new User_info(getContext()).getLanguage().equals("en")){
                            Toast.makeText(getContext(),new_account.getMessageEn(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),new_account.getMessageAr(),Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });
        return view;
    }
}
