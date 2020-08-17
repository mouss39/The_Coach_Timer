package com.example.thecoachtimer.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.thecoachtimer.data.models.Player;
import com.example.thecoachtimer.MainActivity;
import com.example.thecoachtimer.R;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class InputScreen extends AppCompatActivity {
    EditText edit_text_distance;
    Button startSession;
    ImageView imageView_playerImage;
    TextView text_view_playerName;
    Player player;
    String playerFullName,enteredDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //add the arrow back
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        player=new Player();
        //get player intent passed
        player= (Player) getIntent().getSerializableExtra("player");
        //show full name in the Activity
        playerFullName=player.getName().getTitle()+" "+player.getName().getFirst()+" "
                + player.getName().getLast();

        imageView_playerImage= findViewById(R.id.playerImageInput);
        text_view_playerName= findViewById(R.id.playerNameInput);
        edit_text_distance= findViewById(R.id.Et_distance);
        startSession= findViewById(R.id.Btn_start_session);

        //profile pic set and the full name
       Picasso.get().load(player.getPicture().getLarge()).into(imageView_playerImage);
        text_view_playerName.setText(playerFullName);







        //when button Start session is clicked
        startSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredDistance=edit_text_distance.getText().toString();
                //if there is no values entered
                if(enteredDistance.equals("")){
                    Toast.makeText(getBaseContext(), "Please enter a Distance! ",
                            Toast.LENGTH_LONG).show();

                }else if(Double.parseDouble(enteredDistance)<5){ //make sure that the distance is greater than 5 m
                            Toast.makeText(getBaseContext(), "Please enter a Distance more than 5 meters ",
                                    Toast.LENGTH_LONG).show();
                }
                else{
                    // pass values to next activity (player and distance
                Intent intent = new Intent(InputScreen.this, Session.class);
                 intent.putExtra("player", player);
                intent.putExtra("distance",enteredDistance);
                startActivity(intent);
            }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

}


