package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Rootes.user;
import com.osamayastal.easycare.R;

import java.util.Locale;

public class ConfCode extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(this);
        setContentView(R.layout.activity_conf_code);
        init();
        Timer_resendButton();
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
private PinView cod;
    private Button conf_btn;
    private LinearLayout resned;
    private TextView timer;
private ImageView back_btn;
    private void init() {
        cod=findViewById(R.id.confCode_pinView);
        conf_btn=findViewById(R.id.conf_btn);
        resned=findViewById(R.id.resend);
        timer=findViewById(R.id.timer);
        back_btn=findViewById(R.id.back_btn);
        /*****************************Actions*********************************/
        conf_btn.setOnClickListener(this);
        resned.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }
    private void Timer_resendButton() {
        resned.setEnabled(false);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                String text =  millisUntilFinished / 1000+"";
                timer.setText(text);
                //do what you need every 1 sec khaled
            }

            public void onFinish() {
                resned.setEnabled(true);

            }

        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resend:
                Timer_resendButton();
                break;
                case R.id.conf_btn:
                    Conferme_fun();

                break;
            case R.id.back_btn:
                startActivity(new Intent(ConfCode.this,LoginActivity.class));
                finish();
                break;
        }
    }

    private void Conferme_fun() {
        user user=new user();
        final User_info user_info=new User_info(this);
        String tokenFCM = FirebaseInstanceId.getInstance().getToken();
        user.Put_ActvitPhone_user(this, user_info.getId(), cod.getText().toString(), tokenFCM
                , new user.user_Listener() {
                    @Override
                    public void onSuccess(users new_account) {
                        if (new User_info(ConfCode.this).getLanguage().equals("en")){
                            Toast.makeText(ConfCode.this,new_account.getMessageEn(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ConfCode.this,new_account.getMessageAr(),Toast.LENGTH_SHORT).show();
                        }
                        if (new_account.isStatus()){
                            new User_info(new_account.getItems(),ConfCode.this);
                            user_info.DO_CONF_phone(ConfCode.this);
                            startActivity(new Intent(ConfCode.this,MainActivity.class));
                            finish();
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
}
