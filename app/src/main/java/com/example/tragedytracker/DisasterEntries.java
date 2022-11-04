package com.example.tragedytracker;

public class DisasterEntries {
    String date , time,  status,  name,  url;
    double score;


    DisasterEntries(String date, String time, String status, String name, String url,double score){
        this.date = date;
        this.time = time;
        this.status = status;
        this.name = name;
        this.url = url;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
