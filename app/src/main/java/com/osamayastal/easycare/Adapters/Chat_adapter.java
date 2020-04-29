package com.osamayastal.easycare.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.osamayastal.easycare.Model.Classes.Message.Message;
import com.osamayastal.easycare.Model.Classes.Message.Messages;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 26/02/2020.
 */

public class Chat_adapter extends RecyclerView.Adapter<Chat_adapter.MyViewHolder> {
    private List<Message> mDataset;
    public static final int MSG_LEFT = 0;
    public static final int MSG_RIGHT = 1;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView msg,msg_time;

        public MyViewHolder(View v) {
            super(v);
            msg = v.findViewById(R.id.msg_tv);
            msg_time = v.findViewById(R.id.time_tv);
        }
    }
    Context mcontext;
    // Provide a suitable constructor (depends on the kind of dataset)
    public Chat_adapter(Context mcontext, List<Message> mDataset) {
        this.mDataset = mDataset;
        this.mcontext=mcontext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Chat_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == MSG_LEFT){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_sender, parent, false);

        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_reciver, parent, false);

        }
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try{
            holder.msg.setText(mDataset.get(position).getContent());
            String dateString = DateFormat.format("yyyy/MM/dd hh:mm aa",
                    new Date(mDataset.get(position).getTime_long())).toString();
            /******* TimeAgo *******/
            Locale LocaleBylanguageTag = Locale.forLanguageTag(new User_info(mcontext).getLanguage());
            TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();
            dateString= TimeAgo.using(mDataset.get(position).getTime_long(),messages);
            holder.msg_time.setText(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(!mDataset.get(position).getIs_driver())
            return MSG_LEFT;//msg send
        else
            return MSG_RIGHT;//msg recive

    }
}