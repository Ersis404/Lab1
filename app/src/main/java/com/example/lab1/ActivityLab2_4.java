package com.example.lab1;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class ActivityLab2_4 extends AppCompatActivity {

    int col = Color.WHITE;
    private Button button11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab24);
        button11 = findViewById(R.id.button11);
        button11.setBackgroundColor(Color.WHITE);
    }

    public void changeColor(View v) {
        if(col == Color.WHITE) {
            int x = 0;
            Random rand = new Random();
            while (x < 5) {
                col = -rand.nextInt(0xFFFFFF);
                x++;
            }
        }else
            col = Color.WHITE;
        button11.setBackgroundColor(col);
    }
}