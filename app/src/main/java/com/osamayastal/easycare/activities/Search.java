package com.osamayastal.easycare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.osamayastal.easycare.R;

import top.defaults.drawabletoolbox.DrawableBuilder;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
