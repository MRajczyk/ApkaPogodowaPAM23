package com.example.weatherapp.models.subtypes;

import java.io.Serializable;

public class Sys5d implements Serializable {
    public String pod;

    @Override
    public String toString() {
        return "Sys5d{" +
                "pod='" + pod + '\'' +
                '}';
    }
}
