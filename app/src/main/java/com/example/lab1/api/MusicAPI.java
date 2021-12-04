package com.example.lab1.api;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MusicAPI {
    @POST("api_get_current_song.php")
    Call<MusicRespons> getNameMusic(@Body LoginBody loginBody);
}
