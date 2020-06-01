package com.osamayastal.easycare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.ConfCode;
import com.osamayastal.easycare.activities.LoginActivity;
import com.osamayastal.easycare.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginFrag extends Fragment implements View.OnClickListener {
private TextView forget,logup,skip;
private EditText phone,passord;
private Button login_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        forget=view.findViewById(R.id.forget);
        logup=view.findViewById(R.id.logup);
        phone=view.findViewById(R.id.phone_ed);
        passord=view.findViewById(R.id.password_ed);
        login_btn=view.findViewById(R.id.login_btn);
        skip=view.findViewById(R.id.skip);
        /**************************Actions*****************************/
        if (new User_info(getContext()).getLanguage().equals("ar")){
            skip.setBackgroundResource(R.drawable.bg_blue_gradiant_26dp_right);
        }
        login_btn.setOnClickListener(this);
        forget.setOnClickListener(this);
        logup.setOnClickListener(this);
        skip.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logup:
                switchFGM(new SignUp());
                break;
            case R.id.forget:
                switchFGM(new ResetPassword());
                break;
            case R.id.login_btn:
                login_fun();
                break;
            case R.id.skip:
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                break;

        }
    }

    private void login_fun() {

        List<EditText> list=new ArrayList<>();
        list.add(phone);
        list.add(passord);
        if (Verefy(list)){
            final user user=new user();
            String tokenFCM = FirebaseInstanceId.getInstance().getToken();
            Log.d("tokenFCM",tokenFCM);
            user.Post_Login(getContext(), tokenFCM, passord.getText().toString(),
                   "966"+ phone.getText().toString(), new user.user_Listener() {
                        @Override
                        public void onSuccess(users new_account) {
                            if (new User_info(getContext()).getLanguage().equals("en")){
                                Toast.makeText(getContext(),new_account.getMessageEn(),Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getContext(),new_account.getMessageAr(),Toast.LENGTH_SHORT).show();
                            }
                            if (new_account.getItems().getId()==null){
                                return;
                            }
                            new User_info().Password(passord.getText().toString(),getContext());
                            if (new_account.getItems().isIsVerify() ) {

                                new User_info(new_account.getItems(),getContext());
                                new User_info().DO_CONF_phone(getContext());

                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }
                            else {
                                Intent intent=new Intent(getActivity(), ConfCode.class);
                                intent.putExtra("user_id",new_account.getItems().getId());
                                startActivity(intent);
                                getActivity().finish();
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
    }

    private boolean Verefy(List<EditText> list) {
        for (EditText ed:list
             ) {
            if (ed.getText().toString().isEmpty()){
                ed.setError(ed.getHint());
                return false;
            }
        }
        return true;
    }

    public void switchFGM(Fragment fragment){
        LoginActivity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        LoginActivity. transaction.replace(R.id.LoginContainer, fragment);
        LoginActivity. transaction.commit();
    }
}
