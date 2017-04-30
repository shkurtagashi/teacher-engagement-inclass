package com.example.android.teacher.Courses;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
//import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Calendar;


/**
 * Created by shkurtagashi on 11.02.17.
 */

public class FinalScheduler {

    /* Creation of the courses - BEGIN */

    //Linear Algebra on Monday and  Wednesday from 8:30 until 10:15
    public Weekday mondayLA = new Weekday(8, 25, Calendar.MONDAY);             //Exact schedule should be Monday 8:30     DAY - SUNDAY=1, MONDAY=2
    public Weekday wednesdayLA = new Weekday(8, 25, Calendar.WEDNESDAY);       //Exact schedule should be Wednesday 8:30
    public Course LinearAlgebra = new Course(mondayLA, wednesdayLA, "Linear Algebra");

    //Programming Fundamentals on Monday, Wednesday and Friday from 10:30 until 12:15
    public Weekday mondayPF = new Weekday(10, 25, Calendar.MONDAY);            //Exact schedule should be Monday 10:30
    public Weekday wednesdayPF = new Weekday(10, 25, Calendar.WEDNESDAY);      //Exact schedule should be Wednesday 10:30
    public Weekday fridayPF = new Weekday(10, 25, Calendar.FRIDAY);            //Exact schedule should be Friday 10:30
    public Course ProgrammingFundamentals = new Course(mondayPF, wednesdayPF,fridayPF, "Programming Fundamentals");

    //Cyber Communication on Tuesday, Wednesday and Thursday from 10:30 until 12:15
    public Weekday tuesdayCC = new Weekday(10, 25, Calendar.TUESDAY);          //Exact schedule should be Tuesday 10:30
    public Weekday wednesdayMCC = new Weekday(8, 25, Calendar.WEDNESDAY);      //Exact schedule should be Wednesday 8:30
    public Weekday wednesdayCC = new Weekday(10, 25, Calendar.WEDNESDAY);      //Exact schedule should be Wednesday 10:30
    public Weekday thursdayMCC = new Weekday(8, 25, Calendar.THURSDAY);        //Exact schedule should be Thursday 8:30
    public Weekday thursdayCC = new Weekday(10, 25, Calendar.THURSDAY);        //Exact schedule should be Thursday 10:30
    public Course CyberCommunication = new Course(tuesdayCC, wednesdayCC,thursdayCC, "Cyber Communication");

    //Information Security on Monday from 13:30 until 17:15
    public Weekday mondayInf1 = new Weekday(13, 25, Calendar.MONDAY);          //Exact schedule should be Monday 13:30
    public Weekday mondayInf2 = new Weekday(15, 25, Calendar.MONDAY);          //Exact schedule should be Monday 15:30
    public Course InformationSecurity = new Course(mondayInf1, mondayInf2, "Information Security");

    //Software Architecture on Tuesday and Thursday from 13:30 until 17:15
    public Weekday tuesdaySAD = new Weekday(13, 25, Calendar.TUESDAY);         //Exact schedule should be Tuesday 13:30
    public Weekday thursdaySAD = new Weekday(13, 25, Calendar.THURSDAY);       //Exact schedule should be Thursday 13:30
    public Course SoftwareArchitecture = new Course(tuesdaySAD, thursdaySAD, "Software Architecture and Design");


    /* Creation of the courses - END */

    /********** FIRST PAM **********/
    public void createFirstPAM(Context context, String courses) {

        /*LINEAR ALGEBRA*/
        if (courses.contains(LinearAlgebra.getName())) {
            setAlarm(context, "MondayLA", "FirstPAM", 10, mondayLA, 0, 0);
            setAlarm(context, "WednesdayLA", "FirstPAM", 20, wednesdayLA, 0, 0);
        }

        /*PROGRAMMING FUNDAMENTALS 2*/
        if (courses.contains(ProgrammingFundamentals.getName())) {
            setAlarm(context, "MondayPF", "FirstPAM", 30, mondayPF, 0, 0);
            setAlarm(context, "WednesdayPF", "FirstPAM", 40, wednesdayPF, 0, 0);
            setAlarm(context, "FridayPF", "FirstPAM", 50, fridayPF, 0, 0);
        }

        /*CYBER COMMUNICATION*/
        if (courses.contains(CyberCommunication.getName())) {
            setAlarm(context, "TuesdayCC", "FirstPAM", 60, tuesdayCC, 0, 0);
            setAlarm(context, "WednesdayMCC", "FirstPAM", 71, wednesdayMCC, 0, 0);
            setAlarm(context, "WednesdayCC", "FirstPAM", 70, wednesdayCC, 0, 0);
            setAlarm(context, "ThursdayMCC", "FirstPAM", 81, thursdayMCC, 0, 0);
            setAlarm(context, "ThursdayCC", "FirstPAM", 80, thursdayCC, 0, 0);
        }

        /*INFORMATION SECURITY*/
        if (courses.contains(InformationSecurity.getName())) {
            setAlarm(context, "MondayInf1", "FirstPAM", 90, mondayInf1, 0, 0);
            setAlarm(context, "MondayInf2", "FirstPAM", 100, mondayInf2, 0, 0);
        }

        /*SOFTWARE ARCHITECTURE AND DESIGN*/
        if (courses.contains(SoftwareArchitecture.getName())) {
            setAlarm(context, "TuesdaySAD", "FirstPAM", 110, tuesdaySAD, 0, 0);
            setAlarm(context, "ThursdaySAD", "FirstPAM", 120, thursdaySAD, 0, 0);
        }
    }


