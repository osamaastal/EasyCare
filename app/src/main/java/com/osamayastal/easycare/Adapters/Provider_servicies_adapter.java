package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import top.defaults.drawabletoolbox.DrawableBuilder;

/**
 * Created by User on 26/02/2020.
 */

public class Provider_servicies_adapter extends RecyclerView.Adapter<Provider_servicies_adapter.ViewHolder>  {

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
    public Provider_servicies_adapter(Context context, List<Categorie> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_services_main, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
if (new User_info(mContext).getLanguage().equals("en")){
    holder.type.setText(mItems.get(position).getEnName());

}else {
    holder.type.setText(mItems.get(position).getArName());

}
        Picasso .with(mContext)
                .load(mItems.get(position).getImage())
                .into(holder.img);
if (mItems.get(position).isActive()){
    holder.soon.setVisibility(View.GONE);
}else {
    holder.img.setBackground(mContext.getDrawable(R.drawable.bg_circle_gray));
}

        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                item_select=position;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

ImageView img;
      TextView type,soon;
      public ViewHolder(View itemView) {
            super(itemView);
          type = itemView.findViewById(R.id.type_tv);
          img = itemView.findViewById(R.id.Img);
          soon = itemView.findViewById(R.id.soon_tv);



        }
    }
}