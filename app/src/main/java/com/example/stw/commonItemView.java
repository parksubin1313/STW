package com.example.stw;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class commonItemView extends LinearLayout {

    TextView textTitle;
    Button btnJoin;

    public commonItemView(Context context) {
        super(context);
        init(context);
    }

    public commonItemView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    private void init (Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.common_item_list,this,true);

        textTitle = findViewById(R.id.title);
    }

    public void setTitle(String title){
        textTitle.setText(title);
    }
}
