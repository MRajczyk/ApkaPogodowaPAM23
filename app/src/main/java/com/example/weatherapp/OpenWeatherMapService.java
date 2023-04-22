package com.example.weatherapp;

import com.example.weatherapp.models.TodayResponse;
import com.example.weatherapp.models.subtypes.Coord;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {

    static final String API_KEY = "62cec116baa2f884d8ff51f21cc745ed";

    static final String API_URL = "http://api.openweathermap.org/data/2.5/";

    static final String LOCALIZATION_PARAM = "q";
    static final String API_ID_PARAM = "appid";

//
//    @GET("users/{user}/repos")
//    Call<List<WeatherResponse>> listRepos(@Path("user") String user);

    @GET("weather")
    Call<TodayResponse> getWeather(
            @Query(LOCALIZATION_PARAM) String localizationName,
            @Query(API_ID_PARAM) String apiKey
    );
}
