package com.example.thecoachtimer.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecoachtimer.data.models.Player;
import com.example.thecoachtimer.MainActivity;
import com.example.thecoachtimer.adapters.LapAdapter;
import com.example.thecoachtimer.data.rest.JsonPlaceHolderAPI;
import com.example.thecoachtimer.models.SessionFinalStats;
import com.example.thecoachtimer.R;
import com.example.thecoachtimer.room.PlayerViewModel;
import com.example.thecoachtimer.models.RoomPlayer;
import com.example.thecoachtimer.models.SessionStats;
import com.example.thecoachtimer.Utils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Session extends AppCompatActivity {
    Context context;

    Player player;
    SessionStats sessionStats;
    Chronometer chronometer;
    GraphView graph;

    ImageView playerImage;
    TextView tv_playerName,tv_distanceValue,Tv_numberOfLaps,Tv_averageTimeLap;
    TextView Tv_peakSpeed,Tv_averageSpeed,Tv_cadence;
    Button btn_lapStart,btn_LapEnd,btn_endSession;


    String lapDistance,fullName,timerValue,firstName,lastName;
    @SuppressLint("SimpleDateFormat")
    String   sessionId = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    double distance,speed, avgGraphTime,avgSpeed,avgTime,peakSpeed;
    long candence,previousTimerValue=0;
    int numberOfLaps=0;
    int counter;
    List<Long> listOfAllLapTimes;
    final Utils util=new Utils();

    //room
    private PlayerViewModel playerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context=this;

        //Recycler View to view all the laps
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final LapAdapter adapter = new LapAdapter();
        recyclerView.setAdapter(adapter);



        player=new Player();


        //get values passed by the intent (selected player and distance
        player= (Player) getIntent().getSerializableExtra("player");
        lapDistance=getIntent().getStringExtra("distance");
        firstName=player.getName().getFirst();
        lastName=player.getName().getLast();
        //create the player view model which contains the main database functions
        playerViewModel= new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(PlayerViewModel.class);

        //initialization of layout fields
        playerImage=findViewById(R.id.playerImageSession);
        tv_playerName= findViewById(R.id.playerNameSession);
        tv_distanceValue= findViewById(R.id.distance_value);
        btn_lapStart= findViewById(R.id.Btn_start_lap);
        btn_LapEnd= findViewById(R.id.Btn_end_lap);
        btn_endSession= findViewById(R.id.Btn_end_session);
        chronometer= findViewById(R.id.chronometer);

        //stats textViews
        Tv_numberOfLaps=findViewById(R.id.text_view_lapNumber);
        Tv_averageTimeLap=findViewById(R.id.text_view_avgTimeLap);
        Tv_averageSpeed=findViewById(R.id.text_view_avgSpeed);
        Tv_peakSpeed=findViewById(R.id.text_view_peakSpeed);
        Tv_cadence=findViewById(R.id.text_view_cadence);


        //setting the profile pic and name
        fullName=player.getName().getTitle()+" "+firstName+" "+lastName;
        Picasso.get().load(player.getPicture().getLarge()).into(playerImage);
        tv_playerName.setText(fullName);

        //distance and timer
        tv_distanceValue.setText(lapDistance+" Meters");
        distance=Double.parseDouble(lapDistance);
        btn_LapEnd.setEnabled(false);
        btn_endSession.setEnabled(false);

        graph =  findViewById(R.id.graph);

//*************************************************************************************************

        //Button onCLicks
        //lap start button
        btn_lapStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pauseOffset is to remove the paused time between 2 different laps
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                //disable the start lap button in order to make sure the user does not
                //click it again for better results
                //and make sure the end lap button is activated
                btn_lapStart.setEnabled(false);
                btn_LapEnd.setEnabled(true);
                btn_endSession.setEnabled(true);

            }
        });

        btn_LapEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avgGraphTime=0;
                //to manage the buttons so the user have to stick to how the timer works



                //get the timer value which is the time elapsed in milli seconds
                long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                if((elapsedMillis-previousTimerValue)<999){//if it is less than a second it will not work
                    Toast.makeText(getBaseContext(), "Lap should at least take a second, Sorry, But you are not flash ;) ",
                            Toast.LENGTH_LONG).show();
                }
                else {//less than a second will be ignored because speed cannot be calculated
                    //timer value in minute:second:millisecond
                    timerValue = util.getTimerValue(elapsedMillis-previousTimerValue);

                    speed = distance /( (elapsedMillis-previousTimerValue)/ 1000);//speed m/s
                    speed=Math.round(speed * 100.0) / 100.0;//round it into 2 decimal values

                    //insert it to the database
                    RoomPlayer roomPlayer = new RoomPlayer(firstName, lastName, timerValue,
                            distance,speed,sessionId,(elapsedMillis-previousTimerValue));

                    playerViewModel.insert(roomPlayer);
                    //reset the timer

                    previousTimerValue=elapsedMillis;
                    getSessionStats();//for calculating the statics after every lap
                    getAllLapTimes();//for the graph to show the time variance

                }

            }
        });
        btn_endSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                btn_lapStart.setEnabled(false);
                btn_LapEnd.setEnabled(false);
                btn_endSession.setEnabled(true);
            }
        });

