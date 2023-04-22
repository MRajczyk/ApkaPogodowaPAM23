package com.example.weatherapp.models.subtypes;

public class Main {
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public Integer pressure;
    public Integer humidity;
    public Integer sea_level;
    public Integer grnd_level;

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", feels_like=" + feels_like +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", sea_level=" + sea_level +
                ", grnd_level=" + grnd_level +
                '}';
    }
}
