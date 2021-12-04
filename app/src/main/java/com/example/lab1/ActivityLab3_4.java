package com.example.lab1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.xml.sax.DTDHandler;

import java.time.LocalDateTime;

public class ActivityLab3_4 extends AppCompatActivity implements View.OnClickListener {

    private Button btnEdit;
    private EditText nameEdit;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab34);
        dbHelper = new DBHelper(this);

        btnEdit = findViewById(R.id.btnEdit);
        nameEdit = findViewById(R.id.nameEdit);
        btnEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_S, null, null, null, null, null, null);
        cursor.moveToLast();
        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, nameEdit.getText().toString());
        database.update(DBHelper.TABLE_S, contentValues, "_id = ?", new String[]{cursor.getString(idIndex)});
        dbHelper.close();
    }
}