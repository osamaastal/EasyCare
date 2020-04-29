package com.osamayastal.easycare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.osamayastal.easycare.Adapters.Favorit_adapter;
import com.osamayastal.easycare.Adapters.Product_adapter;
import com.osamayastal.easycare.Adapters.Search_adapter;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Favorite_get;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Controle.Categories;
import com.osamayastal.easycare.Model.Controle.Favorites;
import com.osamayastal.easycare.Model.Controle.Favorites_get;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.Model.Rootes.Favorite_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.MainActivity;
import com.osamayastal.easycare.activities.Search;
import com.osamayastal.easycare.activities.ServiceProfiderDetails;
import com.osamayastal.easycare.activities.Service_Single;

import java.util.ArrayList;
import java.util.List;

public class MyFavorites extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_favorites, container, false);
        init(view);
        Loading();
        return view;
    }

    private void Loading() {
        Favorite_root root=new Favorite_root();
        root.GetFavorites(getContext(), "0", new Favorite_root.FavoriteListener_get() {
            @Override
            public void onSuccess(Favorites_get favorites) {
                progressBar.setVisibility(View.GONE);
                providerList.clear();
providerList.addAll(favorites.getItems());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    ProgressBar progressBar;
    private ImageButton back;
    RecyclerView RV;
    List<Favorite_get> providerList;
    Favorit_adapter adapter;
    private void init(View view) {
        progressBar=view.findViewById(R.id.progress);
        back=view.findViewById(R.id.back_btn);
        RV=view.findViewById(R.id.RV);
        /******************Actions*******************/
        back.setOnClickListener(this);
        providerList=new ArrayList<>();
        adapter=new Favorit_adapter(getContext(), providerList, new Favorit_adapter.Selected_item() {
            @Override
            public void Onselcted(Favorite_get favorite_get) {
                Intent intent=new Intent(getContext(), ServiceProfiderDetails.class);
                intent.putExtra("provider_id",favorite_get.getProvider_id().get_id());
                startActivity(intent);
                getActivity().finish();
            }
        });
        RV.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        RV.setAdapter(adapter);

    }
    public void switchFGM(Fragment fragment){
        MainActivity.transaction = getActivity().getSupportFragmentManager().beginTransaction();
        MainActivity. transaction.replace(R.id.mainContainer, fragment);
        MainActivity. transaction.commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                getActivity().finish();

                break;
        }
    }
}
