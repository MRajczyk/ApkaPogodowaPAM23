package com.example.weatherapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.RecyclerViewSearchAdapter;
import com.example.weatherapp.utility.ISearchListener;
import com.example.weatherapp.utility.ShowDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements ISearchListener, MyDialog.Callback {

    private RecyclerViewSearchAdapter adapter;
    private final ArrayList<String> cityList = new ArrayList<>();
    private ImageView addToFavorites;
    private TextView selectedCity;
    private final String TODAY_WEATHER_FILENAME = "today.txt";
    private final String FORECAST_WEATHER_FILENAME = "forecast.txt";
    ShowDialog activity_showDialog;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            activity_showDialog = (ShowDialog)activity;
        }
        catch(ClassCastException e)
        {
            Log.e(this.getClass().getSimpleName(), "ShowDialog interface needs to be     implemented by Activity.", e);
            throw e;
        }
    }

    @Override
    public void accept(int position) {
        String cityName = cityList.get(position);
        cityList.remove(position);
        adapter.notifyItemRemoved(position + 1);
        this.saveFile();

        File file1 = new File(getContext().getFilesDir(), cityName + '_' + TODAY_WEATHER_FILENAME);
        File file2 = new File(getContext().getFilesDir(), cityName + '_' + FORECAST_WEATHER_FILENAME);

        if(file1.delete()) {
            System.out.println("deleted today forecast data for: " + cityName);
            if(file2.delete()) {
                System.out.println("deleted five day forecast data for: " + cityName);
            }
            else {
                System.out.println("Error while deleting forecast data for city: " + cityName);
            }
        }
        else {
            System.out.println("Error while deleting storage data for city: " + cityName);
        }
    }

    @Override
    public void decline() {
        //ignore
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.selectedCity = getActivity().findViewById(R.id.selectedCity);
        this.addToFavorites = getActivity().findViewById(R.id.addToFavorites);
        if(this.addToFavorites != null && this.selectedCity != null) {
            this.addToFavorites.setOnClickListener(v -> this.onClickAddToFavorite(this.selectedCity.getText().toString()));
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavoriteCities);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readFile();
        adapter = new RecyclerViewSearchAdapter(cityList,this);
        recyclerView.setAdapter(adapter);
    }

    public void readFile() {
        try {
            FileInputStream fileInputStream = requireActivity().getApplicationContext().openFileInput("FavoriteCities.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.lines().forEach(cityList::add);
        } catch (IOException ignored) {
            new File(requireActivity().getApplicationContext().getFilesDir(), "FavoriteCities.txt");
        }
    }

    private void reloadFragment(String city){
        saveFile();
        ((MainActivity) requireActivity()).readData(city);
    }

    private void saveFile() {
        try {
            FileOutputStream outputStream = requireActivity().getApplicationContext().openFileOutput("FavoriteCities.txt", Context.MODE_PRIVATE);
            cityList.forEach(s -> {
                try {
                    s = s +"\n";
                    outputStream.write(s.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickTrash(int position) {
        MyDialog dialog = new MyDialog(position);
        dialog.setTargetFragment(this, 1); //request code
        activity_showDialog.showDialog(dialog);
    }

    @Override
    public void onClickAddToFavorite(String city) {
        if(!cityList.contains(city)) {
            cityList.add(0, city);
            adapter.notifyItemInserted(1);
            this.saveFile();
        }
        else {
            Toast.makeText(getActivity(), "City already added to favorites!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickSelectCity(int position) {
        reloadFragment(cityList.get(position));
    }

    @Override
    public void onClickApply(String city) {
        reloadFragment(city);
    }
}
