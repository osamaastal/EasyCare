package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

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
    String categorie_id=null;
    String city_id=null;
    int rat =-1;
    private PopupWindow setPopUpWindow() {
//        LayoutInflater inflater = (LayoutInflater).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.popup_filter, null);

        final Spinner city=view1.findViewById(R.id.city_spinner);
        RecyclerView RV=view1.findViewById(R.id.Rv_type);
        final IndicatorSeekBar rang=view1.findViewById(R.id.range);
        SwitchButton watch=view1.findViewById(R.id.watch_switch);
        RatingBar rate=view1.findViewById(R.id.ratingBar);
        ImageButton save=view1.findViewById(R.id.save_btn);
        ImageButton cacel=view1.findViewById(R.id.cancel_btn);

        /*******************************************Actions************************************/

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
        List<City> cityArrayList=new ArrayList<>();
        City_adapter adaptercity=new City_adapter(Search.this,R.layout.row_service_text,R.id.type_tv, cityArrayList);
        city.setAdapter(adaptercity);
        Get_all_city(cityArrayList,adaptercity);
        /********************************************************************************/

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                City city1=(City) city.getSelectedItem();
                city_id=city1.get_id();
                rat=rang.getProgress();
                Search_fun(name.getText(),categorie_id,city_id,rat);
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
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void Search_fun(CharSequence charSequence, String categorie_id, String city_id, int rat) {
        final Search_root search_root=new Search_root();
        search_root.GetSearch(this, categorie_id, city_id, charSequence.toString(), rat, 0,
                new Search_root.homeListener() {
                    @Override
                    public void onSuccess(com.osamayastal.easycare.Model.Controle.Search home) {
                        if (home.getStatus_code()==200) {
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
                Search_fun(name.getText(), categorie_id, city_id, rat);
                break;
            case R.id.filter_btn:
                mypopupWindow_filter.showAsDropDown(filter,0,0);
                break;

        }
    }
}
