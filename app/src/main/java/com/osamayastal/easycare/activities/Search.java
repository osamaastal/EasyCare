package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.osamayastal.easycare.R;

import top.defaults.drawabletoolbox.DrawableBuilder;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }
private EditText name;
    private ImageView filter,search,close,back;
    private TextView result_nb;
    private LinearLayout no_result;
    private RecyclerView RV;
    private void init() {
        filter=findViewById(R.id.filter_btn);
        search=findViewById(R.id.search_btn);
        close=findViewById(R.id.close_btn);
        result_nb=findViewById(R.id.result_nb);
        no_result=findViewById(R.id.linear_no_results);
        RV=findViewById(R.id.RV);
        back=findViewById(R.id.back_btn);
        RV=findViewById(R.id.RV);
    }

    private void makeDrawable(int color, TextView view, boolean isChoose) {
        if (isChoose){
            Drawable drawable = new DrawableBuilder()
                    .rectangle()
                    .solidColor(color)//0xffe67e22
                    .cornerRadii(18, 18, 18, 18)// pixel
                    // top-left  top-right  bottom-right   bottom-left
                    .build();
            view.setBackground(drawable);
        }else {
            Drawable drawable = new DrawableBuilder()
                    .rectangle()
                    .strokeColor(0xffffffff)//0xffe67e22
                    .strokeWidth(1)
                    .cornerRadii(18, 18, 18, 18)// pixel
                    // top-left  top-right  bottom-right   bottom-left
                    .build();
            view.setBackground(drawable);
        }

    }
}
