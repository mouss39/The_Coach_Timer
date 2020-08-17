package com.example.thecoachtimer.models;



public class SessionStats {

    public long totalTime;
    public long avgTime;
    public double avgSpeed;
    public double peakSpeed;

    public SessionStats(long totalTime, long avgTime, double avgSpeed,double peakSpeed) {
        this.totalTime = totalTime;
        this.avgTime = avgTime;
        this.avgSpeed=avgSpeed;
        this.peakSpeed=peakSpeed;
    }



    public long getAvgTime() {
        return avgTime;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public long getTotalTime() { return totalTime; }

    public double getPeakSpeed() { return peakSpeed; }
}
