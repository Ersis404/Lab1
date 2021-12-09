package com.example.lab1.lab6;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lab1.R;

public class MainActivityLAB61 extends AppCompatActivity {

    private TextView title, notification, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab61);
        title = findViewById(R.id.textViewNotifyTitle);
        notification = findViewById(R.id.textViewNotifyDescription);
        date = findViewById(R.id.textViewNotifyDate);

        Intent intent = getIntent();
        String titleForView = intent.getStringExtra("titleShow");
        String notificationForView = intent.getStringExtra("notificationShow");
        String dateForView = intent.getStringExtra("dateShow");
        title.setText(titleForView);
        notification.setText(notificationForView);
        date.setText(dateForView);
    }
}