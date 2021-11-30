package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class diary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        ImageView commonDiary = findViewById(R.id.common);
        commonDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(diary.this, commonDiary.class);
                startActivity(intent);
                finish();
            }
        });


        ImageView personalDiary = findViewById(R.id.personal);
        personalDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(diary.this, personalDiary.class);
                startActivity(intent);
                finish();
            }
        });



        //------하단바------
        ImageView bottomUserpage = (ImageView) findViewById(R.id.mypage);
        bottomUserpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(diary.this, userPage.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomDiary = (ImageView) findViewById(R.id.diary);
        bottomDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(diary.this, diary.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomNakigi = (ImageView) findViewById(R.id.nakigi);
        bottomNakigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(diary.this, nakigi.class);
                startActivity(intent);
                finish();
            }
        });




    }
}