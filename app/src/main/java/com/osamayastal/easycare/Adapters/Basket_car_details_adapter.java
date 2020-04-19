package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Basket.SubCategory_basket;
import com.osamayastal.easycare.Model.Classes.Sub_service;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Basket_car_details_adapter extends RecyclerView.Adapter<Basket_car_details_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<SubCategory_basket> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Sub_service sub_service);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Basket_car_details_adapter(Context context, List<SubCategory_basket> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_basket_car_details, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
if(new User_info(mContext).getLanguage().equals("en")){
    holder.name.setText(mItems.get(position).getSubCategory_id().getNameEn());
}else {
    holder.name.setText(mItems.get(position).getSubCategory_id().getNameAr());
}
        holder.price.setText(mItems.get(position).getPrice().toString());




        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


      TextView name,price;

      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          price = itemView.findViewById(R.id.price_tv);



        }
    }
}