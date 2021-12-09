package com.example.stw;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class commonWrtieView extends LinearLayout {

    TextView textID, textMemory;

    public commonWrtieView(Context context) {
        super(context);
        init(context);
    }

    public commonWrtieView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    private void init (Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.common_memory_list,this,true);

        textID = findViewById(R.id.user);
        textMemory = findViewById(R.id.memory);
    }

    public void setID(String ID)
    {
        textID.setText(ID);
    }

    public void setMemory(String memory)
    {
        textMemory.setText(memory);
    }
}
