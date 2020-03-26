package com.osamayastal.easycare.classes.adapters;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.osamayastal.easycare.R;
import com.osamayastal.easycare.classes.Card;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CardAdapter extends com.github.islamkhsh.CardSliderAdapter {

    private ArrayList<Card> movies;

    public CardAdapter(ArrayList<Card> movies){
        this.movies = movies;
    }

    @Override
    public int getItemCount(){
        return movies.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_cardslider, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void bindVH(@NotNull RecyclerView.ViewHolder viewHolder, int i) {

    }


    class MovieViewHolder extends RecyclerView.ViewHolder {

        public MovieViewHolder(View view){
            super(view);
        }
    }
}
