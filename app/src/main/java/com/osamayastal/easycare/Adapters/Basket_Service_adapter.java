package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.osamayastal.easycare.Model.Classes.Basket.Bascket;
import com.osamayastal.easycare.Model.Classes.Basket.Sub_service_basket;
import com.osamayastal.easycare.Model.Classes.Basket.categories_basket;
import com.osamayastal.easycare.Model.Classes.Car_servece;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Rootes.Bascket_root;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import top.defaults.drawabletoolbox.DrawableBuilder;

/**
 * Created by User on 26/02/2020.
 */

public class Basket_Service_adapter extends RecyclerView.Adapter<Basket_Service_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    public boolean isOrder=false;

    //vars
    private List<categories_basket> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Car_servece car_servece);
    }
    public static int item_select=-1;
    Selected_item listenner;
    Categorie categorie;
    public Basket_Service_adapter(Context context, List<categories_basket> names, Categorie categorie, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.categorie=categorie;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_basket_service, parent, false);

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (new User_info(mContext).getLanguage().equals("en")){
            holder.name.setText(mItems.get(position).getCategory_id().getEnName());
        }else {
            holder.name.setText(mItems.get(position).getCategory_id().getArName());
        }
        Picasso.with(mContext)
                .load(mItems.get(position).getCategory_id().getImage())
        .into(holder.img);
        try{
            makeDrawable(Color.parseColor(categorie.getColor()),holder.img,0);
        } catch (Exception e) {
            e.printStackTrace();
        }

            Basket_Car_adapter adapter=new Basket_Car_adapter(mContext, mItems.get(position).getSub_services(), new Basket_Car_adapter.Selected_item() {
                @Override
                public void Onselcted(Car_servece car_servece) {

                    listenner.Onselcted(null);
                }
            });
            holder.RV.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
            adapter.isOrder=isOrder;
            holder.RV.setAdapter(adapter);




    }
    private void makeDrawable(int color, View view, int corner) {
        Drawable drawable = new DrawableBuilder()
                .oval()
                .solidColor(color)//0xffe67e22
                .height(90)
                .width(90)
//                .cornerRadii(corner, corner, corner, corner)// pixel
                // top-left  top-right  bottom-right   bottom-left
                .build();
        view.setBackground(drawable);
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     RecyclerView RV;
      TextView name;
      ImageView img;
//        ImageButton delete;

        public ViewHolder(View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.name);
          img = itemView.findViewById(R.id.Img);

          RV = itemView.findViewById(R.id.RV);
//          delete = itemView.findViewById(R.id.delete_btn);



        }
    }
}