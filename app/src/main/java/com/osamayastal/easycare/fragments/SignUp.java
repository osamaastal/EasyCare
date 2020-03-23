package com.osamayastal.easycare.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.ConfCode;
import com.osamayastal.easycare.activities.LoginActivity;
import com.osamayastal.easycare.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class SignUp extends Fragment implements View.OnClickListener {

private LinearLayout term_btn,login_btn;
private Button logup_btn;
private EditText name,phone,email,password;
private ImageView back_btn;
private AppCompatCheckBox accept;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        init(view);

     GetLocation(getContext());
        return view;
    }




    private LatLng mlatlng=null;
    private void init(View view) {
        term_btn=view.findViewById(R.id.term_btn);
        login_btn=view.findViewById(R.id.login_btn);
        logup_btn=view.findViewById(R.id.Logup_btn);
        name=view.findViewById(R.id.full_name_ed);
        phone=view.findViewById(R.id.phone_ed);
        email=view.findViewById(R.id.email_ed);
        password=view.findViewById(R.id.password_ed);
        back_btn=view.findViewById(R.id.back_btn);
        accept=view.findViewById(R.id.accept);
        /*********************************Actions******************************/
        term_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        logup_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.login_btn:
            switchFGM(new LoginFrag());
            break;
        case R.id.Logup_btn:
            Logup_fun();
            break;
        case R.id.term_btn:

            break;
        case R.id.back_btn:
            switchFGM(new LoginFrag());

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
            user_.setLat(Float.parseFloat(mlatlng.latitude+""));
            user_.setLng(Float.parseFloat(mlatlng.longitude+""));
            user_.setCity("no city");

            user user=new user();
            user.Post_create_user(getContext(), user_, new user.user_Listener() {
                @Override
                public void onSuccess(users new_account) {
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
            this.mcontext = mcontext;

            locationManager = (LocationManager) mcontext.getSystemService(Context.LOCATION_SERVICE);
            enableMyLocationIfPermitted();

        }
        private String[] LocationPermissions = {Manifest.permission.ACCESS_FINE_LOCATION};

        private void enableMyLocationIfPermitted2() {

            if (EasyPermissions.hasPermissions(mcontext, LocationPermissions)) {

                get_location();


            } else {
                EasyPermissions.requestPermissions(this, "Access for Location",
                        1000, LocationPermissions);

                get_location();
            }


        }
    private void enableMyLocationIfPermitted() {
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
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 15:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                   get_location();
                } else {
                  enableMyLocationIfPermitted();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
        private Boolean flag;
        private Context mcontext;
        @SuppressLint("MissingPermission")
        private void get_location() {

            flag = displayGpsStatus();
            if (flag) {
                dialog=new ProgressDialog(mcontext);
                dialog.setMessage("يرجي الانتظار حتى يتم تحديد موقعك..");
                dialog.show();
                locationListener = new MyLocationListener();
                locationManager.requestLocationUpdates(LocationManager
                        .GPS_PROVIDER, 5000, 10, locationListener);



            } else {

                alertbox("Gps Status!!", "Your GPS is: OFF");
            }

        }
        /*---------- Listener class to get coordinates ------------- */
        int gps=0;
        private class MyLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location loc) {



                mLatLng= new LatLng(loc.getLatitude(),loc.getLongitude());
                Log.d("myLocation",mLatLng.toString());
                dialog.dismiss();


            }

            @Override
            public void onProviderDisabled(String provider) {}

            @Override
            public void onProviderEnabled(String provider) {}



            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}


        }


        private LatLng mLatLng=null;

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

        /*----------Method to create an AlertBox ------------- */
        protected void alertbox(String title, String mymessage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            final Dialog dialog = new Dialog(mcontext);
            dialog .setContentView(R.layout.popup_opengps);
            dialog.setCancelable(false);
            Button cancel=(Button)dialog.findViewById(R.id.cancel_btn);
            TextView tex1=(TextView)dialog.findViewById(R.id.tx1_tv);
            TextView tex2=(TextView)dialog.findViewById(R.id.tx2_tv);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            Button go_sitting=(Button)dialog.findViewById(R.id.open_sitting_btn);
            go_sitting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mcontext.startActivity(myIntent);
                    dialog.cancel();
                }
            });
            dialog.show();


        }

        private LocationManager locationManager = null;
        private LocationListener locationListener = null;
        private ProgressDialog dialog;


}
