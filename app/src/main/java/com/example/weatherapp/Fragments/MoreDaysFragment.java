package com.example.weatherapp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.RecyclerViewAdapter;
import com.example.weatherapp.models.FiveDayResponse;
import com.example.weatherapp.viewmodels.WeatherViewModel;

public class MoreDaysFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_days, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        final Observer<FiveDayResponse> weatherDataObserver = this::startRecyclerView;

        WeatherViewModel model = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        model.getForecastWeatherData().observe(getViewLifecycleOwner(), weatherDataObserver);
    }

    private void startRecyclerView(FiveDayResponse weatherData) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(weatherData, this.view.getContext()));
    }
}