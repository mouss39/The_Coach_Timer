package com.example.thecoachtimer;


import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.example.thecoachtimer.activities.InputScreen;
import com.example.thecoachtimer.activities.LeaderBoard;
import com.example.thecoachtimer.adapters.PlayerListAdapter;
import com.example.thecoachtimer.data.models.Player;
import com.example.thecoachtimer.data.models.PlayerResponse;
import com.example.thecoachtimer.data.rest.JsonPlaceHolderAPI;


public class MainActivity extends AppCompatActivity {

private PlayerListAdapter.RecyclerViewClickListener listener;
    List<Player> listOfPlayers;

    private static final String BASE_URL = "https://randomuser.me/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_player_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final PlayerListAdapter adapter = new PlayerListAdapter();
        recyclerView.setAdapter(adapter);

        //building retrofit with Gson Converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating retrofit with json place holder Interface that contains api requests
        JsonPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        //call the API to get the players based on the Get request specified in jsonPlaceHolder interface
        retrofit2.Call<PlayerResponse> callAPI= jsonPlaceHolderApi.getPlayers("empatica",
                "name,picture", "female", 20, "");

        //callback to get the response
        callAPI.enqueue(new retrofit2.Callback<PlayerResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PlayerResponse> call, retrofit2.Response<PlayerResponse> response) {
                PlayerResponse playerResponse=response.body();
                 listOfPlayers= Objects.requireNonNull(playerResponse).getResults();
                adapter.updatePlayers(listOfPlayers,listener);

            }


            @Override
            public void onFailure(retrofit2.Call<PlayerResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to get the List of Players ",
                        Toast.LENGTH_LONG).show();
            }
        });

        // listener to get the selected item
        listener= new PlayerListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, InputScreen.class);
                //pass the selected player to the next activity
                Player selectedPlayer=listOfPlayers.get(position);
                 intent.putExtra("player", selectedPlayer);
                startActivity(intent);
            }
        };

    }


    @Override
    public void onBackPressed() {

        //to make sure that the when the back button is pressed it closes the app
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //leaderBoard button in the action bar
        menu.findItem(R.id.leaderBoard_btn).setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.leaderBoard_btn) {
            Intent intent = new Intent(MainActivity.this, LeaderBoard.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
