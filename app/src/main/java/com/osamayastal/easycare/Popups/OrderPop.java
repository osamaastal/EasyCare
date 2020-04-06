package com.osamayastal.easycare.Popups;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import com.cncoderx.wheelview.Wheel3DView;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Rootes.City_root;
import com.osamayastal.easycare.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OrderPop {
    private static String date;
    private static String time;
    private static int payment;
    private   Context mcontext;
   public void GetDate_pop( Context mcontext){

       final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
       LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
       final View sheetView = inflater.inflate(R.layout.bottom_sheet_calender, null);
       mBottomSheetDialog.setContentView(sheetView);
       mBottomSheetDialog.show();
       final CalendarView calendarView=sheetView.findViewById(R.id.calendar);
       Button save=sheetView.findViewById(R.id.save_btn);
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
               String formatedDate = format.format(calendarView.getDate());
               Log.d("date", formatedDate);
               OrderPop.date=formatedDate;
               mBottomSheetDialog.dismiss();


           }
       });


   }
    public void GetTime_pop( Context mcontext){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_time, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final TimePicker timePicker=sheetView.findViewById(R.id.timer);
        timePicker.setIs24HourView(true);
        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                else{
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }

                Log.d("date", hour +":"+ minute+" "+am_pm);
                OrderPop.time=hour +":"+ minute+" "+am_pm;
                mBottomSheetDialog.dismiss();


            }
        });


    }
    public void GetWay_pop( Context mcontext){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_payment_methud, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final ImageView payment1,payment2,payment3,check1,check2,check3;
        check1=sheetView.findViewById(R.id.checked_1);
        check2=sheetView.findViewById(R.id.checked_2);
        check3=sheetView.findViewById(R.id.checked_3);
        payment1=sheetView.findViewById(R.id.payment_img1);
        payment2=sheetView.findViewById(R.id.payment_img2);
        payment3=sheetView.findViewById(R.id.payment_img3);
        payment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPop.payment=1;
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                check3.setVisibility(View.GONE);

            }
        });
        payment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPop.payment=2;
                check2.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                check3.setVisibility(View.GONE);

            }
        });
        payment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPop.payment=3;
                check3.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                check1.setVisibility(View.GONE);

            }
        });

        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("payment", String.valueOf(payment));

                mBottomSheetDialog.dismiss();


            }
        });


    }
}
