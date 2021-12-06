package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
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

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class personalDiary extends AppCompatActivity {

    TextView timer;
    EditText text;
    public static String content, curDate;
    String fileName = "diary.txt";

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

                diaryRead dRead = new diaryRead();
                dRead.execute();
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
                    Intent intent = new Intent(personalDiary.this, personalStorage.class);
                    startActivity(intent);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public class diaryRead extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try{

                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                PrintWriter out = new PrintWriter(fos, true);

                DiaryDAO doi = new DiaryDAO();
                ArrayList<DiaryDTO> diaryInfo = doi.read("flysamsung");
                for(int i=0; i<diaryInfo.size(); i++){
                    DiaryDTO dInfo = diaryInfo.get(i);
                    String day = dInfo.get_cdate();
                    int id = dInfo.get_id();
                    String ctt = dInfo.get_contents();

                    Log.i("APIManager", "id: "+id);
                    Log.i("APIManager", "day: "+day);
                    Log.i("APIManager", "content: "+ctt);

                    String[] d = day.split(" ");
                    String DAY = d[1];
                    String MONTH = d[2];
                    String YEAR = d[3];

                    String month = "";
                    switch(MONTH) {
                        case "Jan":
                            month = "01";
                            break;
                        case "Feb":
                            month = "02";
                            break;
                        case "Mar":
                            month = "03";
                            break;
                        case "Apr":
                            month = "04";
                            break;
                        case "May":
                            month = "05";
                            break;
                        case "Jun":
                            month = "06";
                            break;
                        case "Jul":
                            month = "07";
                            break;
                        case "Aug":
                            month = "08";
                            break;
                        case "Sep":
                            month = "09";
                            break;
                        case "Oct":
                            month = "10";
                            break;
                        case "Nov":
                            month = "11";
                            break;
                        case "Dec":
                            month = "12";
                            break;
                    }
                    String writeDay = YEAR + "/" + month + "/" + DAY;
                    out.println(writeDay);
                    out.println(id);
                }

                out.close();

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