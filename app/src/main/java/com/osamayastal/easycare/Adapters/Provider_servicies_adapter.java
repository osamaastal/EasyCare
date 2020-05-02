package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Sub_servic;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Popups.OrderPop;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.interfaces.Type_Price;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import top.defaults.drawabletoolbox.DrawableBuilder;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by User on 26/02/2020.
 */

public class Provider_servicies_adapter extends RecyclerView.Adapter<Provider_servicies_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Categorie> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    private static double type_price=0, size_price=0;




    public interface Selected_item {
        void Onselcted(Categorie categorie);
    }


    public static int item_select = -1;
    Selected_item listenner;

    public Provider_servicies_adapter(Context context, List<Categorie> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner = listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_services_main, parent, false);

        mview = view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (new User_info(mContext).getLanguage().equals("en")) {
            holder.type.setText(mItems.get(position).getEnName());

        } else {
            holder.type.setText(mItems.get(position).getArName());

        }
        Picasso.with(mContext)
                .load(mItems.get(position).getImage())
                .into(holder.img);

        holder.soon.setVisibility(View.GONE);

        try {
            String color = mItems.get(position).getColor();
            makeDrawable(Color.parseColor(color), holder.img, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mItems.get(position).isActive()) {
            holder.soon.setVisibility(View.GONE);
        } else {
            holder.soon.setVisibility(View.VISIBLE);
            makeDrawable(Color.parseColor("#C2C2C2"), holder.img, 0);
        }

        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mItems.get(position).isActive()) {
//                    show_bottomSheet();
                   item_select=position;
                   listenner.Onselcted(mItems.get(position));
                }


            }
        });
    }

    private void makeDrawable(int color, View view, int corner) {
        Drawable drawable = new DrawableBuilder()
                .oval()
                .solidColor(color)//0xffe67e22
                .height(100)
                .width(100)
//                .cornerRadii(corner, corner, corner, corner)// pixel
                // top-left  top-right  bottom-right   bottom-left
                .build();
        view.setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView type, soon;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type_tv);
            img = itemView.findViewById(R.id.Img);
            soon = itemView.findViewById(R.id.soon_tv);
        }
    }
//    private void show_bottomSheet() {
//        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(mContext);
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View sheetView = inflater.inflate(R.layout.bottom_sheet_add_car, null);
//        RecyclerView RV_car = sheetView.findViewById(R.id.RV_car);
//        RecyclerView RV_services = sheetView.findViewById(R.id.RV_services);
//        RecyclerView RV_services_type = sheetView.findViewById(R.id.RV_services_type);
//        LinearLayoutManager layoutManager_car = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
//        LinearLayoutManager layoutManager_services = new LinearLayoutManager(mContext);
//        LinearLayoutManager layoutManager_services_type = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
//        Car_adapter car_adapter = new Car_adapter();
//        mBottomSheetDialog.setContentView(sheetView);
//        mBottomSheetDialog.show();
//    }
//
//    private static void updateTypePrice(double amount, int operation){
//        if (operation==0)
//            type_price += amount;
//        else if (type_price >= 0)
//            type_price -= amount;
//    }


}