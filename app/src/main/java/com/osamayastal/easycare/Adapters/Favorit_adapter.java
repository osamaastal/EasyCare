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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Favorite_get;
import com.osamayastal.easycare.Model.Classes.Search;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Favorites;
import com.osamayastal.easycare.Model.Controle.Favorites_get;
import com.osamayastal.easycare.Model.Rootes.Favorite_root;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.ServiceProfiderDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Favorit_adapter extends RecyclerView.Adapter<Favorit_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Favorite_get> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Categorie categorie);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Favorit_adapter(Context context, List<Favorite_get> names, Selected_item listenner) {
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
        holder.name.setText(mItems.get(position).getProvider_id().getName());
        holder.address.setText(mItems.get(position).getProvider_id().getAddress());
holder.ratingBar.setRating(mItems.get(position).getProvider_id().getRate());
        holder.type.setVisibility(View.GONE);
//if (new User_info(mContext).getLanguage().equals("en")) {
//    holder.type.setText(mItems.get(position).getProvider_id().getCategory_id().getEnName());
//}else {
//    holder.type.setText(mItems.get(position).getProvider_id().getCategory_id().getArName());
//
//}

       try {
           Picasso.with(mContext)
                   .load(mItems.get(position).getProvider_id().getImage())
                   .into(holder.Img);
       } catch (Exception e) {
           e.printStackTrace();
       }
if (!mItems.get(position).getProvider_id().getFavorite_id().equals("null")){
    holder.like_btn.setImageDrawable(mContext.getDrawable(R.drawable.ic_like));
}else {
    holder.like_btn.setImageDrawable(mContext.getDrawable(R.drawable.ic_unlike));

}
//        mview.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                ServiceProfiderDetails.provider=mItems.get(position);
//                mContext.startActivity(new Intent(mContext,ServiceProfiderDetails.class));
//            }
//        });

       holder.like_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Favorite_root root=new Favorite_root();
               Search provider=mItems.get(position).getProvider_id();
               if (!provider.getFavorite_id().equals("null")){

                   root.DELETEFavorites(mContext, provider.getFavorite_id(), new Favorite_root.FavoriteListener() {
                       @Override
                       public void onSuccess(Favorites favorites) {
                           if (new User_info(mContext).getLanguage().equals("en")){
                               Toast.makeText(mContext,favorites.getMessageEn(),Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(mContext,favorites.getMessageAr(),Toast.LENGTH_SHORT).show();

                           }
                           if (favorites.getStatus_code()==200){
                               holder.like_btn.setImageDrawable(mContext.getDrawable(R.drawable.ic_unlike));
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
               else {
                   root.POSTFavorites(mContext, provider.get_id(), new Favorite_root.FavoriteListener() {
                       @Override
                       public void onSuccess(Favorites favorites) {
                           if (new User_info(mContext).getLanguage().equals("en")){
                               Toast.makeText(mContext,favorites.getMessageEn(),Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(mContext,favorites.getMessageAr(),Toast.LENGTH_SHORT).show();

                           }
                           if (favorites.getStatus_code()==200){
                               holder.like_btn.setImageDrawable(mContext.getDrawable(R.drawable.ic_like));
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