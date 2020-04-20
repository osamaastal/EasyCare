package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.ServiceProfiderDetails;
import com.osamayastal.easycare.activities.Service_Single;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Categories_adapter extends RecyclerView.Adapter<Categories_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Categorie> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Categorie categorie);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Categories_adapter(Context context, List<Categorie> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_our_services, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
if(new User_info(mContext).getLanguage().equals("en")){
    holder.name.setText(mItems.get(position).getEnName());
}else {
    holder.name.setText(mItems.get(position).getArName());

}
        if (mItems.get(position).isActive()){
            holder.soon.setVisibility(View.GONE);
        }else {
            holder.Img.setBackground(mContext.getDrawable(R.drawable.bg_circle_gray));
        }

       try {
           mItems.get(position).getImage().replace("http", "https");
           Picasso.with(mContext)
                   .load(mItems.get(position).getImage())
                   .into(holder.Img);

           Log.d("image", mItems.get(position).getImage().replace("http", "https"));
       } catch (Exception e) {
           e.printStackTrace();
       }

        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Service_Single.id=mItems.get(position).get_id();
                mContext.startActivity(new Intent(mContext,Service_Single.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     ImageView Img;
      TextView name,soon;
      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          Img = itemView.findViewById(R.id.Img);
          soon= itemView.findViewById(R.id.soon_tv);


        }
    }
}