package com.example.lab1.lab5;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitDownload {
    @GET("{fileName}")
    Call<ResponseBody> downloadRetrofit(@Path("fileName") String fileName);
}
