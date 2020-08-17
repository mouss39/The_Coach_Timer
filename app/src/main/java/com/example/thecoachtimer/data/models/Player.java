package com.example.thecoachtimer.data.models;

import java.io.Serializable;

public class Player implements Serializable {


PlayerName  name;
PlayerPicture picture;

    public Player() {
    }

    public Player(PlayerName name, PlayerPicture picture) {
        this.name = name;
        this.picture = picture;
    }

    public PlayerName getName() {
        return name;
    }

    public PlayerPicture getPicture() {
        return picture;
    }


}
