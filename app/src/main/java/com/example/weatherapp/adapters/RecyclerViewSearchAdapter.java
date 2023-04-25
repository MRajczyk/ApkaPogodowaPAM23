package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.utility.ClickListenerFinder;
import java.util.ArrayList;

public class RecyclerViewSearchAdapter extends RecyclerView.Adapter<RecyclerViewSearchAdapter.ViewHolderFinder> {

    private final ArrayList<String> cityList;
    private final ClickListenerFinder clickListenerFinder;

    public RecyclerViewSearchAdapter(ArrayList<String> cityList, ClickListenerFinder clickListenerFinder) {
        this.cityList = cityList;
        this.clickListenerFinder = clickListenerFinder;
    }

    @NonNull
    @Override
    public ViewHolderFinder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == R.layout.favorite_cities_row) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_cities_row, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search, parent, false);
        }
        view.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
        int height = view.getMeasuredHeight();
        return new ViewHolderFinder(view, clickListenerFinder, height);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFinder holder, int position) {
        if(position != 0){
            holder.cityName.setText(cityList.get(position-1));
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return (position == 0) ? R.layout.search : R.layout.favorite_cities_row;
    }

    @Override
    public int getItemCount() {
        return cityList.size() + 1;
    }

    public static class ViewHolderFinder extends RecyclerView.ViewHolder{
        EditText findCity;
        Button search;
        TextView cityName;
        ImageView deleteFromFavoriteList;

        public ViewHolderFinder(@NonNull View itemView, ClickListenerFinder clickListenerFinder, int height) {
            super(itemView);

            itemView.setOnClickListener(v -> clickListenerFinder.onClickSelectCity(position(getAdapterPosition() - 1)));
            itemView.setMinimumHeight(height);
            findCity = itemView.findViewById(R.id.citySearch);
            search = itemView.findViewById(R.id.searchCity);
            cityName = itemView.findViewById(R.id.city_name);
            deleteFromFavoriteList = itemView.findViewById(R.id.deleteCity);

            if(deleteFromFavoriteList != null) {
                deleteFromFavoriteList.setOnClickListener(v -> clickListenerFinder.onClickTrash(getAdapterPosition() - 1));
            }
            if(search != null){
                search.setOnClickListener(v -> clickListenerFinder.onClickApply(findCity.getText().toString()));
            }
        }

        private int position(int expected){
            if(expected==-1){
                return 0;
            }
            else{
                return expected;
            }
        }
    }
}