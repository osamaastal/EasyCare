package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Basket.categories_basket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Basket_Products_adapter extends RecyclerView.Adapter<Basket_Products_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Product> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Car_servece car_servece);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Basket_Products_adapter(Context context, List<Product> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_basket_product, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.name.setText(mItems.get(position).getName());
        holder.qty.setText(mItems.get(position).getQty()+"");
        holder.price.setText(mItems.get(position).getTotal().toString());

        Picasso.with(mContext)
                .load(mItems.get(position).getImage())
        .into(holder.img);



        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(mContext);
                dialog.setContentView(R.layout.pop);
                Bascket_root root=new Bascket_root();
                root.Delete(mContext, mItems.get(position).getCart_id(), new Bascket_root.PostbasketListener() {
                    @Override
                    public void onSuccess(Result bascket) {
                        if (new User_info(mContext).getLanguage().equals("en")){
                            Toast.makeText(mContext,bascket.getMessageEn(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mContext,bascket.getMessageAr(),Toast.LENGTH_SHORT).show();
                        }
                       if (bascket.isStatus()){
                           mItems.remove(mItems.get(position));
                           notifyDataSetChanged();
                           listenner.Onselcted(null);
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
        });
        holder.mines.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mItems.get(position).getQty()!=0) {
                    mItems.get(position).setQty(mItems.get(position).getQty() - 1);
                    notifyDataSetChanged();
                }
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mItems.get(position).setQty(mItems.get(position).getQty()+1);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

      TextView name,price,qty;
      ImageView img;
      ImageButton delete,add,mines;
      public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          price = itemView.findViewById(R.id.price);
          img = itemView.findViewById(R.id.Img);
          delete = itemView.findViewById(R.id.delete_btn);
          mines = itemView.findViewById(R.id.mines);
          add = itemView.findViewById(R.id.add);
          qty = itemView.findViewById(R.id.qty);



        }
    }
}