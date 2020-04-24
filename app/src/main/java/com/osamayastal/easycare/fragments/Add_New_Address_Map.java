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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.Auther_activity;
import com.osamayastal.easycare.activities.MainActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Add_New_Address_Map extends Fragment implements OnMapReadyCallback {
    View view;
    Button conf_address_btn;
    ImageView cancel_btn;
    TextView location_tv;
    private GoogleMap mMap;
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
private LinearLayout address_lay;
    public Add_New_Address_Map() {
        // Required empty public constructor
    }
    private Boolean flag;
    private FusedLocationProviderClient fusedLocationClient;

    @SuppressLint("MissingPermission")
    private void get_location() {

        flag = displayGpsStatus();
        if (flag) {
            locationListener = new MyLocationListener();




            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
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

            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }
    /*---------- Listener class to get coordinates ------------- */
    int gps=0;
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {



            if (gps==0 && mMap!=null) {
                make_marke(new LatLng(loc.getLatitude(),loc.getLongitude()));
                gps=1;
            }

        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}



        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
    /**
     /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getContext()
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

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final Dialog dialog = new Dialog(getContext());
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
                startActivity(myIntent);
                dialog.cancel();
            }
        });



    }


    public Add_New_Address_Map newInstance(String param1, String param2) {
        Add_New_Address_Map fragment = new Add_New_Address_Map();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) Add_New_Address_Map.this.getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//    private ProgressDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.layout_f_add_new_address_map, container, false);
        /***************************************************************/

        locationManager = (LocationManager) getActivity().
                getSystemService(Context.LOCATION_SERVICE);
        get_location();
        /*****************************************************************/
        conf_address_btn = (Button) view.findViewById(R.id.save_btn);
        cancel_btn = (ImageView) view.findViewById(R.id.back_btn);
        location_tv = (TextView) view.findViewById(R.id.location_tv);
address_lay=(LinearLayout)view.findViewById(R.id.address_lay);

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        /********************************************************/
address_lay.setEnabled(false);
        conf_address_btn.setEnabled(false);
        conf_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mLatLng!=null){
                   switchFGM(new EditProfile());
               }
               else {
                   Toast.makeText(getContext(),"يرجى اختيار العنوان من الخريطة",Toast.LENGTH_SHORT).show();
                }




            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFGM(new EditProfile());
            }
        });

        return view;
    }
    public static String  Location=null;
    private void make_marke(final LatLng latLng){
mLatLng=latLng;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).draggable(true).title("المنزل");
        mMap.clear();
        mMap.addMarker(options);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        Geocoder geocoder;
        List<Address> addresses = null;


        try {
            geocoder = new Geocoder(getContext(), Locale.getDefault());
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
        address_lay.setEnabled(true);
        conf_address_btn.setEnabled(true);}
}catch (Exception e){
    e.printStackTrace();
}

    }
    public void switchFGM(Fragment fragment){
        Auther_activity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Auther_activity. transaction.replace(R.id.mainContainer, fragment);
        Auther_activity. transaction.commit();
    }



public static LatLng mLatLng=null;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(15);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLatLng=latLng;
                make_marke(latLng);
            }
        });

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

            } else  if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(getContext(), "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        }, 15);


                    } else {
                    showDefaultLocation();
                }}
                return;



    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
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

