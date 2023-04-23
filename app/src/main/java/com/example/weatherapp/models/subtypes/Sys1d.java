package com.example.weatherapp.models.subtypes;

import java.io.Serializable;

public class Sys1d implements Serializable {
    public Integer type;
    public Integer id;
    public String country;
    public Long sunrise;
    public Long sunset;

    @Override
    public String toString() {
        return "sys{" +
                "type=" + type +
                ", id=" + id +
                ", country='" + country + '\'' +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}
