package com.example.lab1.lab5;

import android.app.Dialog;
import android.content.*;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.example.lab1.R;
import com.google.common.io.Files;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.File;
import java.io.IOException;


public class Lab5Activity extends AppCompatActivity  {
    public static final String EXTRA_MESSAGE = "com.example.fifthapp.MESSAGE";
    //    public File filesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
    public String fileName;
    private Button button, button2, button3;
    private EditText idJournal;
    private TextView status;
    private String pAth = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/Journals/";
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab5);

        dialog = new Dialog(this, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.poupwindow);
        dialog.show();

        if (getDialogStatus()) {
            dialog.hide();
        } else {
            dialog.show();
        }


//        final CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox2);
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        final SharedPreferences.Editor editor = preferences.edit();
//        if(preferences.contains("checked") && preferences.getBoolean("checked",false) == true) {
//            checkBox.setChecked(true);
//        }else {
//            checkBox.setChecked(false);
//        }
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(checkBox.isChecked()) {
//                    editor.putBoolean("checked", true);
//                    editor.apply();
//                }else{
//                    editor.putBoolean("checked", false);
//                    editor.apply();
//                }
//            }
//        });


        File filesDir = new File(pAth);
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }

        status = findViewById(R.id.statusS);
        status.setTextColor(Color.YELLOW);
        idJournal = findViewById(R.id.idJournal);
        button = findViewById(R.id.btnDownload);
        button.setBackgroundColor(Color.GREEN);
        button2 = findViewById(R.id.btnReadJ);
        button3 = findViewById(R.id.btnDell);
        disableBtn();
//        button2.setOnClickListener(this);
        idJournal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                File file = new File(pAth  + idJournal.getText().toString() + ".pdf");
                if (file.exists()) {
                    enableBtn();
                } else {
                    disableBtn();
                }
            }
        });



//        button.setOnClickListener(this);

//        File dir_ = new File(this.getExternalFilesDir("FolderName"),"YOUR_DIR");
    }


//    PopupWindow popupWindow;
//    private void initPopupWindow(View view) {
//        if(popupWindow == null){
//            View popupView = LayoutInflater.from(Lab5Activity.this).inflate(R.layout.item_popip,null);
//            // ???????????? ???? ????????????????
//            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
//        }
//
//        // ???????????? ?????????? ???????????????? ???????????????????? ?????????????????????? ????????
//        popupWindow.showAsDropDown(view);
//    }

    public void disableBtn(){
        button3.setClickable(false);
        button2.setClickable(false);
        button3.setBackgroundColor(Color.RED);
        button2.setBackgroundColor(Color.RED);
    }
    public void enableBtn(){
        button3.setClickable(true);
        button2.setClickable(true);
        button3.setBackgroundColor(Color.GREEN);
        button2.setBackgroundColor(Color.GREEN);
    }

    public void dellPDF(View v) throws IOException {

        File file = new File(pAth  + idJournal.getText() + ".pdf");
        file.delete();
        disableBtn();
        Toast toast = Toast.makeText(getApplicationContext(),
                "???????? ?????????????? ????????????!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void getPDF(View v) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://ntv.ifmo.ru/file/journal/").
                build();

        RetrofitDownload retrofitDownload = retrofit.create(RetrofitDownload.class);
        fileName = idJournal.getText() + ".pdf";
        Call<ResponseBody> call = retrofitDownload.downloadRetrofit(fileName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if( response.code() == 404){
                        Toast.makeText(getApplicationContext(), "???????? ?? ?????????? id ???? ????????????!", Toast.LENGTH_SHORT).show();
                    } else {
                        button.setClickable(false);
                        Toast.makeText(getApplicationContext(),
                                "???????????????? ???????????????? ??????????, ???????????????????? ????????????????!", Toast.LENGTH_SHORT).show();
                        File file = new File(pAth, fileName);
                        file.createNewFile();
                        Files.asByteSink(file).write(response.body().bytes());
                        Toast.makeText(getApplicationContext(),
                                "???????? ?????????????? ???????????????? ?? ??????????" + pAth +"!", Toast.LENGTH_SHORT).show();
                        enableBtn();
//                        remoteViews.setTextViewText(R.id.btnDownload, "??????????????");

                        Log.e("TAG", "File Path= " + file.toString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR ETHERNET SOS!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeDialogStatus(boolean isChecked) {
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }

    private boolean getDialogStatus() {
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }

    public void closeDialog(View view) {
        dialog.dismiss();
    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            storeDialogStatus(true);
        } else {
            storeDialogStatus(false);
        }
    }

    public void readPDF(View v) {
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());

        File file = new File(pAth + idJournal.getText().toString() + ".pdf");
        Uri uri = FileProvider.getUriForFile(this,
                "com.example.lab1.fileprovider", file);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(uri, "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent intent = Intent.createChooser(target, "Open PDF using");
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No Applications found to open pdf", Toast.LENGTH_SHORT).show();
        }
    }
}
