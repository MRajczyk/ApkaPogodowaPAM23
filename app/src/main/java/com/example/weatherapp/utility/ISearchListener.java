package com.example.weatherapp.utility;

public interface ISearchListener {
    void onClickTrash(int position);
    void onClickApply(String city);
    void onClickAddToFavorite(String city);
    void onClickSelectCity(int position);
}
