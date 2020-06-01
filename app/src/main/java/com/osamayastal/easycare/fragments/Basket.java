package com.osamayastal.easycare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
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
import com.osamayastal.easycare.Model.Controle.Basket_prices;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Popups.AppPop;
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
                    tax.setText(String.format("%.2f", bascket_.getTax()));
                    discount.setText(String.format("%.2f", bascket_.getTotal_discount()));
                    total.setText(String.format("%.2f", bascket_.getFinal_total()));
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
    TextView subtotal,discount,total,tax;
    Button save;
    List<Bascket> baskets;
    Basket_adapter adapter;
    ProgressBar progressBar;
    private List<String> provider_id;
    private void init(final View view) {
        RV=view.findViewById(R.id.RV_basket);
        tax=view.findViewById(R.id.tax_amount_tv);
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
             public void OnRefresh(Car_servece car_servece) {

                 loading(view);

             }

             @Override
             public void Onselcted(int potions) {
                 bascket_index=potions;
                 String id=baskets.get(potions).getProvider().get_id();
                 if (provider_id.contains(id)){
                     provider_id.remove(id);
                 }else {
                     provider_id.add(id);

                 }
                 Bascket_root root=new Bascket_root();
                 Basket_prices prices=new Basket_prices(null);
                 root.GetItemPrices(getContext(),prices.to_JSON(provider_id) , new Bascket_root.GetPricesListener() {
                     @Override
                     public void onSuccess(Basket_prices bascket) {
                         subtotal.setText(String.format("%.2f", bascket.getTotal_price()));
                         tax.setText(String.format("%.2f", bascket.getTax()));
                         discount.setText(String.format("%.2f", bascket.getTotal_discount()));
                         total.setText(String.format("%.2f", bascket.getFinal_total()));
                     }

                     @Override
                     public void onStart() {

                     }

                     @Override
                     public void onFailure(String msg) {

                     }
                 });

             }
         });
        RV.setAdapter(adapter);


        /*****************/
        provider_id=new ArrayList<>();
    }

    int bascket_index=-1;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_btn:
                AppPop pop=new AppPop();
                if (baskets.size()==1){
                    bascket_index=0;
                    provider_id=new ArrayList<>();
                    String id=baskets.get(bascket_index).getProvider().get_id();
                    provider_id.add(id);
                }
                if (provider_id.size()==0 || provider_id.size()>1){
                    pop.ShowMessage(getString(R.string.maste_select_one),getView());
                    return;
                }

                pop.Conferme_POP(getContext(), getString(R.string.chetout_msg), new AppPop.goListenner() {
                    @Override
                    public void Go() {

                        Bundle bundle = new Bundle();
                        Basket_prices prices=new Basket_prices(null);
                        bundle.putString("provider_id",prices.to_JSON(provider_id).toString());
                        bundle.putString("order_id",null);
                        bundle.putString("json",baskets.get(bascket_index).getMjsonObject().toString());

                        Intent intent=new Intent(getActivity(), OrderDetails_Create.class);
                        intent.putExtras(bundle);
                       startActivity(intent);
                    }

                    @Override
                    public void Cancel() {

                    }
                });



                break;

        }
    }
}
