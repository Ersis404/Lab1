package com.example.lab1;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityNext extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        Intent i = getIntent();
        textView = findViewById(R.id.textView);
        textView.setText(i.getStringExtra("String"));
        findViewById(R.id.buttonBack).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       ActivityNext.this.finish();
    }
}