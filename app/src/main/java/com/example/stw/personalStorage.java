package com.example.stw;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class personalStorage extends AppCompatActivity implements OnDateSelectedListener {

    String fileName = "diary.txt";
    ArrayList<CalendarDay> dates;
    CalendarDay myDate;
    String userid = "flysamsung";
    EditText ctt;
    String contents;
    CalendarDay selectDate;
    HashMap<String, String> diaryInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_storage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        dates = new ArrayList<>();

        MaterialCalendarView calendarView = findViewById(R.id.calendarView);
        //calendarView.setSelectedDate(CalendarDay.today());

        OneDayDecorator oneDayDecorator = new OneDayDecorator();
        calendarView.addDecorators(oneDayDecorator);

        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.addDecorators(new SaturdayDecorator());
        calendarView.addDecorators(new SundayDecorator());


        ctt = findViewById(R.id.content);
        contents = ctt.getText().toString();

        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine();

            int i = 1;
            String stryear, strmonth, strday;
            int year, month, day;
            while (str != null) {
                if(i%2 == 1){
                    String[] date = str.split("/");
                    stryear = date[0];
                    strmonth = date[1];
                    strday = date[2];
                    Log.i("diary", stryear+" "+strmonth+" "+strday);

                    year = Integer.parseInt(stryear);
                    month = Integer.parseInt(strmonth)-1;
                    day = Integer.parseInt(strday);

                    myDate = CalendarDay.from(year, month, day);
                    Log.i("diary", myDate.toString());
                    dates.add(myDate);
                }

                str = buffer.readLine();
                i++;
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //일기를 작성한 날 빨간색 점으로 표시
        calendarView.addDecorator(new EventDecorator(Color.RED, dates));
        Log.i("diary", "red dots");



        //map 형식으로 날짜와 content 저장
        diaryRead2 dRead = new diaryRead2();
        try {
            diaryInfo = dRead.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        calendarView.setOnDateChangedListener(this);

        //선택한 날짜의 일기 보여주기
        /*
        String getContents = diaryAll.get(calendarView.getSelectedDate());
        Log.i("day", calendarView.getSelectedDate().toString());
        ctt.setText(getContents);
        */

        //수정 버튼
        Button btnModify = (Button) findViewById(R.id.update);
        btnModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                diaryUpdate dUpdate = new diaryUpdate();
                dUpdate.execute();
            }
        });

        //삭제 버튼
        Button btnDelete = (Button) findViewById(R.id.delete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                diaryDelete dDelete = new diaryDelete();
                dDelete.execute();
                calendarView.addDecorator(new EventDecorator(Color.RED, dates));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(personalStorage.this, userPage.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

    public class diaryRead2 extends AsyncTask<Void, Void, HashMap<String, String>> {

        @Override
        protected HashMap<String, String> doInBackground(Void... params) {
            HashMap<String, String> dAll = new HashMap<>();
            try {
                DiaryDAO dao = new DiaryDAO();
                ArrayList<DiaryDTO> dInfo = dao.read("flysamsung");

                for(int i=0; i<dInfo.size(); i++) {
                    DiaryDTO dto = dInfo.get(i);
                    String day = dto.get_cdate();
                    int id = dto.get_id();
                    String contents = dto.get_contents();

                    Log.i("APIManager", "id: " + id);
                    Log.i("APIManager", "day: " + day);
                    Log.i("APIManager", "content: " + contents);

                    String[] d = day.split(" ");
                    String DAY = d[1];
                    String MONTH = d[2];
                    String YEAR = d[3];

                    String month = "";
                    switch (MONTH) {
                        case "Jan":
                            month = "00";
                            break;
                        case "Feb":
                            month = "01";
                            break;
                        case "Mar":
                            month = "02";
                            break;
                        case "Apr":
                            month = "03";
                            break;
                        case "May":
                            month = "04";
                            break;
                        case "Jun":
                            month = "05";
                            break;
                        case "Jul":
                            month = "06";
                            break;
                        case "Aug":
                            month = "07";
                            break;
                        case "Sep":
                            month = "08";
                            break;
                        case "Oct":
                            month = "09";
                            break;
                        case "Nov":
                            month = "10";
                            break;
                        case "Dec":
                            month = "11";
                            break;
                    }

                    if(DAY.charAt(0) == '0'){
                        DAY = DAY.substring(1);
                    }

                    String writeDay = (YEAR + "-" + month + "-" + DAY);
                    String to = "CalendarDay{" + writeDay + "}";
                    Log.i("diary", to);
                    dAll.put(to, contents);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dAll;
        }
    }

    public class diaryUpdate extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String updateDay;
            try{
                FileInputStream fis = openFileInput(fileName);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
                String str = buffer.readLine();
                String id = buffer.readLine();

                DiaryDAO doi = new DiaryDAO();

                while(str != null) {
                    String[] d = str.split("/");
                    String YEAR = d[0];
                    String MONTH = d[1];
                    String DAY = d[2];
                    int month = Integer.parseInt(MONTH) - 1;
                    if(DAY.charAt(0) == '0'){
                        DAY = DAY.substring(1);
                    }
                    String sDate = "CalendarDay{"+YEAR+"-"+ month +"-"+DAY+"}";

                    if(sDate.equals(selectDate.toString())){
                        contents = ctt.getText().toString();
                        int did = Integer.parseInt(id);
                        boolean success = doi.update(did, contents);
                        diaryInfo.put(selectDate.toString(), contents);
                        /*
                        if(success){
                            Toast.makeText(getApplicationContext(), "일기를 수정했어요", Toast.LENGTH_SHORT).show();
                        }
                         */
                        break;
                    }

                    str = buffer.readLine();
                    id = buffer.readLine();
                }
                buffer.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class diaryDelete extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try{
                FileInputStream fis = openFileInput(fileName);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
                String str = buffer.readLine();
                String id = buffer.readLine();

                DiaryDAO doi = new DiaryDAO();

                String dummy = "";
                while(str != null) {
                    String[] d = str.split("/");
                    String YEAR = d[0];
                    String MONTH = d[1];
                    String DAY = d[2];
                    int month = Integer.parseInt(MONTH) - 1;
                    if(DAY.charAt(0) == '0'){
                        DAY = DAY.substring(1);
                    }
                    String sDate = "CalendarDay{"+YEAR+"-"+ month +"-"+DAY+"}";

                    if(sDate.equals(selectDate.toString())){
                        int did = Integer.parseInt(id);
                        ctt.setText("");
                        boolean success = doi.delete(did);
                        diaryInfo.remove(selectDate.toString());
                        /*
                        if(success){
                            Toast.makeText(getApplicationContext(), "일기를 삭제했어요", Toast.LENGTH_SHORT).show();
                        }
                         */
                    }
                    else{
                        dummy += (str + "\r\n");
                        dummy += (id + "\r\n");
                    }

                    str = buffer.readLine();
                    id = buffer.readLine();
                }
                buffer.close();
                fis.close();

                FileWriter fw = new FileWriter(fileName);
                fw.write(dummy);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileInputStream fis = openFileInput(fileName);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
                String str = buffer.readLine();

                int i = 1;
                String stryear, strmonth, strday;
                int year, month, day;
                while (str != null) {
                    if(i%2 == 1){
                        String[] date = str.split("/");
                        stryear = date[0];
                        strmonth = date[1];
                        strday = date[2];
                        Log.i("diary", stryear+" "+strmonth+" "+strday);

                        year = Integer.parseInt(stryear);
                        month = Integer.parseInt(strmonth)-1;
                        day = Integer.parseInt(strday);

                        myDate = CalendarDay.from(year, month, day);
                        Log.i("diary", myDate.toString());
                        dates.add(myDate);
                    }

                    str = buffer.readLine();
                    i++;
                }
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        if (selected) {
            selectDate = date;
            String show = diaryInfo.get(date.toString());
            if(show != null){
                ctt.setText(show);
                Log.i("diary", date.toString());
                Log.i("diary", show);
            }
            else{
                ctt.setText("");
                Toast.makeText(getApplicationContext(), "아직 일기를 작성하지 않았어요!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}