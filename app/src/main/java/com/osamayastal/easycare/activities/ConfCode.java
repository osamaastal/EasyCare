package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;

public class ConfCode extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_code);
        init();
        Timer_resendButton();
    }
private PinView cod;
    private Button conf_btn;
    private LinearLayout resned;
    private TextView timer;

    private void init() {
        cod=findViewById(R.id.confCode_pinView);
        conf_btn=findViewById(R.id.conf_btn);
        resned=findViewById(R.id.resend);
        timer=findViewById(R.id.timer);
        /*****************************Actions*********************************/
        conf_btn.setOnClickListener(this);
        resned.setOnClickListener(this);
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
                break;
                case R.id.conf_btn:
                    Conferme_fun();
                break;
        }
    }

    private void Conferme_fun() {
        User_info user_info=new User_info();
        user_info.DO_CONF_phone(this);
        startActivity(new Intent(ConfCode.this,MainActivity.class));
        finish();
    }
}
