package com.osamayastal.easycare.Popups;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cncoderx.wheelview.Wheel3DView;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.osamayastal.easycare.Adapters.Car_adapter;
import com.osamayastal.easycare.Adapters.Size_adapter;
import com.osamayastal.easycare.Adapters.SubCategories_adapter;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Classes.Size;
import com.osamayastal.easycare.Model.Classes.Sub_categorie;
import com.osamayastal.easycare.Model.Classes.Sub_servic;
import com.osamayastal.easycare.Model.Classes.Sub_service;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Controle.Sub_categories;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.Model.Rootes.City_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import top.defaults.drawabletoolbox.DrawableBuilder;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OrderPop {
    public static String date;
    public static String time;
    public static int payment;
    private   Context mcontext;
    Car_servece servic;
    Sub_categorie sub_categorie;
    List<Sub_service>  sub_servics;
    List<Size>  sizeList;
    Double Total=0.0;
    TextView price,basket_nb;
    ImageButton basket;
     int i=-1;
     public interface OrderLisstenner{
         void onGoBasket();
     }
    public interface POPLisstenner{
        void ongetResult(String  result);
    }
    public OrderPop(Context mcontext) {
        this.mcontext = mcontext;
    }

    private void Calculate_total(List<Car_servece>  carList){
         Total=0.0;
         for (Car_servece s :carList
         ) {
             Total=Total+s.getTotal();
         }
         price.setText(Total.toString());

         if (Total==0.0){
             basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_gray));
         }
         else {
             basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_basket));
         }
     }
    private void makeDrawable(int color, View view, int corner) {
        Drawable drawable = new DrawableBuilder()
                .oval()
                .solidColor(color)//0xffe67e22
                .height(100)
                .width(100)
//                .cornerRadii(corner, corner, corner, corner)// pixel
                // top-left  top-right  bottom-right   bottom-left
                .build();
        view.setBackground(drawable);
    }
    public void AddOrder_pop(final Context mcontext, final String cat_id, final String prov_id, final OrderLisstenner lisstenner){
this.mcontext=mcontext;
        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_add_car, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        ///////RV
        final RecyclerView car,details,type;
        car=sheetView.findViewById(R.id.RV_car);
        details=sheetView.findViewById(R.id.RV_services);
        type=sheetView.findViewById(R.id.RV_services_type);
        car.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.HORIZONTAL,false));
        details.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));
        type.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.HORIZONTAL,false));
        ///////List Array
        final List<Car_servece>  carList=new ArrayList<>();
        sub_servics=new ArrayList<>();
        sizeList=new ArrayList<>();
        sub_categorie=new Sub_categorie(null);
        final Double[] size_price = {0.0};
        final String[] size_id = {""};
        ///////adapters
        final Size_adapter size_adapter=new Size_adapter(mcontext, sizeList, new Size_adapter.Selected_item() {
            @Override
            public void Onselcted(Size size) {

               if (i!=-1){
                   carList.get(i).setTotal(carList.get(i).getTotal()- size_price[0] +size.getPrice());
                   carList.get(i).setSize_id(size.getSize_id());
                   Calculate_total(carList);
               }
               else{
                   servic.setSize_id(size.getSize_id());
               }
               size_price[0] =size.getPrice();
                size_id[0] =size.getSize_id();

            }
        });
        type.setAdapter(size_adapter);
        /////////
        final SubCategories_adapter subCategories_adapter=new SubCategories_adapter(mcontext, sub_servics, new SubCategories_adapter.Selected_item() {
            @Override
            public void Onselcted(Sub_service sub_service) {
                String id=servic.getProviderSubCategory_id();
                Double tot= size_price[0];
                for (Sub_service s:sub_servics
                ) {
                    if (s.isActive()){
                        tot=tot+s.getPrice();
                        if (id.isEmpty()){
                            id=s.getProvider_subCategory_id();

                        }else {
                            id=id+","+s.getProvider_subCategory_id();
                        }
                    }

                }


                if (i!=-1) {
                    if (id.isEmpty()){
                        Toast.makeText(mcontext,"يجب احتيار عنصر على الأقل من نوع الخدمة ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    carList.get(i).setProviderSubCategory_id(id);
                    carList.get(i).setTotal(tot);
                    Calculate_total(carList);
                }else {
                    servic.setProviderSubCategory_id(id);
                }

            }
        });
        details.setAdapter(subCategories_adapter);
        /////
        final Car_adapter car_adapter=new Car_adapter(mcontext, carList, new Car_adapter.Selected_item() {
            @Override
            public void Onselcted(Car_servece car_servece) {

                i= Car_adapter.item_select;

                for (Sub_service s:sub_servics
                     ) {
                    if (car_servece.getProviderSubCategory_id().contains(s.getProvider_subCategory_id())){
                        s.setActive(true);
                    }else {
                        s.setActive(false);
                    }
                }

                for (int i=0;i<sub_categorie.getSizes().size();i++
                ) {
                    if (car_servece.getSize_id().equals(sub_categorie.getSizes().get(i).getSize_id())){
                        size_adapter.item_select=i;
                    }
                }

                subCategories_adapter.notifyDataSetChanged();
                size_adapter.notifyDataSetChanged();
            }

            @Override
            public void Ondelete(Car_servece car_servece) {
                Calculate_total(carList);
                i=-1;
//////////////////////////////Init////////////////////////////////////
                for (Sub_service s:sub_servics
                ) {
                    s.setActive(false);
                }

                size_adapter.item_select=-1;

                subCategories_adapter.notifyDataSetChanged();
                size_adapter.notifyDataSetChanged();
            }
        });
        car.setAdapter(car_adapter);





        ////////////////////////////////////////Button
        LinearLayout add;

        basket=sheetView.findViewById(R.id.basket_btn);

        add=sheetView.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("size*******",servic.getSize_id()+"SubC********"+servic.getProviderSubCategory_id());

                String id=servic.getProviderSubCategory_id();
                Double tot= size_price[0];
                for (Sub_service s:sub_servics
                ) {
                    if (s.isActive()){
                        tot=tot+s.getPrice();
                        if (id.isEmpty()){
                            id=s.getProvider_subCategory_id();

                        }else {
                            id=id+","+s.getProvider_subCategory_id();
                        }
                    }
                    s.setActive(false);
                }
                servic.setProviderSubCategory_id(id);
                servic.setTotal(tot);
                servic.setSize_id(size_id[0]);
                if (!servic.getSize_id().isEmpty() && !servic.getProviderSubCategory_id().isEmpty()){

                    carList.add(servic);
                    Calculate_total(carList);
                    servic=new Car_servece();
                    servic.setCategory_id(sub_categorie.get_id());


                    subCategories_adapter.notifyDataSetChanged();
                    car_adapter.notifyDataSetChanged();
                    size_adapter.item_select=-1;
                    size_adapter.notifyDataSetChanged();


                }else {
                    Toast.makeText(mcontext,"يجب احتيار عنصر على الأقل من نوع الخدمة و تفاصيل الخدمة",Toast.LENGTH_SHORT).show();
                    return;
                }




            }
        });

