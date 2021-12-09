package com.example.lab1.lab6;

import android.app.AlarmManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lab1.R;
import com.example.lab1.lab6.AlarmReceiver;
import com.example.lab1.lab6.DBHelper3;

public class MainActivityLAB63 extends AppCompatActivity {

    TextView notificationList;
    DBHelper3 dbHelper;
    SQLiteDatabase db;
//    Cursor notificationCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab63);
        notificationList = findViewById(R.id.listNotifications);
        notificationList.setMovementMethod(new ScrollingMovementMethod());
        dbHelper = new DBHelper3(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(DBHelper3.TABLE_N, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
        do {
            this.notificationList.append( "\nID уведомления: "+ cursor.getInt(0) + "\n" + "Заголовок: " + cursor.getString(1) + "\n" + "Содержание напоминания:" + cursor.getString(2) + "\n" + "Дата и время срабатывания:" + cursor.getString(3)  + "\n" + "------------------------------------------------------------------------------------------------");
        } while (cursor.moveToNext());}
    }

    public void deleteNotification(View view) {
        EditText id = findViewById(R.id.IDforDelete);
        String idForDelete = id.getText().toString();
        if (!idForDelete.equals("".trim())) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(Integer.parseInt(idForDelete));
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    Integer.parseInt(idForDelete),
                    myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            if(database.delete(DBHelper3.TABLE_N, "id = ?", new String[]{idForDelete}) >= 1){
                Toast.makeText(getApplicationContext(), "Уведомление с ID = " + idForDelete + " успешно удалено!", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getApplicationContext(), "Заметки с данным ID не существует!", Toast.LENGTH_SHORT).show();
            dbHelper.close();

        } else {
            Toast.makeText(getApplicationContext(), "Некорректно указан ID!", Toast.LENGTH_SHORT).show();
        }
    }
}