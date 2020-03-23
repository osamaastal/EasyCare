package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.osamayastal.easycare.Adapters.Categories_adapter;
import com.osamayastal.easycare.Adapters.Provider_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.TopRequestedProviders;
import com.osamayastal.easycare.Model.Rootes.Home_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        Loading_data();
        return view;

    }

    private void Loading_data() {
        Home_root home_root=new Home_root();
        home_root.GetHome(getContext(), new Home_root.homeListener() {
            @Override
            public void onSuccess(com.osamayastal.easycare.Model.Controle.Home home) {
                top_rate_list.clear();
                top_req_list.clear();
                categories.clear();
                //////addAll
                top_req_list.addAll(home.getTopRequestedProviders());
                top_rate_list.addAll(home.getTopRatedProviders());
                categories.addAll(home.getCategories());
                adapter_rate.notifyDataSetChanged();
                adapter_req.notifyDataSetChanged();
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
    private List<TopRequestedProviders> top_rate_list;
    private List<TopRequestedProviders> top_req_list;
    private Categories_adapter categories_adapter;
    private Provider_adapter adapter_rate;
    private Provider_adapter adapter_req;

    private void init(View view) {
        more_rat=view.findViewById(R.id.more_rate);
        more_req=view.findViewById(R.id.more_request);
        more_server=view.findViewById(R.id.more_server);
        RV_server=view.findViewById(R.id.RV_server);
        RV_top_rat=view.findViewById(R.id.RV_top_rate);
        RV_top_req=view.findViewById(R.id.RV_top_request);
     /*******************************Actions************************************/
        more_server.setOnClickListener(this);
        more_req.setOnClickListener(this);
        more_rat.setOnClickListener(this);
        /********************************List****************************************/
        categories=new ArrayList<>();
        top_rate_list=new ArrayList<TopRequestedProviders>();
        top_req_list=new ArrayList<com.osamayastal.easycare.Model.Classes.TopRequestedProviders>();
        /********************************adapters****************************************/
        adapter_rate=new Provider_adapter(getContext(),top_rate_list,null);
        adapter_req=new Provider_adapter(getContext(),top_req_list,null);
        categories_adapter=new Categories_adapter(getContext(),categories,null);
        /********************************RV****************************************/
        RV_top_req.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,true));
        RV_top_req.setAdapter(adapter_req);
        RV_top_rat.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,true));
        RV_top_rat.setAdapter(adapter_rate);
        RV_server.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,true));
        RV_server.setAdapter(categories_adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.more_rate:
                break;
            case R.id.more_request:
                break;
            case R.id.more_server:
                break;
        }
    }
}
