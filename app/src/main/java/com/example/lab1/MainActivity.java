package com.example.lab1;


import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lab1.lab1.MainActivityLAB1;
import com.example.lab1.lab3.ActivityLab3_2;
import com.example.lab1.lab3.DBHelper;
import com.example.lab1.lab2.Lab2Activity;
import com.example.lab1.lab3.Lab3Activity;
import com.example.lab1.lab4.Lab4AsyncTaskActivity;
import com.example.lab1.lab5.Lab5Activity;
import com.example.lab1.lab6.MainActivityLAB6;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onClickLab1(View v) {
        Intent i;
        i = new Intent(this, MainActivityLAB1.class);
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

    public void onClickLab6(View v) {
        Intent i;
        i = new Intent(this, MainActivityLAB6.class);
        startActivity(i);
    }
}