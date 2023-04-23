package com.example.weatherapp.models.subtypes;

import java.io.Serializable;

public class Weather implements Serializable {
    public Integer id;
    public String main;
    public String description;
    public String icon;

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
