package com.example.weatherapp.models.subtypes;

public class Coord {
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