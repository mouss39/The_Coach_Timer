package com.example.thecoachtimer.data.rest;


import com.example.thecoachtimer.data.models.PlayerResponse;
import com.example.thecoachtimer.models.SessionFinalStats;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface JsonPlaceHolderAPI {
    //retrofit api requests
    @GET("api")
    Call<PlayerResponse> getPlayers(
            @Query("seed") String seed,
            @Query("inc") String inc,
            @Query("gender") String gender,
            @Query("results") Integer results,
            @Query("noinfo") String noinfo);

    @POST("trainings")
    Call<SessionFinalStats> postSession(@Body SessionFinalStats sessionFinalStats);



}
