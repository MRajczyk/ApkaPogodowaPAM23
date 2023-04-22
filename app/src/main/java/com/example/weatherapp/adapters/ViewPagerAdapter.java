package com.example.weatherapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weatherapp.Fragments.FavoriteCitiesFragment;
import com.example.weatherapp.Fragments.MoreDaysFragment;
import com.example.weatherapp.Fragments.MoreInfoFragment;
import com.example.weatherapp.Fragments.TodayFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TodayFragment();
            case 2:
                return new MoreDaysFragment();
            case 3:
                return new MoreInfoFragment();
            default:
                return new FavoriteCitiesFragment();
        }
    }
}
