package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.osamayastal.easycare.Model.Classes.Search;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Search_adapter extends RecyclerView.Adapter<Search_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Search> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Categorie categorie);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Search_adapter(Context context, List<Search> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_favorites, parent, false);

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
if (new User_info(mContext).getLanguage().equals("en")) {
    holder.type.setText(mItems.get(position).getCategory_id().getArName());
}else {
    holder.type.setText(mItems.get(position).getCategory_id().getEnName());

}

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


            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     ImageView Img,like_btn;
      TextView name,address,type;
      RatingBar ratingBar;
      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.provider_name_tv);
          Img = itemView.findViewById(R.id.provider_Img);
          like_btn = itemView.findViewById(R.id.like_btn);
          address= itemView.findViewById(R.id.address_tv);
          ratingBar= itemView.findViewById(R.id.ratingBar);
          type= itemView.findViewById(R.id.type_tv);


        }
    }
}