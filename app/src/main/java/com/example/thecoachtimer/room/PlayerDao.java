package com.example.thecoachtimer.room;

import com.example.thecoachtimer.models.RoomPlayer;
import com.example.thecoachtimer.models.SessionStats;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlayerDao {


    //enter a new value
    @Insert
    void insert(RoomPlayer player);

    @Update
    void update(RoomPlayer player);

    @Delete
    void delete(RoomPlayer player);

    //to all lap times to show graph time variance
    @Query("SELECT msTimerValue FROM player_table Where sessionId= :sessionId ORDER BY id ASC")
        List<Long> getAllLapTimes(String sessionId);
    // get all laps based on session id to show them in the Session Activity
    @Query("SELECT * FROM player_table Where sessionId= :sessionId ORDER BY id DESC")
    LiveData<List<RoomPlayer>> getAllSessionLaps(String sessionId);

    //stats calculation average time, total Time, peak Speed, and average speed
    @Query("SELECT AVG(msTimerValue) as  avgTime , SUM(msTimerValue)as totalTime, AVG(speed) as avgSpeed," +
            " MAX(speed) as peakSpeed FROM player_table WHERE sessionId= :sessionId ")
    SessionStats getSessionStats(String sessionId);

}
