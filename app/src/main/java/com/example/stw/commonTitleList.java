package com.example.stw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class commonTitleList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_title_list);

        setitle title = new setitle();
        title.execute();

        /*
        ListView listView = findViewById(R.id.listView);

        titleAdapter adapter = new titleAdapter();
        adapter.addItem(new commonItem("one"));
        adapter.addItem(new commonItem("two"));

        listView.setAdapter(adapter);

         */
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

                for(int i=0; i<comm.size(); i++)
                {
                    Log.e("/",comm.get(i).getTitle());
                    title[i] = comm.get(i).getTitle();
                }

                ListView listView = findViewById(R.id.listView);

                titleAdapter adapter = new titleAdapter();

                for(int i=0; i<title.length; i++)
                {
                    adapter.addItem(new commonItem(title[i]));
                }

                listView.setAdapter(adapter);
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
}