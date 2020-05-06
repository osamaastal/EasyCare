package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Basket.Sub_service_basket;
import com.osamayastal.easycare.Model.Classes.Basket.categories_basket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
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

public class Basket_Car_adapter extends RecyclerView.Adapter<Basket_Car_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Sub_service_basket> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public boolean isOrder=false;

    public interface Selected_item{
        void Onselcted(Car_servece car_servece);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Basket_Car_adapter(Context context, List<Sub_service_basket> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_basket_car, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

Double price=mItems.get(position).getSize().getPrice();
        int k=position+1;
        holder.name.setText(mContext.getString(R.string.car_name)+" "+k);


        if (new User_info(mContext).getLanguage().equals("en")){
            holder.size.setText(mItems.get(position).getSize().getEnName()+" - "+String.format("%.2f",price)+mContext.getString(R.string.RS_short2));
        }else {
            holder.size.setText(mItems.get(position).getSize().getArName()+" - "+String.format("%.2f",price)+mContext.getString(R.string.RS_short2));
        }
Basket_car_details_adapter adapter=new Basket_car_details_adapter(mContext,mItems.get(position).getSubCategory_basketList(),null);
        holder.RV.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        holder.RV.setAdapter(adapter);

        if (isOrder){
            holder.delete.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(mContext);
                dialog.setContentView(R.layout.popup_conf);
                Button conf=dialog.findViewById(R.id.confBtn);
                Button cancel=dialog.findViewById(R.id.cancelBtn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                conf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bascket_root root=new Bascket_root();
                        root.Delete_Service(mContext, mItems.get(position).getCart_id(),mItems.get(position).getProviderSubCategory_id(), new Bascket_root.PostbasketListener() {
                            @Override
                            public void onSuccess(Result bascket) {
                                if (new User_info(mContext).getLanguage().equals("en")){
                                    Toast.makeText(mContext,bascket.getMessageEn(),Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(mContext,bascket.getMessageAr(),Toast.LENGTH_SHORT).show();
                                }
                                if (bascket.isStatus()){
                                    listenner.Onselcted(null);
                                }
                                dialog.dismiss();
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
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     RecyclerView RV;
      TextView name,size,price;
        ImageButton delete;

        public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          size = itemView.findViewById(R.id.car_size_tv);
          RV = itemView.findViewById(R.id.RV);
            delete = itemView.findViewById(R.id.delete_btn);

          price = itemView.findViewById(R.id.price);


        }
    }
}