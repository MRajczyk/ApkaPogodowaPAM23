package com.example.weatherapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weatherapp.Fragments.MoreDaysFragment;
import com.example.weatherapp.Fragments.MoreInfoFragment;
import com.example.weatherapp.Fragments.SearchFragment;
import com.example.weatherapp.Fragments.TodayFragment;
import com.example.weatherapp.Fragments.TodayFragmentTablet;

public class ViewPagerAdapterTablet extends FragmentStateAdapter {

    public ViewPagerAdapterTablet(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TodayFragmentTablet();
            case 2:
                return new MoreDaysFragment();
            default:
                return new SearchFragment();
        }
    }
}
