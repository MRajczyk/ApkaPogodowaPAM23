package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.adapters.ViewPagerAdapter;
import com.example.weatherapp.adapters.ViewPagerAdapterTablet;
import com.example.weatherapp.models.FiveDayResponse;
import com.example.weatherapp.models.TodayResponse;
import com.example.weatherapp.viewmodels.WeatherViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback {

    private String units;
    private WeatherViewModel weatherVM;
    private TextView cityTextView;
    private DataDownloader dataDownloader;
    private final String TODAY_WEATHER_FILENAME = "today.txt";
    private final String FORECAST_WEATHER_FILENAME = "forecast.txt";
    private Context context;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 15*1000; //Delay for 15 seconds.  One second = 1000 milliseconds.


    @Override
    protected void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                refreshData();
                //Toast.makeText(MainActivity.this, "Refreshing data", Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }
    // If onPause() is not included the threads will double up when you
    // reload the activity

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

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
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        this.context = getApplicationContext();
        this.cityTextView = findViewById(R.id.selectedCity);
        this.weatherVM = new ViewModelProvider(this).get(WeatherViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //read temperature unit from preferences, works cuz we always create main activity when coming back from settings
        this.units = preferences.getString("Temperature_units","Kelvin");
        this.dataDownloader = new DataDownloader(this);

        readData(this.cityTextView.getText().toString());

        TabLayout tabLayout = findViewById(R.id.fragmentTabs);
        ViewPager2 viewPager2 = findViewById(R.id.viewPagerFragments);

        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp >= 600) {
            ViewPagerAdapterTablet adapter = new ViewPagerAdapterTablet(this);
            viewPager2.setAdapter(adapter);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
                switch (position) {
                    case 1:
                        tab.setText("Today");
                        break;
                    case 2:
                        tab.setText("More days");
                        break;
                    default:
                        tab.setText("Search");
                        break;
                }
            }).attach();
        }
        else {
            ViewPagerAdapter adapter = new ViewPagerAdapter(this);
            viewPager2.setAdapter(adapter);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
                switch (position) {
                    case 1:
                        tab.setText("Today");
                        break;
                    case 2:
                        tab.setText("More info");
                        break;
                    case 3:
                        tab.setText("More days");
                        break;
                    default:
                        tab.setText("Search");
                        break;
                }
            }).attach();
        }
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
            this.refreshData();
        } else if (itemId == R.id.menu_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            this.startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshData() {
        this.readData(this.cityTextView.getText().toString());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        this.saveFile(response);
        if(response.code() > 400) {
            Toast.makeText(MainActivity.this, "City doesn't exist!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        System.out.println("Error!");
        Toast.makeText(MainActivity.this, "Failed to download data", Toast.LENGTH_SHORT).show();
    }

    public void readData(String cityName) {
        try {
            File file = new File(getApplicationContext().getFilesDir(), cityName + '_' + TODAY_WEATHER_FILENAME);
            boolean networkAvailable = isNetworkAvaliable(getApplicationContext());
            if (file.exists()) {
                System.out.println("File exists... Trying to read if modified less than one hour ago...");
                long FIFTEEN_MINUTES = 900000;
                if (file.lastModified() + FIFTEEN_MINUTES > System.currentTimeMillis() || !networkAvailable) {
                    System.out.println("Modified less than hour ago");
                    //System.out.println("reading today's data");
                    FileInputStream fis = context.openFileInput(cityName + '_' + TODAY_WEATHER_FILENAME);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    TodayResponse todayResponse = (TodayResponse) is.readObject();
                    is.close();
                    fis.close();
                    this.weatherVM.setTodayWeather(todayResponse);
                    //System.out.println(this.weatherVM.getTodayWeatherData().getValue());
                    //System.out.println("reading forecast 5 days data");
                    fis = context.openFileInput(cityName + '_' + FORECAST_WEATHER_FILENAME);
                    is = new ObjectInputStream(fis);
                    FiveDayResponse fiveDayResponse = (FiveDayResponse) is.readObject();
                    is.close();
                    fis.close();
                    this.weatherVM.setForecastWeather(fiveDayResponse);
                    //System.out.println(this.weatherVM.getForecastWeatherData().getValue());

                    this.cityTextView.setText(cityName);
                    return;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading data from file, trying to download!");
            this.dataDownloader.downloadTodaysWeather(cityName);
            this.dataDownloader.downloadForecastWeather(cityName);
            e.printStackTrace();
        }
        if(isNetworkAvaliable(getApplicationContext())) {
            System.out.println("Refreshing data...");
            this.dataDownloader.downloadTodaysWeather(cityName);
            this.dataDownloader.downloadForecastWeather(cityName);
        }
        else {
            Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveFile(Response response) {
        if(response.body() instanceof TodayResponse) {
            try {
                System.out.println(response.body());
                this.cityTextView.setText(((TodayResponse) response.body()).name);
                this.weatherVM.setTodayWeather((TodayResponse)response.body());
                FileOutputStream fos = context.openFileOutput(((TodayResponse) response.body()).name + '_' + TODAY_WEATHER_FILENAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(response.body());
                os.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("error saving today data!");
                e.printStackTrace();
            }
        } else if(response.body() instanceof FiveDayResponse) {
            try {
                System.out.println(response.body());
                this.weatherVM.setForecastWeather((FiveDayResponse)response.body());
                FileOutputStream fos = context.openFileOutput(((FiveDayResponse) response.body()).city.name + '_' + FORECAST_WEATHER_FILENAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(response.body());
                os.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("error saving forecast data!");
                e.printStackTrace();
            }
        }
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }
}