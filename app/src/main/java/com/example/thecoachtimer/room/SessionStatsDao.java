package com.example.thecoachtimer.room;


import com.example.thecoachtimer.models.SessionFinalStats;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SessionStatsDao {
    //insert Session
    @Insert
    void insertSession(SessionFinalStats sessionFinalStats);
    //get the sessions sorted based on PeakSpeed
    @Query("Select * from session_table order by peakSpeed desc")
    LiveData<List<SessionFinalStats>> getAllSessionSpeedSort();
    //get the sessions sorted based on number of Laps
    @Query("Select * from session_table order by numberOfLaps desc")
    LiveData<List<SessionFinalStats>> getAllSessionNumOFLapsSort();


}
