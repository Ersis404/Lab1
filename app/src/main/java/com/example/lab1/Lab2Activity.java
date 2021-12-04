package com.example.lab1;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Lab2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);
    }

    public void onClick1(View v) {
        Intent i;
        i = new Intent(this, ActivityLab2_2.class);
        startActivity(i);
    }
    public void onClick2(View v) {
        Intent i;
        i = new Intent(this, ActivityLab2_3.class);
        startActivity(i);
    }
    public void onClick3(View v) {
        Intent i;
        i = new Intent(this, ActivityLab2_4.class);
        startActivity(i);
    }

    public void exit(View view) {
        Lab2Activity.this.finish();
    }
}