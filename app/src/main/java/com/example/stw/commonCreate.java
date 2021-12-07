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
import static com.example.stw.newLogin.access;

public class commonCreate extends AppCompatActivity {


    public static String ID, title;
    ImageView create;
    EditText textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_create);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        textTitle = findViewById(R.id.title);

        create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = access;
                title=textTitle.getText().toString();

                diaryCreate diaryInfo = new diaryCreate();
                diaryInfo.execute();
            }
        });
    }

    public class diaryCreate extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try{
                CommonDiaryDAO dao = new CommonDiaryDAO();
                boolean result = dao.create("", ID, title);
                Log.e("APIManager", title + "/ create");

                if(result)
                {
                    Intent intent = new Intent(commonCreate.this, commonDiary.class);
                    startActivity(intent);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}