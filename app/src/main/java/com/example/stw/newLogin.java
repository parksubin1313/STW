package com.example.stw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class newLogin extends AppCompatActivity {

    String ID, password;
    String access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        ImageView signup = findViewById(R.id.btnSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(MainActivity.this, checkNakigi.class);
                Intent intent = new Intent(newLogin.this, newSignup.class);
                startActivity(intent);
                finish();
            }
        });

        EditText textID = findViewById(R.id.editID);
        EditText textPW = findViewById(R.id.editPassword);

        ID = String.valueOf(textID.getText());
        password = String.valueOf(textPW.getText());

        ImageView buttonLogin = (ImageView) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new userinfo().execute();

            }
        });


    }

    public class userinfo extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void...params) // 세부동작
        {
            try {

                //user dao를 통해 로그인과 회원가입 가능
                UserDao dao = new UserDao();
                access=dao.login(ID,password); //로그인 access에 userid가 담김

                Log.e("login",access);

                if(access == ID)
                {
                    Intent intent = new Intent(newLogin.this, nakigi.class);
                    startActivity(intent);
                    finish();
                }

            }
            catch(Exception e)
            {

            }
            return null;
        }
    }
}