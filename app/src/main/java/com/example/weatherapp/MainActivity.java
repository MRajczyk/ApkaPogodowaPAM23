package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import java.util.List;

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
            //your codes here

        }

        System.out.println("Connetction: " + isNetworkAvailable(getApplication()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);

        Call<OpenWeatherMapService.WeatherResponse> weatherCall = service.getWeather("London", OpenWeatherMapService.API_KEY);

        try
        {
            System.out.println("downloading data");
            Response<OpenWeatherMapService.WeatherResponse> response = weatherCall.execute();
            OpenWeatherMapService.WeatherResponse apiResponse = response.body();

            //API response
            System.out.println(apiResponse);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private Boolean isNetworkAvailable(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        if (nw == null) return false;
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_data_refresh) {
            System.out.println("CLICK");
            //                Intent i = new Intent(this,SecondActivity.class);
//                this.startActivity(i);
            return true;
        } else if (itemId == R.id.menu_localization_settings) {
            //                Intent i = new Intent(this,SecondActivity.class);
//                this.startActivity(i);
            return true;
        } else if (itemId == R.id.menu_units_settings) {
            //                Intent i = new Intent(this,SecondActivity.class);
//                this.startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    class MyAsyncTask extends AsyncTask<String,Void, JSONObject> {
//
//        @Override
//        protected JSONObject doInBackground(String... urls) {
//            return RestService.doGet(urls[0]);
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            TextView tv = (TextView) findViewById(R.id.txtView);
//            tv.setText(jsonObject.toString());
//        }
//    }
}