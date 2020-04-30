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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.osamayastal.easycare.Model.Classes.Employee;
import com.osamayastal.easycare.Model.Classes.Provider.Provider_map;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Favorites;
import com.osamayastal.easycare.Model.Controle.Maps;
import com.osamayastal.easycare.Model.Rootes.Favorite_root;
import com.osamayastal.easycare.Model.Rootes.Maps_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.Search;
import com.osamayastal.easycare.activities.ServiceProfiderDetails;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import top.defaults.drawabletoolbox.DrawableBuilder;

public class MyPlace extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_place, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mLatLng != null) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
            Show_nearServic(mLatLng);
        } else {

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            GetLocation(getContext());
        }


        init();

        return view;
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

    public void GetLocation(Context mcontext) {


        locationManager = (LocationManager) mcontext.getSystemService(Context.LOCATION_SERVICE);
        enableMyLocationIfPermitted();


    }

    private String[] LocationPermissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    private void enableMyLocationIfPermitted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(LocationPermissions, 15);

            } else {

                get_location();
            }
        }
    }


    private FusedLocationProviderClient fusedLocationClient;

    private void getCurrentLocation() {
        if (getActivity() == null)
            return;

    }

    private TextView name, address, dis, type;
    private ImageView img, like;
    private RatingBar ratingBar;
    private ImageButton search_btn;

    private void init() {
        name = view.findViewById(R.id.provider_name_tv);
        address = view.findViewById(R.id.location_tv);
        type = view.findViewById(R.id.type_tv);
        dis = view.findViewById(R.id.distance_tv);
        img = view.findViewById(R.id.provider_Img);
        like = view.findViewById(R.id.like_btn);
        ratingBar = view.findViewById(R.id.ratingBar);
        search_btn = view.findViewById(R.id.search_btn);
        /****************************Actions******************************/
        search_btn.setOnClickListener(this);
        view.findViewById(R.id.provider_inf).setOnClickListener(this);
    }
