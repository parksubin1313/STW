package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import static com.example.stw.newLogin.access;

public class commonSearch extends AppCompatActivity {

    String ID, title;
    ListView listView;
    titleAdapter adapter;
    //String fileName = "commonDiary.txt";
    String fTitle;
    int fDid;
    String uid = access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_search);

        listView = findViewById(R.id.listView);
        adapter = new titleAdapter();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //adapter.addItem(new commonItem("one"));
        setitle title = new setitle();
        title.execute();
        //listView.setAdapter(adapter);

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
                    adapter.addItem(new commonItem(title[i]));
                }

                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Log.e("여기봐/////",title[i]);
                        Log.e("여기봐/////",did[i]+" ");

                        fTitle=title[i];
                        fDid=did[i];

                        //commonUpdate upDate = new commonUpdate();
                        //upDate.execute();

                        commonInvite cInvite = new commonInvite();
                        cInvite.execute();
                    }
                });
            }
            catch(Exception e)
            {

            }
            return null;
        }
    }

    class titleAdapter extends BaseAdapter {

        ArrayList<commonItem> items = new ArrayList<commonItem>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(commonItem item){
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

            commonItemView CommonItemView = null;

            if(view == null){
                CommonItemView = new commonItemView(getApplicationContext());
            } else {
                CommonItemView=(commonItemView)view;
            }

            commonItem item = items.get(pos);
            CommonItemView.setTitle(item.getTitle());

            return CommonItemView;
        }
    }

    public class commonUpdate extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try{

                ID = access;

                CommonDiaryDAO dao = new CommonDiaryDAO();
                int did=fDid;
                boolean result = dao.update(ID + " : hi",did);

                if(result){
                    Log.e("result","success");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class commonInvite extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try{
                CommonDiaryDAO dao = new CommonDiaryDAO();
                boolean invite = dao.invite(fDid, uid);

                if(invite)
                {
                    Intent intent = new Intent(commonSearch.this, commonWrite.class);
                    startActivity(intent);  //인텐트 이동
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(commonSearch.this, commonDiary.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}