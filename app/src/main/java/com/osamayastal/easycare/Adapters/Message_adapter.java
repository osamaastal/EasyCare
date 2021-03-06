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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Message.Message;
import com.osamayastal.easycare.Model.Classes.Notification;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.Messages;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

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
        void ondelete(com.osamayastal.easycare.Model.Classes.Message.Messages messages);
    }
   private int item_select=-1;
    Selected_item listenner;
    FirebaseDatabase database;
    DatabaseReference reference;
    public Message_adapter(Context context, List<com.osamayastal.easycare.Model.Classes.Message.Messages> names, Selected_item listenner) {
        mItems = names;
        mContext = context;
        this.listenner=listenner;



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type

        View view;
        if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages_readed, parent, false);

        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages, parent, false);

        }


        mview=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

          try {
              holder.main.setText(mItems.get(position).getLast_msg());
              holder.name.setText(mItems.get(position).getDriver().getName()+" - "+mItems.get(position).getOrder_number());
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
                   if (mItems.get(position).getCan_open()){
                       listenner.Onselcted(mItems.get(position));
                   }else {
                       AppPop pop=new AppPop();
                       pop.Show_mssg_POP(mContext, mContext.getString(R.string.order_dane), new AppPop.goListenner() {
                           @Override
                           public void Go() {

                           }

                           @Override
                           public void Cancel() {

                           }
                       });
                   }


               }
           });

        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AppPop pop=new AppPop();
                pop.Conferme_POP(mContext, mContext.getString(R.string.delet_convercition), new AppPop.goListenner() {
                    @Override
                    public void Go() {

                    listenner.ondelete(mItems.get(position));

                    }

                    @Override
                    public void Cancel() {

                    }
                });

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
        if (mItems.get(position).getRead_user())
      return 1;
        else return 0;

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
                ImageButton delete;
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
          delete = itemView.findViewById(R.id.delete_btn);

        }
    }
}