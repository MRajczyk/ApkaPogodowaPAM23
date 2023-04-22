package com.example.weatherapp.models;

import com.example.weatherapp.models.subtypes.City;
import com.example.weatherapp.models.subtypes.List;

import java.util.Arrays;

public class FiveDayResponse {
    public String cod;
    public Integer message;
    public Integer cnt;
    public List[] list;
    public City city;

    @Override
    public String toString() {
        return "FiveDayResponse{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + Arrays.toString(list) +
                ", city=" + city +
                '}';
    }
}
