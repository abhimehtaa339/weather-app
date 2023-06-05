package com.example.calculator.weather;


import android.util.Log;

public class weather_modal {

    private String temprature , icon , windspeed , time;

    public weather_modal(String temprature, String icon, String windspeed, String time) {
        this.temprature = temprature;
        this.icon = icon;
        this.windspeed = windspeed;
        this.time = time;
    }


    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
