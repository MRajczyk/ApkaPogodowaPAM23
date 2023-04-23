package com.example.weatherapp.models.subtypes;

import java.io.Serializable;
import java.util.Arrays;

public class List implements Serializable {
    public Long dt;
    public Main main;
    public Weather[] weather;
    public Clouds clouds;
    public Wind wind;
    public Integer visibility;
    public double pop;
    public Sys5d sys;
    public String dt_txt;

    @Override
    public String toString() {
        return "List{" +
                "dt=" + dt +
                ", main=" + main +
                ", weather=" + Arrays.toString(weather) +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", visibility=" + visibility +
                ", pop=" + pop +
                ", sys=" + sys +
                ", dt_txt='" + dt_txt + '\'' +
                '}';
    }
}
