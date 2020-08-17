package com.example.thecoachtimer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.thecoachtimer.adapters.LeadBoardAdapter;
import com.example.thecoachtimer.models.SessionFinalStats;
import com.example.thecoachtimer.R;
import com.example.thecoachtimer.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
* In this class I got all the exported CSV files and list there contents
* with the help of a recyclerView
* and the readFile function in the Utils
* Bonus Features 4
* */
public class CSVFilesImport extends AppCompatActivity {
    Utils utils;
    Context context;
    List<SessionFinalStats> sessionFinalStatsList,returnedStatsList;
    LeadBoardAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_s_v_files_import);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        utils=new Utils();
        context=this;

        //set up the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view_CSVImport);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //we are going to use the same adapter as the LeaderBoard since it have the same values
        adapter = new LeadBoardAdapter();
        recyclerView.setAdapter(adapter);
        //List to save all retrieved values
        sessionFinalStatsList= new ArrayList<>();
        //path of the files
        String filePath = context.getFilesDir().getPath().toString();
        File input = new File(filePath);
        //list of files
        List<File> filesList =  utils.listFilesForFolder(input);
        // TODO: intent to list view activity and use filesList
        for (File file: filesList){
            //for each file
            //read the contents of the file and add them to the list
            returnedStatsList= utils.readFromCsv(file.getAbsolutePath());
            sessionFinalStatsList.addAll( returnedStatsList);
        }
        //show the list in the recycler view with the help of the already configured adapter
        adapter.setSessionLaps(sessionFinalStatsList);
        if (sessionFinalStatsList.size()==0){
            Toast.makeText(getBaseContext(), "No Exported CSV Files",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        //when the back is pressed got back to the main activity
        startActivity(new Intent(context, LeaderBoard.class));
    }

}