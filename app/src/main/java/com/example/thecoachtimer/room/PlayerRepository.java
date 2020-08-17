package com.example.thecoachtimer.room;

import android.app.Application;
import android.os.AsyncTask;

import com.example.thecoachtimer.models.RoomPlayer;
import com.example.thecoachtimer.models.SessionFinalStats;
import com.example.thecoachtimer.models.SessionStats;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PlayerRepository {

//the connection between the database functions and view models
    private PlayerDao playerDao;
    private SessionStatsDao sessionStatsDao;


    public PlayerRepository(Application application) {
        PlayerDatabase database = PlayerDatabase.getInstance(application);
        playerDao = database.playerDoa();
        sessionStatsDao =database.sessionStatsDao();

    }
    //SessionStatsDoa

    public void insertSession(SessionFinalStats sessionFinalStats){
        new InsertSessionStatsAsyncTask(sessionStatsDao).execute(sessionFinalStats);
    }
    public LiveData<List<SessionFinalStats>> getAllSessionSpeedSort(){
        return sessionStatsDao.getAllSessionSpeedSort();
    }
    public LiveData<List<SessionFinalStats>> getAllSessionNumOFLapsSort() {
        return sessionStatsDao.getAllSessionNumOFLapsSort();
    }

    //PlayerDoa
    //insert delete update for player
    public void insert(RoomPlayer player){
        new InsertPlayerAsyncTask(playerDao).execute(player);
    }
    public void update(RoomPlayer player){ new UpdatePlayerAsyncTask(playerDao).execute(player); }
    public void delete(RoomPlayer player){ new DeletePlayerAsyncTask(playerDao).execute(player); }

    //get lap times to do Time Variance
    public List<Long> getAllLapTimes(String sessionId) {
        return playerDao.getAllLapTimes(sessionId);
    }
    //get the session laps to show them in session activity
    public LiveData<List<RoomPlayer>> getAllSessionLaps(String sessionId) {
        return playerDao.getAllSessionLaps(sessionId);
    }
    //get stats
    public SessionStats getSessionStats(String sessionId){
        return playerDao.getSessionStats(sessionId);
    }

    //AsyncTask function in order to do there work in the background without blocking the main Thread

    //insert Player
     private static class InsertPlayerAsyncTask  extends AsyncTask<RoomPlayer, Void, Void> {
        private PlayerDao playerDao;
        public InsertPlayerAsyncTask(PlayerDao playerDao) {
            this.playerDao=playerDao;
        }

        @Override
        protected Void doInBackground(RoomPlayer... roomPlayers) {
            playerDao.insert(roomPlayers[0]);
            return null;
        }
    }
    //update player
    private static class UpdatePlayerAsyncTask  extends AsyncTask<RoomPlayer, Void, Void> {
        private PlayerDao playerDao;
        public UpdatePlayerAsyncTask(PlayerDao playerDao) {
            this.playerDao=playerDao;
        }

        @Override
        protected Void doInBackground(RoomPlayer... roomPlayers) {
            playerDao.update(roomPlayers[0]);
            return null;
        }
    }
    //Delete Player
    private static class DeletePlayerAsyncTask  extends AsyncTask<RoomPlayer, Void, Void> {
        private PlayerDao playerDao;
        public DeletePlayerAsyncTask(PlayerDao playerDao) {
            this.playerDao=playerDao;
        }

        @Override
        protected Void doInBackground(RoomPlayer... roomPlayers) {
            playerDao.delete(roomPlayers[0]);
            return null;
        }
    }

    //insert Session stats at the end of each session
    private static class InsertSessionStatsAsyncTask extends AsyncTask<SessionFinalStats, Void, Void> {
        private SessionStatsDao sessionStatsDao;
        public InsertSessionStatsAsyncTask(SessionStatsDao sessionStatsDao) {
            this.sessionStatsDao=sessionStatsDao;
        }
        @Override
        protected Void doInBackground(SessionFinalStats... sessionFinalStats) {
            sessionStatsDao.insertSession(sessionFinalStats[0]);
            return null;
        }
    }
}
