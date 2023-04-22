package com.example.weatherapp.models.subtypes;

public class City {
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
