package com.example.stw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import static com.example.stw.newLogin.access;

public class chatbot extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<chatData> chatList;
    private String nick = "user"; // 1:1 or 1:da로
    String uid;

    private EditText EditText_chat;
    private Button Button_send;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button_send = findViewById(R.id.Button_send);
        EditText_chat = findViewById(R.id.EditText_chat);

        uid=access;

        //샌드 눌렀을떄
        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = EditText_chat.getText().toString(); //msg
                EditText_chat.setText(null);
                //널이 아닐때만 값전송하게
                if(msg != null){
                    chatData chat = new chatData();
                    chat.setNickname(nick);
                    chat.setMsg(msg);
                    myRef.child("/Chatbot/").child(uid).push().setValue(chat);
                    send cb = new send();
                    cb.execute();
                }

            }
        });

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new chatAdapter(chatList, chatbot.this, nick);
        mRecyclerView.setAdapter(mAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //chatData chat = new chatData();
        //chat.setNickname(nick);
        //chat.setMsg("hi");
        //myRef.setValue(chat);


        myRef.child("/Chatbot/").child(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Log.d("CHATCHAT",snapshot.getValue().toString());
                chatData chat = snapshot.getValue(chatData.class);
                ((chatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //------하단바------
        ImageView bottomChatbot = (ImageView) findViewById((R.id.diary));
        bottomChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chatbot.this, personalDiary.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomUserpage = (ImageView) findViewById(R.id.mypage);
        bottomUserpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chatbot.this, userPage.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomNakigi = (ImageView) findViewById(R.id.nakigi);
        bottomNakigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chatbot.this, nakigi.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class send extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String sendText = EditText_chat.getText().toString();
            String result = "";
            try{
                Log.e("sharry- user: ", sendText);
                Chat dao = new Chat();
                result = dao.chatting(sendText);
                chatData bot = new chatData();
                bot.setNickname("chatbot");
                bot.setMsg(result);
                myRef.child("/Chatbot/").child(uid).push().setValue(bot);
                Log.e("sharry- chatbot: ", result);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
