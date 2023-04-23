package com.example.weatherapp.models.subtypes;

import java.io.Serializable;

public class Coord implements Serializable {
    public double lon;
    public double lat;

    @Override
    public String toString() {
        return "Coord{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
