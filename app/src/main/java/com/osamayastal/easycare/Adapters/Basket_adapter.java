package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Basket.Bascket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Basket_adapter extends RecyclerView.Adapter<Basket_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Bascket> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void OnRefresh(Car_servece car_servece);
        void Onselcted(int potions);
    }
    private static int item_select=0;
    Selected_item listenner;
    public Boolean isOrder=false;
    public Basket_adapter(Context context, List<Bascket> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_basket_main, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (isOrder){
            holder.top.setVisibility(View.GONE);
        }
        holder.nb_products.setText(mItems.get(position).getProducts().size()+"");
        holder.nb_service.setText(mItems.get(position).getCategories().size()+"");
        holder.name.setText(mItems.get(position).getProvider().getName()+"");

        Basket_Service_adapter adapter=new Basket_Service_adapter(mContext, mItems.get(position).getCategories()
                ,mItems.get(position).getCategorie(), new Basket_Service_adapter.Selected_item() {
            @Override
            public void Onselcted(Car_servece car_servece) {
                listenner.OnRefresh(null);

            }
        });
        holder.RV_service.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        holder.RV_service.setAdapter(adapter);
        adapter.isOrder=isOrder;
        adapter.notifyDataSetChanged();

        Basket_Products_adapter adapter2=new Basket_Products_adapter(mContext, mItems.get(position).getProducts(), new Basket_Products_adapter.Selected_item() {
            @Override
            public void Onselcted(Car_servece car_servece) {
                listenner.OnRefresh(null);

            }
        });
        holder.RV_product.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        holder.RV_product.setAdapter(adapter2);
        adapter2.isOrder=isOrder;
        adapter2.notifyDataSetChanged();
        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

//        if (item_select==position){
//            holder.checkBox.setChecked(true);
//        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listenner.Onselcted(position);

//                if (b){
//                    listenner.Onselcted(position);
////                    item_select=position;
//
//                }

//                item_select=position;
//                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout top;
     RecyclerView RV_service,RV_product;
      TextView nb_service,nb_products,name;
        AppCompatCheckBox checkBox;
      public ViewHolder(View itemView) {
            super(itemView);
          nb_service = itemView.findViewById(R.id.nb_service);
          nb_products = itemView.findViewById(R.id.nb_products);
          RV_service = itemView.findViewById(R.id.RV_service);
          RV_product = itemView.findViewById(R.id.RV_product);
          name = itemView.findViewById(R.id.name);
          top = itemView.findViewById(R.id.top);
          checkBox = itemView.findViewById(R.id.checkbox);



        }
    }
}