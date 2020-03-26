package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.City;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;

import java.util.ArrayList;
import java.util.List;

import top.defaults.drawabletoolbox.DrawableBuilder;

/**
 * Created by User on 26/02/2020.
 */
public class City_adapter extends ArrayAdapter<City> {

    LayoutInflater flater;
String lang="";
    public City_adapter(Activity context,int resouceId, int textviewId, List<City> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
       lang= new User_info(context).getLanguage();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        City rowItem = getItem(position);
        Log.d("City_name",rowItem.getArName());

        View rowview = flater.inflate(R.layout.row_city_text,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.type_tv);

        if (lang.equals("en")){
            txtTitle.setText(rowItem.getEnName().toString());

        }else {
            txtTitle.setText(rowItem.getArName().toString());

        }



        return rowview;
    }
}

//
//
//public class City_adapter extends RecyclerView.Adapter<City_adapter.ViewHolder> {
//
//    private static final String TAG = "RecyclerViewAdapter";
//
//    //vars
//    private List<City> mItems = new ArrayList<>();
//    private Context mContext;
//    private View mview;
//    public interface Selected_item{
//        void Onselcted(City city);
//    }
//    public static int item_select=-1;
//    Selected_item listenner;
//    public City_adapter(Context context, List<City> names, Selected_item listenner) {
//        mItems = names;
//        mContext = context;
//        this.listenner=listenner;
//
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
//
//        View view;
//
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_text, parent, false);
//
//        mview=view;
//        return new ViewHolder(view); // Inflater means reading a layout XML
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @SuppressLint("ResourceAsColor")
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//if (new User_info(mContext).getLanguage().equals("en")){
//    holder.type.setText(mItems.get(position).getEnName());
//
//}else {
//    holder.type.setText(mItems.get(position).getArName());
//
//}
//
//
//        mview.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//item_select=position;
//notifyDataSetChanged();
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mItems.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//
//      TextView type;
//      public ViewHolder(View itemView) {
//            super(itemView);
//          type = itemView.findViewById(R.id.type_tv);
//
//
//
//        }
//    }
//}