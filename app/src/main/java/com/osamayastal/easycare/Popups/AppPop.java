package com.osamayastal.easycare.Popups;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.osamayastal.easycare.Model.Classes.Complain;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Complains;
import com.osamayastal.easycare.Model.Rootes.Complain_root;
import com.osamayastal.easycare.R;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AppPop {


    public void PostRat_pop( Context mcontext){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_rate_service, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
       EditText text=sheetView.findViewById(R.id.details);
        RatingBar ratingBar=sheetView.findViewById(R.id.ratingBar_popup);

        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBottomSheetDialog.dismiss();


            }
        });


    }


}
