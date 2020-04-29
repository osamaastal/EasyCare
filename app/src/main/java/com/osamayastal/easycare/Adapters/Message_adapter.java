package com.osamayastal.easycare.Adapters;

import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Message.Message;
import com.osamayastal.easycare.Model.Classes.Notification;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.Messages;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 26/02/2020.
 */

public class Message_adapter extends RecyclerView.Adapter<Message_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<com.osamayastal.easycare.Model.Classes.Message.Messages> mItems = new ArrayList<>();
    private Context mContext;
    private View mview;
    public interface Selected_item{
        void Onselcted(com.osamayastal.easycare.Model.Classes.Message.Messages messages);
    }
    public static int item_select=-1;
    Selected_item listenner;
    public Message_adapter(Context context, List<com.osamayastal.easycare.Model.Classes.Message.Messages> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages_readed, parent, false);


        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

          try {
              holder.main.setText(mItems.get(position).getLast_msg());
              holder.name.setText(mItems.get(position).getDriver().getName());
              String dateString = DateFormat.format("yyyy/MM/dd hh:mm aa",
                      new Date(mItems.get(position).getEdit_time_long())).toString();
              /******* TimeAgo *******/
              Locale LocaleBylanguageTag = Locale.forLanguageTag(new User_info(mContext).getLanguage());
              TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();
              dateString= TimeAgo.using(mItems.get(position).getEdit_time_long(),messages);
              holder.date.setText(dateString);
          } catch (Exception e) {
              e.printStackTrace();
          }

        try{
               Log.d("isread",mItems.get(position).getRead_user().toString());
               if (!mItems.get(position).getRead_user()){
               holder.container.setBackground(mContext.getDrawable(R.drawable.bg_req_gray_28dp_stroke));
           }else {
               holder.container.setBackground(mContext.getDrawable(R.drawable.bg_req_white_30dp));

           }
           } catch (Exception e) {
               e.printStackTrace();
           }
        String image=mItems.get(position).getDriver().getImg();
        if (!image.contains("https")){
            image=image.replace("http", "https");
        }
           Picasso.with(mContext)
                   .load(image)
                   .into(holder.image);
//bg_req_white_30dp  not red

           mview.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                   listenner.Onselcted(mItems.get(position));

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

    public class ViewHolder extends RecyclerView.ViewHolder{

      TextView main,date,name;
      ImageView image;
        ConstraintLayout container;
      public ViewHolder(View itemView) {
            super(itemView);
          main = itemView.findViewById(R.id.main_tv);
          date= itemView.findViewById(R.id.date_tv);
          name= itemView.findViewById(R.id.user_name_tv);
          image= itemView.findViewById(R.id.user_img);
          container= itemView.findViewById(R.id.container);


        }
    }
}