package com.example.weatherapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.FiveDayResponse;
import com.example.weatherapp.utility.ConvertTempString;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class RecyclerViewMoreDaysAdapter extends RecyclerView.Adapter<RecyclerViewMoreDaysAdapter.ViewHolder> {

    private final FiveDayResponse weatherData;
    private final Context context;

    public RecyclerViewMoreDaysAdapter(FiveDayResponse weatherData, Context context) {
        this.weatherData = weatherData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == R.layout.more_days_row) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_days_row, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeholder, parent, false);
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.more_days_row;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("#.##");
        final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("hh-mm");

        Long time = weatherData.list[position].dt;
        final long unixTime = Long.parseLong(String.valueOf(time));

        holder.myTextViewDays.setText(Instant.ofEpochSecond(unixTime)
                .atZone(ZoneOffset.UTC)
                .format(formatterDate));
        holder.myTextViewHour.setText(Instant.ofEpochSecond(unixTime)
                .atZone(ZoneOffset.UTC)
                .format(formatterHour));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String tempRes = ConvertTempString.convertTemp(preferences, df, weatherData.list[position].main.temp);
        holder.myTextViewTemperature.setText(tempRes);

        int resId = context.getResources().getIdentifier('_' + weatherData.list[position].weather[0].icon, "drawable", context.getPackageName());
        holder.imageView.setBackgroundResource(resId);
    }

    @Override
    public int getItemCount() {
        return weatherData.cnt;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextViewTemperature;
        TextView myTextViewDays;
        TextView myTextViewHour;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextViewDays = itemView.findViewById(R.id.day);
            myTextViewHour = itemView.findViewById(R.id.hour);
            myTextViewTemperature = itemView.findViewById(R.id.city_name);
            imageView = itemView.findViewById(R.id.WeatherImage);
        }
    }
}
