package com.example.weatherapp.Fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import com.example.weatherapp.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}