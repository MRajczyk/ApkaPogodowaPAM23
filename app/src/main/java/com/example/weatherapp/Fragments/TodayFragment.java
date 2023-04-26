package com.example.weatherapp.Fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.TodayResponse;
import com.example.weatherapp.utility.ConvertTempString;
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String tempRes = ConvertTempString.convertTemp(preferences, df, result.main.temp);

        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp < 600) {
            ImageView weatherImage = view.findViewById(R.id.WeatherImage);
            int resId = view.getContext().getResources().getIdentifier('_' + result.weather[0].icon, "drawable", view.getContext().getPackageName());
            weatherImage.setBackgroundResource(resId);
        }

        TextView temperatureData = view.findViewById(R.id.TemperatureData);
        temperatureData.setText(tempRes);

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