//*****************************************************1********************************************
        //recyclerView get all session laps by setting a observer
        //every time a new value is added it will start and get the value
        playerViewModel.getAllSessionLaps(sessionId)
                .observe(this, new Observer<List<RoomPlayer>>() {
            @Override
            public void onChanged(List<RoomPlayer> roomPlayers) {
                adapter.setSessionLaps(roomPlayers);

                numberOfLaps=roomPlayers.size();// number of laps in the ongoing  session


                //SET STATS
                if( numberOfLaps>0) {
                    String numberOfLapsString=Integer.toString(numberOfLaps);
                    Tv_numberOfLaps.setText(numberOfLapsString);
                    //round the value of the average speed and avg time per lap
                    avgSpeed=Math.round((sessionStats.avgSpeed) * 100.0) / 100.0;
                    avgTime =Math.round((sessionStats.avgTime/1000) * 100.0) / 100.0;
                    peakSpeed=sessionStats.getPeakSpeed();
                    String peakSpeedString=Double.toString(peakSpeed);
                    String averageTimeString=avgTime+" s";
                    String averageSpeedString=Double.toString(avgSpeed);
                    Tv_averageSpeed.setText(averageSpeedString);
                    Tv_averageTimeLap.setText(averageTimeString);
                    Tv_peakSpeed.setText(peakSpeedString);

                    long minutesPassed=sessionStats.getTotalTime()/60000;//get the minutes
                    if(minutesPassed>=1) {//at least 1 min passed
                        candence=numberOfLaps/minutesPassed;
                        String candenceString=Long.toString(candence);
                        Tv_cadence.setText(candenceString);
                    }


                    DataPoint[] timeVarianceDataPoint = new DataPoint[getAllLapTimes().size()];
                    DataPoint[] averageDataPoint = new DataPoint[getAllLapTimes().size()];
                    counter=0;//reset the counter

                    avgGraphTime=Math.round((sessionStats.avgTime/1000) * 100.0) / 100.0;

                    for(Long lapTime: getAllLapTimes()){
                        lapTime=lapTime/1000;//seconds
                        //set a data point for every lap time
                        timeVarianceDataPoint[counter]=new DataPoint(counter, lapTime);//(x,y)
                        //set the average time
                        averageDataPoint[counter]=new DataPoint(counter,avgGraphTime);
                        counter++;

                    }
                    graph.removeAllSeries();//remove all the series already existed
                    //graph set up with the entered values
                    LineGraphSeries<DataPoint> timeVarianceSeries = new LineGraphSeries<>(timeVarianceDataPoint);
                    LineGraphSeries<DataPoint> avgSeries = new LineGraphSeries<>(averageDataPoint);
                    timeVarianceSeries.setColor(getResources().getColor(R.color.color_btn));

                    graph.addSeries(timeVarianceSeries);
                    graph.addSeries(avgSeries);
                    graph.setTitle("Time variance of lap times ");
                }


            }
        });

    }

    //*************************************************************************************************
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        quit();
    }
    //*************************************************************************************************
    //Methods

    //todo thread sync
    public SessionStats getSessionStats() {
        //thread to get the stats so it does not work on the main thread
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //get the session stats
                sessionStats = playerViewModel.getSessionStats(sessionId);

            }
        });
        return sessionStats;
    }
    public List<Long> getAllLapTimes() {
        //thread to get the lap times
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                listOfAllLapTimes = playerViewModel.getAllLapTimes(sessionId);

            }
        });
        return listOfAllLapTimes;
    }
        public void quit(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_alert_dialog_quit);
        TextView dialogButtonNo =  dialog.findViewById(R.id.noEndSession);
        TextView dialogButtonYes = dialog.findViewById(R.id.yesEndSession);
        // if button is clicked, close the custom dialog
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(peakSpeed>0) {//to make sure at least the player did one lap
                    SessionFinalStats sessionFinalStats = new SessionFinalStats(
                            sessionId,firstName,lastName, numberOfLaps, avgTime,
                            peakSpeed, avgSpeed, candence, player.getPicture().getLarge());
                    playerViewModel.insertSession(sessionFinalStats);

                    //post to API Bonus features 5
                    util.postJsonData(sessionFinalStats);
                }
                startActivity(new Intent(context, MainActivity.class));
            }
        });
        dialog.show();
    }


}
