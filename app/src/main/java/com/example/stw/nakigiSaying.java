package com.example.stw;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import jxl.Sheet;
import jxl.Workbook;

public class nakigiSaying extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nakigi_saying);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        text = findViewById(R.id.saying);
        String[] saying_array = new String[61];
        readExcel(saying_array);

        //text.setText(saying_array[10]);
        //text.setText(str[0]);
    }

    public void readExcel(String[] saying_array) {

        Workbook workbook = null;
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("saying.xls");
            workbook = Workbook.getWorkbook(is);

            //엑셀파일이 있다면
            if (workbook != null) {
                Sheet sheet = workbook.getSheet(0);

                text.setText("엥");
                if (sheet != null) {
                    int colTotal = sheet.getColumns();
                    int rowIndexStart = 1;
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    StringBuilder sb;

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        sb = new StringBuilder();

                        for (int col = 0; col < colTotal; col++) {
                            String contents = sheet.getCell(col, row).getContents();
                            saying_array[col]=contents;
                            //Log.d("Main", col + "번째: " + contents);
                        }
                    }


                }
            }
        } catch (Exception e) {
            text.setText("no");
            e.printStackTrace();
        }

    }
}