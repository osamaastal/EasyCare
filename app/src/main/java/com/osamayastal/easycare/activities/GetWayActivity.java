package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Getpayment;
import com.osamayastal.easycare.Model.Rootes.Getway;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.R;

public class GetWayActivity extends AppCompatActivity {

    Getpayment payment=null;
    String current_url="null";
    Boolean checking_payment=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_way);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        payment= (Getpayment) bundle.getSerializable("payment");


        init();
        Loading();
    }
Context mcontext=GetWayActivity.this;
    private void Loading() {
        if (payment!=null){
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.setWebChromeClient(new WebChromeClient());

            String htmlstring =payment.getItems();
            webView.loadDataWithBaseURL(null, htmlstring, null, "UTF-8", null);

            webView.setWebViewClient(new WebViewClient() {
                public boolean shuldOverrideKeyEvent (WebView view, KeyEvent event) {

                    return true;
                }

                public boolean shouldOverrideUrlLoading (WebView view, String url) {
                    Log.d("url",url);
                    Log.d("succese url",payment.getSuccessRedirectURL());

                    if (url.equals("https://mobile.twitter.com/")){
                        AppPop pop=new AppPop();
                        pop.Getway_POP(mcontext, mcontext.getString(R.string.there_is_error), new AppPop.goListenner() {
                            @Override
                            public void Go() {
                                webView.loadData(payment.getItems(), "text/html", "UTF-8");
                            }

                            @Override
                            public void Cancel() {

                            }
                        });
                        return false;
                    }else {
                        if (url.contains(payment.getSuccessRedirectURL())){
                            current_url=url;
                            /////waiting to checking
                            final ProgressDialog dialog=new ProgressDialog(mcontext);
                            dialog.setCancelable(false);
                            dialog.setMessage(mcontext.getString(R.string.checking_payment));
                            dialog.show();
                            ////
                            Getway root=new Getway();
                            root.CheckPayment(mcontext, payment.getPayment_order_id(), new Getway.GetwayListener() {
                                @Override
                                public void onSuccess(Getpayment payment) {
                                    dialog.dismiss();

                                    String msg=payment.getMessageAr();
                                    if (new User_info(mcontext).getLanguage().equals("en")){
                                        msg=payment.getMessageEn();
                                    }
                                    if (payment.isStatus()){
                                        checking_payment=true;
                                        AppPop pop=new AppPop();
                                        pop.Getway_POP(mcontext, msg, new AppPop.goListenner() {
                                            @Override
                                            public void Go() {
                                                Intent resultIntent = new Intent();
                                                setResult(Activity.RESULT_OK, resultIntent);
                                                finish();
                                            }

                                            @Override
                                            public void Cancel() {

                                            }
                                        });

                                    }else {
                                        AppPop pop=new AppPop();
                                        pop.Getway_POP(mcontext, msg, new AppPop.goListenner() {
                                            @Override
                                            public void Go() {
                                                Intent resultIntent = new Intent();
                                                setResult(Activity.RESULT_CANCELED, resultIntent);
                                                finish();
                                            }

                                            @Override
                                            public void Cancel() {

                                            }
                                        });

                                    }

                                }

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onFailure(String msg) {

                                }
                            });
                            return false;
                        }

                    }

                    // reject anything other
                    webView.loadUrl(url);
                    return true;
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                    Toast.makeText(mcontext,url,Toast.LENGTH_SHORT).show();


                    super.onPageStarted(view, url, favicon);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (current_url.contains(payment.getSuccessRedirectURL()) && checking_payment){
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }else {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
        super.onBackPressed();
    }

    WebView webView;
    private void init() {
      webView=findViewById(R.id.webview);
    }
}
