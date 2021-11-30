package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class personalDiary extends AppCompatActivity {

    EditText text, timer;
    String memory, curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_diary);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        text = findViewById(R.id.memory);
        timer = findViewById(R.id.timer);

        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        timer.setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                timer.setText(year +"-"+ (month+1) +"-"+ dayOfMonth);
            }
        }, mYear, mMonth, mDay);

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });



        Button save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(personalDiary.this, personalStorage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}