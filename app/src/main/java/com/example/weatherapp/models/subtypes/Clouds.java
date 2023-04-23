package com.example.weatherapp.models.subtypes;

import java.io.Serializable;

public class Clouds implements Serializable {
    public Integer all;

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }
}
