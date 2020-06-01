package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Order;
import com.osamayastal.easycare.Model.Classes.Provider.Provider;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Order_adapter extends RecyclerView.Adapter<Order_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Order> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Order order);
    }
    private int item_select=-1;
    Selected_item listenner;
    public Order_adapter(Context context, List<Order> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getdate(String date_){
        Log.e("date_", date_.substring(0, date_.indexOf("T")));
        return date_.substring(0, date_.indexOf("T"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(mItems.get(position).getProvider_id().getName());
        holder.id.setText(mItems.get(position).getOrder_no());
        holder.date.setText(getdate(mItems.get(position).getDate()));
        holder.time.setText(mItems.get(position).getTime());
        holder.price.setText(String.format("%.2f",mItems.get(position).getTotal()));
//if (new User_info(mContext).getLanguage().equals("en")){
//    holder.type.setText(mItems.get(position).getProvider_id().getCategory_id());
//
//}


       try {
           Picasso.with(mContext)
                   .load(mItems.get(position).getProvider_id().getImage())
                   .into(holder.Img);
       } catch (Exception e) {
           e.printStackTrace();
       }

        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listenner.Onselcted(mItems.get(position));

            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     ImageView Img;
      TextView name,date,time,type,id,price;

      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.provider_name_tv);
          Img = itemView.findViewById(R.id.provider_Img);
          date= itemView.findViewById(R.id.date_tv);
          time= itemView.findViewById(R.id.time_tv);
          type= itemView.findViewById(R.id.type_tv);
          id= itemView.findViewById(R.id.order_id);
          price= itemView.findViewById(R.id.price);


        }
    }
}