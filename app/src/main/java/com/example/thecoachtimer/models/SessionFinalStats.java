package com.example.thecoachtimer.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Session_table")
public class SessionFinalStats {

    @NonNull
    @PrimaryKey
    private String sessionId;
    private String firstName;
    private String lastName;
    private int numberOfLaps;
    private double averageTimePerLap;
    private double peakSpeed;
    private double avgSpeed;
    private double cadence;
    private String mediumImage;


    public SessionFinalStats(String sessionId,String firstName, String lastName, int numberOfLaps,
                             double averageTimePerLap, double peakSpeed, double avgSpeed, double cadence,String mediumImage) {
        this.sessionId = sessionId;
        this.firstName=firstName;
        this.lastName=lastName;
        this.numberOfLaps = numberOfLaps;
        this.averageTimePerLap = averageTimePerLap;
        this.peakSpeed = peakSpeed;
        this.avgSpeed = avgSpeed;
        this.cadence = cadence;
        this.mediumImage=mediumImage;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public double getAverageTimePerLap() {
        return averageTimePerLap;
    }

    public double getPeakSpeed() {
        return peakSpeed;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public double getCadence() {
        return cadence;
    }

    @Override
    public String toString(){
        return this.getSessionId()+","+this.getFirstName()+","+this.getLastName()+","+this.getNumberOfLaps()+","+
                this.getAverageTimePerLap()+","+this.getPeakSpeed()+","+this.getAvgSpeed()+","+
                this.getCadence()+","+this.getMediumImage();
    }
}
