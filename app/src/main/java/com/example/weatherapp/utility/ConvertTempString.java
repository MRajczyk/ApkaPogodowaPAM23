package com.example.weatherapp.utility;

import android.content.SharedPreferences;

import java.text.DecimalFormat;

public class ConvertTempString {
    public static String convertTemp(SharedPreferences preferences, DecimalFormat df, double temp) {
        String tempUnit = preferences.getString("Temperature_units","Kelvin");
        String Res = "";
        double conversionUnit = 273.15D;

        switch (tempUnit) {
            case "Kelvin":
                Res = df.format(temp) + " K";
                break;
            case "Celsius":
                Res = df.format(temp - conversionUnit) + " °C";
                break;
            case "Fahrenheit":
                //° F = 9/5(K - 273.15) + 32
                Res = df.format(9D / 5 * (temp - conversionUnit) + 32D) + " °F";
                break;
        }

        return Res;
    }
}
