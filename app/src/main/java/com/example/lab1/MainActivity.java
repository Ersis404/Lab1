package com.example.lab1;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lab1.lab5.Lab5Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private Button buttonNext;
    private EditText editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        buttonNext = findViewById(R.id.buttonNext);
        editString = findViewById(R.id.editString);
        buttonNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i;
        i = new Intent(this, ActivityNext.class);
        String string = String.valueOf(editString.getText());
        i.putExtra("String", string);
        startActivity(i);
    }

    public void onClickLab2(View v) {
        Intent i;
        i = new Intent(this, Lab2Activity.class);
        startActivity(i);
    }

    public void onClickLab3(View v) {
        Intent i;
        i = new Intent(this, Lab3Activity.class);
        startActivity(i);
    }

    public void onClickNewVersionDB(View v) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.DATABASE_VERSION = 3;
        dbHelper.close();
        Intent i;
        i = new Intent(this, ActivityLab3_2.class);
        startActivity(i);
    }

    public void onClickLab4(View v) {
        Intent i;
        i = new Intent(this, Lab4AsyncTaskActivity.class);
        startActivity(i);
    }

    public void onClickLab5(View v) {
        Intent i;
        i = new Intent(this, Lab5Activity.class);
        startActivity(i);
    }

}