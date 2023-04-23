package com.example.weatherapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.weatherapp.R;
import com.example.weatherapp.models.TodayResponse;
import com.example.weatherapp.viewmodels.WeatherViewModel;

import java.text.DecimalFormat;

public class MoreInfoFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        final Observer<TodayResponse> weatherDataObserver = this::refreshMoreData;

        WeatherViewModel model = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        model.getTodayWeatherData().observe(getViewLifecycleOwner(), weatherDataObserver);
    }

    private void refreshMoreData(TodayResponse result) {
        //System.out.println("refresh");

        ImageView weatherImage = view.findViewById(R.id.WeatherImage);
        int resId = view.getContext().getResources().getIdentifier('_' + result.weather[0].icon, "drawable", view.getContext().getPackageName());
        //System.out.println(resId);
        weatherImage.setBackgroundResource(resId);

        DecimalFormat df = new DecimalFormat("#.##");

        TextView windDirection = view.findViewById(R.id.WindDirection);
        windDirection.setText(result.wind.deg + " °");

        TextView windStrength = view.findViewById(R.id.WindStrength);
        windStrength.setText(df.format(result.wind.speed) + " m/s");

        TextView visibility = view.findViewById(R.id.Visibility);
        visibility.setText(result.visibility.toString() + " m");

        TextView humidity = view.findViewById(R.id.Humidity);
        humidity.setText(result.main.humidity.toString() + " %");
    }

}