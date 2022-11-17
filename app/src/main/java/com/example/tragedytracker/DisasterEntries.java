package com.example.tragedytracker;

public class DisasterEntries {
    String tDate , tTime,  tStatus,  tName,  tUrl;
    double tScore;


    DisasterEntries(String tDate, String tTime, String tStatus, String tName, String tUrl,double tScore){
        this.tDate = tDate;
        this.tTime = tTime;
        this.tStatus = tStatus;
        this.tName = tName;
        this.tUrl = tUrl;
        this.tScore = tScore;
    }

    public double getScore() {
        return tScore;
    }

    public String getDate() {
        return tDate;
    }

    public String getTime() {
        return tTime;
    }

    public String getStatus() {
        return tStatus;
    }

    public String getName() {
        return tName;
    }

    public String getUrl() {
        return tUrl;
    }
}
