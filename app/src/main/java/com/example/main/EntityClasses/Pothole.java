package com.example.main.EntityClasses;


import java.util.Date;

public class Pothole {
    private String username;
    private String time;
    private String date;
    private Double latitude;
    private Double longitude;
    private Double differenceY;

    public Pothole(String username, String time, String date, Double latitude, Double longitude, Double differenceY) {
        this.username = username;
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.differenceY = differenceY;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDifferenceY() {
        return differenceY;
    }

    public void setDifferenceY(Double differenceY) {
        this.differenceY = differenceY;
    }
}
