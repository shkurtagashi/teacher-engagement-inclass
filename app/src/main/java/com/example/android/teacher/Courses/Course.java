package com.example.android.teacher.Courses;

/**
 * Created by shkurtagashi on 11.02.17.
 */

public class Course {
    public Weekday day1;
    public Weekday day2;
    public Weekday day3;
    public String name;


    public Course(Weekday d1, Weekday d2, String name){
        setWeekday1(d1);
        setWeekday2(d2);
        setName(name);

    }

    public Course(Weekday d1, Weekday d2, Weekday d3, String name){
        setWeekday1(d1);
        setWeekday2(d1);
        setWeekday3(d1);
        setName(name);
    }


    public void setWeekday1(Weekday weekday) {
        this.day1 = weekday;
    }

    public void setWeekday2(Weekday weekday) {
        this.day2 = weekday;
    }

    public void setWeekday3(Weekday weekday) {
        this.day3 = weekday;
    }

    public void setName(String n){
        this.name = n;
    }

    public Weekday getWeekday1(){
        return this.day1;
    }

    public Weekday getWeekday2(){
        return this.day2;
    }

    public Weekday getWeekday3(){
        return this.day3;
    }

    public String getName(){
        return this.name;
    }

}
