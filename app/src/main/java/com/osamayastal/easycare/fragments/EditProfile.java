package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cncoderx.wheelview.Wheel3DView;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.City_root;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.Auther_activity;
import com.osamayastal.easycare.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class EditProfile extends Fragment implements View.OnClickListener {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        init(view);
        Loading_data();
        return view;
    }

    String city_id=null;
    private void Loading_data() {
        User_info user_info=new User_info(getContext());
        fullname.setText(user_info.getName());
        if (Add_New_Address_Map.Location!=null){
            address.setText(Add_New_Address_Map.Location);
        }else {
            address.setText(user_info.getAddress());
        }
        email.setText(user_info.getEmail());

        city_id=user_info.getCityID();
        Getcity();
    }
    private void show_bottomSheet(){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_choose_city, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final Wheel3DView wheel3DView=sheetView.findViewById(R.id.wheel);
        final ProgressBar progressBar=sheetView.findViewById(R.id.progress);

        wheel3DView.setEntries(cities);
        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city.setText(wheel3DView.getCurrentItem());
                city_id=cityList.get(wheel3DView.getCurrentIndex()).get_id();
                Log.d("City ID", city_id);

                mBottomSheetDialog.dismiss();
            }
        });

    }
    final List<City> cityList=new ArrayList<>();
     List<String> cities=new ArrayList<>();
    private void Getcity(){
        City_root root=new City_root();
        root.GetCities(getContext(), new City_root.cityListener() {
            @Override
            public void onSuccess(final com.osamayastal.easycare.Model.Controle.City cities_) {
              cities=cities_.getCityList();

                cityList.clear();
                cityList.addAll(cities_.getItems());
                for (int i=0; i<cityList.size();i++
                     ) {
                    if (cityList.get(i).get_id().equals(city_id)){
                        city.setText(cities.get(i));
                        return;
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
    private ImageView back;
    public EditText email,fullname;
    private TextView city,address;
    private Button save;
    private void init(View view) {
        back=view.findViewById(R.id.back_btn);
        email=view.findViewById(R.id.email_ed);
        address=view.findViewById(R.id.address_ed);
        city=view.findViewById(R.id.city_tv);
        fullname=view.findViewById(R.id.fullname_ed);
        save=view.findViewById(R.id.save_btn);
        /******************************Actions***************************************/
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        city.setOnClickListener(this);
        address.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.save_btn:
              update_data();
              break;
          case R.id.back_btn:
              getActivity().finish();

              break;
          case R.id.city_tv:
             show_bottomSheet();
              break;
          case R.id.address_ed:
             switchFGM(new Add_New_Address_Map());
              break;

      }
    }
    public void switchFGM(Fragment fragment){
        Auther_activity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Auther_activity. transaction.replace(R.id.mainContainer, fragment);
        Auther_activity. transaction.commit();
    }
    private void update_data() {
        List<EditText> list=new ArrayList<>();
        list.add(fullname);
        list.add(email);
        if (Verefy(list)){
           User mUser=new User();
           mUser.setCity_id(city_id);
           mUser.setFullName(fullname.getText().toString());
           mUser.setAddress(address.getText().toString());
           mUser.setEmail(email.getText().toString());
           if (Add_New_Address_Map.mLatLng!=null){
               mUser.setLat(Add_New_Address_Map.mLatLng.latitude);
               mUser.setLng(Add_New_Address_Map.mLatLng.longitude);

           }
            user user=new user();
            user.Post_UPDATE_user(getContext(),mUser , new user.user_Listener() {
                @Override
                public void onSuccess(users new_account) {
                    if (new_account.isStatus()){
                        User user1=new_account.getItems();
                        user1.setCity(city.getText().toString());
                        new User_info(user1,getContext());
                    }
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
