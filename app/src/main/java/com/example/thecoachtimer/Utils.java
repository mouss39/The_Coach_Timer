package com.example.thecoachtimer;


import com.example.thecoachtimer.data.rest.JsonPlaceHolderAPI;
import com.example.thecoachtimer.models.SessionFinalStats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {


    //change the time from the timer into the format mm:ss:ms
    public String getTimerValue(long elapsedMillis) {
        long mm = TimeUnit.MILLISECONDS.toMinutes(elapsedMillis);
        long minutesMillis = TimeUnit.MINUTES.toMillis(mm);
        elapsedMillis -= minutesMillis;
        long ss = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis);
        long secondsMillis = TimeUnit.SECONDS.toMillis(ss);
        elapsedMillis -= secondsMillis;
        return   mm + ":" + ss + ":" + elapsedMillis;
    }



    //to read and write to CSV
    public void writeStructureToCsv(List<SessionFinalStats> sessionFinalStatsList, String outputFileName){
        try {
            String result="";
            if(sessionFinalStatsList.size()>0) {
                //identify the table titles
                StringBuilder stringBuilder = new StringBuilder()
                        .append("SessionId,").append("First Name,").append("Last Name,")
                        .append("Number of laps,").append("Average Time Per Lap,").append("Peak Speed")
                        .append("Average Speed").append("Cadence").append("Image Url")
                        .append("\r\n"); // New line

                for (SessionFinalStats sessionFinalStats : sessionFinalStatsList) {

                    stringBuilder.append(sessionFinalStats).append("\r\n");
                }
                result = stringBuilder.toString();
            }

            File file = new File(outputFileName +".csv");

            // if file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(result);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read from the CSV file and return the list of the sessionFinalStat already stored in it
    public    List<SessionFinalStats> readFromCsv(String inputFileName){
        BufferedReader bufferedReader = null;
        int counter=0;
        List<SessionFinalStats> sessionFinalStatsList= new ArrayList<>();
        try {
            String sCurrentLine;
            bufferedReader = new BufferedReader(new FileReader(inputFileName));

            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                if(counter!=0) {//to make sure not to read the titles
                    String[] splitCurrentLine=sCurrentLine.split(",");
                    //to get  the values in the string and save them to sessionFinalStat instance
                    //in order to add it to the list and return it
                    if(splitCurrentLine.length==9) {//make sure it is a session read file and no null pointer
                        SessionFinalStats sessionFinalStats = new SessionFinalStats(splitCurrentLine[0], splitCurrentLine[1],
                                splitCurrentLine[2], Integer.parseInt(splitCurrentLine[3]), Double.parseDouble(splitCurrentLine[4]),
                                Double.parseDouble(splitCurrentLine[5]), Double.parseDouble(splitCurrentLine[6]),
                                Double.parseDouble(splitCurrentLine[6]), splitCurrentLine[7]);

                        sessionFinalStatsList.add(sessionFinalStats);
                    }

                }
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sessionFinalStatsList;
    }

    //get the list of CSV files already exported
    public List<File> listFilesForFolder(final File folder) {
        List<File> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()&& fileEntry.getName().endsWith(".csv")) {
                files.add(fileEntry);
            }

        }
        return files;
    }


    // this method is to post to the url but am getting a not found
    //message I even tried it with postMan with some Json Body and I got the same response
    //supposing that this endpoints accepts any JSON Structure
    public void postJsonData(SessionFinalStats sessionFinalStats){
        final String BASE_URL = "http://empatica-homework.free.beeceptor.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating retrofit with json place holder Interface that contains api requests
        JsonPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        Call<SessionFinalStats> call=jsonPlaceHolderApi.postSession(sessionFinalStats);

        call.enqueue(new Callback<SessionFinalStats>() {
            @Override
            public void onResponse(Call<SessionFinalStats> call, Response<SessionFinalStats> response) {
                if(!response.isSuccessful()){
                    System.out.println("unSuccessful call ---"+response);
                    //getting a not found response
                    //Response{protocol=http/1.1, code=404, message=Not Found, url=http://empatica-homework.free.beeceptor.com/trainings}
                    return;
                }

            }

            @Override
            public void onFailure(Call<SessionFinalStats> call, Throwable t) {
                System.out.println("----failed---"+call+"---t-"+t);

            }
        });

    }


}
