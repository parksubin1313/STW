package com.example.stw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.w3c.dom.Text;

public class nakigi extends AppCompatActivity {

    //public static String userid;
    TextView timer;
    EditText text;
    String memory, date, uid, curDate;
    DatabaseReference reference;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nakigi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        uid = access;

        text = findViewById(R.id.memory);
        timer = findViewById(R.id.timer);


        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        timer.setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));

        curDate = String.valueOf(timer.getText());

        //If memory is exist on today, load nakigiPopup
        mReference.child("Nakigi").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(curDate))
                {
                    Intent intent = new Intent(nakigi.this, nakigiPopup.class);
                    startActivity(intent);
                }
                else{
                    //Intent intent = new Intent(nakigi.this, nakigi.class);
                    //startActivity(intent);
                    //finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                timer.setText(year +"-"+ (month+1) +"-"+ dayOfMonth);
            }
        }, mYear, mMonth, mDay);


        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });

        ImageView send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                //Intent intent = new Intent(nakigi.this, nakigiSaying.class);
                Intent intent = new Intent(nakigi.this, nakigiSaying.class);
                startActivity(intent);
                finish();
            }
        });

        //------하단바------
        ImageView bottomChatbot = (ImageView) findViewById((R.id.chatbot));
        bottomChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nakigi.this, chatbot.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomUserpage = (ImageView) findViewById(R.id.mypage);
        bottomUserpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nakigi.this, userPage.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bottomDiary = (ImageView) findViewById(R.id.diary);
        bottomDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nakigi.this, personalDiary.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Data storage and modification method
    public void postFirebaseDataBase(boolean add) {

        reference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if (add) {
            FirebasePost post = new FirebasePost(memory, date);
            postValues = post.toMap();
        }

        childUpdates.put("/Nakigi/" + uid + "/" + date + "/", postValues);
        reference.updateChildren(childUpdates);
    }

    // Save to Firebase
    public void save() {
        memory = String.valueOf(text.getText());
        date = String.valueOf(timer.getText());
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