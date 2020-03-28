package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class ChangePass extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_pass, container, false);
        init(view);
        return view;
    }

    private ImageView back;
    public EditText lastPW,newPW,confPW;
    private Button save;
    private void init(View view) {
        back=view.findViewById(R.id.back_btn);
        lastPW=view.findViewById(R.id.last_password_ed);
        newPW=view.findViewById(R.id.new_password_ed);
        confPW=view.findViewById(R.id.conf_password_ed);
        save=view.findViewById(R.id.save_btn);
        /******************************Actions***************************************/
        save.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_btn:
                update_data();
                break;
            case R.id.back_btn:
                switchFGM(new Profile());
                break;

        }
    }
    public void switchFGM(Fragment fragment){
        MainActivity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        MainActivity. transaction.replace(R.id.mainContainer, fragment);
        MainActivity. transaction.commit();
    }
    private void update_data() {
        List<EditText> list=new ArrayList<>();
        list.add(lastPW);
        list.add(newPW);
        list.add(confPW);
        if (Verefy(list)){
            if (!lastPW.getText().toString().equals(new User_info(getContext()).getPw())){
                lastPW.setError(newPW.getHint());
                return;
            }
            if (!newPW.getText().toString().equals(confPW.getText().toString())){
                newPW.setError(newPW.getHint());
                confPW.setError(confPW.getHint());
                return;
            }

            user user=new user();
            user.Post_change_password(getContext(), newPW.getText().toString(), new user.user_Listener() {
                @Override
                public void onSuccess(users new_account) {
                   if (new_account.isStatus()){
                       if (new User_info(getContext()).getLanguage().equals("en")){
                           Toast.makeText(getContext(),new_account.getMessageEn(),Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(getContext(),new_account.getMessageAr(),Toast.LENGTH_SHORT).show();
                       }
                       new User_info(new_account.getItems(),getContext());
                   }
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure(String msg) {

                }
            });

        }else {
            return;
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
}
