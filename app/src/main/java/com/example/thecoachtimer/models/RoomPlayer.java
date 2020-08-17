package com.example.thecoachtimer.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_table")
public class RoomPlayer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String firstName;
    private String lastName;
    private String lapTime;
    private double distance;
    private double speed;
    private String sessionId;
    private long msTimerValue;

    public RoomPlayer(String firstName, String lastName, String lapTime, double distance,double speed,String sessionId, long msTimerValue) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.lapTime = lapTime;
        this.distance = distance;
        this.speed=speed;
        this.sessionId=sessionId;
        this.msTimerValue=msTimerValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLapTime() {
        return lapTime;
    }

    public double getSpeed() { return speed; }

    public double getDistance() { return distance; }

    public String getSessionId() { return sessionId; }

    public long getMsTimerValue() {
        return msTimerValue;
    }
}
