package com.example.weatherapp.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.models.FiveDayResponse;
import com.example.weatherapp.models.TodayResponse;

import java.util.ArrayList;

public class WeatherViewModel extends androidx.lifecycle.ViewModel  {
    private final MutableLiveData<TodayResponse> todayWeatherData = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> todayIcon = new MutableLiveData<>();
    private final MutableLiveData<FiveDayResponse> forecastWeatherData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Bitmap>> forecastIcons = new MutableLiveData<>();

    public void setTodayWeather(TodayResponse weatherData) {
        this.todayWeatherData.setValue(weatherData);
    }

    public LiveData<TodayResponse> getTodayWeatherData(){
        return this.todayWeatherData;
    }

    public void setTodayIcon(Bitmap bitmap) {
        this.todayIcon.setValue(bitmap);
    }

    public LiveData<Bitmap> getTodayIcon(){
        return this.todayIcon;
    }


    public void setForecastWeather(FiveDayResponse weatherData) {
        this.forecastWeatherData.setValue(weatherData);
    }

    public LiveData<FiveDayResponse> getForecastWeatherData(){
        return this.forecastWeatherData;
    }

    public void setForecastIcons(ArrayList<Bitmap> bitmaps) {
        this.forecastIcons.setValue(bitmaps);
    }

    public LiveData<ArrayList<Bitmap>> getForecastIcons(){
        return this.forecastIcons;
    }
}
