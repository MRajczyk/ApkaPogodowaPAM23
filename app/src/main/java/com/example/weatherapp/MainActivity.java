package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.adapters.ViewPagerAdapter;
import com.example.weatherapp.viewmodels.WeatherViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback {

    private String units;
    private WeatherViewModel weatherVM;
    private TextView cityTextView;
    private DataDownloader dataDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.INTERNET") == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.INTERNET"}, 3);
        }
        //check if premission granted, if not, spawn a toast to inform user
        if(ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.INTERNET") == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(MainActivity.this, "Could not get network permissions", Toast.LENGTH_SHORT).show();
        }
        //setting thread policy for main thread api requests(Testing)
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        this.cityTextView = findViewById(R.id.selectedCity);
        this.weatherVM = new ViewModelProvider(this).get(WeatherViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //read temperature unit from preferences, works cuz we always create main activity when coming back from settings
        this.units = preferences.getString("Temperature_units","Kelvin");
        this.dataDownloader = new DataDownloader(getApplicationContext(), this);

        this.dataDownloader.downloadTodaysWeather(this.cityTextView.getText().toString());
        this.dataDownloader.downloadForecastWeather(this.cityTextView.getText().toString());

        TabLayout tabLayout = findViewById(R.id.fragmentTabs);
        ViewPager2 viewPager2 = findViewById(R.id.viewPagerFragments);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText("Today");
                    break;
                case 2:
                    tab.setText("More days");
                    break;
                case 3:
                    tab.setText("More info");
                    break;
                default:
                    tab.setText("Search");
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_data_refresh) {
            //data refresh
            System.out.println("Refresh");
        } else if (itemId == R.id.menu_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            this.startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        System.out.println(response.body());
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        System.out.println("Error!");
    }
}