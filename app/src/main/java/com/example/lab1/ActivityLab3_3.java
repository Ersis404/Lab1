package com.example.lab1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class ActivityLab3_3 extends AppCompatActivity {

    private Button btnAdd;
    private EditText etName;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab33);
        dbHelper = new DBHelper(this);
        etName = findViewById(R.id.etName);

    }

    public void add(View v) {
        String name = etName.getText().toString();
        LocalDateTime date = LocalDateTime.now();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_DATE, String.valueOf(date));

        database.insert(DBHelper.TABLE_S, null, contentValues);
        dbHelper.close();
    }
}