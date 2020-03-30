package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import top.defaults.drawabletoolbox.DrawableBuilder;

/**
 * Created by User on 26/02/2020.
 */

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Product> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Product product);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Product_adapter(Context context, List<Product> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_provider_details_product, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
if (new User_info(mContext).getLanguage().equals("en")){
    holder.name.setText(mItems.get(position).getEnName());

}else {
    holder.name.setText(mItems.get(position).getArName());

}
        Picasso .with(mContext)
                .load(mItems.get(position).getImage())
                .into(holder.img);

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
      TextView name,old_price,new_price;
      ImageButton basket_btn;
      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          old_price = itemView.findViewById(R.id.old_price);
          new_price = itemView.findViewById(R.id.new_price);
          basket_btn = itemView.findViewById(R.id.basket_btn);
          img = itemView.findViewById(R.id.Img);



        }
    }
}