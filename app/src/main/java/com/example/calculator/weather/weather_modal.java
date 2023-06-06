package com.example.calculator.weather;


public class weather_modal {

    private String temprature , windspeed , time;

    public weather_modal(String temprature, String icon, String windspeed) {
        this.temprature = temprature;
        this.windspeed = windspeed;
        this.time = time;
    }


    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
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
