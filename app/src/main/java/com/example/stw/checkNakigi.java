package com.example.stw;

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

public class checkNakigi extends AppCompatActivity {

    String uid;
    String date, memory;

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_nakigi);

        // Get the ID of the currently connected user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
        uid = user != null ? user.getUid() : null; // Get the unique uid of the logged-in user

        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        date = cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE);

        TextView text = findViewById(R.id.text);

        mReference.child("Nakigi").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(date))
                {
                    for(DataSnapshot dataSnapshot : snapshot.child(date).getChildren())
                    {
                        String key = dataSnapshot.getKey();

                        if(key.equals("memory")){
                            memory = ""+dataSnapshot.getValue();
                            text.setText(memory);
                        } else{
                            memory = "오늘은 나쁜 기억이 없습니다.";
                        }
                    }
                }
                else{
                    memory = "오늘은 나쁜 기억이 없습니다.";
                    text.setText(memory);
                }
                /*
                memoryList group = snapshot.getValue(memoryList.class);
                memory = group.getMemory();
*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

    }
}