package com.example.weatherapp.models.subtypes;

public class Wind {
    public double speed;
    public Integer deg;
    public double gust;

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", deg=" + deg +
                ", gust=" + gust +
                '}';
    }
}
