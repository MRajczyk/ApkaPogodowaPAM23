package com.example.weatherapp;

import android.content.Context;

import com.example.weatherapp.models.FiveDayResponse;
import com.example.weatherapp.models.TodayResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataDownloader {

    private final OpenWeatherMapService mapService;
    private final Callback callback;

    DataDownloader(Callback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.mapService = retrofit.create(OpenWeatherMapService.class);
        this.callback = callback;
    }

    public void downloadTodaysWeather(String city) {
        Call<TodayResponse> todayWeather = mapService.getTodayWeather(city, OpenWeatherMapService.API_KEY);
        try {
            todayWeather.enqueue(this.callback);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void downloadForecastWeather(String city) {
        Call<FiveDayResponse> fiveDayWeather = mapService.getForecastWeather(city, OpenWeatherMapService.API_KEY);
        try {
            fiveDayWeather.enqueue(this.callback);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
