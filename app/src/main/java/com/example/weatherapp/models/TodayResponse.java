package com.example.weatherapp.models;

import com.example.weatherapp.models.subtypes.Clouds;
import com.example.weatherapp.models.subtypes.Coord;
import com.example.weatherapp.models.subtypes.Main;
import com.example.weatherapp.models.subtypes.Rain;
import com.example.weatherapp.models.subtypes.Snow;
import com.example.weatherapp.models.subtypes.Weather;
import com.example.weatherapp.models.subtypes.Wind;
import com.example.weatherapp.models.subtypes.Sys;

import java.util.Arrays;

public class TodayResponse {

    public Coord coord;
    public Weather[] weather;
    public String base;
    public Main main;
    public Integer visibility;
    public Wind wind;
    public Rain rain;
    public Snow snow;
    public Clouds clouds;
    public Long dt;
    public Sys sys;
    public Integer timezone;
    public Integer id;
    public String name;
    public Integer cod;

    @Override
    public String toString() {
        return "TodayResponse{" +
                "coord=" + coord +
                ", weather=" + Arrays.toString(weather) +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", rain=" + rain +
                ", snow=" + snow +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}
