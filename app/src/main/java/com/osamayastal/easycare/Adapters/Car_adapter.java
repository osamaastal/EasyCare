package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Sub_servic;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.Service_Single;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Car_adapter extends RecyclerView.Adapter<Car_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Car_servece> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Car_servece car_servece);
        void Ondelete(Car_servece car_servece);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Car_adapter(Context context, List<Car_servece> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_car, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int k=position+1;
        mItems.get(position).setCar_name(mContext.getString(R.string.car_name)+" "+k);
holder.price.setText(mItems.get(position).getTotal().toString()+mContext.getString(R.string.RS_chort));
    holder.name.setText(mItems.get(position).getCar_name());
    holder.delet.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mItems.remove(mItems.get(position));
            listenner.Ondelete(null);
            notifyDataSetChanged();
        }
    });
        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                item_select=position;
                listenner.Onselcted(mItems.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     ImageView delet;
      TextView name,price;
      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          delet = itemView.findViewById(R.id.delete_btn);
          price = itemView.findViewById(R.id.price_tv);



        }
    }
}