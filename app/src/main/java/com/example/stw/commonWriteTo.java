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
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class commonWriteTo extends AppCompatActivity {

    String uid = "wlqkr23";
    int cdid;
    String contentAll;
    TextView textView;
    HashMap<String, String> userContent;

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

            String[] cttSplit = contentAll.split(" : ");
            userContent = new HashMap<String, String>();

            if(cttSplit[0].equals("")){
                for(int j=0; j<cttSplit.length-2; j+=2) {
                    userContent.put(cttSplit[j+1], cttSplit[j+2]);
                    Log.i("common", "user: "+cttSplit[j+1]);
                    Log.i("common", "content: "+cttSplit[j+2]);
                }
            }
            else{
                for(int j=0; j<cttSplit.length-1; j+=2) {
                    userContent.put(cttSplit[j], cttSplit[j+1]);
                    Log.i("common", "content"+cttSplit[j]);
                }
            }

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
                userContent.put(uid, addContents);

                textView.setText(contentUpdate);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
