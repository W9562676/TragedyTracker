package com.example.tragedytracker;

public class EarthquakeEntries {
    private String tRange;
    private String tLocation;
    private double tMagnitude ;
    private String tDate;
    private String tTime;
    private String tUrl;
    String   tStatus,  tName;
    double tLattitude,tLongitude;
    double tScore;

    public double getLattitude() {
        return tLattitude;
    }

    public void setLattitude(double tLattitude) {
        this.tLattitude = tLattitude;
    }

    public double getLongitude() {
        return tLongitude;
    }

    public void setLongitude(double tLongitude) {
        this.tLongitude = tLongitude;
    }

    EarthquakeEntries(double tMag, String tRange, String tLoc, String tDat, String tTime, String tUrl, double tLattitude, double tLongitude){
        tLocation = tLoc;
        tMagnitude = tMag;
        tDate = tDat;
        this.tTime = tTime;
        this.tRange = tRange;
        this.tUrl = tUrl;
        this.tLattitude =tLattitude;
        this.tLongitude = tLongitude;
    }

    EarthquakeEntries(String tDate, String tTime, String tStatus, String tName, String tUrl,double tScore){
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


    public String getStatus() {
        return tStatus;
    }

    public String getName() {
        return tName;
    }

    public String getLocation(){
        return tLocation;
    }
    public double getMagnitude(){
        return tMagnitude;
    }
    public String getDate(){ return tDate; }
    public String getTime(){ return this.tTime; }
    public String getRange(){ return this.tRange; }
    public String getUrl(){ return this.tUrl; }

    @Override
    public String toString() {
        return "EarthquakeEntries{" +
                "range='" + tRange + '\'' +
                ", location='" + tLocation + '\'' +
                ", magnitude=" + tMagnitude +
                ", date='" + tDate + '\'' +
                ", time='" + tTime + '\'' +
                ", url='" + tUrl + '\'' +
                ", status='" + tStatus + '\'' +
                ", name='" + tName + '\'' +
                ", score=" + tScore +
                '}';
    }
}
