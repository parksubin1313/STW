package com.example.stw;

import static com.example.stw.newLogin.access;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class userPage extends AppCompatActivity {

    public static String id;
    TextView username,usermail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        username = findViewById(R.id.username);
        usermail = findViewById(R.id.usermail);

        id = access;
        Log.e("id",id);

        userinfo info = new userinfo();
        info.execute();

        ImageView nakigistorage = findViewById(R.id.nakigistorage);
        nakigistorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userPage.this, nakigiStorage.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView diarystorage = findViewById(R.id.diarystorage);
        diarystorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userPage.this, personalStorage.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView commondiarystorage = findViewById(R.id.commondiarystorage);
        commondiarystorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userPage.this, useCommonWrite.class);
                startActivity(intent);
                finish();
            }
        });


        //------하단바------
        ImageView bottomChatbot = (ImageView) findViewById((R.id.chatbot));
        bottomChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userPage.this, chatbot.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomNakigi = (ImageView) findViewById(R.id.nakigi);
        bottomNakigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userPage.this, nakigi.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomDiary = (ImageView) findViewById(R.id.diary);
        bottomDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userPage.this, personalDiary.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public class userinfo extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void...params) // 세부동작
        {
            try {

                UserDao dao = new UserDao();
                UserDTO dto = new UserDTO();
                dto = dao.myPage(id);
                String name = dto.getName();
                String mail = dto.getEmail();
                username.setText(name);
                usermail.setText(mail);
            }
            catch(Exception e)
            {

            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(userPage.this, nakigi.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}