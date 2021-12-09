package com.example.stw;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import static com.example.stw.newLogin.access;
import static com.example.stw.commonWrite.did;

public class commonWriteTo extends AppCompatActivity {

    static String uid = access;
    static int cdid = did;
    String contentAll;
    TextView commonName;
    HashMap<String, String> userContent;
    EditText addText;
    static String addContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_writeto);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        addText = findViewById(R.id.addContents);

        commonName = findViewById(R.id.commonName);

        setitle title = new setitle();
        title.execute();

        //Intent intent = getIntent();
        //cdid = intent.getExtras().getInt("cdid");

        readAll all = new readAll();

        try {
            contentAll = all.execute().get();
            Log.i("common", "contentAll: "+contentAll);

            String[] cttSplit = contentAll.split(" : ");
            userContent = new HashMap<String, String>();

            if(cttSplit[0].equals("")){
                for(int j=0; j<cttSplit.length-2; j+=2) {
                    userContent.put(cttSplit[j+1], cttSplit[j+2]);
                    Log.e("common", "user: "+cttSplit[j+1]);
                    Log.e("common", "content : "+cttSplit[j+2]);
                }
            }
            else{
                for(int j=0; j<cttSplit.length-1; j+=2) {
                    userContent.put(cttSplit[j], cttSplit[j+1]);
                    Log.e("common2", "user2"+cttSplit[j+1]);
                    Log.e("common2", "content2"+cttSplit[j+1]);
                }
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        TextView btnCommon = findViewById(R.id.btnSendCommon);
        btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addContents = addText.getText().toString();

                commonUpdate cupdate = new commonUpdate();
                cupdate.execute();

                /*
                readAll all2 = new readAll();

                try {
                    contentAll = all2.execute().get();
                    Log.i("common", "contentAll: "+contentAll);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                 */
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

                String contentUpdate = contentAll + " : " + uid + " : " + addContents;
                CommonDiaryDAO dao = new CommonDiaryDAO();
                boolean success = dao.update(contentUpdate, cdid);
                Log.i("common", contentUpdate);
                userContent.put(uid, addContents);
                Log.e("result",contentUpdate);

                if(success)
                {
                    Intent intent = new Intent(commonWriteTo.this, commonCommunity.class);
                    startActivity(intent);
                    finish();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class setitle extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void...params) // 세부동작
        {
            try {
                ArrayList<CommonDiaryDTO> comm = new ArrayList<CommonDiaryDTO>();
                CommonDiaryDAO dao = new CommonDiaryDAO();
                comm = dao.readList();
                int s = comm.size();
                String[] title = new String[s];
                int[] did = new int[s];

                for(int i=0; i<comm.size(); i++)
                {
                    Log.e("/",comm.get(i).getTitle()+"   "+comm.get(i).getHost());
                    title[i] = comm.get(i).getTitle();
                    did[i]=comm.get(i).getCid();
                }

                for(int i=0; i<title.length; i++)
                {
                    if(did[i]==cdid)
                    {
                        commonName.setText(title[i]);
                    }
                }

            }
            catch(Exception e)
            {

            }
            return null;
        }
    }


}
