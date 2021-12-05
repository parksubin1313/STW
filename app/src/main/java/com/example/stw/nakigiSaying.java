package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.InputStream;
import jxl.Sheet;
import jxl.Workbook;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class nakigiSaying extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nakigi_saying);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(nakigiSaying.this, nakigi.class);
                startActivity(intent);
                finish();
            }
        });

        text = findViewById(R.id.saying);
        //String[] saying_array = new String[61];
        readExcel();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(nakigiSaying.this, nakigi.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

    public void readExcel() {

        Workbook workbook = null;
        Sheet sheet;
        String[] saying_array = new String[59];

        try {

            //File file = new File(path+"saying.xls");
            //FileInputStream fis = new FileInputStream(file);

            InputStream fis = getBaseContext().getResources().getAssets().open("nakigi.xls");
            workbook = Workbook.getWorkbook(fis);

            double randomValue = Math.random();
            int random = (int)(randomValue*56);

            //엑셀파일이 있다면
            if (workbook != null) {
                sheet = workbook.getSheet(0);

                Log.e("main","excel");
                //text.setText("엥");
                if (sheet != null) {
                    int colTotal = sheet.getColumns();
                    int rowIndexStart = 1;
                    int rowTotal = sheet.getColumn(colTotal-1).length;

                    StringBuilder sb;

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        sb = new StringBuilder();

                        for(int col=0; col<colTotal; col++)
                        {
                            String contents = sheet.getCell(col, row).getContents();
                            saying_array[row-1]=contents;
                            //Log.e("Main", row + "번째: " + contents);
                        }

                    }

                    Log.e("random",saying_array[random]);
                    text.setText(saying_array[random]);

                }
            }
        } catch (Exception e) {
            //text.setText("no");
            e.printStackTrace();
        }

    }
}