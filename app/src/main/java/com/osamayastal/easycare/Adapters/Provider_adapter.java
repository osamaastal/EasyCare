package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Classes.TopRequestedProviders;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.ServiceProfiderDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Provider_adapter extends RecyclerView.Adapter<Provider_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Provider> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Categorie categorie);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Provider_adapter(Context context, List<Provider> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_more_rates, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(mItems.get(position).getName());
        holder.address.setText(mItems.get(position).getAddress());
holder.ratingBar.setRating(mItems.get(position).getRate());

       try {
           Picasso.with(mContext)
                   .load(mItems.get(position).getImage())
                   .into(holder.Img);
       } catch (Exception e) {
           e.printStackTrace();
       }

        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ServiceProfiderDetails.provider= mItems.get(position).toProvider();
                mContext.startActivity(new Intent(mContext,ServiceProfiderDetails.class));

            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     ImageView Img;
      TextView name,address;
      RatingBar ratingBar;
      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          Img = itemView.findViewById(R.id.Img);
          address= itemView.findViewById(R.id.address_tv);
          ratingBar= itemView.findViewById(R.id.ratingBar);


        }
    }
}