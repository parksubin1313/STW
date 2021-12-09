package com.example.stw;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import static com.example.stw.newLogin.access;
import static com.example.stw.commonWrite.did;

public class commonCommunity extends AppCompatActivity {

    String uid = access;
    int cdid = did;
    String contentAll;
    HashMap<String, String> userContent;
    myAdapter adapter;
    int size;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_community);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        listView = findViewById(R.id.listView);
        listView.setStackFromBottom(true);
        adapter = new myAdapter();

        readAll all = new readAll();

        try {
            contentAll = all.execute().get();
            Log.i("common", "contentAll: "+contentAll);

            String[] cttSplit = contentAll.split(" : ");
            userContent = new HashMap<String, String>();
            size = cttSplit.length-2;
            String[] userID = new String[size];
            String[] userMemory = new String[size];

            if(cttSplit[0].equals("")){
                for(int j=0; j<cttSplit.length-2; j+=2) {
                    userContent.put(cttSplit[j+1], cttSplit[j+2]);
                    Log.e("common", "user: "+cttSplit[j+1]);
                    Log.e("common", "content: "+cttSplit[j+2]);
                    userID[j]=cttSplit[j+1];
                    userMemory[j]=cttSplit[j+2];
                    adapter.addItem(new commonItemWrtie(cttSplit[j+1], cttSplit[j+2]));
                    //adapter.addItem(new commonItemWrtie(userID[j],userMemory[j]));
                }

                listView.setAdapter(adapter);
            }
            else{
                for(int j=0; j<cttSplit.length-1; j+=2) {
                    userContent.put(cttSplit[j], cttSplit[j+1]);
                    Log.e("common", "content"+cttSplit[j]);
                    Log.e("common", "content"+cttSplit[j+1]);
                }
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
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

    class myAdapter extends BaseAdapter {

        ArrayList<commonItemWrtie> items = new ArrayList<commonItemWrtie>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(commonItemWrtie item){
            items.add(item);
        }

        @Override
        public Object getItem(int pos) {
            return items.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View view, ViewGroup viewGroup) {

            commonWrtieView CommonWrtieView = null;

            if(view == null){
                CommonWrtieView = new commonWrtieView(getApplicationContext());
            } else {
                CommonWrtieView=(commonWrtieView)view;
            }

            commonItemWrtie item = items.get(pos);
            CommonWrtieView.setID(item.getID());
            CommonWrtieView.setMemory(item.getMemory());

            return CommonWrtieView;
        }
    }

}
