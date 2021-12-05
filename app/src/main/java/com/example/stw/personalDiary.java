package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class personalDiary extends AppCompatActivity {

    TextView timer;
    EditText text;
    public static String content, curDate;

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

        timer.setText(cal.get(Calendar.YEAR) + "년" + (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DATE) + "일");


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                timer.setText(year + "년" + (month + 1) + "월" + dayOfMonth + "일");
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

        //------하단바------
        ImageView bottomUserpage = (ImageView) findViewById(R.id.mypage);
        bottomUserpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalDiary.this, userPage.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomNakigi = (ImageView) findViewById(R.id.nakigi);
        bottomNakigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalDiary.this, nakigi.class);
                startActivity(intent);
                finish();
            }
        });

        TextView save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                content=text.getText().toString();
                diaryCreate dCreate = new diaryCreate();
                dCreate.execute();
            }

        });
    }

    public class diaryCreate extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try{
                DiaryDAO dao = new DiaryDAO();
                boolean result = dao.create(content, "flysamsung");
                Log.e("APIManager", "create");

                if(result)
                {
                    Intent intent = new Intent(personalDiary.this, nakigi.class);
                    startActivity(intent);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(personalDiary.this, nakigi.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}