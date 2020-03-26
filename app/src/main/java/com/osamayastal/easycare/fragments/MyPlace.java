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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.osamayastal.easycare.Model.Classes.Employee;
import com.osamayastal.easycare.Model.Controle.Maps;
import com.osamayastal.easycare.Model.Rootes.Maps_root;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyPlace extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_my_place, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dialog=new ProgressDialog(getContext());
        dialog.setMessage("يرجي الانتظار حتى يتم تحديد موقعك..");
        dialog.show();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        get_location();
init();

        return view;
    }

    private  TextView name,address,dis;
    private ImageView img,like;
    private RatingBar ratingBar;

    private void init() {
        name=view.findViewById(R.id.provider_name_tv);
        address=view.findViewById(R.id.location_tv);
        dis=view.findViewById(R.id.distance_tv);
        img=view.findViewById(R.id.provider_Img);
        like=view.findViewById(R.id.like_btn);
        ratingBar=view.findViewById(R.id.ratingBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_btn:

                break;
        }
    }
    private GoogleMap mMap;

    private Boolean flag;
    @SuppressLint("MissingPermission")
    private void get_location() {

        flag = displayGpsStatus();
        if (flag) {
            locationListener = new MyLocationListener();




            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);



        } else {

            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }
    /*---------- Listener class to get coordinates ------------- */
    int gps=0;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        mMap.setOnMarkerClickListener(this);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(15);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLatLng=latLng;
               // make_marke(latLng);
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Employee employee =markersMap.get(marker);
        name.setText(employee.getFull_name());
        address.setText(employee.getProvider_id().getAddress());
//        dis.setText(employee.getFull_name());
        try {
            Picasso.with(getContext())
                    .load(employee.getImage())
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.findViewById(R.id.provider_inf).setVisibility(View.VISIBLE);
        return false;
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            mLatLng=new LatLng(loc.getLatitude(),loc.getLongitude());
            Show_nearServic(mLatLng);

            if (gps==0 && mMap!=null) {
                //make_marke(new LatLng(loc.getLatitude(),loc.getLongitude()));

                dialog.dismiss();
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

    private void Show_nearServic(LatLng mLatLng) {
        Maps_root maps_root=new Maps_root();
        maps_root.GetNearServic(getContext(), mLatLng, new Maps_root.MapsListener() {
            @Override
            public void onSuccess(Maps maps) {
                for (Employee e:maps.getEmployees()
                     ) {
                    make_marke(e);
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

    /**
     *
     * @return
     */
    private LatLng mLatLng=null;
    private Map<Marker, Employee> markersMap = new HashMap<Marker, Employee>();
    private void make_marke(final Employee emp){

        LatLng latLng=new LatLng(emp.getProvider_id().getLat()
                ,emp.getProvider_id().getLng());

        Marker marker = mMap.addMarker(new MarkerOptions()
                .title(emp.getFull_name())
                .snippet(emp.getFull_name() )
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.app_logo)));
        markersMap.put(marker, emp);
    }


    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getActivity().getContentResolver();
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
        dialog.show();


    }

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private ProgressDialog dialog;





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

                }
            };
}
