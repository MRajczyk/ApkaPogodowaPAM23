package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.weatherapp.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);
        Call<OpenWeatherMapService.WeatherResponse> weatherCall = service.getWeather("Łódź", OpenWeatherMapService.API_KEY);
        try {
            System.out.println("downloading data");
            Response<OpenWeatherMapService.WeatherResponse> response = weatherCall.execute();
            OpenWeatherMapService.WeatherResponse apiResponse = response.body();

            //API response
            System.out.println(apiResponse);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

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
}