package com.example.tragedytracker;

public class EarthquakeEntries {
    private String range;
    private String location;
    private double magnitude ;
    private String date;
    private String time;
    private String url;
    String   status,  name;
    double lattitude,longitude;
    double score;

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    EarthquakeEntries(double mag, String range, String loc, String dat, String time, String url, double lattitude, double longitude){
        location = loc;
        magnitude = mag;
        date = dat;
        this.time = time;
        this.range = range;
        this.url = url;
        this.lattitude =lattitude;
        this.longitude = longitude;
    }

    EarthquakeEntries(String date, String time, String status, String name, String url,double score){
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


    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getLocation(){
        return location;
    }
    public double getMagnitude(){
        return magnitude;
    }
    public String getDate(){ return date; }
    public String getTime(){ return this.time; }
    public String getRange(){ return this.range; }
    public String getUrl(){ return this.url; }

    @Override
    public String toString() {
        return "EarthquakeEntries{" +
                "range='" + range + '\'' +
                ", location='" + location + '\'' +
                ", magnitude=" + magnitude +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
