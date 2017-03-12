package com.example.android.teacher.Surveys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.teacher.Surveys.SurveysFragments.GeneralSurveys;
import com.example.android.teacher.Surveys.SurveysFragments.LectureSurveys;


/**
 * Created by shkurtagashi on 09.03.17.
 */

public class SurveysFragmentPagerAdapter extends FragmentPagerAdapter {

    public SurveysFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new LectureSurveys();
            case 1:
                return new GeneralSurveys();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Lecture";
            case 1:
                return "General";
            default:
                return null;

        }

    }
}
