package com.osamayastal.easycare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.islamkhsh.CardSliderViewPager;
import com.osamayastal.easycare.Adapters.Categories_adapter;
import com.osamayastal.easycare.Adapters.Provider_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.LanguageHelper;
import com.osamayastal.easycare.Model.Classes.Provider.Provider;
import com.osamayastal.easycare.Model.Classes.Slider;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Model.Rootes.Home_root;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.AllServices;
import com.osamayastal.easycare.activities.Most_rate;
import com.osamayastal.easycare.activities.Most_request;
import com.osamayastal.easycare.activities.Search;
import com.osamayastal.easycare.Adapters.CardAdapter;
import com.osamayastal.easycare.activities.ServiceProfiderDetails;
import com.osamayastal.easycare.activities.Service_Single;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Home extends Fragment implements View.OnClickListener {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //========set default language as persian:start==============
//        LanguageHelper.changeLocale(this.getResources(), "ar");
        //========set default language as persian:end================
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        /******************************Test Fore user_Login*************************************/
      User_info  user_info = new User_info(getContext());
        if (user_info.getId()== null) {
            if (user_info.getToken() == null) {
                user root = new user();
                root.Get_Token(getContext(), new user.Token_Listener() {
                    @Override
                    public void onSuccess(String token) {
                        Loading_data();
                    }
                });
            }
            if (user_info.getId()!= null && user_info.CONF_phone(getContext())){
                Loading_data();
            }

        } else {
            Bascket_root root = new Bascket_root();
            root.GetItemCount(getContext(), new Bascket_root.Basket_count_Listener() {
                @Override
                public void onSuccess(int nb) {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure(String msg) {

                }
            });

            Loading_data();
        }

        return view;

    }

    private void Loading_data() {
        Home_root home_root=new Home_root();
        home_root.GetHome(getContext(), new Home_root.homeListener() {
            @Override
            public void onSuccess(com.osamayastal.easycare.Model.Controle.Home home) {
                view.findViewById(R.id.progress1).setVisibility(View.GONE);
                view.findViewById(R.id.progress2).setVisibility(View.GONE);
                view.findViewById(R.id.progress3).setVisibility(View.GONE);
                view.findViewById(R.id.progress4).setVisibility(View.GONE);

                top_rate_list.clear();
                top_req_list.clear();
                categories.clear();
                sliderList.clear();

                //////addAll
                top_req_list.addAll(home.getTopRequestedProviders());
                top_rate_list.addAll(home.getTopRatedProviders());
                categories.addAll(home.getCategories());
                sliderList.addAll(home.getSliders());
                adapter_rate.notifyDataSetChanged();
                adapter_req.notifyDataSetChanged();
                Slide_adapter.notifyDataSetChanged();
/////Sort by isActive

List<Categorie> categorieList=new ArrayList<>();
                for (Categorie c:categories
                     ) {
                    if (c.isActive()){
                        categorieList.add(c);
                        categories.remove(c);
                    }
                }
                categorieList.addAll(categories);
                categories.clear();
                categories.addAll(categorieList);
                categories_adapter.notifyDataSetChanged();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private RecyclerView RV_server,RV_top_rat,RV_top_req;
    private TextView more_server,more_rat,more_req;
    private List<Categorie> categories;
    private List<Provider> top_rate_list;
    private List<Provider> top_req_list;
    private List<Slider> sliderList;
    private Categories_adapter categories_adapter;
    private Provider_adapter adapter_rate;
    private Provider_adapter adapter_req;
    private CardAdapter Slide_adapter;
private ImageButton search_btn;
    private void init(View view) {
        more_rat=view.findViewById(R.id.more_rate);
        more_req=view.findViewById(R.id.more_request);
        more_server=view.findViewById(R.id.more_server);
        RV_server=view.findViewById(R.id.RV_server);
        RV_top_rat=view.findViewById(R.id.RV_top_rate);
        RV_top_req=view.findViewById(R.id.RV_top_request);
        search_btn=view.findViewById(R.id.search_btn);
     /*******************************Actions************************************/
        more_server.setOnClickListener(this);
        more_req.setOnClickListener(this);
        more_rat.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        /********************************List****************************************/
        categories=new ArrayList<>();
        top_rate_list=new ArrayList<Provider>();
        top_req_list=new ArrayList<Provider>();
        sliderList=new ArrayList<Slider>();
        /********************************adapters****************************************/
        adapter_rate=new Provider_adapter(getContext(), top_rate_list, new Provider_adapter.Selected_item() {
            @Override
            public void Onselcted(Provider provider) {

                Intent intent=new Intent(getContext(), ServiceProfiderDetails.class);
                intent.putExtra("provider_id",provider.get_id());
                startActivity(intent);

            }
        });
        adapter_req=new Provider_adapter(getContext(), top_req_list, new Provider_adapter.Selected_item() {
            @Override
            public void Onselcted(Provider provider) {

                Intent intent=new Intent(getContext(), ServiceProfiderDetails.class);
                intent.putExtra("provider_id",provider.get_id());
                startActivity(intent);
            }
        });
        categories_adapter=new Categories_adapter(getContext(),categories,null);
        /********************************RV****************************************/
        RV_top_req.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        RV_top_req.setAdapter(adapter_req);
        RV_top_rat.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        RV_top_rat.setAdapter(adapter_rate);
        RV_server.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        RV_server.setAdapter(categories_adapter);

        /*************************************ADS***************************************/

        CardSliderViewPager cardSliderViewPager = view.findViewById(R.id.viewPager);
      Slide_adapter=new CardAdapter(getContext(),sliderList);
        cardSliderViewPager.setAdapter(Slide_adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.more_rate:
                startActivity(new Intent(getContext(), Most_rate.class));

                break;
            case R.id.more_request:
                startActivity(new Intent(getContext(), Most_request.class));

                break;
            case R.id.more_server:
                startActivity(new Intent(getContext(), AllServices.class));

                break;
            case R.id.search_btn:
                startActivity(new Intent(getContext(), Search.class));
                break;
        }

    }

}
