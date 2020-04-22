package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.osamayastal.easycare.Adapters.City_adapter;
import com.osamayastal.easycare.Adapters.Search_adapter;
import com.osamayastal.easycare.Adapters.ServicType_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Controle.Categories;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.Model.Rootes.City_root;
import com.osamayastal.easycare.Model.Rootes.Search_root;
import com.osamayastal.easycare.R;
import com.suke.widget.SwitchButton;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Search extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }
private EditText name;
    private ImageView filter,search,close,back;
    private TextView result_nb;
    private LinearLayout no_result;
    private RecyclerView RV;
    private List<com.osamayastal.easycare.Model.Classes.Search> searchList;
    private Search_adapter adapter;
    private ProgressBar progressBar;
    private void init() {
        filter=findViewById(R.id.filter_btn);
        search=findViewById(R.id.search_btn);
        close=findViewById(R.id.close_btn);
        result_nb=findViewById(R.id.result_nb);
        no_result=findViewById(R.id.linear_no_results);
        RV=findViewById(R.id.RV);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
        name=findViewById(R.id.name);
        progressBar=findViewById(R.id.progress);
        /***********************************Actions*****************************/
        filter.setOnClickListener(this);
        search.setOnClickListener(this);
        close.setOnClickListener(this);
        back.setOnClickListener(this);
        searchList=new ArrayList<>();
        adapter=new Search_adapter(this,searchList,null);
        RV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        RV.setAdapter(adapter);

        /*************
         */
mypopupWindow_filter=setPopUpWindow();

    }
    String categorie_id="";
    String city_id="";
    String rat ="";
    String raduis ="";
    private PopupWindow setPopUpWindow() {
//        LayoutInflater inflater = (LayoutInflater).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.popup_filter, null);

        final Spinner city=view1.findViewById(R.id.city_spinner);
        RecyclerView RV=view1.findViewById(R.id.Rv_type);
        final IndicatorSeekBar rang=view1.findViewById(R.id.range);
        SwitchButton watch=view1.findViewById(R.id.watch_switch);

        final RatingBar rate=view1.findViewById(R.id.ratingBar);
        ImageButton save=view1.findViewById(R.id.save_btn);
        ImageButton cacel=view1.findViewById(R.id.cancel_btn);
        TextView default_tv=view1.findViewById(R.id.Default);

        /*******************************************Actions************************************/
default_tv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       rang.setProgress(0);
       rate.setRating(0);

         categorie_id="";
         city_id="";
         rat ="";
         raduis ="";
    }
});
        List<Categorie> categories=new ArrayList<>();
        ServicType_adapter adaptertype=new ServicType_adapter(Search.this, categories, new ServicType_adapter.Selected_item() {
            @Override
            public void Onselcted(Categorie categorie) {
                categorie_id=categorie.get_id();
            }
        });
        RV.setLayoutManager(new GridLayoutManager(this,3));
        RV.setAdapter(adaptertype);
        GEt_all_saervic(categories,adaptertype);
        /*************************************City*********************************/
        List<City> cityArrayList=new ArrayList<City>();
        City_adapter adaptercity=new City_adapter(Search.this,R.layout.row_city_text,R.id.type_tv, cityArrayList);
        city.setAdapter(adaptercity);
        Get_all_city(cityArrayList,adaptercity);
        /****************************************Rating****************************************/
        rang.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                raduis=seekBar.getProgress()+"";
            }
        });
/***********************************************************************************/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                City city1=(City) city.getSelectedItem();
                city_id=city1.get_id();
                rat= String.valueOf(rang.getProgress());
                Search_fun(name,categorie_id,city_id,rat,raduis);
                mypopupWindow_filter.dismiss();

            }
        });
        cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mypopupWindow_filter.dismiss();
            }
        });

        return new PopupWindow(view1,RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);


    }

    private void Get_all_city(final List<City> cityArrayList, final City_adapter adaptercity) {
        City_root city_root=new City_root();
        city_root.GetCities(this, new City_root.cityListener() {
            @Override
            public void onSuccess(com.osamayastal.easycare.Model.Controle.City cities) {
                cityArrayList.clear();
                cityArrayList.addAll(cities.getItems());
                adaptercity.notifyDataSetChanged();
//               if (cityArrayList.size()!=0){
//                   city_id=cityArrayList.get(0).get_id();
//               }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void GEt_all_saervic(final List<Categorie> categories, final ServicType_adapter adaptertype) {
        Categories_root categories_root=new Categories_root();
        categories_root.GetCategories(this, new Categories_root.cat_Listener() {
            @Override
            public void onSuccess(Categories categorie) {
                categories.getClass();
                categories.addAll(categorie.getItems());
                adaptertype.notifyDataSetChanged();
//                if (categories.size()!=0){
//                    categorie_id=categories.get(0).get_id();
//                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void Search_fun(EditText name, String categorie_id, String city_id, String rat, String raduis) {
        progressBar.setVisibility(View.VISIBLE);
        no_result.setVisibility(View.GONE);
        final Search_root search_root=new Search_root();
        search_root.GetSearch(this, categorie_id, city_id, name.getText().toString(), rat, 0,raduis,mLatLng,
                new Search_root.homeListener() {
                    @Override
                    public void onSuccess(com.osamayastal.easycare.Model.Controle.Search home) {
                        if (home.getStatus_code()==200) {
                            progressBar.setVisibility(View.GONE);
                            searchList.clear();
                            searchList.addAll(home.getItems());
                            if (searchList.size()==0){
                                no_result.setVisibility(View.VISIBLE);
                            }else {
                                no_result.setVisibility(View.GONE);

                            }
                            adapter.notifyDataSetChanged();
                            result_nb.setText(home.getPagenation().getSize()+"");
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


    PopupWindow mypopupWindow_filter;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.close_btn:
                name.setText("");
                break;
            case R.id.search_btn:
                Search_fun(name, categorie_id, city_id, rat,raduis);
                break;
            case R.id.filter_btn:
                mypopupWindow_filter.showAsDropDown(filter,0,0);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetLocation(this);
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
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
///////////////////////////////////////////////////*****************GetLastKnownLocation*****************************************/
            Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            mLatLng=new LatLng(networkLocation.getLatitude(),networkLocation.getLongitude());
            Log.d("location",mLatLng.toString());
            dialog.dismiss();

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
