package com.osamayastal.easycare.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cncoderx.wheelview.Wheel3DView;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.City_root;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.Auther_activity;
import com.osamayastal.easycare.activities.ConfCode;
import com.osamayastal.easycare.activities.LoginActivity;
import com.osamayastal.easycare.activities.MainActivity;
import com.osamayastal.easycare.activities.Search;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class SignUp extends Fragment implements View.OnClickListener {

private LinearLayout term_btn,login_btn;
private Button logup_btn;
private EditText name,phone,email,password;
private ImageView back_btn;
TextView city;
String city_id=null;
private AppCompatCheckBox accept;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetLocation(getContext());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 15) {
                Toast.makeText(getContext(), "result .... ", Toast.LENGTH_SHORT).show();

                flag = true; // flag maintain before get location
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                                    Log.d("location",mLatLng.toString());

                                    Toast.makeText(getContext(), "lat: " + mLatLng.latitude + "lng: " + mLatLng.longitude, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        }
    }
    private void init(View view) {
        term_btn=view.findViewById(R.id.term);
        city=view.findViewById(R.id.city_tv);
        login_btn=view.findViewById(R.id.login_btn);
        logup_btn=view.findViewById(R.id.logup_btn);
        name=view.findViewById(R.id.fullname_ed);
        phone=view.findViewById(R.id.phone_ed);
        email=view.findViewById(R.id.email_ed);
        password=view.findViewById(R.id.password_ed);
        back_btn=view.findViewById(R.id.back_btn);
        accept=view.findViewById(R.id.term_check);
        /*********************************Actions******************************/
        term_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        logup_btn.setOnClickListener(this);
        city.setOnClickListener(this);
    }
    private void show_bottomSheet(){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_choose_city, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final Wheel3DView wheel3DView=sheetView.findViewById(R.id.wheel);
        final ProgressBar progressBar=sheetView.findViewById(R.id.progress);
        final List<City> cityList=new ArrayList<>();
        City_root root=new City_root();
        root.GetCities(getContext(), new City_root.cityListener() {
            @Override
            public void onSuccess(final com.osamayastal.easycare.Model.Controle.City cities) {
                progressBar.setVisibility(View.GONE);
                cityList.clear();
                cityList.addAll(cities.getItems());
                wheel3DView.setEntries(cities.getCityList());
                Button save=sheetView.findViewById(R.id.save_btn);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        city.setText(wheel3DView.getCurrentItem());
                        city_id=cities.getItems().get(wheel3DView.getCurrentIndex()).get_id();
                        Log.d("City ID", city_id);

                        mBottomSheetDialog.dismiss();
                    }
                });
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });


    }
    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.login_btn:
            GetLocation(getContext());
            switchFGM(new LoginFrag());
            break;
        case R.id.logup_btn:
            Logup_fun();
            break;
        case R.id.term:
            AboutUs.id="5e7af8931c9d440000013fff";
            Auther_activity.fragment = new AboutUs();
            startActivity(new Intent(getActivity(), Auther_activity.class));

            break;
        case R.id.city_tv:
           show_bottomSheet();

            break;



    }
    }

    private void Logup_fun() {
        List<EditText> list=new ArrayList<>();
        list.add(name);
        list.add(phone);
        list.add(email);
        list.add(password);
        if (Verefy(list)){
            if (city_id==null){
                city.setError(city.getText());
                return;
            }
            if (!accept.isChecked()){
                accept.setError(accept.getText());
                return;
            }
            if (mLatLng==null){
                Toast.makeText(getContext(),"لم يتم أخذ إحداثياتك بعد",Toast.LENGTH_SHORT).show();
                return;
            }
            User user_=new User();
            user_.setFullName(name.getText().toString());
            user_.setPhoneNumber(phone.getText().toString());
            user_.setEmail(email.getText().toString());
            user_.setPassword(password.getText().toString());
            user_.setLat(mLatLng.latitude);
            user_.setLng(mLatLng.longitude);
            user_.setCity_id(city_id);

            user user=new user();
            user.Post_create_user(getContext(), user_, new user.user_Listener() {
                @Override
                public void onSuccess(users new_account) {
                    if (new User_info(getContext()).getLanguage().equals("en")){
                        Toast.makeText(getContext(),new_account.getMessageEn(),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(),new_account.getMessageAr(),Toast.LENGTH_SHORT).show();
                    }
                    if (!new_account.isStatus()){
                        return;
                    }
                    new User_info(new_account.getItems(),getContext());
                    startActivity(new Intent(getActivity(), ConfCode.class));
                    getActivity().finish();
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure(String msg) {

                }
            });


        }
        else {
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

    public void switchFGM(Fragment fragment){
        LoginActivity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        LoginActivity. transaction.replace(R.id.LoginContainer, fragment);
        LoginActivity. transaction.commit();
    }



/******************************************FIND LOCATION*****************************************************************/
        public void GetLocation( Context mcontext) {
           try {
               this.mcontext = mcontext;
               enableMyLocationIfPermitted();
           } catch (Exception e) {
               e.printStackTrace();
           }
        }


    private void enableMyLocationIfPermitted() {
       try {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                       != PackageManager.PERMISSION_GRANTED &&
                       ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                               != PackageManager.PERMISSION_GRANTED) {
                   requestPermissions(new String[]{
                           Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                           Manifest.permission.INTERNET
                   }, 15);

               } else  {

                   get_location();
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 15:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                   get_location();
                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private FusedLocationProviderClient fusedLocationClient;
        private Boolean flag;
        private Context mcontext=getContext();
    private LatLng mLatLng=new LatLng(23.836439,36.699240);

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = mcontext.getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    @SuppressLint("MissingPermission")
        private void get_location() {


            flag = displayGpsStatus();
            if (flag) {



///////////////////////////////////////////////////*****************GetLastKnownLocation*****************************************/
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                                    Log.d("location",mLatLng.toString());
//                                    dialog.dismiss();
//                                        Toast.makeText(getContext(), "lat: " + mLatLng.latitude + "lng: " + mLatLng.longitude, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            } else {
                new com.osamayastal.easycare.Model.Classes.GPS(getContext()).
                        turnGPSOn(new com.osamayastal.easycare.Model.Classes.GPS.onGpsListener() {
                            @Override
                            public void gpsStatus(boolean isGPSEnable) {
                                // turn on GPS
                                flag = isGPSEnable;
                            }
                        });
            }

        }





}
