package com.example.weatherapp.Fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.weatherapp.R;
import com.example.weatherapp.models.TodayResponse;
import com.example.weatherapp.utility.ConvertTempString;
import com.example.weatherapp.viewmodels.WeatherViewModel;

import java.text.DecimalFormat;

public class TodayFragmentTablet extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today_tablet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        TodayFragment one = new TodayFragment();
        MoreInfoFragment two = new MoreInfoFragment();

        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.fragment1, one, "fragmentone").commit();
        fm.beginTransaction().replace(R.id.fragment2, two, "fragmenttwo").commit();

        final Observer<TodayResponse> weatherDataObserver = this::refreshData;

        WeatherViewModel model = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        model.getTodayWeatherData().observe(getViewLifecycleOwner(), weatherDataObserver);
    }

    private void refreshData(TodayResponse todayResponse) {
        ImageView weatherImage = view.findViewById(R.id.WeatherImage);
        int resId = view.getContext().getResources().getIdentifier('_' + todayResponse.weather[0].icon, "drawable", view.getContext().getPackageName());
        weatherImage.setBackgroundResource(resId);
    }
}