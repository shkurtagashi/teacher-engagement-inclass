package com.example.android.teacher.Sensors.Fragments;

import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.android.teacher.Sensors.Fragments.AccFragment;
import com.example.android.teacher.Sensors.Fragments.BloodVolumeFragment;
import com.example.android.teacher.Sensors.Fragments.EDAFragment;
import com.example.android.teacher.Sensors.Fragments.TempFragment;


/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new EDAFragment();
            case 1:
                return new TempFragment();
            case 2:
                return new AccFragment();
            case 3:
                return new BloodVolumeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "EDA";
            case 1:
                return "TEMP";
            case 2:
                return "ACC";
            case 3:
                return "BVP";
            default:
                return null;

        }

    }
}