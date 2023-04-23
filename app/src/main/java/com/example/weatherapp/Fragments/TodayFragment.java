package com.example.weatherapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.TodayResponse;
import com.example.weatherapp.viewmodels.WeatherViewModel;

import java.text.DecimalFormat;

public class TodayFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        final Observer<TodayResponse> weatherDataObserver = this::refreshTodayData;

        WeatherViewModel model = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        model.getTodayWeatherData().observe(getViewLifecycleOwner(), weatherDataObserver);
    }

    private void refreshTodayData(TodayResponse result) {
        //System.out.println("refresh");
        DecimalFormat df = new DecimalFormat("#.##");
        TextView temperatureData = view.findViewById(R.id.TemperatureData);
        temperatureData.setText(df.format(result.main.temp));

        TextView weatherText = view.findViewById(R.id.Weather);
        weatherText.setText(result.weather[0].main);

        TextView latData = view.findViewById(R.id.LatitudeData);
        latData.setText(df.format(result.coord.lat));

        TextView lonData = view.findViewById(R.id.LongitudeData);
        lonData.setText(df.format(result.coord.lon));

        TextView pressData = view.findViewById(R.id.PressureData);
        String pressureStr = result.main.pressure + " hPa";
        pressData.setText(pressureStr);
    }

}