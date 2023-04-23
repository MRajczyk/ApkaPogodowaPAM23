package com.example.weatherapp.models.subtypes;

import java.io.Serializable;

public class Wind implements Serializable {
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
