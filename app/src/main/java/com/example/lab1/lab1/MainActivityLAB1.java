package com.example.lab1.lab1;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lab1.R;

public class MainActivityLAB1 extends AppCompatActivity{

    private EditText editGet;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab1);
        editGet = findViewById(R.id.textGet);
        buttonNext = findViewById(R.id.LAB1);
    }

    public void onClick1(View v) {
        Intent i;
        i = new Intent(this, ActivityNext.class);
        String string = String.valueOf(editGet.getText());
        i.putExtra("String", string);
        startActivity(i);
    }
}