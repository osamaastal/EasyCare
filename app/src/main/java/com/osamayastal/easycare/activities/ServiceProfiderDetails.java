package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Adapters.Provider_servicies_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider.ProviderSetting;
import com.osamayastal.easycare.Model.Classes.Search;
import com.osamayastal.easycare.Model.Classes.Sub_servic;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Favorites;
import com.osamayastal.easycare.Model.Controle.Provider_Details;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Model.Rootes.Favorite_root;
import com.osamayastal.easycare.Model.Rootes.ProviderDetails_root;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.Popups.OrderPop;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import top.defaults.drawabletoolbox.DrawableBuilder;

public class ServiceProfiderDetails extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

   private String provider_id;
    private Search provider=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profider_details);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        provider_id=bundle.getString("provider_id");

        init();
        FetchData();

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
    private void FetchData() {
        /*******************Loading********************/
        ProviderDetails_root root=new ProviderDetails_root();
        root.GetDetailsListener(this, provider_id, new ProviderDetails_root.DetailsListener() {
            @Override
            public void onSuccess(Provider_Details details) {
                findViewById(R.id.linear_wait).setVisibility(View.GONE);
                provider=details.getProviderDetails();
                Loading();
                onMapReady(mMap);
                sub_servicList.clear();
                providerSettingList.clear();
                productList.clear();
                sub_servicList.addAll(details.getServicList());

                categorieList.clear();
                categorieList.addAll(details.getCategorieList());

                productList.addAll(details.getProductList());
                categories_adapter.notifyDataSetChanged();
                product_adapter.notifyDataSetChanged();
                progressBar1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);

                if (productList.size()==0){
                    findViewById(R.id.linear_product).setVisibility(View.GONE);
                }
/******************************From ...To *********************************/
                providerSettingList.addAll(details.getProviderSettingList());
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

    private void Loading() {
       if (provider!=null){
          
           /*******/
           Picasso.with(this)
                   .load(provider.getImage())
                   .into(img);
           /*************/
           name.setText(provider.getName());
           title.setText(provider.getName());
           address.setText(provider.getAddress());
           ratingBar.setRating(provider.getRate());
           if (new User_info(this).getLanguage().equals("en")){
               type.setText(provider.getCategory_id().getEnName());
           }else {
               type.setText(provider.getCategory_id().getArName());
           }
           try{
               String color=provider.getCategory_id().getColor();
               makeDrawable(Color.parseColor(color),type,18);
           } catch (Exception e) {
               e.printStackTrace();
           }
           Log.d("favorite_id ", provider.getFavorite_id().toString());

           if (!provider.getFavorite_id().equals("null")){
               like_btn.setImageDrawable(getDrawable(R.drawable.ic_like));
           }else {
               like_btn.setImageDrawable(getDrawable(R.drawable.ic_unlike));

           }
           basket_count();
           /*************
            * Images of product
            */
           mDemoSlider.removeAllSliders();

           for(String name : provider.getImages()){
               TextSliderView textSliderView = new TextSliderView(this);
               // initialize a SliderLayout
               textSliderView
                       .description("")
                       .image(name)
                       .setScaleType(BaseSliderView.ScaleType.Fit);

               Log.d("image_url",name);


               //add your extra information
               textSliderView.bundle(new Bundle());
               textSliderView.getBundle()
                       .putString("extra",name);
               mDemoSlider.addSlider(textSliderView);
           }
           // Toast.makeText(getApplicationContext(),real_estate.getImagesURL().size()+"",Toast.LENGTH_SHORT).show();
           mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
           mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
           mDemoSlider.setCustomAnimation(new DescriptionAnimation());
           mDemoSlider.setDuration(4000);
           mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.app_indicator));


       }
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
    private void basket_count() {
        if (new User_info(mcontext).getId()==null){
            basket_btn.setBackground(getDrawable(R.drawable.bg_circle_gray));
            basket_nb.setVisibility(View.GONE);
            return;
        }
        Bascket_root root=new Bascket_root();
        root.GetItemCount(this, new Bascket_root.Basket_count_Listener() {
            @Override
            public void onSuccess(int nb) {
                if (nb ==0){
                    basket_btn.setBackground(getDrawable(R.drawable.bg_circle_gray));
                    basket_nb.setVisibility(View.GONE);
                }
                else {
                    basket_nb.setText(nb +"");
                    basket_btn.setBackground(getDrawable(R.drawable.bg_circle_basket));
                    basket_nb.setVisibility(View.VISIBLE);
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

    private SliderLayout mDemoSlider;
    ProgressBar progressBar1,progressBar2;
Context mcontext=ServiceProfiderDetails.this;
    ImageButton like_btn,basket_btn,back_btn;
    CircleImageView img;
    TextView name,address,from,to,type,more_pro,basket_nb,title;
    RecyclerView prod_RV,serv_RV;
    RatingBar ratingBar;
    List<Product> productList;
    List<Sub_servic> sub_servicList;
    List<Categorie> categorieList;
    List<ProviderSetting> providerSettingList;
    Provider_servicies_adapter categories_adapter;
    Product_adapter product_adapter;
    private void init() {
        progressBar1=findViewById(R.id.progress1);
        progressBar2=findViewById(R.id.progress2);
        img=findViewById(R.id.provider_Img);
        mDemoSlider = (SliderLayout)findViewById(R.id.ImgSlider);

        like_btn=findViewById(R.id.like_btn);
        basket_btn=findViewById(R.id.basket_btn);
        back_btn=findViewById(R.id.back_btn);
        name=findViewById(R.id.provider_name_tv);
        title=findViewById(R.id.title);
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
        categorieList=new ArrayList<>();
        product_adapter=new Product_adapter(this, productList, new Product_adapter.Selected_item() {
            @Override
            public void Onselcted(Product product) {
                basket_count();
            }
        });
        categories_adapter=new Provider_servicies_adapter(this, categorieList, new Provider_servicies_adapter.Selected_item() {
            @Override
            public void Onselcted(Categorie categorie) {
                if (new User_info(ServiceProfiderDetails.this).getId()==null){
                    LoginAlert();
                }else {
                    OrderPop pop = new OrderPop(ServiceProfiderDetails.this);
                    pop.AddOrder_pop(ServiceProfiderDetails.this, categorie.get_id(), provider.get_id()
               ,new OrderPop.OrderLisstenner() {
                        @Override
                        public void onGoBasket() {

                            MainActivity.item_select=R.id.basket;
                             finish();
                        }

                                @Override
                                public void onCancel() {
                                    basket_count();
                                }
                            });
                }
            }
        });
        serv_RV.setLayoutManager(new GridLayoutManager(this,3));
        prod_RV.setLayoutManager(new GridLayoutManager(this,2));
        serv_RV.setAdapter(categories_adapter);
        prod_RV.setAdapter(product_adapter);
        /***********************************Map***************************************/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    public void LoginAlert(){
        AppPop pop=new AppPop();
        pop.Login_POP(ServiceProfiderDetails.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLocale(this);

        basket_count();
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
                provider_id=null;
                MainActivity.item_select=R.id.basket;
                finish();
                break;
            case R.id.back_btn:
                provider_id=null;
                finish();
                break;
            case R.id.more_product:
                Bundle bundle = new Bundle();
                bundle.putString("provider_id",provider.get_id());
                Intent intent=new Intent(mcontext, AllProducts.class);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }
    }

    private GoogleMap mMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


if (provider!=null) {

    mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.setMinZoomPreference(12);
    LatLng latLng = new LatLng(provider.getLat()
            , provider.getLng());

    Marker marker = mMap.addMarker(new MarkerOptions()
            .title(provider.getName())
            .snippet(provider.getName())
            .position(latLng)
            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_mark)));

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
