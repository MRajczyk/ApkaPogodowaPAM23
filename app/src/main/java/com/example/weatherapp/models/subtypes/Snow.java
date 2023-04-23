package com.example.weatherapp.models.subtypes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Snow implements Serializable {
    @SerializedName("1h")
    public double h1;
    @SerializedName("3h")
    public double h3;

    @Override
    public String toString() {
        return "Snow{" +
                "1h=" + h1 +
                "3h=" + h3 +
                '}';
    }
}
