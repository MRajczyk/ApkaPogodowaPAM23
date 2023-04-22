package com.example.weatherapp.models.subtypes;

import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    public double h1;
    @SerializedName("3h")
    public double h3;

    @Override
    public String toString() {
        return "Rain{" +
                "1h=" + h1 +
                "3h=" + h3 +
                '}';
    }
}
