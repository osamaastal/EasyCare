package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.osamayastal.easycare.Adapters.Wallet_adapter;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Getpayment;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.Getway;
import com.osamayastal.easycare.Model.Rootes.Wallet_root;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Wallet extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        setLocale(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_wallet);
        init();
        Loading();
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
    private void Loading() {
        price.setText(new User_info(mcontext).getWallet().toString());//String.format("%.2f",);
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
    private TextView price;
    RecyclerView RV;
Wallet_adapter adapter;
List<com.osamayastal.easycare.Model.Classes.Wallet> walletList;
ConstraintLayout add_btn;
    private void init() {
        back=findViewById(R.id.back_btn);
        add_btn=findViewById(R.id.add);
        RV=findViewById(R.id.RV);
        price=findViewById(R.id.wallet_price);
        /**************************Action***************************/

        back.setOnClickListener(this);
        add_btn.setOnClickListener(this);
        walletList=new ArrayList<>();
        adapter=new Wallet_adapter(mcontext, walletList, new Wallet_adapter.Selected_item() {
            @Override
            public void Onselcted(com.osamayastal.easycare.Model.Classes.Wallet wallet) {

            }
        });
        RV.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));
RV.setAdapter(adapter);

    }
String payment_id=null;
    Double amount=0.0;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                AppPop pop=new AppPop();
                pop.chargeWallet_POP(mcontext, new AppPop.walletListenner() {
                    @Override
                    public void Go(Double price) {
                        amount=price;
                        Getway root=new Getway();
                        root.GoPayment(mcontext, price, null, new Getway.GetwayListener() {
                            @Override
                            public void onSuccess(Getpayment payment) {
                                payment_id=payment.getPayment_order_id();
                                Intent intent=new Intent(mcontext, GetWayActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("payment",payment);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 2);
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
                    public void Cancel() {

                    }
                });
                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 2: {
                if (resultCode == Activity.RESULT_OK) {
                    Wallet_root root=new Wallet_root();
                    root.updateWallet(mcontext, amount, new user.user_Listener() {
                        @Override
                        public void onSuccess(users account) {
                            new User_info(account.getItems(),mcontext);
                            Loading();
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
            }
        }
    }
}
