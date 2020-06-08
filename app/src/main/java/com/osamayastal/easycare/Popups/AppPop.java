package com.osamayastal.easycare.Popups;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.osamayastal.easycare.Model.Classes.Complain;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Complains;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Model.Rootes.Complain_root;
import com.osamayastal.easycare.Model.Rootes.Order_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.LoginActivity;
import com.osamayastal.easycare.activities.MainActivity;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AppPop {
public interface goListenner{
    void Go();
    void Cancel();
}
    public void ShowMessage(String msg,View view){
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);

        snackbar.show();
    }
    public void Login_POP(final Context mContext){
        final Dialog dialog=new Dialog(mContext);
        dialog.setContentView(R.layout.popup_conf);
        Button conf=dialog.findViewById(R.id.confBtn);
        conf.setText("تسجيل الدخول");
        TextView content=dialog.findViewById(R.id.contentTV);
        content.setText("قم بتسجيل الدخول ");
        Button cancel=dialog.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void GoBasket_POP(final Context mContext, String msg, final goListenner listenner){
        final Dialog dialog=new Dialog(mContext);
        dialog.setContentView(R.layout.popup_conf);
        Button conf=dialog.findViewById(R.id.confBtn);
        conf.setText("نعم");
        TextView content=dialog.findViewById(R.id.contentTV);
        TextView title=dialog.findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        content.setText(msg);
        Button cancel=dialog.findViewById(R.id.cancelBtn);
        cancel.setVisibility(View.GONE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.Go();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void PostRat_pop(final Context mcontext, final String orderID, final goListenner listenner){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_rate_service, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
       final EditText text=sheetView.findViewById(R.id.details);
        final RatingBar ratingBar=sheetView.findViewById(R.id.ratingBar_popup);

        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text.getText().toString().isEmpty()){
                    text.setError(text.getHint());
                    return;
                }
                Order_root order_root=new Order_root();
                order_root.PostOrderRate(mcontext, orderID, text.getText().toString(), String.valueOf(ratingBar.getProgress())
                        , new Order_root.PostOrderListener() {
                            @Override
                            public void onSuccess(Result result) {
                                try {
                                    if (new User_info(mcontext).getLanguage().equals("en")){
                                        Toast.makeText(mcontext,result.getMessageEn(),Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(mcontext,result.getMessageAr(),Toast.LENGTH_LONG).show();
                                    }
                                    if (result.isStatus()){
                                       listenner.Go();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                mBottomSheetDialog.dismiss();
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


    }
    public void Conferme_POP(final Context mContext, String msg, final goListenner listenner ){
        final Dialog dialog=new Dialog(mContext);
        dialog.setContentView(R.layout.popup_conf);
        Button conf=dialog.findViewById(R.id.confBtn);
        TextView content=dialog.findViewById(R.id.contentTV);
        content.setText(msg);
        Button cancel=dialog.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.Cancel();
                dialog.dismiss();
            }
        });
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listenner.Go();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public ProgressDialog Loading_POP(final Context mContext, String msg){
        ProgressDialog dialog=new ProgressDialog(mContext);
        dialog.setMessage(msg);
        dialog.show();
        return dialog;
    }

}
