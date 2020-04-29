package com.osamayastal.easycare.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Notification;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/02/2020.
 */

public class Notifications_adapter extends RecyclerView.Adapter<Notifications_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Notification> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(Categorie categorie);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Notifications_adapter(Context context, List<Notification> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;
switch (viewType){//حالات التنبيهات: type
                     //١- طلبات

    case 1:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_accept, parent, false);
        break;
    case 2://٢- تقييمات
    case 4://٤- مسجات عامة من لوحة التحكم — مابفتح اي شي
    case 3://٣- كوبونات او عروض — مابفتح اي شي

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_wallet, parent, false);
        break;
    default:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_accept, parent, false);

}

        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.main.setText(mItems.get(position).getMsg());
        holder.date.setText(getdate(mItems.get(position).getDt_date()));



        mview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getdate(String date_){
        Log.e("date_", date_.substring(0, date_.indexOf("T")));
        return date_.substring(0, date_.indexOf("T"));
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

      TextView main,date;
      public ViewHolder(View itemView) {
            super(itemView);
          main = itemView.findViewById(R.id.main_tv);
         date= itemView.findViewById(R.id.date_tv);


        }
    }
}