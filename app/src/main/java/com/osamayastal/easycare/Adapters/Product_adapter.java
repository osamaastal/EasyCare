package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.LoginActivity;
import com.osamayastal.easycare.activities.MainActivity;
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

            holder.name.setText(mItems.get(position).getName());
        Double price=mItems.get(position).getDiscountPrice();
        if (price==0){
            price=mItems.get(position).getPrice();
            holder.old_price.setText(String.format("%.2f",mItems.get(position).getDiscountPrice()));
            holder.new_price.setText(String.format("%.2f",mItems.get(position).getPrice()));
        }else {
            holder.old_price.setText(String.format("%.2f",mItems.get(position).getPrice()));
            holder.new_price.setText(String.format("%.2f",mItems.get(position).getDiscountPrice()));
        }


        Picasso .with(mContext)
                .load(mItems.get(position).getImage())
                .into(holder.img);

        holder.basket_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (new User_info(mContext).getId()==null){

                    AppPop pop=new AppPop();
                    pop.Login_POP(mContext);

                }else {
                    Bascket_root root = new Bascket_root();
                    root.PostProduct(mContext, mItems.get(position).get_id(), 1, new Bascket_root.PostbasketListener() {
                        @Override
                        public void onSuccess(Result bascket) {
                            if (new User_info(mContext).getLanguage().equals("en")) {
                                Toast.makeText(mContext, bascket.getMessageEn(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, bascket.getMessageAr(), Toast.LENGTH_SHORT).show();
                            }
                            listenner.Onselcted(mItems.get(position));
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