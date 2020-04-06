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

   public void PostADDComplain_pop(final Context mcontext){

       final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
       LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
       final View sheetView = inflater.inflate(R.layout.bottom_sheet_contact_us, null);
       mBottomSheetDialog.setContentView(sheetView);
       mBottomSheetDialog.show();
       ImageButton whatup,phone,email;
       final EditText title,details;
       final Spinner categorie;
       whatup=sheetView.findViewById(R.id.whatup);
       phone=sheetView.findViewById(R.id.phone);
       email=sheetView.findViewById(R.id.email);
       whatup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try{
                   PackageManager packageManager =mcontext.getPackageManager();
                   Intent i = new Intent(Intent.ACTION_VIEW);
                   String url = "https://api.whatsapp.com/send?phone="+ Server_info.phone +"&text=" + URLEncoder.encode(" ", "UTF-8");

                   i.setPackage("com.whatsapp");
                   i.setData(Uri.parse(url));
                   if (i.resolveActivity(packageManager) != null) {
                      mcontext.startActivity(i);
                   }else {
                       Toast.makeText(mcontext, mcontext.getString(R.string.no_whatsapp), Toast.LENGTH_SHORT);
                   }
               } catch(Exception e) {
                   Log.e("ERROR WHATSAPP",e.toString());

               }
           }
       });
       email.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_SEND);
               intent.setType("text/html");
               String[] TO ={Server_info.email};
               intent.putExtra(Intent.EXTRA_EMAIL,TO );
               intent.putExtra(Intent.EXTRA_SUBJECT, "استفسار");
               intent.putExtra(Intent.EXTRA_TEXT, "");

               mcontext.startActivity(Intent.createChooser(intent, "Send Email"));
           }
       });

       phone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_SEND);
               intent.putExtra(Intent.EXTRA_PHONE_NUMBER,Server_info.phone );

               mcontext.startActivity(Intent.createChooser(intent, "call phone"));
           }
       });
       title=sheetView.findViewById(R.id.title);
       details=sheetView.findViewById(R.id.details);
       categorie=sheetView.findViewById(R.id.categories_spinner);

       Button save=sheetView.findViewById(R.id.save_btn);
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (title.getText().toString().isEmpty()){
                   title.setError(title.getHint());
                   return;
               }
               if (details.getText().toString().isEmpty()){
                   details.setError(details.getHint());
                   return;
               }
               Complain complain=new Complain(null);
               complain.setCategory(categorie.getSelectedItem().toString());
               complain.setTitle(title.getText().toString());
               complain.setTitle(details.getText().toString());
               Complain_root root=new Complain_root();
               root.PostComplain(mcontext, complain, new Complain_root.complain_Listener() {
                   @Override
                   public void onSuccess(Complains complain) {
                       if (complain.getStatus_code()==200){
                           if (new User_info(mcontext).getLanguage().equals("en")){
                               Toast.makeText(mcontext,complain.getMessageEn(),Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(mcontext,complain.getMessageAr(),Toast.LENGTH_SHORT).show();

                           }
                           mBottomSheetDialog.dismiss();
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
       });


   }

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
    public void ChangeLanguge_pop(final String languge, final Context mcontext){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_language, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            new User_info(languge,mcontext);
                mBottomSheetDialog.dismiss();


            }
        });

        Button cancel=sheetView.findViewById(R.id.save_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mBottomSheetDialog.dismiss();


            }
        });


    }

}
