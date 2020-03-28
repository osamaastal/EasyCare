package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.LoginActivity;
import com.osamayastal.easycare.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class EditProfile extends Fragment implements View.OnClickListener {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        init(view);
        return view;
    }
private ImageView back;
    public EditText email,phone;
    private Button save;
    private void init(View view) {
        back=view.findViewById(R.id.back_btn);
        email=view.findViewById(R.id.email_ed);
        phone=view.findViewById(R.id.phone_ed);
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
        list.add(phone);
        list.add(email);
        if (Verefy(list)){
            user user=new user();

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
