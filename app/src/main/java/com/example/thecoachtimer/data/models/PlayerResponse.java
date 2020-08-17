package com.example.thecoachtimer.data.models;

import java.util.List;

public class PlayerResponse {



    List<Player> results;



    public PlayerResponse(List<Player> results) {
        this.results = results;
    }

    public List<Player> getResults() {
        return results;
    }

    public void setResults(List<Player> results) {
        this.results = results;
    }
}
