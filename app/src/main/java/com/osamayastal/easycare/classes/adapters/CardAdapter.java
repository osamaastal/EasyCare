package com.osamayastal.easycare.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.Model.Classes.Slider;
import com.osamayastal.easycare.R;
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

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_cardslider, parent, false);
        return new MovieViewHolder(view);
    }



    @Override
    public void bindVH(@NotNull RecyclerView.ViewHolder viewHolder, int i) {
       ImageView img=viewHolder.itemView.findViewById(R.id.Img);
        Picasso.with(mcontext)
                .load(mItems.get(i).getImage())
                .into(img);
    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
TextView title,dis,more;

ImageView img;
        public MovieViewHolder(View view){
            super(view);
//            img=view.findViewById(R.id.Img);
//            title=view.findViewById(R.id.title_tv);
//            dis =view.findViewById(R.id.details_tv);
//            more=view.findViewById(R.id.more);
        }
    }
}
