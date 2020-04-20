package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.osamayastal.easycare.Model.Classes.StaticPage;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.StaticPages;
import com.osamayastal.easycare.Model.Rootes.StaticPage_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.MainActivity;


public class AboutUs extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_us, container, false);
        init(view);
        Loading();
        return view;
    }

    private void Loading() {
        StaticPage_root root=new StaticPage_root();
        root.Get_StaticPages(getContext(), new StaticPage_root.StaticPages_Listener() {
            @Override
            public void onSuccess(StaticPages staticPages) {
                if (staticPages.getStatus_code()==200){
                    for (StaticPage staticPage: staticPages.getItems()
                         ) {
                        if (staticPage.get_id().equals("5e418d96ee6c030024e30751")){
                            if (new User_info(getContext()).getLanguage().equals("en")){
                                about_us.setText(staticPage.getEnContent());
                                title.setText(staticPage.getEnTitle());
                            }else {
                                about_us.setText(staticPage.getArContent());
                                title.setText(staticPage.getArTitle());

                            }
                            return;
                        }
                    }
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

    private TextView about_us,title;
    private Button web_sit_btn;
    private ImageButton back;
    private void init(View view) {
        about_us=view.findViewById(R.id.about_us);
        title=view.findViewById(R.id.title);
        web_sit_btn=view.findViewById(R.id.web_site_btn);
        back=view.findViewById(R.id.back_btn);
        /*****************************Actions*******************************/
        web_sit_btn.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.web_site_btn:
                break;
            case R.id.back_btn:
                switchFGM(new Home());
                MainActivity.SetVisibillty(View.VISIBLE);
                break;
        }
    }
    public void switchFGM(Fragment fragment){
        MainActivity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        MainActivity. transaction.replace(R.id.mainContainer, fragment);
        MainActivity. transaction.commit();
    }
}
