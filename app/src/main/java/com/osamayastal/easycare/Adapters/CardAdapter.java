package com.osamayastal.easycare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Slider;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.activities.ServiceProfiderDetails;
import com.osamayastal.easycare.classes.items.Card;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends com.github.islamkhsh.CardSliderAdapter {

    private List<Slider> mItems;
    Context mcontext;
    public CardAdapter(Context mcontext,List<Slider> mItems){
        this.mItems = mItems;
        this.mcontext=mcontext;
    }

    @Override
    public int getItemCount(){
        return mItems.size();
    }
    View view;
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_cardslider, parent, false);
        return new MovieViewHolder(view);
    }



    @Override
    public void bindVH(@NotNull RecyclerView.ViewHolder viewHolder, final int i) {
       ImageView img=viewHolder.itemView.findViewById(R.id.Img);
      TextView  title=viewHolder.itemView.findViewById(R.id.title_tv);
        TextView   dis =viewHolder.itemView.findViewById(R.id.details_tv);
        TextView  more=viewHolder.itemView.findViewById(R.id.more);
        if (mItems.get(i).getAds_for().equals("1")) {
            more.setVisibility(View.GONE);
        }

viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       mor_action(i);
    }
});
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mor_action(i);
            }
        });
        Picasso.with(mcontext)
                .load(mItems.get(i).getImage())
                .into(img);

        if (new User_info(mcontext).getLanguage().equals("en")){

            title.setText(mItems.get(i).getTitleEn());
            dis.setText(mItems.get(i).getDescriptionEn());
        }else {

            title.setText(mItems.get(i).getTitleAr());
            dis.setText(mItems.get(i).getDescriptionAr());
        }
    }

private void mor_action(int i){
    if (mItems.get(i).getAds_for().equals("2")){
        Intent intent=new Intent(mcontext, ServiceProfiderDetails.class);
        intent.putExtra("provider_id",mItems.get(i).getStore_id());
        mcontext.startActivity(intent);
    }
    if (mItems.get(i).getAds_for().equals("3")) {
        if (!mItems.get(i).getUrl().isEmpty()) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mItems.get(i).getUrl()));
            mcontext.startActivity(myIntent);
        }
    }
}
    class MovieViewHolder extends RecyclerView.ViewHolder {

        public MovieViewHolder(View view){
            super(view);

        }
    }
}
