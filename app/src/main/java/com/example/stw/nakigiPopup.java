package com.example.stw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static com.example.stw.newLogin.access;

public class nakigiPopup extends Activity {

    String uid;
    String date, memory;

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference();

    TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_nakigi_popup);

        uid = access;

        Calendar cal = Calendar.getInstance();

        date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);

        TextView text = findViewById(R.id.memory);

        mReference.child("Nakigi").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(date)) {
                    for (DataSnapshot dataSnapshot : snapshot.child(date).getChildren()) {
                        String key = dataSnapshot.getKey();

                        if (key.equals("memory")) {
                            memory = "" + dataSnapshot.getValue();
                            text.setText(memory);
                        } else {
                        }
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                deleteData(date);
                finish();
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(date);
                finish();
            }
        });

    }

    //확인 버튼 클릭
    public void mOnClose(View v) {

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    private void deleteData(String date) {
        mReference.child("Nakigi").child(uid).child(date).removeValue();
    }

    // Data storage and modification method
    public void postFirebaseDataBase(boolean add) {

        mReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if (add) {
            nakigiPopup.FirebasePost post = new nakigiPopup.FirebasePost(memory, date);
            postValues = post.toMap();
        }

        childUpdates.put("/Nakigi List/" + uid + "/" + date + "/", postValues);
        mReference.updateChildren(childUpdates);
    }

    // Save to Firebase
    public void saveData() {
        //memory = String.valueOf(text.getText());
        //date = String.valueOf(timer.getText());
        postFirebaseDataBase(true);
    }

    public class FirebasePost {

        public String memory;
        public String date;

        public FirebasePost(String memory, String date) {
            this.memory = memory;
            this.date = date;
        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("memory", memory);
            result.put("date", date);
            return result;
        }

    }
}