package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
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
import static com.example.stw.newLogin.access;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class nakigiStorage extends AppCompatActivity {

    public CalendarView calendarView;
    String date, uid, memory;
    TextView textDate, textMemory;

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nakigi_storage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        uid = access;

        calendarView = findViewById(R.id.calendarView);
        textDate = findViewById(R.id.date);
        textMemory = findViewById(R.id.memory);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                date = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                textDate.setText(date);

                mReference.child("Nakigi List").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(date)) {

                            for (DataSnapshot snapshot : dataSnapshot.child(date).getChildren()) {

                                String key = snapshot.getKey();

                                if (key.equals("memory")) {
                                    Log.d("test", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + memory);
                                    memory = "" + snapshot.getValue();
                                    textMemory.setText(memory);
                                } else {
                                    textMemory.setText("");
                                }
                            }

                        } else {
                            textMemory.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(date);
            }
        });
    }

    private void deleteData(String date) {
        mReference.child("Nakigi List").child(uid).child(date).removeValue();
    }
}