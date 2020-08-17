package com.example.thecoachtimer;

import com.example.thecoachtimer.data.models.PlayerResponse;
import com.example.thecoachtimer.data.rest.JsonPlaceHolderAPI;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerApiTest {
    final String BASE_URL = "https://randomuser.me/";
    @Test
    public void PlayerApiTestMethod() {



        //building retrofit with Gson Converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        retrofit2.Call<PlayerResponse> callAPI = jsonPlaceHolderApi.getPlayers("empatica",
                "name,picture", "female", 10, "");

        //used latch because the main thread terminates before the response is retrieved
        final CountDownLatch latch = new CountDownLatch(1);

        callAPI.enqueue(new Callback<PlayerResponse>() {

            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
                PlayerResponse playerResponse=response.body();
                System.out.println("success");
                if(playerResponse.getResults().size()>0){
                    System.out.println("API Successful data get");

                }

            }

            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
                System.out.println("failed to get the data");
            }
        });
        try {
            latch.await(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
