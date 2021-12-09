package com.example.lab1.lab3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab1.R;

import java.time.LocalDateTime;

public class ActivityLab3_2 extends AppCompatActivity {

    private TextView listSt;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab32);
        dbHelper = new DBHelper(this);

        listSt = findViewById(R.id.listSt);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_S, null, null, null, null, null, null);

        if (DBHelper.DATABASE_VERSION == 2) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                do {
                    this.listSt.append(cursor.getInt(idIndex) + " " + cursor.getString(nameIndex) + " " + cursor.getString(dateIndex) + "\n" + "\n");
                } while (cursor.moveToNext());
            } else
                Log.i("mLog", "0 rows");

        } else if (DBHelper.DATABASE_VERSION == 3) {
//            dbHelper.onUpgrade(database, 2, 3);
            String[] position_F = { "Филатов", "Борисов",
                    "Реснянский", "Морковкина", "Бухтояров" };
            String[] position_I = { "Михаил", "Максим",
                    "Константин", "Светлана", "Михаил" };
            String[] position_O = { "Александрович", "Александрович",
                    "Сергеевич", "Сергеевна", "Максимович" };

            ContentValues cv = new ContentValues();
            for (int i = 0; i < position_F.length; i++) {
                cv.clear();
                cv.put(dbHelper.KEY_F, position_F[i]);
                cv.put(dbHelper.KEY_I, position_I[i]);
                cv.put(dbHelper.KEY_O, position_O[i]);
                cv.put(dbHelper.KEY_DATE, LocalDateTime.now().toString());
                database.insert(DBHelper.TABLE_S, null,  cv);
            }
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int fIndex = cursor.getColumnIndex(DBHelper.KEY_F);
                int iIndex = cursor.getColumnIndex(DBHelper.KEY_I);
                int oIndex = cursor.getColumnIndex(DBHelper.KEY_O);
                int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                do {
                    this.listSt.append(cursor.getInt(idIndex) + " " + cursor.getString(fIndex) + " " + cursor.getString(iIndex) + " " + cursor.getString(oIndex) + " " + cursor.getString(dateIndex) + "\n" + "\n");
                } while (cursor.moveToNext());
            } else
                Log.d("mLog", "0 rows");
        }
        cursor.close();
        dbHelper.close();
    }

}