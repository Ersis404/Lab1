package com.example.lab1.lab6;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lab1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivityLAB6 extends AppCompatActivity {

    private DBHelper3 dbHelper;
    private Button btnAdd,btnViev;
    private EditText title, textnotification,date;
    private Calendar dateAndTime = Calendar.getInstance();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd MMM yyyy., HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab6);
        dbHelper = new DBHelper3(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        title = findViewById(R.id.editTextTextPersonName);
        textnotification = findViewById(R.id.editTextTextPersonName2);
        date = findViewById(R.id.editTextTextPersonName3);

        // отображаем диалоговое окно для выбора даты
        setInitialDateTime();


    }

    public void getDate(View v){
    setDate();
    setTime();
    }

    public void addNotification(View v) throws ParseException {

        String titles = String.valueOf(title.getText());
        String notification = String.valueOf(textnotification.getText());
        String _date = String.valueOf(date.getText());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
            cv.put(dbHelper.COLUMN_TITLE, titles);
            cv.put(dbHelper.COLUMN_NOTIFICATION, notification);
            cv.put(dbHelper.COLUMN_DATE, _date);
            database.insert(DBHelper3.TABLE_N, null,  cv);

        String selectQuery = "SELECT id FROM " + DBHelper3.TABLE_N + " WHERE id = (SELECT MAX(id) FROM " + DBHelper3.TABLE_N + ");";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToLast();
        String id = cursor.getString(0);

//        Intent intentToMainClass = new Intent(this, MainActivityLAB6.class);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("id", id);
        notificationIntent.putExtra("title", titles);
        notificationIntent.putExtra("notification", notification);
        notificationIntent.putExtra("date", SDF.format(dateAndTime.getTime()));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(id), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Date dateToNotify = SDF.parse(SDF.format(dateAndTime.getTime()));
        long millis = dateToNotify.getTime();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        Toast.makeText(getApplicationContext(), "Заметка успешно создана!", Toast.LENGTH_SHORT).show();
        title.setText("");
        textnotification.setText("");
        setInitialDateTime();
    }

    public void setDate() {
        new DatePickerDialog(MainActivityLAB6.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime() {
        new TimePickerDialog(MainActivityLAB6.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };
    // установка начальных даты и времени
    private void setInitialDateTime() {
        date.setText(SDF.format(dateAndTime.getTime()));
    }

    public void onClickView(View v) {
        Intent i;
        i = new Intent(this, MainActivityLAB63.class);
        startActivity(i);
    }

}