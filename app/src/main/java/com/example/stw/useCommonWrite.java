package com.example.stw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import static com.example.stw.newLogin.access;

public class useCommonWrite extends AppCompatActivity {

    static String uid = access;
    String[] titles;
    int[] cid;
    static int did;

    HashMap<Integer, String> cdiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_write);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setList list = new setList();
        try {
            cdiary = list.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        ListView listView = findViewById(R.id.enjoyList);

        enjoyAdapter adapter = new enjoyAdapter();

        Iterator<Integer> keys = cdiary.keySet().iterator();
        int length = cdiary.size();
        titles = new String[length];
        cid = new int[length];
        int i = 0;
        while(keys.hasNext()){
            int key = keys.next();
            adapter.addItem(new commonItem(cdiary.get(key)));
            Log.e("join list",cdiary.get(key));
            titles[i] = cdiary.get(key);
            cid[i] = key;
            i++;
        }

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String clickTitle = titles[position];
                Toast.makeText(getApplicationContext(), clickTitle, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(useCommonWrite.this, useCommonCommunity.class);
                //Intent intent = new Intent(commonWrite.this, commonCommunity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
                intent.putExtra("cdid", cid[position]);
                did=cid[position];
                startActivity(intent);  //인텐트 이동
                finish();

            }
        });

    }

    class enjoyAdapter extends BaseAdapter {

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
        public View getView(int position, View view, ViewGroup parent) {
            commonEnjoyView commonEnjoyView = null;

            if(view == null){
                commonEnjoyView = new commonEnjoyView(getApplicationContext());
            } else {
                commonEnjoyView=(commonEnjoyView)view;
            }

            commonItem item = items.get(position);
            commonEnjoyView.setTitle(item.getTitle());
            return commonEnjoyView;
        }
    }


    public class setList extends AsyncTask<Void, Integer, HashMap<Integer, String>>
    {
        @Override
        protected HashMap<Integer, String> doInBackground(Void...params) // 세부동작
        {
            HashMap<Integer, String> title = new HashMap<Integer, String>();
            try {
                ArrayList<CommonDiaryDTO> comm = new ArrayList<CommonDiaryDTO>();
                CommonDiaryDAO dao = new CommonDiaryDAO();
                comm = dao.read(uid);

                for(int i=0; i<comm.size(); i++)
                {
                    Log.i("common", "did: " + comm.get(i).getCid()+"  title: "+comm.get(i).getTitle());
                    title.put(comm.get(i).getCid(), comm.get(i).getTitle());
                }

            }
            catch(Exception e)
            {

            }
            return title;
        }
    }
}