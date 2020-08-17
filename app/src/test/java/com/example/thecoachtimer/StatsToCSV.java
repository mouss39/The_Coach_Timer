package com.example.thecoachtimer;

import com.example.thecoachtimer.models.SessionFinalStats;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatsToCSV {

    @Test
    public void StatsToCsv() {
            /*
            * this is a unit test that changes the stats in the leaderBoard into CSV,
            * it is just an implementation of how I would do it
            * instead of giving it this list I would give it the sorted list
            * */


        String result = "";

        List<SessionFinalStats> sessionFinalStatsList= new ArrayList<>();

        sessionFinalStatsList.add(new SessionFinalStats("sessionID1","first1","last1"
        ,5,6.0,5.5,4.05,0.00,"imageUrl"));
        sessionFinalStatsList.add(new SessionFinalStats("sessionID2","first2","last2"
                ,4,4.0,3.5,5.05,1.00,"imageUrl"));
        sessionFinalStatsList.add(new SessionFinalStats("sessionID3","first3","last3"
                ,6,7.0,5.5,6.05,2.00,"imageUrl"));


        if(sessionFinalStatsList.size()>0){
            StringBuilder sb = new StringBuilder()
                    .append("SessionId,").append("First Name,").append("Last Name,")
                    .append("Number of laps,").append("Average Time Per Lap,").append("Peak Speed")
                    .append("Average Speed").append("Cadence").append("Image Url")
                    .append("\r\n"); // New line

            for(SessionFinalStats sessionFinalStats: sessionFinalStatsList){

                sb.append(sessionFinalStats).append("\r\n");
            }
            result = sb.toString();
        }

        writeToCsv(result, "StatsToCSVOutput");

        readFromCsv("StatsToCSVOutput");
    }

    public void writeToCsv(String body, String outputFileName){
        try {
            File file = new File(outputFileName +".csv");
            // if file does not  exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(body);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromCsv(String inputFileName){
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(inputFileName+".csv"));
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                //here we can insert them to the database
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
