package com.example.stw;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class chatConnect extends AppCompatActivity{

    EditText et;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button); //전송 버튼
        et = findViewById(R.id.user);   //사용자 입력
        tv = findViewById(R.id.bot);    //챗봇 응답 프린트

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send chatbot = new send();
                chatbot.execute();
            }
        });

    }

    public class send extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String sendText = et.getText().toString();
            String result = "";
            try{
                Log.e("sharry- user: ", sendText);
                Chat dao = new Chat();
                result = dao.chatting(sendText);
                tv.setText(result);
                Log.e("sharry- chatbot: ", result);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}