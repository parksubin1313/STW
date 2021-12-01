package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class newSignup extends AppCompatActivity {

    public static String signupID, signupPW, signupMail, signupName;
    ImageView signup;
    EditText textID, textPW, textEmail, textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        textID = findViewById(R.id.editTextID);
        textPW = findViewById(R.id.editTextPassword);
        textEmail = findViewById(R.id.editTextEmail);
        textName = findViewById(R.id.editTextNickname);

        /*
        ID = String.valueOf(textID.getText());
        password = String.valueOf(textPW.getText());
        email = String.valueOf(textEmail.getText());
        name = String.valueOf(textName.getText());

         */

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupID=textID.getText().toString();
                signupPW=textPW.getText().toString();
                signupMail=textEmail.getText().toString();
                signupName=textName.getText().toString();

                userinfo info = new userinfo();
                info.execute();
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
                UserDTO dto = new UserDTO(signupID, signupPW, signupMail, signupName);
                boolean test = dao.createUser(dto); // 회원가입 , true 또는 false를 return함

                if(test)
                {
                    Intent intent = new Intent(newSignup.this, newLogin.class);
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