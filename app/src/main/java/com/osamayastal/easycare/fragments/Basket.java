package com.osamayastal.easycare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.osamayastal.easycare.Adapters.Basket_Service_adapter;
import com.osamayastal.easycare.Adapters.Basket_adapter;
import com.osamayastal.easycare.Model.Classes.Basket.Bascket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Popups.OrderPop;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.OrderDetails_Create;

import java.util.ArrayList;
import java.util.List;

public class Basket extends Fragment implements View.OnClickListener {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_basket, container, false);
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loading(view);
    }

    private void loading(final View view) {
        Bascket_root root=new Bascket_root();
        root.GetBasket(getContext(), 0, new Bascket_root.GetbasketListener() {
            @Override
            public void onSuccess(com.osamayastal.easycare.Model.Controle.Bascket bascket_) {
                if (bascket_.getStatus()) {
                    baskets.clear();
                    baskets.addAll(bascket_.getItems());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    if (baskets.size() == 0) {
                        view.findViewById(R.id.linear_no_results).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.tot_lay).setVisibility(View.GONE);
                        view.findViewById(R.id.save_btn).setVisibility(View.GONE);
                    } else {
                        view.findViewById(R.id.linear_no_results).setVisibility(View.GONE);
                        view.findViewById(R.id.tot_lay).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.save_btn).setVisibility(View.VISIBLE);
                    }
                    subtotal.setText(String.format("%.2f", bascket_.getTotal_price()));
                    discount.setText("00");
                    total.setText(String.format("%.2f", bascket_.getTotal_price()));
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

    RecyclerView RV;
    TextView subtotal,discount,total;
    Button save;
    List<Bascket> baskets;
    Basket_adapter adapter;
    ProgressBar progressBar;
    private void init(final View view) {
        RV=view.findViewById(R.id.RV_basket);
        subtotal=view.findViewById(R.id.subtotal);
        discount=view.findViewById(R.id.discount_amount_tv);
        total=view.findViewById(R.id.total_price_tv);
        save=view.findViewById(R.id.save_btn);
        progressBar=view.findViewById(R.id.progress);
        /*************************ACTIONS************************/
        save.setOnClickListener(this);
        baskets=new ArrayList<>();
        RV.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
         adapter=new Basket_adapter(getContext(), baskets, new Basket_adapter.Selected_item() {
             @Override
             public void Onselcted(Car_servece car_servece) {

                 loading(view);
//
             }
         });
        RV.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_btn:

                Bundle bundle = new Bundle();
                                        bundle.putInt("bascket_index",0);
                                        Intent intent=new Intent(getActivity(), OrderDetails_Create.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);


                break;

        }
    }
}
