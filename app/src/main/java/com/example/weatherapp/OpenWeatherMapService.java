package com.example.weatherapp;

import com.example.weatherapp.models.Coord;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {

    static final String API_KEY = "787c28b50d8d0e38ae920b839afdee7a";

    static final String API_URL = "http://api.openweathermap.org/data/2.5/";

    static final String LOCALIZATION_PARAM = "q";
    static final String API_ID_PARAM = "appid";

    public static class WeatherResponse {
        public String name;
        public Coord coord;
        public Integer visibility;

        @Override
        public String toString() {
            return "WeatherResponse{" +
                    "name='" + name + '\'' +
                    ", coord=" + coord +
                    ", visibility=" + visibility +
                    '}';
        }
    }
//
//    @GET("users/{user}/repos")
//    Call<List<WeatherResponse>> listRepos(@Path("user") String user);

    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query(LOCALIZATION_PARAM) String localizationName,
            @Query(API_ID_PARAM) String apiKey
    );
}