private String provider_id=null;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                startActivity(new Intent(getContext(), Search.class));
                break;
            case R.id.provider_inf:
                if (provider_id!=null){
                    Intent intent=new Intent(getContext(), ServiceProfiderDetails.class);
                    intent.putExtra("provider_id",provider_id);
                    startActivity(intent);

                }
                break;

        }
    }

    private GoogleMap mMap;

    private Boolean flag;

    private void get_location() {
        if (new User_info(getContext()).getLat()!=null && new User_info(getContext()).getLng()!=null){
            mLatLng=new LatLng(Double.parseDouble(new User_info(getContext()).getLat()),
                    Double.parseDouble(new User_info(getContext()).getLng()));
        }
//        Toast.makeText(getContext(), "lat: " + new User_info(getContext()).getLat()
//                + "lng: " + new User_info(getContext()).getLng() , Toast.LENGTH_SHORT).show();

        flag = displayGpsStatus();
        if (flag) {
            locationListener = new MyLocationListener();

///////////////////////////////////////////////////*****************GetLastKnownLocation*****************************************/
            try {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {


                                    if (gps==0 && mMap!=null) {
                                        mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                                        Show_nearServic(mLatLng);
                                        Log.d("location",mLatLng.toString());
                                        gps=1;

                                        new User_info().SetLocation(getContext(),mLatLng);
                                        Toast.makeText(getContext(), "lat: " + new User_info(getContext()).getLat()
                                                + "lng: " + new User_info(getContext()).getLng() , Toast.LENGTH_SHORT).show();

                                    }
//                                    Toast.makeText(getContext(), "lat: " + mLatLng.latitude + "lng: " + mLatLng.longitude, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
//
            } catch (Exception e) {
                e.printStackTrace();
            }

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

        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.setMinZoomPreference(12);

    }
    private void makeDrawable(int color, View view, int corner) {
        Drawable drawable = new DrawableBuilder()
                .rectangle()
                .solidColor(color)//0xffe67e22
//                .height(90)
//                .width(90)
                .cornerRadii(corner, corner, corner, corner)// pixel
                // top-left  top-right  bottom-right   bottom-left
                .build();
        view.setBackground(drawable);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        final Provider_map provider =markersMap_prov.get(marker);
        provider_id=provider.get_id();
        name.setText(provider.getName());
        address.setText(provider.getAddress());
if (new User_info(getContext()).getLanguage().equals("en")){
    type.setText(provider.getCategory_id().getEnName());
}else {
    type.setText(provider.getCategory_id().getArName());
}
        Location loc1 = new Location("");
        loc1.setLatitude(mLatLng.latitude);
        loc1.setLongitude(mLatLng.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(provider.getLat());
        loc2.setLongitude(provider.getLng());

        float distanceInMeters = loc1.distanceTo(loc2)/1000;
        dis.setText(distanceInMeters+"");
        try{
            String color=provider.getCategory_id().getColor();
            makeDrawable(Color.parseColor(color),type,18);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!provider.getFavorite_id().equals("null")){

            like.setImageDrawable(getContext().getDrawable(R.drawable.ic_like));
    }else {
        like.setImageDrawable(getContext().getDrawable(R.drawable.ic_unlike));

    }
    like.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Favorite_root root=new Favorite_root();
            if (!provider.getFavorite_id().equals("null")){

                root.DELETEFavorites(getContext(), provider.getFavorite_id(), new Favorite_root.FavoriteListener() {
                    @Override
                    public void onSuccess(Favorites favorites) {
                        if (new User_info(getContext()).getLanguage().equals("en")){
                            Toast.makeText(getContext(),favorites.getMessageEn(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),favorites.getMessageAr(),Toast.LENGTH_SHORT).show();

                        }
                        if (favorites.getStatus_code()==200){
                            like.setImageDrawable(getContext().getDrawable(R.drawable.ic_unlike));
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
            else {
                root.POSTFavorites(getContext(), provider.get_id(), new Favorite_root.FavoriteListener() {
                    @Override
                    public void onSuccess(Favorites favorites) {
                        if (new User_info(getContext()).getLanguage().equals("en")){
                            Toast.makeText(getContext(),favorites.getMessageEn(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),favorites.getMessageAr(),Toast.LENGTH_SHORT).show();

                        }
                        if (favorites.getStatus_code()==200){
                            like.setImageDrawable(getContext().getDrawable(R.drawable.ic_like));
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
    });

//        dis.setText(employee.getFull_ name());
        try {
            Picasso.with(getContext())
                    .load(provider.getImage())
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ratingBar.setRating(provider.getRate());

        view.findViewById(R.id.provider_inf).setVisibility(View.VISIBLE);
        return false;
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {



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
       try {
           maps_root.GetNearServic(getContext(), mLatLng, new Maps_root.MapsListener() {
               @Override
               public void onSuccess(Maps maps) {
                   for (Employee e:maps.getEmployees()
                   ) {
                       make_marke(e,null);
                   }

                   for (Provider_map provider:maps.getProviders()
                   ) {
                       make_marke(null,provider);
                   }
               }

               @Override
               public void onStart() {

               }

               @Override
               public void onFailure(String msg) {

               }
           });
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    /**
     *
     * @return
     */
//    private LatLng mLatLng=new LatLng(33.39877956546843,6.875997707247734);
    private LatLng mLatLng=new LatLng(23.836439,36.699240);
    private Map<Marker, Employee> markersMap_emp = new HashMap<Marker, Employee>();
    private Map<Marker, Provider_map> markersMap_prov = new HashMap<Marker, Provider_map>();
    private void make_marke(final Employee emp, Provider_map provider){
       try {
           LatLng latLng=mLatLng;
           if (emp!=null){
               latLng=new LatLng(emp.getProvider_id().getLat()
                       ,emp.getProvider_id().getLng());

               Marker marker = mMap.addMarker(new MarkerOptions()
                       .title(emp.getFull_name())
                       .snippet(emp.getFull_name() )
                       .position(latLng)
                       .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_icon_emp)));



//            markersMap_prov.put(marker, emp.getProvider_id());
           }
           if (provider!=null){
               latLng=new LatLng(provider.getLat()
                       ,provider.getLng());

               Marker marker = mMap.addMarker(new MarkerOptions()
                       .title(provider.getName())
                       .snippet(provider.getName() )
                       .position(latLng)
                       .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_icon_prov)));


               markersMap_prov.put(marker, provider);
           }

           mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
           float zoomLevel = 8.0f; //This goes up to 21
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
       } catch (Exception e) {
           e.printStackTrace();
       }



    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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

                    Show_nearServic(mLatLng);

                }
            };



}
