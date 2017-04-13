package com.example.android.teacher.Courses;

/**
 * Created by shkurtagashi on 11.02.17.
 */

public class Weekday {
    public int _HOUR;
    public int _MINUTE;
    public String _DAY;
    public int day;

    public Weekday(int h, int m, String d){
        setHour(h);
        setMinute(m);
        setDay(d);
    }

    public Weekday(int h, int m, int d){
        setHour(h);
        setMinute(m);
        setDay2(d);
    }

    public void setHour(int hour) {
        this._HOUR = hour;
    }

    public void setMinute(int minute) {
        this._MINUTE = minute;
    }

    public void setDay(String day) {
        this._DAY = day;
    }

    public int getHour(){
        return this._HOUR;
    }

    public int getMinute(){
        return this._MINUTE;
    }

    public String getDay(){
        return this._DAY;
    }

    public int getDay2(){
        return this.day;
    }

    public void setDay2(int day) {
        this.day = day;
    }

}
