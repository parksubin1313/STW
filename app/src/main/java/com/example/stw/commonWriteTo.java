package com.example.stw;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class commonWriteTo extends AppCompatActivity {

    String uid = "wlqkr23";
    int cdid;
    String contentAll;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_writeto);


        Intent intent = getIntent();
        cdid = intent.getExtras().getInt("cdid");

        textView = findViewById(R.id.existContents);

        readAll all = new readAll();
        try {
            contentAll = all.execute().get();
            Log.i("common", "contentAll: "+contentAll);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        Button btnCommon = findViewById(R.id.btnSendCommon);
        btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonUpdate cupdate = new commonUpdate();
                cupdate.execute();

                readAll all2 = new readAll();
                try {
                    contentAll = all2.execute().get();
                    Log.i("common", "contentAll: "+contentAll);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    public class readAll extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String ctt = "";
            try{
                CommonDiaryDAO dao = new CommonDiaryDAO();
                ArrayList<CommonDiaryDTO> commonInfo = dao.read(uid);
                for(int i=0; i<commonInfo.size(); i++) {
                    CommonDiaryDTO dto = commonInfo.get(i);
                    if(dto.getCid() == cdid){
                        ctt = dto.getCcontents();
                    }

                    textView.setText(ctt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ctt;
        }
    }

    public class commonUpdate extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try{
                EditText addText = findViewById(R.id.addContents);
                String addContents = addText.getText().toString();

                String contentUpdate = contentAll + " : " + uid + " : " + addContents;
                CommonDiaryDAO dao = new CommonDiaryDAO();
                dao.update(contentUpdate, cdid);
                Log.i("common", contentUpdate);

                textView.setText(contentUpdate);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
