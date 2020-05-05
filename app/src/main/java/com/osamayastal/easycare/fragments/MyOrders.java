package com.osamayastal.easycare.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.osamayastal.easycare.Adapters.Order_adapter;
import com.osamayastal.easycare.Model.Classes.Order;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Orders;
import com.osamayastal.easycare.Model.Rootes.Order_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.MainActivity;
import com.osamayastal.easycare.activities.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends Fragment implements View.OnTouchListener {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_my_orders, container, false);
        init(view);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Loading(statusID);
        switch (statusID){
            case 1:
                wait.requestFocus();
                break;

            case 3:
                current.requestFocus();
                break;
            case 4:
                complete.requestFocus();
                break;
            case 5:
                cancel.requestFocus();
                break;
        }

    }

    private int statusID=1;
private LinearLayout wait,current,complete,cancel;
    private RecyclerView RV;
    List<Order> orderList;
Order_adapter adapter;
private Context mcontext;
    private void init(View view) {
        wait=view.findViewById(R.id.wait_req);
        current=view.findViewById(R.id.current_req);
        complete=view.findViewById(R.id.complet_req);
        cancel=view.findViewById(R.id.cancel_req);
        RV=view.findViewById(R.id.RV_orders);
        mcontext=getContext();
        /****************************Actions***********************/
        wait.setOnTouchListener(this);
        complete.setOnTouchListener(this);
        current.setOnTouchListener(this);
        cancel.setOnTouchListener(this);
        wait.requestFocus();
        RV.setLayoutManager(new LinearLayoutManager(mcontext,LinearLayoutManager.VERTICAL,false));
    }




    private void Loading(final int i) {
        view. findViewById(R.id.no_data).setVisibility(View.GONE);
        view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
        Order_root root=new Order_root(mcontext);
        root.GetAllOrder(mcontext, 0, i, new Order_root.GetOrderListener() {
            @Override
            public void onSuccess(Orders orders) {

                if (i==statusID){
                    view.findViewById(R.id.progress).setVisibility(View.GONE);

                    orderList=new ArrayList<>();
                    orderList.addAll(orders.getItems());
                    adapter=new Order_adapter(mcontext, orderList, new Order_adapter.Selected_item() {
                        @Override
                        public void Onselcted(Order order) {
                            Intent intent=new Intent(mcontext, OrderDetails.class);
                            intent.putExtra("order_id",order.get_id());
                            startActivity(intent);
                        }
                    });
                    RV.setAdapter(adapter);
                    if (orderList.size()==0){
                        view. findViewById(R.id.no_data).setVisibility(View.VISIBLE);

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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.wait_req:
                Loading(1);
                statusID=1;
                break;
            case R.id.current_req:
                Loading(3);
                statusID=3;
                break;
            case R.id.complet_req:
                Loading(4);
                statusID=4;

                break;
            case R.id.cancel_req:
                Loading(5);
                statusID=5;

                break;
        }
        return false;
    }
}
