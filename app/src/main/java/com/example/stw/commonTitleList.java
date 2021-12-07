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

        ListView listView = findViewById(R.id.listView);

        titleAdapter adapter = new titleAdapter();
        adapter.addItem(new commonItem("one"));
        adapter.addItem(new commonItem("two"));

        listView.setAdapter(adapter);
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