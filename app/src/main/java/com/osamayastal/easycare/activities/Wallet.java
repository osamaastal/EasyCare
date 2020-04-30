package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.osamayastal.easycare.Adapters.Wallet_adapter;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Order_root;
import com.osamayastal.easycare.Model.Rootes.Wallet_root;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet);
        init();
        Loading();
    }

    private void Loading() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        Wallet_root root=new Wallet_root();
        root.GetWaleet_operations(mcontext, 0, new Wallet_root.WaleetListener() {
            @Override
            public void onSuccess(com.osamayastal.easycare.Model.Controle.Wallet wallet) {
                findViewById(R.id.progress).setVisibility(View.GONE);
                walletList.clear();
                walletList.addAll(wallet.getItems());
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
Context mcontext=Wallet.this;
    private ImageButton back;
    private TextView add,price;
    RecyclerView RV;
Wallet_adapter adapter;
List<com.osamayastal.easycare.Model.Classes.Wallet> walletList;
    private void init() {
        back=findViewById(R.id.back_btn);
        add=findViewById(R.id.add);
        RV=findViewById(R.id.RV);
        price=findViewById(R.id.wallet_price);
        /**************************Action***************************/
        price.setText(new User_info(mcontext).getWallet().toString());//String.format("%.2f",);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        walletList=new ArrayList<>();
        adapter=new Wallet_adapter(mcontext, walletList, new Wallet_adapter.Selected_item() {
            @Override
            public void Onselcted(com.osamayastal.easycare.Model.Classes.Wallet wallet) {

            }
        });
        RV.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));
RV.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:

                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
