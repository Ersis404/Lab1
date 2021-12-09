package com.example.lab1.lab3;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lab1.R;

import java.time.LocalDateTime;

public class Lab3Activity extends AppCompatActivity {

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);
        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(DBHelper.TABLE_S, null, null);

        String[] position_name = { "Филатов М А", "Борисов М А",
                "Реснянский К С", "Морковкина С С", "Бухтояров М М" };

        ContentValues contentValues = new ContentValues();

            for (int i = 0; i < position_name.length; i++) {
                contentValues.clear();
                contentValues.put(DBHelper.KEY_NAME, position_name[i]);
                contentValues.put(DBHelper.KEY_DATE, LocalDateTime.now().toString());
                database.insert(DBHelper.TABLE_S, null,  contentValues);
            }



    }

    public void onClickAllStudents(View v) {
        Intent i;
        i = new Intent(this, ActivityLab3_2.class);
        startActivity(i);
    }
    public void onClickAddStudent(View v) {
        Intent i;
        i = new Intent(this, ActivityLab3_3.class);
        startActivity(i);
    }

    public void onClickEditLastStudent(View v) {
        Intent i;
        i = new Intent(this, ActivityLab3_4.class);
        startActivity(i);
    }


}