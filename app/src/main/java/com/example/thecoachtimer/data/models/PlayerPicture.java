package com.example.thecoachtimer.data.models;

import java.io.Serializable;

public class PlayerPicture implements Serializable {

     String large;
     String medium;
     String thumbnail;

    public PlayerPicture(String large, String medium, String thumbnail) {
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    public String getLarge() {
        return large;
    }


    public String getMedium() {
        return medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

}
