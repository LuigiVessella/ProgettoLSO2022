package com.example.main.EntityClasses;

import java.sql.Time;
import java.util.Date;

public class Pothole {
    private String username;
    private Time time;
    private Date date;
    private Double latitude;
    private Double longitude;
    private Double differenceY;

    public Pothole(String username, Time time, Date date, Double latitude, Double longitude, Double differenceY) {
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
