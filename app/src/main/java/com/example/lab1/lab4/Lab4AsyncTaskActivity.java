package com.example.lab1.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.lab1.api.MusicAPI;
import com.example.lab1.R;
import io.reactivex.Observer;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;


public class Lab4AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "https://media.itmo.ru/";
    private static final String login = "4707login";
    private static final String password = "4707pass";
    private TextView textView, textView2, textView3;
    private Button button;
    private Observer<String> observerString;

    DBHelper2 dbHelper2;


    public  Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4_async_task);
        dbHelper2 = new DBHelper2(this);
        button = findViewById(R.id.lolo);
        button.setOnClickListener(this);
        textView = findViewById(R.id.textView5);
        textView2 = findViewById(R.id.status);
        textView3 = findViewById(R.id.allMusics);
        textView3.setMovementMethod(new ScrollingMovementMethod());

        showAll();
        if (hasConnection(this) == false) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Проверьте подключение к интернету, приложение работает в офлайн режиме!", Toast.LENGTH_SHORT);
            toast.show();
            textView2.setText("Офлайн");
            textView2.setTextColor(Color.RED);
        } else {
            textView2.setText("Онлайн");
            textView2.setTextColor(Color.GREEN);

        }


        TimerTask timerTask = new TimerTask() {
            @Override

            public void run(){
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .client(okHttpClient)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MusicAPI service = retrofit.create(MusicAPI.class);

                Call<MusicRespons> call=service.getNameMusic(new LoginBody(login,password));
                call.enqueue(new Callback<MusicRespons>() {
                    @Override
                    public void onResponse(Call<MusicRespons> call, Response<MusicRespons> response) {
                        textView2.setText("Онлайн");
                        textView2.setTextColor(Color.GREEN);
                        textView.setText(response.body().info);
                        System.out.println("+");
                        String[] strings = response.body().info.split(" - ");
                        if(!lastMusic().equals(strings[1])){
                            addToDB(strings[0], strings[1]);
                            showAll();
                        }
                    }

                    @Override
                    public void onFailure(Call<MusicRespons> call, Throwable t) {
                        textView2.setText("Офлайн");
                        textView2.setTextColor(Color.RED);
                        textView.setText("Офлайн режим работы, функция недоступна!");
                    }
                });
            }

        };
        timer.schedule(timerTask, 0, 20000);

    }

    public void showAll(){
        SQLiteDatabase database = dbHelper2.getWritableDatabase();
        Cursor cursor = database.query(DBHelper2.TABLE_M, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            textView3.setText("");
            int idIndex = cursor.getColumnIndex(DBHelper2.KEY_ID);
            int aIndex = cursor.getColumnIndex(DBHelper2.KEY_AUTHOR);
            int mIndex = cursor.getColumnIndex(DBHelper2.KEY_MUSIC);
            int dateIndex = cursor.getColumnIndex(DBHelper2.KEY_DATE);
            do {
                this.textView3.append(cursor.getInt(idIndex) + " " + cursor.getString(aIndex) + " " + cursor.getString(mIndex) + " "  + cursor.getString(dateIndex) + "\n" + "\n");
            } while (cursor.moveToNext());
        }else textView3.setText("Сохраненые записи не обнаруженны!");

    }

    public String lastMusic() {
        SQLiteDatabase database = dbHelper2.getWritableDatabase();
        Cursor cursor = database.query(DBHelper2.TABLE_M, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.moveToLast();
            int index = cursor.getColumnIndex(DBHelper2.KEY_MUSIC);

            dbHelper2.close();
            return cursor.getString(index);
        }
        else return "null";
    }

    public void addToDB(String nameAuthor, String musicName){
        LocalDateTime date = LocalDateTime.now();

        SQLiteDatabase database = dbHelper2.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper2.KEY_AUTHOR, nameAuthor);
        contentValues.put(DBHelper2.KEY_MUSIC, musicName);
        contentValues.put(DBHelper2.KEY_DATE, String.valueOf(date));

        database.insert(DBHelper2.TABLE_M, null, contentValues);
        dbHelper2.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase database = dbHelper2.getWritableDatabase();

        database.delete(DBHelper2.TABLE_M, null, null);
        showAll();
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

}





