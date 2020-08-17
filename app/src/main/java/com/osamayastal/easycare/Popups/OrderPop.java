package com.osamayastal.easycare.Popups;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.osamayastal.easycare.Adapters.Car_adapter;
import com.osamayastal.easycare.Adapters.Size_adapter;
import com.osamayastal.easycare.Adapters.SubCategories_adapter;
import com.osamayastal.easycare.Model.Classes.Basket.Service_for_basket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Classes.Payment;
import com.osamayastal.easycare.Model.Classes.Size;
import com.osamayastal.easycare.Model.Classes.Sub_categorie;
import com.osamayastal.easycare.Model.Classes.Sub_service;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Coupon;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Controle.Sub_categories;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Model.Rootes.Categories_root;
import com.osamayastal.easycare.Model.Rootes.City_root;
import com.osamayastal.easycare.Model.Rootes.Coupon_root;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import top.defaults.drawabletoolbox.DrawableBuilder;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OrderPop {
    private String date;
    private String time;
    private int payment;
    private   Boolean upfont=false;
    private   Context mcontext;
    Car_servece servic;
    Sub_categorie sub_categorie;
    List<Sub_service>  sub_servics;
    List<Size>  sizeList;
    Double Total=0.0;
    TextView price,basket_nb;
    ImageButton basket;
     Double size_price =0.0;
     String size_id = "";
     int i=-1;
     Car_adapter car_adapter;
     SubCategories_adapter subCategories_adapter;
     Size_adapter size_adapter;
     Service_for_basket service_for_basket;
     List<Car_servece>  carList;
     public interface OrderLisstenner{
         void onGoBasket();
         void onCancel();
     }
    public interface OrderCanelLisstenner{

        void onCancel(String reson);
    }
    public interface POPLisstenner{
        void ongetResult(String result, String time, Boolean upfont,String coupontxt);
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
         price.setText(String.format("%.2f",Total));

         if (Total==0.0){
             basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_gray));
         }
         else {
             basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_basket));
         }
     }
    private Double Calculate_service_total(Service_for_basket service_for_basket){
       Double Total=0.0;
        for (Sub_service s :service_for_basket.getSub_serviceList()
        ) {
            Total=Total+s.getPrice();
        }
        Total=Total+service_for_basket.getSize().getPrice();
        return Total;

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
mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        lisstenner.onCancel();
    }
});
service_for_basket=new Service_for_basket();
        ///////RV
        final RecyclerView car,details,type;
        car=sheetView.findViewById(R.id.RV_car);
        details=sheetView.findViewById(R.id.RV_services);
        type=sheetView.findViewById(R.id.RV_services_type);
        car.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.HORIZONTAL,false));
        details.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.VERTICAL,false));
        type.setLayoutManager(new LinearLayoutManager(mcontext,RecyclerView.HORIZONTAL,false));
        ///////List Array
       carList=new ArrayList<>();
        sub_servics=new ArrayList<>();
        sizeList=new ArrayList<>();
        sub_categorie=new Sub_categorie(null);

        ///////adapters
       size_adapter=new Size_adapter(mcontext, sizeList, new Size_adapter.Selected_item() {
            @Override
            public void Onselcted(Size size) {
                service_for_basket.setSize(size);
                if (i!=-1){

                    carList.get(i).setService_for_basket(service_for_basket);
                    carList.get(i).setTotal(Calculate_service_total(service_for_basket));
                    car_adapter.notifyDataSetChanged();
                    Calculate_total(carList);

                }
            }
        });
        type.setAdapter(size_adapter);
        /////////
         subCategories_adapter=new SubCategories_adapter(mcontext, sub_servics, new SubCategories_adapter.Selected_item() {
            @Override
            public Boolean Onselcted(Sub_service sub_service, boolean b) {
//

                if (b ){
                    if (!service_for_basket.getSub_serviceList().contains(sub_service)){
                        service_for_basket.getSub_serviceList().add(sub_service);
                    }

                }else {

                    service_for_basket.getSub_serviceList().remove(sub_service);
                }

//                if (i!=-1){
//
//                        carList.get(i).setService_for_basket(service_for_basket);
//                        carList.get(i).setTotal(Calculate_service_total(service_for_basket));
//                        car_adapter.notifyDataSetChanged();
//                        Calculate_total(carList);
//
//
//                }
//
//                if (service_for_basket.getSub_serviceList().size()==0){
////                    Toast.makeText(mcontext,"يجب احتيار عنصر على الأقل من نوع الخدمة ",Toast.LENGTH_SHORT).show();
//                    return false;
//                }
                return true;
            }
        });
        details.setAdapter(subCategories_adapter);
        /////
         car_adapter=new Car_adapter(mcontext, carList, new Car_adapter.Selected_item() {
            @Override
            public void Onselcted(Car_servece car_servece,int potions) {
//////////////////////////////Init////////////////////////////////////
//                service_for_basket=new Service_for_basket();
//                for (Sub_service s:sub_servics
//                ) {
//                    s.setActive(false);
//                }
//Toast.makeText(mcontext,potions+"",Toast.LENGTH_LONG).show();
//                size_adapter.item_select="";
//                subCategories_adapter.notifyDataSetChanged();
//                size_adapter.notifyDataSetChanged();
//                //////////////////////////////////////////////////////////
//
//                i= potions;
//                service_for_basket=carList.get(potions).getService_for_basket();
//
//                for (Sub_service sub:sub_servics
//                     ) {
//                    if (service_for_basket.getSub_serviceList().contains(sub)){
//                        sub.setActive(true);
//                    }
//                }
//                subCategories_adapter.notifyDataSetChanged();
//
//                size_adapter.item_select=service_for_basket.getSize().getSize_id();
//                size_adapter.notifyDataSetChanged();
            }

            @Override
            public void Ondelete(Car_servece car_servece) {

                Calculate_total(carList);
                car_adapter.notifyDataSetChanged();
                basket_count(mcontext);


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
               // Log.d("size*******",servic.getSize_id()+"SubC********"+servic.getProviderSubCategory_id());

                if (service_for_basket.getSize()!=null &&
                        service_for_basket.getSub_serviceList().size()!=0){

                    Car_servece servic=new Car_servece();
                    servic.setService_for_basket(service_for_basket);
                    servic.setTotal(Calculate_service_total(service_for_basket));
                    servic.setCategory_id(cat_id);
                    carList.add(servic);
//                    Toast.makeText(mcontext,carList.size()+" size list",Toast.LENGTH_LONG).show();

                    Calculate_total(carList);

                    for (Sub_service s:sub_servics
                    ) {
                        s.setActive(false);
                    }
                    i=-1;
                    service_for_basket=new Service_for_basket();
                    size_adapter.item_select="";
                    subCategories_adapter.notifyDataSetChanged();
                    size_adapter.notifyDataSetChanged();
                    car_adapter.notifyDataSetChanged();

                    basket_count(mcontext);


                }else {
                    Toast.makeText(mcontext,mcontext.getString(R.string.maste_select_one_ser_size),Toast.LENGTH_SHORT).show();
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

        basket_count(mcontext);
        ////////save data
        Button save=sheetView.findViewById(R.id.save_btn);
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carList.size()==0){
                    Toast.makeText(mcontext,mcontext.getString(R.string.maste_select_one),Toast.LENGTH_SHORT).show();
                    return;
                }
                Bascket_root root=new Bascket_root();
                Car_servece car_servece=new Car_servece();
                Log.d("service", car_servece.Order_JSON(prov_id, carList).toString());

                root.PostService(mcontext, car_servece.Order_JSON(prov_id, carList), new Bascket_root.PostbasketListener() {
                    @Override
                    public void onSuccess(Result bascket) {
                        String msg="";
                        if (new User_info(mcontext).getLanguage().equals("en")) {
                            msg=bascket.getMessageEn();
//                            Toast.makeText(mcontext, bascket.getMessageEn(), Toast.LENGTH_SHORT).show();
                        } else {
                            msg=bascket.getMessageAr();
//                            Toast.makeText(mcontext, bascket.getMessageAr(), Toast.LENGTH_SHORT).show();
                        }
                        AppPop appPop=new AppPop();
                        appPop.Conferme_POP(mcontext, msg+"\n"+mcontext.getString(R.string.go_basket), new AppPop.goListenner() {
                            @Override
                            public void Go() {

                                lisstenner.onGoBasket();
                            }

                            @Override
                            public void Cancel() {
                                carList.clear();
                                car_adapter.notifyDataSetChanged();
                                Calculate_total(carList);
                                basket_count(mcontext);
                            }
                        });
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carList.size()==0){
                    Toast.makeText(mcontext,mcontext.getString(R.string.maste_select_one),Toast.LENGTH_SHORT).show();
                    return;
                }
                Bascket_root root=new Bascket_root();
                Car_servece car_servece=new Car_servece();
                Log.d("service", car_servece.Order_JSON(prov_id, carList).toString());

                root.PostService(mcontext, car_servece.Order_JSON(prov_id, carList), new Bascket_root.PostbasketListener() {
                    @Override
                    public void onSuccess(Result bascket) {
                        String msg="";
                        if (new User_info(mcontext).getLanguage().equals("en")) {
                            msg=bascket.getMessageEn();
//                            Toast.makeText(mcontext, bascket.getMessageEn(), Toast.LENGTH_SHORT).show();
                        } else {
                            msg=bascket.getMessageAr();
//                            Toast.makeText(mcontext, bascket.getMessageAr(), Toast.LENGTH_SHORT).show();
                        }
                        AppPop appPop=new AppPop();
                        appPop.Conferme_POP(mcontext, msg+"\n"+mcontext.getString(R.string.go_basket), new AppPop.goListenner() {
                            @Override
                            public void Go() {

                                lisstenner.onGoBasket();
                            }

                            @Override
                            public void Cancel() {
                                carList.clear();
                                car_adapter.notifyDataSetChanged();
                                Calculate_total(carList);
                                basket_count(mcontext);
                            }
                        });
                    }
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });


//                if (carList.size()==0){
//                    Toast.makeText(mcontext,mcontext.getString(R.string.maste_select_one),Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Bascket_root root=new Bascket_root();
//                Car_servece car_servece=new Car_servece();
//                Log.d("service", car_servece.Order_JSON(prov_id, carList).toString());
//
//                root.PostService(mcontext, car_servece.Order_JSON(prov_id, carList), new Bascket_root.PostbasketListener() {
//                    @Override
//                    public void onSuccess(Result bascket) {
//                        String msg="";
//                        if (new User_info(mcontext).getLanguage().equals("en")) {
//                            msg=bascket.getMessageEn();
//                            Toast.makeText(mcontext, bascket.getMessageEn(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            msg=bascket.getMessageAr();
//                            Toast.makeText(mcontext, bascket.getMessageAr(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("bascket_index",0);
//                        Intent intent=new Intent(mcontext, OrderDetails_Create.class);
//                        intent.putExtras(bundle);
//                        mcontext.startActivity(intent);
//
//                        carList.clear();
//                        car_adapter.notifyDataSetChanged();
//                        Calculate_total(carList);
//                        basket_count(mcontext);
//                    }
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//
//                    }
//                });



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

                ////notify
                sub_servics.clear();
                sub_servics.addAll(sub_categorie.getSub_services());
                for (Sub_service s:sub_servics
                ) {
                    s.setActive(false);
                }
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
    private void basket_count(final Context mcontext) {
        int nb=carList.size();
        if (nb ==0){
            basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_gray));
            basket_nb.setVisibility(View.GONE);
        }
        else {
            basket_nb.setText(nb +"");
            basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_basket));
            basket_nb.setVisibility(View.VISIBLE);
        }

//        Bascket_root root=new Bascket_root();
//        root.GetItemCount(mcontext, new Bascket_root.Basket_count_Listener() {
//            @Override
//            public void onSuccess(int nb) {
//                if (nb ==0){
//                    basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_gray));
//                    basket_nb.setVisibility(View.GONE);
//                }
//                else {
//                    basket_nb.setText(nb +"");
//                    basket.setBackground(mcontext.getDrawable(R.drawable.bg_circle_basket));
//                    basket_nb.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onFailure(String msg) {
//
//            }
//        });

    }

    public void GetDate_pop( final POPLisstenner lisstenner){

       final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mcontext,R.style.BottomSheetDialog);
       LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
       final View sheetView = inflater.inflate(R.layout.bottom_sheet_calender, null);
       mBottomSheetDialog.setContentView(sheetView);

       mBottomSheetDialog.show();
       final CalendarView calendarView=sheetView.findViewById(R.id.calendar);
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date(calendarView.getDate()));
       calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
           public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//               this.calendar = new GregorianCalendar( year, month, dayOfMonth );
               date=year+"-"+month+"-"+dayOfMonth;
           }//met
       });
       Button save=sheetView.findViewById(R.id.save_btn);
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               Log.d("date", date);

               lisstenner.ongetResult(date,"",false,null);
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
//                    hour = hour - 12;

                }
                else
                {
                    am_pm="AM";
                }

                Log.d("date", hour +":"+ minute);//+" "+am_pm
                time=hour +":"+ minute;//+" "+am_pm;
                lisstenner.ongetResult(time,time,false,null);

                mBottomSheetDialog.dismiss();


            }
        });


    }
    public void GetWay_pop(final Boolean isupfront, final Double mount,
                           List<Payment> payment_id, final String provID,
                           final Boolean reOrder, final String order_id,
                           final POPLisstenner lisstenner){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_payment_methud, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final ImageView payment1,payment2,payment3,check1,check2,check3;
        final TextView upFrontmount;
      final LinearLayout linear_upfront;
         final EditText coupon=sheetView.findViewById(R.id.coupon);
        CheckBox payall =sheetView.findViewById(R.id.payAll);
        linear_upfront=sheetView.findViewById(R.id.linear_upfront);
        upFrontmount=sheetView.findViewById(R.id.upfrontMount_tv);
        check1=sheetView.findViewById(R.id.checked_1);
        check2=sheetView.findViewById(R.id.checked_2);
        check3=sheetView.findViewById(R.id.checked_3);
        payment2=sheetView.findViewById(R.id.payment_img1);//wallet 2
        payment3=sheetView.findViewById(R.id.payment_img2);//online 3
        payment1=sheetView.findViewById(R.id.payment_img3);//cache 1
        payment1.setEnabled(false);
        payment2.setEnabled(false);
        payment3.setEnabled(false);

        payment=0;//default
        final Boolean[] payAll_amount = {true};
        payall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                payAll_amount[0] =b;
            }
        });
        for (Payment p:payment_id
             ) {
            switch (p.getId()){
                case "1":
                    payment1.setBackground(mcontext.getDrawable(R.drawable.bg_circle_green_gradiant));
                    payment1.setEnabled(true);
                    break;
                case "2":
                    payment2.setBackground(mcontext.getDrawable(R.drawable.bg_circle_purple));
                    payment2.setEnabled(true);
                    break;
                case "3":
                    payment3.setBackground(mcontext.getDrawable(R.drawable.bg_circle_orang));
                    payment3.setEnabled(true);
                    break;
            }
        }

        payment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Cache

                payment=1;
                check3.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                check1.setVisibility(View.GONE);

                if (isupfront){
                    linear_upfront.setVisibility(View.GONE);
                    upfont=false;
                }


            }
        });
        payment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mcontext,"هذه الخاصية غير مفعلة حاليا",Toast.LENGTH_SHORT).show();
