package com.example.thecoachtimer.room;

import android.content.Context;

import com.example.thecoachtimer.models.RoomPlayer;
import com.example.thecoachtimer.models.SessionFinalStats;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RoomPlayer.class, SessionFinalStats.class}, version = 9, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {
//database creation based on the entities specified

    private static PlayerDatabase instance;
    public abstract PlayerDao playerDoa();
    public abstract SessionStatsDao sessionStatsDao();


    //only one thread can create this instance
    public static synchronized PlayerDatabase getInstance(Context context){

        if(instance==null){

            instance= Room.databaseBuilder(context.getApplicationContext(),
                    PlayerDatabase.class, "player_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }



}
