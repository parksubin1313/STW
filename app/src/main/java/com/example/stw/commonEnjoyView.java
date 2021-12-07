package com.example.stw;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class commonEnjoyView extends LinearLayout {

    TextView textTitle;

    public commonEnjoyView(Context context) {
        super(context);
        init(context);
    }

    public commonEnjoyView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    private void init (Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.common_enjoy_list,this,true);

        textTitle = findViewById(R.id.enjoyTitle);
    }

    public void setTitle(String title){
        textTitle.setText(title);
    }
}

