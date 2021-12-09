package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class commonDiary extends AppCompatActivity {

    ImageView btnCreate, btnSearch, btnWrite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_diary);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(commonDiary.this, commonCreate.class);
                startActivity(intent);
                finish();
            }
        });

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(commonDiary.this, commonSearch.class);
                startActivity(intent);
                finish();
            }
        });

        btnWrite = findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(commonDiary.this, commonWrite.class);
                startActivity(intent);
                finish();
            }
        });
    }
}