///////////////////////////////Textview
        final TextView service_name,service_details;
        final ImageView service_img;

        service_name=sheetView.findViewById(R.id.service_title);
        service_details=sheetView.findViewById(R.id.servic_details);
        basket_nb=sheetView.findViewById(R.id.basket_nb);
        service_img=sheetView.findViewById(R.id.service_img);

        price=sheetView.findViewById(R.id.price_tv);

        service_details.setText("");
        int nb=new User_info(mcontext).getBasket();
        basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_gray));
        if (nb==0){
            basket_nb.setVisibility(View.GONE);
        }
        else {
            basket_nb.setText(nb+"");
        }
        ////////save data
        Button save=sheetView.findViewById(R.id.save_btn);
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carList.size()==0){
                    Toast.makeText(mcontext,"يجب احتيار عنصر على الأقل",Toast.LENGTH_SHORT).show();
                    return;
                }
                Bascket_root root=new Bascket_root();
                Car_servece car_servece=new Car_servece();
                root.PostService(mcontext, car_servece.Order_JSON(prov_id, carList), new Bascket_root.PostbasketListener() {
                    @Override
                    public void onSuccess(Result bascket) {
                        if (new User_info(mcontext).getLanguage().equals("en")) {
                            Toast.makeText(mcontext, bascket.getMessageEn(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mcontext, bascket.getMessageAr(), Toast.LENGTH_SHORT).show();
                        }
                        lisstenner.onGoBasket();
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

        Categories_root root=new Categories_root();
        root.Get_SubCategories(mcontext, cat_id, prov_id, new Categories_root.cat_Listener2() {
            @Override
            public void onSuccess(Sub_categories sub_categories) {
                if (sub_categories.getStatus_code()!=200){
                    return;
                }
                sheetView.findViewById(R.id.progress).setVisibility(View.GONE);
                sub_categorie=sub_categories.getItems();
                Picasso .with(mcontext)
                        .load(sub_categorie.getImage())
                        .into(service_img);
                try{
                    String color=sub_categorie.getColor();
                    Log.d("color",color);
                    makeDrawable(Color.parseColor(color),service_img,0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (new User_info(mcontext).getLanguage().equals("en")){
                    service_name.setText(sub_categorie.getNameEn());
                }else {
                    service_name.setText(sub_categorie.getNameAr());
                }
                ////////creat new car
                servic=new Car_servece();
                int k=carList.size()+1;
                servic.setCar_name(mcontext.getString(R.string.car_name)+" "+k);
                try {
                    servic.setCategory_id(sub_categories.getItems().get_id());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                carList.add(servic);
                ////notify
                sub_servics.clear();
                sub_servics.addAll(sub_categorie.getSub_services());
                sizeList.clear();
                sizeList.addAll(sub_categorie.getSizes());
                subCategories_adapter.notifyDataSetChanged();
                size_adapter.notifyDataSetChanged();
                car_adapter.notifyDataSetChanged();


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }
   public void GetDate_pop( final POPLisstenner lisstenner){

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
               lisstenner.ongetResult(date);
               mBottomSheetDialog.dismiss();



           }
       });


   }
    public void GetTime_pop( final POPLisstenner lisstenner){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_time, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final TimePicker timePicker=sheetView.findViewById(R.id.time);
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
                lisstenner.ongetResult(time);

                mBottomSheetDialog.dismiss();


            }
        });


    }
    public void GetWay_pop(final POPLisstenner lisstenner){

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
                OrderPop.payment=2;
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                check3.setVisibility(View.GONE);

            }
        });
        payment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPop.payment=3;
                check2.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                check3.setVisibility(View.GONE);

            }
        });
        payment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPop.payment=1;
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
                lisstenner.ongetResult( String.valueOf(payment));

                mBottomSheetDialog.dismiss();


            }
        });


    }
}
