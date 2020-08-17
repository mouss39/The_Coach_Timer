package com.example.thecoachtimer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.thecoachtimer.adapters.LeadBoardAdapter;
import com.example.thecoachtimer.MainActivity;
import com.example.thecoachtimer.models.SessionFinalStats;
import com.example.thecoachtimer.R;
import com.example.thecoachtimer.room.PlayerViewModel;
import com.example.thecoachtimer.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LeaderBoard extends AppCompatActivity {
    private PlayerViewModel playerViewModel;
    Context context;
    TextView numberOfLapsSort,peakSpeedSort;
    RelativeLayout sortRelativeLayout;
    Utils utils;
    LeadBoardAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        context=this;
        final LifecycleOwner lifecycleOwner=this;

        //Recycler View to view all the laps
        RecyclerView recyclerView = findViewById(R.id.recycler_view_leaderBoard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new LeadBoardAdapter();
        recyclerView.setAdapter(adapter);

        sortRelativeLayout=findViewById(R.id.sortLayout);
        numberOfLapsSort=findViewById(R.id.textView_numOfLaps);
        peakSpeedSort=findViewById(R.id.textView_peakSpeedS);

        //Create an instance of the view model which contains all the database methods
        playerViewModel= new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(PlayerViewModel.class);
        //get the list of the session sorted based on the peak speed which can be change when clicked
        //on the sorting option on the top of the screen
        utils=new Utils();



        //show the list sorted according to the peak speed the first time the user enters
        playerViewModel.getAllSessionSpeedSort()
                .observe(lifecycleOwner, new Observer<List<SessionFinalStats>>() {
                    @Override
                    public void onChanged(List<SessionFinalStats> sessionFinalStats) {
                        //set the adapter by the list retrieved from the database
                        adapter.setSessionLaps(sessionFinalStats);
                        if(sessionFinalStats.size()==0){
                            //if the there is no training sessions yet a toast will be shown
                            sortRelativeLayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(), "No Sessions Yet ",
                                    Toast.LENGTH_LONG).show();

                        }else{
                            sortRelativeLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
        //when the sorting based on number of laps is selected
        peakSpeedSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerViewModel.getAllSessionSpeedSort()
                        .observe(lifecycleOwner, new Observer<List<SessionFinalStats>>() {
                            //list sorted based on the number of laps
                            @Override
                            public void onChanged(List<SessionFinalStats> sessionFinalStats) {
                                adapter.setSessionLaps(sessionFinalStats);
                            }
                        });
            }
                                           });
        //sorting based on the peak speed
        numberOfLapsSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerViewModel.getAllSessionNumOFLapsSort()
                        .observe(lifecycleOwner, new Observer<List<SessionFinalStats>>() {
                            @Override
                            public void onChanged(List<SessionFinalStats> sessionFinalStats) {
                                adapter.setSessionLaps(sessionFinalStats);
                            }
                        });
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
        //when the back is pressed got back to the main activity
        startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leader_board, menu);
        //leaderBoard button in the action bar
        menu.findItem(R.id.import_files_csv).setVisible(true);
        menu.findItem(R.id.export_file_csv).setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.import_files_csv) {
            Intent intent = new Intent(LeaderBoard.this, CSVFilesImport.class);
            startActivity(intent);
        }
        if (id == R.id.export_file_csv) {
            //Bonus Feature 3
            //get the list of the sessionFinalStats
            List<SessionFinalStats> sessionFinalStats = adapter.getSessionLaps();
            if(sessionFinalStats.size()!=0) {
                //there are some records
                String filePath = context.getFilesDir().getPath().toString() + "/output"+System.currentTimeMillis();

                //write them to the CSV file
                utils.writeStructureToCsv(sessionFinalStats, filePath);
                Toast.makeText(getBaseContext(), "CSV File Exported Successfully ",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getBaseContext(), "No Records To Export ",
                        Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