    /********** FIRST POSTLECTURE **********/
    public void createFirstPostlecture(Context context, String courses) {

        /*LINEAR ALGEBRA*/
        if (courses.contains(LinearAlgebra.getName())) {
            setAlarm(context, "MondayLA", "FirstPostlecture", 370, mondayLA, 0, 50);  //hour and time addition 1 and 15
            setAlarm(context, "WednesdayLA", "FirstPostlecture", 380, wednesdayLA, 0, 50);
        }

        /*PROGRAMMING FUNDAMENTALS 2*/
        if (courses.contains(ProgrammingFundamentals.getName())) {
            setAlarm(context, "MondayPF", "FirstPostlecture", 390, mondayPF, 0, 50);
            setAlarm(context, "WednesdayPF", "FirstPostlecture", 400, wednesdayPF, 0, 50);
            setAlarm(context, "FridayPF", "FirstPostlecture", 410, fridayPF, 0, 50);
        }

        /*CYBER COMMUNICATION*/
        if (courses.contains(CyberCommunication.getName())) {
            setAlarm(context, "TuesdayCC", "FirstPostlecture", 420, tuesdayCC, 0, 50);
            setAlarm(context, "WednesdayMCC", "FirstPostlecture", 431, wednesdayMCC, 0, 50);
            setAlarm(context, "WednesdayCC", "FirstPostlecture", 430, wednesdayCC, 0, 50);
            setAlarm(context, "ThursdayMCC", "FirstPostlecture", 441, thursdayMCC, 0, 50);
            setAlarm(context, "ThursdayCC", "FirstPostlecture", 440, thursdayCC, 0, 50);
        }

        /*INFORMATION SECURITY*/
        if (courses.contains(InformationSecurity.getName())) {
            setAlarm(context, "MondayInf1", "FirstPostlecture", 450, mondayInf1, 0, 50);
            setAlarm(context, "MondayInf2", "FirstPostlecture", 460, mondayInf2, 0, 50);
        }

        /*SOFTWARE ARCHITECTURE AND DESIGN*/
        if (courses.contains(SoftwareArchitecture.getName())) {
            setAlarm(context, "TuesdaySAD", "FirstPostlecture", 470, tuesdaySAD, 0, 50);
            setAlarm(context, "ThursdaySAD", "FirstPostlecture", 480, thursdaySAD, 0, 50);
        }
    }

    /********** SECOND POSTLECTURE **********/
    public void createSecondPostlecture(Context context, String courses) {

        /*LINEAR ALGEBRA*/
        if (courses.contains(LinearAlgebra.getName())){
            setAlarm(context, "MondayLA", "SecondPostlecture", 490, mondayLA, 1, 50);  //hour and time addition 1 and 40, why 2 :15?
            setAlarm(context, "WednesdayLA", "SecondPostlecture", 500, wednesdayLA, 1, 50);
        }

        /*PROGRAMMING FUNDAMENTALS 2*/
        if (courses.contains(ProgrammingFundamentals.getName())) {
            setAlarm(context, "MondayPF", "SecondPostlecture", 510, mondayPF, 1, 50);
            setAlarm(context, "WednesdayPF", "SecondPostlecture", 520, wednesdayPF, 1, 50);
            setAlarm(context, "FridayPF", "SecondPostlecture", 530, fridayPF, 1, 50);
        }

        /*CYBER COMMUNICATION*/
        if (courses.contains(CyberCommunication.getName())) {
            setAlarm(context, "TuesdayCC", "SecondPostlecture", 540, tuesdayCC, 1, 50);
            setAlarm(context, "WednesdayMCC", "SecondPostlecture", 551, wednesdayMCC, 1, 50);
            setAlarm(context, "WednesdayCC", "SecondPostlecture", 550, wednesdayCC, 1, 50);
            setAlarm(context, "ThursdayMCC", "SecondPostlecture", 561, thursdayMCC, 1, 50);
            setAlarm(context, "ThursdayCC", "SecondPostlecture", 560, thursdayCC, 1, 50);
        }

        /*INFORMATION SECURITY*/
        if (courses.contains(InformationSecurity.getName())) {
            setAlarm(context, "MondayInf1", "SecondPostlecture", 570, mondayInf1, 1, 50);
            setAlarm(context, "MondayInf2", "SecondPostlecture", 580, mondayInf2, 1, 50);
        }

        /*SOFTWARE ARCHITECTURE AND DESIGN*/
        if (courses.contains(SoftwareArchitecture.getName())) {
            setAlarm(context, "TuesdaySAD", "SecondPostlecture", 590, tuesdaySAD, 1, 50);
            setAlarm(context, "ThursdaySAD", "SecondPostlecture", 600, thursdaySAD, 1, 50);
        }
    }

    public Calendar createCalendar(int day, int hour, int minute){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    public void setAlarm(Context context, String course, String questionnaire, int requestCode, Weekday weekday, int hourAddition, int minuteAddition){
        Intent myIntent = new Intent(context, AlarmNotificationReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        myIntent.putExtra("course", course);
        myIntent.putExtra("questionnaire", questionnaire);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode, myIntent, PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar1 = createCalendar(weekday.getDay2(),weekday.getHour()+hourAddition, weekday.getMinute()+minuteAddition);

        if (calendar1.getTimeInMillis() > System.currentTimeMillis()) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        } else {
            calendar1.add(java.util.Calendar.DAY_OF_MONTH, 7);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        }

    }

}