// wallet
                payment=2;
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                check3.setVisibility(View.GONE);

                if (isupfront){
                    linear_upfront.setVisibility(View.GONE);
                    upfont=false;
                }
            }
        });
        payment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
///Online
                payment=3;
                check2.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                check3.setVisibility(View.GONE);



                if (isupfront){
                    upfont=true;
                    linear_upfront.setVisibility(View.VISIBLE);
                    upFrontmount.setHint(String.format("%.2f",mount)+" ريال ");
                }



            }
        });

        final String[] Coupon_txt = {null};
        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payment==0){
                    Toast.makeText(mcontext,mcontext.getString(R.string.select_getway),Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("payment", String.valueOf(payment));
                if (payAll_amount[0]){
                    upfont=false;
                }
                lisstenner.ongetResult( String.valueOf(payment),"",upfont,Coupon_txt[0]);

                mBottomSheetDialog.dismiss();


            }
        });

        /********************************coupon**********************************/
        coupon. setOnEditorActionListener(
                new EditText.OnEditorActionListener() {


                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {

                                if (payment==0){
                                    Toast.makeText(mcontext,mcontext.getString(R.string.select_getway),Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                                // the user is done typing.
                                Coupon_root root=new Coupon_root();

                                root.Check_coupon(mcontext, coupon.getText().toString(),provID,reOrder,order_id,payment
                                        , new Coupon_root.Listener() {
                                            @Override
                                            public void onSuccess(Coupon coup) {
                                                String msg=null;

                                                if (new User_info(mcontext).getLanguage().equals("en")){
                                                    msg=coup.getMessageEn();
                                                }else
                                                {
                                                    msg=coup.getMessageAr();

                                                }
                                                if (msg!=null){
//                                                    AppPop pop=new AppPop();
//                                                    pop.ShowMessage(msg,sheetView);

                                                    Toast.makeText(mcontext,msg,Toast.LENGTH_SHORT).show();

                                                }


                                                if (coup.isStatus()){

                                                    Coupon_txt[0] =coupon.getText().toString();
                                                }

                                            }

                                            @Override
                                            public void onStart() {

                                            }

                                            @Override
                                            public void onFailure(String msg) {

                                            }
                                        });
                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );


    }

    /***********************************************************/
    final List<City> cityList=new ArrayList<>();
    List<String> cities=new ArrayList<>();
    public  void Cancel_order_pop(final Context mcontext, final OrderCanelLisstenner lisstenner){

        final RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mcontext);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View sheetView = inflater.inflate(R.layout.bottom_sheet_choose_reson, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final Wheel3DView wheel3DView=sheetView.findViewById(R.id.wheel);
        final ProgressBar progressBar=sheetView.findViewById(R.id.progress);
        final EditText note=sheetView.findViewById(R.id.note);
            City_root root=new City_root();
            root.GetResons(mcontext, new City_root.cityListener() {
                @Override
                public void onSuccess(final com.osamayastal.easycare.Model.Controle.City cities_) {


                    cityList.clear();
                    cityList.addAll(cities_.getItems());
                    cityList.add(new City("0","آخر","other"));
                    cities=cities_.getCityList(new City("0","آخر","other"),mcontext);
                    progressBar.setVisibility(View.GONE);
                    wheel3DView.setEntries(cities);
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure(String msg) {

                }
            });

        Button save=sheetView.findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String reson=null;
               if (new User_info(mcontext).getLanguage().equals("en")){
                   reson=cityList.get(wheel3DView.getCurrentIndex()).getEnName();

               }else {
                   reson=cityList.get(wheel3DView.getCurrentIndex()).getArName();

               }
                Log.d("reson", reson);
               if (cityList.get(wheel3DView.getCurrentIndex()).get_id().equals("0") ){
                   if ( note.getText().toString().isEmpty()){
                       note.setError(note.getHint());
                       return;
                   }else {
                       reson=note.getText().toString();
                   }

               }
                lisstenner.onCancel(reson);
                mBottomSheetDialog.dismiss();
            }
        });

    }

}
