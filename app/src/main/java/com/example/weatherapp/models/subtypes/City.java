package com.example.weatherapp.models.subtypes;

import java.io.Serializable;

public class City implements Serializable {
    public Integer id;
    public String name;
    public Coord coord;
    public String country;
    public Integer population;
    public Integer timezone;
    public Long sunrise;
    public Long sunset;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coord=" + coord +
                ", country='" + country + '\'' +
                ", population=" + population +
                ", timezone=" + timezone +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}
