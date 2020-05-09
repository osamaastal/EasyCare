package com.osamayastal.easycare.activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.EditProfile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Add_New_Address_Map extends AppCompatActivity implements OnMapReadyCallback {

    Button conf_address_btn;
    ImageView cancel_btn;
    TextView location_tv;
    private GoogleMap mMap;
private LinearLayout address_lay;

    private Boolean flag;
    private FusedLocationProviderClient fusedLocationClient;
private Context mcontext=Add_New_Address_Map.this;
    @SuppressLint("MissingPermission")
    private void get_location() {

        flag = displayGpsStatus();
        if (flag) {

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {


                                if (gps==0 && mMap!=null) {
                                    mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                                    gps=1;
                                    make_marke(mLatLng);
                                }
//                                    Toast.makeText(getContext(), "lat: " + mLatLng.latitude + "lng: " + mLatLng.longitude, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


        } else {

            new com.osamayastal.easycare.Model.Classes.GPS(mcontext).
                    turnGPSOn(new com.osamayastal.easycare.Model.Classes.GPS.onGpsListener() {
                        @Override
                        public void gpsStatus(boolean isGPSEnable) {
                            // turn on GPS
                            flag = isGPSEnable;
//                        Toast.makeText(Logo.this, "isGPS = " +isGPS, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
    /*---------- Listener class to get coordinates ------------- */
    int gps=0;

     /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = this
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

//    private ProgressDialog dialog;
public void setLocale(Context context ){
    User_info user_info;
    user_info = new User_info(context);
    String language=user_info.getLanguage();
    Locale locale = new Locale(language);
    Configuration config = new Configuration(getResources().getConfiguration());
    Locale.setDefault(locale);
    config.setLocale(locale);
    getBaseContext().getResources().updateConfiguration(config,
            getBaseContext().getResources().getDisplayMetrics());
//    Toast.makeText(this, "Language: "+ Locale.getDefault().getLanguage() , Toast.LENGTH_SHORT).show();
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(this);
        setContentView(R.layout.layout_f_add_new_address_map);
        /***************************************************************/

        /*****************************************************************/
        conf_address_btn = (Button) findViewById(R.id.save_btn);
        cancel_btn = (ImageView) findViewById(R.id.back_btn);
        location_tv = (TextView) findViewById(R.id.location_tv);
address_lay=(LinearLayout)findViewById(R.id.address_lay);

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        /********************************************************/
address_lay.setEnabled(false);
        conf_address_btn.setEnabled(false);
        conf_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mLatLng!=null){
                   Intent resultIntent = new Intent();
                   resultIntent.putExtra("lat", mLatLng.latitude);
                   resultIntent.putExtra("lng", mLatLng.longitude);
                   resultIntent.putExtra("Location", Location);
                   setResult(Activity.RESULT_OK, resultIntent);
                   finish();
//                   switchFGM(new EditProfile());
               }
               else {
                   Toast.makeText(mcontext,"يرجى اختيار العنوان من الخريطة",Toast.LENGTH_SHORT).show();
                }




            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });





    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 15) {
                flag = true; // flag maintain before get location
                get_location();
                mMap.setMyLocationEnabled(true);
            }
        }
    }
    private String  Location=null;
    private void make_marke(final LatLng latLng){
mLatLng=latLng;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).draggable(true).title("المنزل");
        mMap.clear();
        mMap.addMarker(options);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        Geocoder geocoder;
        List<Address> addresses = null;

        address_lay.setEnabled(true);
        conf_address_btn.setEnabled(true);

        try {
            geocoder = new Geocoder(mcontext, Locale.getDefault());
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }



try{
    if (addresses.size()!=0){
        String area=addresses.get(0).getAdminArea();
        String loc=addresses.get(0).getLocality();
        if (area!=null){
            Location=area;
        }
        if (loc!=null){
            Location=area+" , "+ loc;
        }

        location_tv.setText(Location);
        }
}catch (Exception e){
    e.printStackTrace();
}

    }




private LatLng mLatLng=null;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng latLng=new LatLng(21.143333,39.272779);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLatLng=latLng;
                make_marke(latLng);
            }
        });

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
//                    switchFGM(new LoginFrag());
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void enableMyLocationIfPermitted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 15);

            } else  if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            get_location();
        }
        }
    }



    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {


                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {


                    mLatLng=new LatLng(location.getLatitude(),location.getLongitude());
                    make_marke(mLatLng);
                     }
            };

}

