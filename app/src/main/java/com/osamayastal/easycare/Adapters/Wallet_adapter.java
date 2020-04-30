package com.osamayastal.easycare.Adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Order;
import com.osamayastal.easycare.Model.Classes.Wallet;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Wallet_adapter extends RecyclerView.Adapter<Wallet_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Wallet> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Wallet wallet);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Wallet_adapter(Context context, List<Wallet> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet_convert_proccesses, parent, false);

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
        holder.name.setText(mItems.get(position).getTo());
        holder.id.setText(mItems.get(position).getNo());
        holder.date.setText(getdate(mItems.get(position).getCreateAt()));
        holder.price.setText(String.format("%.2f",mItems.get(position).getAmount()));

        holder.nb.setVisibility(View.GONE);
        holder.order_status.setVisibility(View.GONE);
        holder.time.setVisibility(View.GONE);


//        try {
//           Picasso.with(mContext)
//                   .load(mItems.get(position).getProvider_id().getImage())
//                   .into(holder.Img);
//       } catch (Exception e) {
//           e.printStackTrace();
//       }

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
      TextView name,date,time,type,id,price,payment;
      ImageView order_status;
        LinearLayout nb;
      public ViewHolder(View itemView) {
            super(itemView);
          nb = itemView.findViewById(R.id.nb);
          payment = itemView.findViewById(R.id.payment_way_tv);
          Img = itemView.findViewById(R.id.userImg);
          order_status = itemView.findViewById(R.id.order_status);
          name = itemView.findViewById(R.id.userName_tv);
          Img = itemView.findViewById(R.id.userImg);
          date= itemView.findViewById(R.id.date_tv);
          time= itemView.findViewById(R.id.time_tv);
          type= itemView.findViewById(R.id.type_tv);
          id= itemView.findViewById(R.id.order_id);
          price= itemView.findViewById(R.id.price);


        }
    }
}