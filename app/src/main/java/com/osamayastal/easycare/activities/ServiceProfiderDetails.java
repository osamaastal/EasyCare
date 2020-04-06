package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joooonho.SelectableRoundedImageView;
import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Adapters.Provider_servicies_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Classes.ProviderSetting;
import com.osamayastal.easycare.Model.Classes.Search;
import com.osamayastal.easycare.Model.Classes.Sub_servic;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Favorites;
import com.osamayastal.easycare.Model.Controle.Provider_Details;
import com.osamayastal.easycare.Model.Rootes.Favorite_root;
import com.osamayastal.easycare.Model.Rootes.ProviderDetails_root;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceProfiderDetails extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    public static Search provider=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profider_details);
        init();
        Loading();
    }

    private void Loading() {
       if (provider!=null){
           Picasso.with(this)
                   .load(provider.getImage())
                   .into(img_slide);
           /*******/
           Picasso.with(this)
                   .load(provider.getImage())
                   .into(img);
           /*************/
           name.setText(provider.getName());
           address.setText(provider.getAddress());
           ratingBar.setRating(provider.getRate());
           Log.d("favorite_id ", provider.getFavorite_id().toString());

           if (!provider.getFavorite_id().equals("null")){
               like_btn.setImageDrawable(getDrawable(R.drawable.ic_like));
           }else {
               like_btn.setImageDrawable(getDrawable(R.drawable.ic_unlike));

           }
           /*******************Loading********************/
           ProviderDetails_root root=new ProviderDetails_root();
           root.GetDetailsListener(this, provider.get_id(), new ProviderDetails_root.DetailsListener() {
               @Override
               public void onSuccess(Provider_Details details) {
                   sub_servicList.clear();
                   providerSettingList.clear();
                   productList.clear();
                   sub_servicList.addAll(details.getProviderDetails().getSub_services());
                   productList.addAll(details.getProviderDetails().getProducts());
                   serviciesAdapter.notifyDataSetChanged();
                   product_adapter.notifyDataSetChanged();
                   progressBar1.setVisibility(View.GONE);
                   progressBar2.setVisibility(View.GONE);
/******************************From ...To *********************************/
                   providerSettingList.addAll(details.getProviderDetails().getProviderSetting());
                  if (providerSettingList.size()!=0){
                      from.setText(providerSettingList.get(0).getMin());
                      to.setText(providerSettingList.get(0).getMax());
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

    ProgressBar progressBar1,progressBar2;
    SelectableRoundedImageView img_slide;
    ImageButton like_btn,basket_btn,back_btn;
    CircleImageView img;
    TextView name,address,from,to,type,more_pro,basket_nb;
    RecyclerView prod_RV,serv_RV;
    RatingBar ratingBar;
    List<Product> productList;
    List<Sub_servic> sub_servicList;
    List<ProviderSetting> providerSettingList;
    Provider_servicies_adapter serviciesAdapter;
    Product_adapter product_adapter;
    private void init() {
        progressBar1=findViewById(R.id.progress1);
        progressBar2=findViewById(R.id.progress2);
        img=findViewById(R.id.provider_Img);
        img_slide=findViewById(R.id.ImgSlider);
        like_btn=findViewById(R.id.like_btn);
        basket_btn=findViewById(R.id.basket_btn);
        back_btn=findViewById(R.id.back_btn);
        name=findViewById(R.id.provider_name_tv);
        address=findViewById(R.id.address_tv);
        from=findViewById(R.id.from_tv);
        to=findViewById(R.id.to_tv);
        type=findViewById(R.id.type_tv);
        more_pro=findViewById(R.id.more_product);
        basket_nb=findViewById(R.id.basket_nb);
        prod_RV=findViewById(R.id.RV_products);
        serv_RV=findViewById(R.id.RV_services);
        ratingBar=findViewById(R.id.ratingBar);
        /*********************************Actions***********************************/
        back_btn.setOnClickListener(this);
        basket_btn.setOnClickListener(this);
        like_btn.setOnClickListener(this);
        more_pro.setOnClickListener(this);
        /*******************************RV******************************************/
        productList=new ArrayList<>();
        sub_servicList=new ArrayList<>();
        providerSettingList=new ArrayList<>();
        product_adapter=new Product_adapter(this,productList,null);
        serviciesAdapter=new Provider_servicies_adapter(this,sub_servicList,null);
        serv_RV.setLayoutManager(new GridLayoutManager(this,3));
        prod_RV.setLayoutManager(new GridLayoutManager(this,2));
        serv_RV.setAdapter(serviciesAdapter);
        prod_RV.setAdapter(product_adapter);
        /***********************************Map***************************************/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.like_btn:
                Favorite_root root=new Favorite_root();
                if (!provider.getFavorite_id().equals("null")){

                    root.DELETEFavorites(this, provider.getFavorite_id(), new Favorite_root.FavoriteListener() {
                        @Override
                        public void onSuccess(Favorites favorites) {
                            if (new User_info(ServiceProfiderDetails.this).getLanguage().equals("en")){
                                Toast.makeText(ServiceProfiderDetails.this,favorites.getMessageEn(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ServiceProfiderDetails.this,favorites.getMessageAr(),Toast.LENGTH_SHORT).show();

                            }
                            if (favorites.getStatus_code()==200){
                                like_btn.setImageDrawable(getDrawable(R.drawable.ic_unlike));
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
                    root.POSTFavorites(this, provider.get_id(), new Favorite_root.FavoriteListener() {
                        @Override
                        public void onSuccess(Favorites favorites) {
                            if (new User_info(ServiceProfiderDetails.this).getLanguage().equals("en")){
                                Toast.makeText(ServiceProfiderDetails.this,favorites.getMessageEn(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ServiceProfiderDetails.this,favorites.getMessageAr(),Toast.LENGTH_SHORT).show();

                            }
                            if (favorites.getStatus_code()==200){
                                like_btn.setImageDrawable(getDrawable(R.drawable.ic_like));
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
                break;
            case R.id.basket_btn:
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.more_product:
                startActivity(new Intent(ServiceProfiderDetails.this,AllProducts.class));
                break;
        }
    }

    private GoogleMap mMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(12);

        LatLng latLng=new LatLng(provider.getLat()
                ,provider.getLng());

        Marker marker = mMap.addMarker(new MarkerOptions()
                .title(provider.getName())
                .snippet(provider.getName() )
                .position(latLng)
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker_wash)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                String label = provider.getName();
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
            }
        });

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
