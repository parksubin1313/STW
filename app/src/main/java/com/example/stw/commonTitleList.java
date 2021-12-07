package com.example.stw;

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
import java.util.ArrayList;
import static com.example.stw.newLogin.access;

public class commonTitleList extends AppCompatActivity {

    String ID, title;
    ListView listView;
    titleAdapter adapter;
    String fileName = "commonDiary.txt";
    String fTitle;
    int fDid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_title_list);

        listView = findViewById(R.id.listView);
        adapter = new titleAdapter();

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
                    Log.e("/",comm.get(i).getTitle());
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

                        commonUpdate upDate = new commonUpdate();
                        upDate.execute();
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
            /*
            //String updateDay;
            try {
                FileInputStream fis = openFileInput(fileName);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
                //String str = buffer.readLine();
                //String id = buffer.readLine();

                CommonDiaryDAO dao = new CommonDiaryDAO();
                int did = fDid;
                boolean result = dao.update("hi", did);

                if (result) {
                    Log.e("result", "success");
                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

             */
            return null;
        }
    }
}