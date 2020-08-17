package com.example.thecoachtimer.room;

import android.app.Application;

import com.example.thecoachtimer.models.RoomPlayer;
import com.example.thecoachtimer.models.SessionFinalStats;
import com.example.thecoachtimer.models.SessionStats;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository repository;


    public PlayerViewModel(@NonNull Application application) {
        super(application);

        repository=new PlayerRepository(application);
    }

    //player session laps
    public void insert(RoomPlayer player) {
        repository.insert(player);
    }
    public void update(RoomPlayer player) {
        repository.update(player);
    }
    public void delete(RoomPlayer player) {
        repository.delete(player);
    }

    //get all lap times to calculate time variance
    public List<Long> getAllLapTimes(String sessionId){
        return repository.getAllLapTimes(sessionId);
    }
    //get all sessions lap to view them in Session activity
    public LiveData<List<RoomPlayer>> getAllSessionLaps ( String sessionId){
        return repository.getAllSessionLaps(sessionId);
    }

    //get session stats calculated
    public SessionStats getSessionStats(String sessionId){
        return repository.getSessionStats(sessionId);
    }


    //insert session data based on SessionStatsDao
    public void insertSession(SessionFinalStats sessionFinalStats){
        repository.insertSession(sessionFinalStats);
    }
    //sort the sessions based on Speed
    public LiveData<List<SessionFinalStats>> getAllSessionSpeedSort() {
        return repository.getAllSessionSpeedSort();
    }
    //sort the sessions based on number of Laps
    public LiveData<List<SessionFinalStats>> getAllSessionNumOFLapsSort() {
        return repository.getAllSessionNumOFLapsSort();
    }

}
