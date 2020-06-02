package com.osamayastal.easycare.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.osamayastal.easycare.Adapters.Basket_Products_adapter;
import com.osamayastal.easycare.Adapters.Basket_Service_adapter;
import com.osamayastal.easycare.Model.Classes.Basket.Bascket;
import com.osamayastal.easycare.Model.Classes.Basket.SubCategory_basket;
import com.osamayastal.easycare.Model.Classes.Basket.Sub_service_basket;
import com.osamayastal.easycare.Model.Classes.Basket.categories_basket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Basket_prices;
import com.osamayastal.easycare.Model.Controle.Coupon;
import com.osamayastal.easycare.Model.Controle.Order_Details;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Model.Rootes.Coupon_root;
import com.osamayastal.easycare.Model.Rootes.Order_root;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.Popups.OrderPop;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.fragments.MyOrders;
import com.suke.widget.SwitchButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDetails_Create extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onResume() {
        super.onResume();
        setLocale(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_details__create);
        Intent intent = this.getIntent();

        Bundle bundle = intent.getExtras();

        order_id=bundle.getString("order_id");

        init();
        if (order_id==null){
            provider_id=bundle.getString("provider_id");
            loading(bundle.getString("json"));
        }else {
            reOrder=true;
            Reorder_loading();
        }


    }
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
//        Toast.makeText(this, "Language: "+ Locale.getDefault().getLanguage() , Toast.LENGTH_SHORT).show();
    }
    private Double Caculate_Tot(){
        Double tot=0.0;
        if (bascket!=null){
            for (Product p:bascket.getProducts()
                 ) {
                Double price=p.getDiscountPrice();
                if (price==0){
                   price=p.getPrice();
                }
                tot=tot+(p.getQty()*price);
            }
            for (categories_basket s:bascket.getCategories()
                 ) {
                for (Sub_service_basket sub:s.getSub_services()
                     ) {
                    tot=tot+sub.getSize().getPrice();
                    for (SubCategory_basket subcat:sub.getSubCategory_basketList()
                         ) {
                        tot=tot+subcat.getPrice();
                    }
                }
            }
        }
        return tot;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loading(String json) {
        if (json!=null){
            JSONObject Jobject = null;
            try {
                Jobject = new JSONObject(json);
                bascket=new Bascket(Jobject);
                FetchDATA();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        findViewById(R.id.linear_wait).setVisibility(View.VISIBLE);
        Bascket_root root = new Bascket_root();
        try {
            root.GetItemPrices(this, new JSONObject(provider_id), new Bascket_root.GetPricesListener() {
                @Override
                public void onSuccess(Basket_prices bascket_) {

    if (bascket_.isStatus()){

        findViewById(R.id.linear_wait).setVisibility(View.GONE);
        sub_tot.setText(String.format("%.2f",bascket_.getTotal_price()));
        tax.setText(String.format("%.2f", bascket_.getTax()));

        tot_price.setText(String.format("%.2f",bascket_.getFinal_total()));
        totale.setText(String.format("%.2f",bascket_.getFinal_total()));
        discount.setText(String.format("%.2f",bascket_.getTotal_discount()));
    }

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure(String msg) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void Reorder_loading() {
        findViewById(R.id.linear_wait).setVisibility(View.VISIBLE);
        Order_root root=new Order_root();
        root.GetOrderDetails(mcontext, order_id, new Order_root.GetOrderDetailsListener() {
            @Override
            public void onSuccess(Order_Details order_details) {
                if (order_details.getStatus()) {
                    findViewById(R.id.linear_wait).setVisibility(View.GONE);
                    if (order_details.getItems().size()!=0){
                        bascket=order_details.getItems().get(0);
                        date=bascket.getDate();
                        time=bascket.getTime();
                        sub_tot.setText(String.format("%.2f",order_details.getTotal_price()));
                        tax.setText(String.format("%.2f", order_details.getTax()));
                        tot_price.setText(String.format("%.2f",order_details.getFinal_total()));
                        totale.setText(String.format("%.2f",order_details.getFinal_total()));
                        discount.setText(String.format("%.2f",order_details.getTotal_discount()));
                        FetchDATA();
                    }else {
                        finish();
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
    private Boolean reOrder=false;
    private Context mcontext=OrderDetails_Create.this;
    private String date;
    private String order_id=null;
    private String time="09:12";
    private int payment,location_typ=1;
    private String provider_id;
    private ImageButton back_btn,date_btn,time_btn;
    private SwitchButton provider_location;
    private Button save;
    private TextView name,item_nb,sub_tot,tax,discount,tot_price,totale,date_tv,time_tv;
    private RecyclerView service_RV,product_RV;
    private Bascket bascket=null;
    private Basket_Service_adapter service_adapter;
    private Basket_Products_adapter products_adapter;
    private SupportMapFragment mapFragment;
private EditText coupon;
private String Coupon_txt="";
private LinearLayout myplace;
    private void init(){
        provider_location=findViewById(R.id.prov_location_switch);
        back_btn=findViewById(R.id.back_btn);
        coupon=findViewById(R.id.coupon);
        date_btn=findViewById(R.id.date_btn);
        time_btn=findViewById(R.id.time_btn);
        save=findViewById(R.id.save_btn);
        name=findViewById(R.id.provider_name_tv);
        item_nb=findViewById(R.id.item_nb);
        sub_tot=findViewById(R.id.price_before_discount_tv);
        tax=findViewById(R.id.tax_amount_tv);
        discount=findViewById(R.id.discount_amount_tv);
        tot_price=findViewById(R.id.total_price_tv);
        totale=findViewById(R.id.total);
        time_tv=findViewById(R.id.time_tv);
        date_tv=findViewById(R.id.date_tv);
        service_RV=findViewById(R.id.RV_services);
        product_RV=findViewById(R.id.RV_products);
        myplace=findViewById(R.id.my_place);
     /***************************************************************/
        back_btn.setOnClickListener(this);
        date_btn.setOnClickListener(this);
        time_btn.setOnClickListener(this);
        save.setOnClickListener(this);
        provider_location.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    location_typ=2;
                    myplace.setVisibility(View.GONE);
                }else {
                    location_typ=1;
                    myplace.setVisibility(View.VISIBLE);

                }
            }
        });
        /********************************coupon**********************************/
       coupon. setOnEditorActionListener(
                new EditText.OnEditorActionListener() {


                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                // the user is done typing.
                                Coupon_root root=new Coupon_root();
                                String prov_id=null;
                                prov_id=bascket.getProvider().get_id();
                                if (bascket==null){
                                    prov_id=bascket.getProvider().get_id();
                                }
                                root.Check_coupon(mcontext, coupon.getText().toString(),prov_id,reOrder,order_id
                                        , new Coupon_root.Listener() {
                                    @Override
                                    public void onSuccess(Coupon coup) {
                                        String msg=null;

                                        if (new User_info(mcontext).getLanguage().equals("en")){
                                             msg=coup.getMessageEn();
                                        }else
                                        {
                                            msg=coup.getMessageAr();

                                        }
                                        if (msg!=null){
                                            AppPop pop=new AppPop();
                                            pop.ShowMessage(msg,getCurrentFocus());
                                        }
                                        sub_tot.setText(String.format("%.2f",coup.getTotal_price()));
                                        tax.setText(String.format("%.2f", coup.getTax()));
                                        tot_price.setText(String.format("%.2f",coup.getFinal_total()));
                                        totale.setText(String.format("%.2f",coup.getFinal_total()));
                                        discount.setText(String.format("%.2f",coup.getTotal_discount()));
                                       if (coup.isStatus()){

                                           Coupon_txt=coupon.getText().toString();
                                       }

                                    }

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onFailure(String msg) {

                                    }
                                });
                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );
/**********************************Maps*******************************/
         mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
private void FetchDATA(){
        if (bascket!=null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date d = new Date();
           date = format.format(d);
            name.setText(bascket.getProvider().getName());
            item_nb.setText(bascket.getCategories().size()+bascket.getProducts().size()+"");
            date_tv.setText(date);
            time_tv.setText(time);
            service_adapter=new Basket_Service_adapter(this, bascket.getCategories(), bascket.getCategorie(), new Basket_Service_adapter.Selected_item() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void Onselcted(Car_servece car_servece) {
                    loading(null);
                }
            });
            service_RV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            service_RV.setAdapter(service_adapter);

            products_adapter=new Basket_Products_adapter(this, bascket.getProducts(), new Basket_Products_adapter.Selected_item() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void Onselcted(Car_servece car_servece) {
                    loading(null);
                }
            });
            product_RV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            product_RV.setAdapter(products_adapter);

                products_adapter.isOrder=true;
                service_adapter.isOrder=true;
                products_adapter.notifyDataSetChanged();
                service_adapter.notifyDataSetChanged();


        }
}
    @Override
    public void onClick(View view) {
        OrderPop pop=new OrderPop(this);
        switch (view.getId()){

            case R.id.back_btn:
                finish();
                break;
            case R.id.date_btn:
                pop.GetDate_pop(new OrderPop.POPLisstenner() {
                    @Override
                    public void ongetResult(String result,String time,Boolean upfont) {
                        date_tv.setText(result);
                    }
                });
                break;
            case R.id.save_btn:
                pop.GetWay_pop(bascket.getProvider().isUpfront()
            ,bascket.getProvider().getUpfrontAmount()*Caculate_Tot(),bascket.getPayment_id(),
                        new OrderPop.POPLisstenner() {
                                                        @Override
                                    public void ongetResult(String result,String time_,Boolean upfont) {
                                                            findViewById(R.id.linear_wait).setVisibility(View.VISIBLE);
                                                            Order_root root=new Order_root();
                                                           if (reOrder) {
                                                               root.ReOrder(mcontext,
                                                                       location_typ,
                                                                       String.valueOf(mLatLng.latitude),
                                                                       String.valueOf(mLatLng.longitude),
                                                                       date,
                                                                       time,
                                                                       String.valueOf(bascket.getProvider().getUpfrontAmount()),
                                                                       Coupon_txt,
                                                                       result,
                                                                       upfont,
                                                                       order_id

                                                                       , new Order_root.PostOrderListener() {
                                                                           @Override
                                                                           public void onSuccess(Result result) {
                                                                               findViewById(R.id.linear_wait).setVisibility(View.GONE);
                                                                               try {
                                                                                   if (new User_info(mcontext).getLanguage().equals("en")){
                                                                                       Toast.makeText(OrderDetails_Create.this,result.getMessageEn(),Toast.LENGTH_SHORT).show();
                                                                                   }else {
                                                                                       Toast.makeText(OrderDetails_Create.this,result.getMessageAr(),Toast.LENGTH_SHORT).show();
                                                                                   }
                                                                               } catch (Exception e) {
                                                                                   e.printStackTrace();
                                                                               }
                                                                               MyOrders.statusID=1;
                                                                               MainActivity.item_select=R.id.my_orders;
                                                                               finish();


                                                                           }

                                                                           @Override
                                                                           public void onStart() {

                                                                           }

                                                                           @Override
                                                                           public void onFailure(String msg) {

                                                                           }
                                                                       }
                                                               );
                                                           }
                                                           else {
                                                                   root.PostOrder(mcontext,
                                                                           bascket.getProvider().get_id()
                                                                           , location_typ,
                                                                           String.valueOf(mLatLng.latitude),
                                                                           String.valueOf(mLatLng.longitude),
                                                                           date,
                                                                           time,
                                                                           String.valueOf(bascket.getProvider().getUpfrontAmount()),
                                                                           "",
                                                                           result,
                                                                           upfont

                                                                           , new Order_root.PostOrderListener() {
                                                                               @Override
                                                                               public void onSuccess(Result result) {
                                                                                   findViewById(R.id.linear_wait).setVisibility(View.GONE);
                                                                                   try {
                                                                                       if (new User_info(mcontext).getLanguage().equals("en")){
                                                                                           Toast.makeText(OrderDetails_Create.this,result.getMessageEn(),Toast.LENGTH_SHORT).show();
                                                                                       }else {
                                                                                           Toast.makeText(OrderDetails_Create.this,result.getMessageAr(),Toast.LENGTH_SHORT).show();
                                                                                       }
                                                                                   } catch (Exception e) {
                                                                                       e.printStackTrace();
                                                                                   }
                                                                                   MyOrders.statusID=1;
                                                                                   MainActivity.item_select=R.id.my_orders;
                                                                                   finish();

                                                                               }

                                                                               @Override
                                                                               public void onStart() {

                                                                               }

                                                                               @Override
                                                                               public void onFailure(String msg) {

                                                                               }
                                                                           }
                                                                   );

                                                           }
                                    }
        });

                break;
            case R.id.time_btn:
                pop.GetTime_pop(new OrderPop.POPLisstenner() {
                    @Override
                    public void ongetResult(String result,String time_,Boolean upfont) {
                        time_tv.setText(result);
                        time=result;
                    }
                });

                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    mLatLng  = new LatLng(data.getDoubleExtra("lat",0.0),
                            data.getDoubleExtra("lng",0.0));
                    String Location=data.getStringExtra("Location");
                    make_marke(mLatLng);
                }
                break;
            }
        }
    }
    /**********************************location********************************/
    private Boolean flag;
    private FusedLocationProviderClient fusedLocationClient;
    int gps=0;

    private GoogleMap mMap;
    public static LatLng mLatLng=null;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocationIfPermitted();
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent=new Intent(mcontext,Add_New_Address_Map.class);
                intent.putExtra("lat",mLatLng.latitude);
                intent.putExtra("lng",mLatLng.longitude);
                startActivityForResult(intent, 1);

            }
        });


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

            } else{
                get_location();

            }
        }
    }
    @SuppressLint("MissingPermission")
    private void get_location() {

        new com.osamayastal.easycare.Model.Classes.GPS(this).
                turnGPSOn(new com.osamayastal.easycare.Model.Classes.GPS.onGpsListener() {
                    @Override
                    public void gpsStatus(boolean isGPSEnable) {
                        // turn on GPS
                        flag = isGPSEnable;
                    }
                });

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




    }
    private void make_marke(final LatLng latLng){
        mLatLng=latLng;
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_mark)));


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}
