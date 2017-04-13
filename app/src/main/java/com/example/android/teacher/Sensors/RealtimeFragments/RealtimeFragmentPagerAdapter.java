package com.example.android.teacher.Sensors.RealtimeFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by shkurtagashi on 15.03.17.
 */

public class RealtimeFragmentPagerAdapter extends FragmentPagerAdapter {

    public RealtimeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new EdaFragment();
            case 2:
                return new TemperatureFragment();
            case 3:
                return new BvpFragment();
            case 4:
                return new AccelFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Home";
            case 1:
                return "Eda";
            case 2:
                return "Temp";
            case 3:
                return "Bvp";
            case 4:
                return "Acc";
            default:
                return null;

        }

    }
}
