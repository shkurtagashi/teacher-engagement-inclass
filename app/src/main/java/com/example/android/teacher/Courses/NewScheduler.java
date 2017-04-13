package com.example.android.teacher.Courses;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.teacher.R;
import com.example.android.teacher.Surveys.SurveyDataActivity;
import com.example.android.teacher.data.User.UserData;

import java.util.Calendar;

/**
 * Created by shkurtagashi on 16.03.17.
 */

public class NewScheduler {
     /*
    Test Case 1 - Two different Courses in one day

    Test Case 2 - One course but two different lectures in one day
     */

    private int firstNotificationTrigger = 1; //Minutes before the lecture 15
    private int secondNotificationTrigger = 1; //Minutes after the lecture 40
    private int thirdNotificationTrigger = 2; //Minutes after the lecture 100


    //Programming Fundamentals 2 - Monday, Wednesday, Friday 10:30 - 12:15
    private Weekday mondayProgrammingFundamentals2 = new Weekday(10, 16, 2); //HOUR, MINUTE, DAY - SUNDAY=1, MONDAY=2 ...
    private Weekday wednesdayProgrammingFundamentals2 = new Weekday(13, 31, 4);
    private Weekday fridayProgrammingFundamentals3 = new Weekday(10, 30, 6);
    private Course programmingFundamentals = new Course(mondayProgrammingFundamentals2, wednesdayProgrammingFundamentals2, fridayProgrammingFundamentals3, "Programming Fundamentals");

    //Linear Algebra - Monday, Wednesday 8:30 - 10:15
    private Weekday mondayLinearAlgebra = new Weekday(8, 30, 2);
    private Weekday wednesdayLinearAlgebra = new Weekday(8, 30, 4);
    private Course linearAlgebra = new Course(mondayLinearAlgebra, wednesdayLinearAlgebra, "Linear Algebra");

    //Information Security on Monday from 13:30 - 17:15
    private Weekday mondayInformationSecurity1 = new Weekday(13, 30, 2); //13-30 Monday
    private Weekday mondayInformationSecurity2 = new Weekday(15, 30, 2); // 15:30 Monday
    private Course InformationSecurity = new Course(mondayInformationSecurity1, mondayInformationSecurity2, "Information Security");

    //Cyber Communication on Tuesday, Wednesday, Thursday from 10:30 - 12:15
    private Weekday tuesdayCyberCommunication = new Weekday(10, 30, 3);
    private Weekday wednesdayCyberCommunication = new Weekday(10, 30, 4);
    private Weekday thursdayCyberCommunication = new Weekday(10, 30, 5);
    private Course CyberCommunication = new Course(tuesdayCyberCommunication, wednesdayCyberCommunication, thursdayCyberCommunication, "Cyber Communication");

    //Software Architecture on Tuesday and Thursday from 13:30 - 15:30
    private Weekday tuesdaySoftwareArchitecture = new Weekday(13, 30, 3);
    private Weekday thursdaySoftwareArchitecture = new Weekday(13, 30, 5);
    private Course SoftwareArchitecture = new Course(tuesdaySoftwareArchitecture, thursdaySoftwareArchitecture, "Software Architecture and Design");


    private void scheduleNotification(Context context, Notification notification, int day, int hour, int minute) {

        Intent notificationIntent = new Intent(context, NotificationAlarmReceiver.class);
        notificationIntent.putExtra(NotificationAlarmReceiver.NOTIFICATION_ID_1, 1);
        notificationIntent.putExtra(NotificationAlarmReceiver.NOTIFICATION_1, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if(calendar.getTimeInMillis() > System.currentTimeMillis()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
            Log.v("Scheduler", "Notification Triggered for this week");
        }else{
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
            Log.v("Scheduler", "Notification Triggered for next week");
        }

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

    }

    private void scheduleSecondNotification(Context context, Notification notification, int day, int hour, int minute) {

        Intent notificationIntent = new Intent(context, NotificationAlarmReceiver.class);
        notificationIntent.putExtra(NotificationAlarmReceiver.NOTIFICATION_ID_2, 2);
        notificationIntent.putExtra(NotificationAlarmReceiver.NOTIFICATION_2, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if(calendar.getTimeInMillis() > System.currentTimeMillis()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
            Log.v("Scheduler", "Notification Triggered for today");
        }
        else{
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
            Log.v("Scheduler", "Notification Triggered for next week");
        }

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

    }

    private void scheduleThirdNotification(Context context, Notification notification, int day, int hour, int minute) {

        Intent notificationIntent = new Intent(context, NotificationAlarmReceiver.class);
        notificationIntent.putExtra(NotificationAlarmReceiver.NOTIFICATION_ID_3, 3);
        notificationIntent.putExtra(NotificationAlarmReceiver.NOTIFICATION_3, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if(calendar.getTimeInMillis() > System.currentTimeMillis()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
            Log.v("Scheduler", "Notification Triggered for today");
        }
        else{
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
            Log.v("Scheduler", "Notification Triggered for next week");
        }

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

    }

    private Notification getNotification(Context context, String content) {
        Intent myIntent = new Intent(context, SurveyDataActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Survey Time");
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.survey);
        return builder.build();
    }

    //Create first PAM 15 minutes before the lecture starts and remove notification 15 after it starts
    //Take the course from the logged in user, the day from the Calendar
    public void createFirstNoification(Context context, String userCourses){

        if(userCourses.contains(linearAlgebra.getName())){
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before  " + linearAlgebra.getName() + " lecture!"), linearAlgebra.getWeekday1().day, linearAlgebra.getWeekday1()._HOUR, linearAlgebra.getWeekday1()._MINUTE);
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + linearAlgebra.getName() + " lecture!"), linearAlgebra.getWeekday2().day, linearAlgebra.getWeekday2()._HOUR, linearAlgebra.getWeekday2()._MINUTE);
        }

        if (userCourses.contains(InformationSecurity.getName())) {
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + InformationSecurity.getName() + " lectures!"), InformationSecurity.getWeekday1().day, InformationSecurity.getWeekday1()._HOUR, InformationSecurity.getWeekday1()._MINUTE);
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + InformationSecurity.getName() + "!"), InformationSecurity.getWeekday2().day, InformationSecurity.getWeekday2()._HOUR, InformationSecurity.getWeekday2()._MINUTE);
        }

        if (userCourses.contains((CyberCommunication.getName()))) {
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday1().day, CyberCommunication.getWeekday1()._HOUR, CyberCommunication.getWeekday1()._MINUTE);
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday2().day, CyberCommunication.getWeekday2()._HOUR, CyberCommunication.getWeekday2()._MINUTE);
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday3().day, CyberCommunication.getWeekday3()._HOUR, CyberCommunication.getWeekday3()._MINUTE);
        }

        if (userCourses.contains(SoftwareArchitecture.getName())) {
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + SoftwareArchitecture.getName() + " lecture!"), SoftwareArchitecture.getWeekday1().day, SoftwareArchitecture.getWeekday1()._HOUR, SoftwareArchitecture.getWeekday1()._MINUTE);
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + SoftwareArchitecture.getName() + " lecture!"), SoftwareArchitecture.getWeekday2().day, SoftwareArchitecture.getWeekday2()._HOUR, SoftwareArchitecture.getWeekday2()._MINUTE);
        }

        if(userCourses.contains(programmingFundamentals.getName())){
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday1().day, programmingFundamentals.getWeekday1()._HOUR, programmingFundamentals.getWeekday1()._MINUTE);
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday2().day, programmingFundamentals.getWeekday2()._HOUR, programmingFundamentals.getWeekday2()._MINUTE);
            scheduleNotification(context, getNotification(context, "Please do not forget to complete the surveys before " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday3().day, programmingFundamentals.getWeekday3()._HOUR, programmingFundamentals.getWeekday3()._MINUTE);
        }
    }

    /*
    *
    * Create second notification, which comes during the break
    */
    public void createSecondNotification(Context context, String userCourses){


        if(userCourses.contains(linearAlgebra.getName())){
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + linearAlgebra.getName() + " lecture!"), linearAlgebra.getWeekday1().day, linearAlgebra.getWeekday1()._HOUR, linearAlgebra.getWeekday1()._MINUTE + secondNotificationTrigger);
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + linearAlgebra.getName() + " lecture!"), linearAlgebra.getWeekday2().day, linearAlgebra.getWeekday2()._HOUR, linearAlgebra.getWeekday2()._MINUTE + secondNotificationTrigger);
        }

        if (userCourses.contains(InformationSecurity.getName())) {
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + InformationSecurity.getName() + " lectures!"), InformationSecurity.getWeekday1().day, InformationSecurity.getWeekday1()._HOUR, InformationSecurity.getWeekday1()._MINUTE + secondNotificationTrigger);
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + InformationSecurity.getName() + "!"), InformationSecurity.getWeekday2().day, InformationSecurity.getWeekday2()._HOUR, InformationSecurity.getWeekday2()._MINUTE + secondNotificationTrigger);
        }

        if (userCourses.contains((CyberCommunication.getName()))) {
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday1().day, CyberCommunication.getWeekday1()._HOUR, CyberCommunication.getWeekday1()._MINUTE + secondNotificationTrigger);
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday2().day, CyberCommunication.getWeekday2()._HOUR, CyberCommunication.getWeekday2()._MINUTE + secondNotificationTrigger);
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday3().day, CyberCommunication.getWeekday3()._HOUR, CyberCommunication.getWeekday3()._MINUTE + secondNotificationTrigger);
        }

        if (userCourses.contains(SoftwareArchitecture.getName())) {
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + SoftwareArchitecture.getName() + " lecture!"), SoftwareArchitecture.getWeekday1().day, SoftwareArchitecture.getWeekday1()._HOUR, SoftwareArchitecture.getWeekday1()._MINUTE + secondNotificationTrigger);
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + SoftwareArchitecture.getName() + " lecture!"), SoftwareArchitecture.getWeekday2().day, SoftwareArchitecture.getWeekday2()._HOUR, SoftwareArchitecture.getWeekday2()._MINUTE + secondNotificationTrigger);
        }

        if(userCourses.contains(programmingFundamentals.getName())){
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday1().day, programmingFundamentals.getWeekday1()._HOUR, programmingFundamentals.getWeekday1()._MINUTE + secondNotificationTrigger);
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday2().day, programmingFundamentals.getWeekday2()._HOUR, programmingFundamentals.getWeekday2()._MINUTE + secondNotificationTrigger);
            scheduleSecondNotification(context, getNotification(context, "Survey during the break of " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday3().day, programmingFundamentals.getWeekday3()._HOUR, programmingFundamentals.getWeekday3()._MINUTE + secondNotificationTrigger);
        }

    }

    ////Create second PAM 5 minutes before the beak and remove notification 5 after break
    public void createThirdNotification(Context context, String userCourses){

        if(userCourses.contains(linearAlgebra.getName())){
            scheduleThirdNotification(context, getNotification(context, "Survey after " + linearAlgebra.getName() + " lecture!"), linearAlgebra.getWeekday1().day, linearAlgebra.getWeekday1()._HOUR, linearAlgebra.getWeekday1()._MINUTE + thirdNotificationTrigger);
            scheduleThirdNotification(context, getNotification(context, "Survey after " + linearAlgebra.getName() + " lecture!"), linearAlgebra.getWeekday2().day, linearAlgebra.getWeekday2()._HOUR, linearAlgebra.getWeekday2()._MINUTE + thirdNotificationTrigger);
        }

        if (userCourses.contains(InformationSecurity.getName())) {
            scheduleThirdNotification(context, getNotification(context, "Survey after " + InformationSecurity.getName() + " lectures!"), InformationSecurity.getWeekday1().day, InformationSecurity.getWeekday1()._HOUR, InformationSecurity.getWeekday1()._MINUTE + thirdNotificationTrigger);
            scheduleThirdNotification(context, getNotification(context, "Survey after the second lecture in " + InformationSecurity.getName() + "!"), InformationSecurity.getWeekday2().day, InformationSecurity.getWeekday2()._HOUR, InformationSecurity.getWeekday2()._MINUTE + thirdNotificationTrigger);
        }

        if (userCourses.contains((CyberCommunication.getName()))) {
            scheduleThirdNotification(context, getNotification(context, "Survey after " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday1().day, CyberCommunication.getWeekday1()._HOUR, CyberCommunication.getWeekday1()._MINUTE + thirdNotificationTrigger);
            scheduleThirdNotification(context, getNotification(context, "Survey after " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday2().day, CyberCommunication.getWeekday2()._HOUR, CyberCommunication.getWeekday2()._MINUTE + thirdNotificationTrigger);
            scheduleThirdNotification(context, getNotification(context, "Survey after " + CyberCommunication.getName() + " lecture!"), CyberCommunication.getWeekday3().day, CyberCommunication.getWeekday3()._HOUR, CyberCommunication.getWeekday3()._MINUTE + thirdNotificationTrigger);
        }

        if (userCourses.contains(SoftwareArchitecture.getName())) {
            scheduleThirdNotification(context, getNotification(context, "Survey after " + SoftwareArchitecture.getName() + " lecture!"), SoftwareArchitecture.getWeekday1().day, SoftwareArchitecture.getWeekday1()._HOUR, SoftwareArchitecture.getWeekday1()._MINUTE + thirdNotificationTrigger);
            scheduleThirdNotification(context, getNotification(context, "Survey after " + SoftwareArchitecture.getName() + " lecture!"), SoftwareArchitecture.getWeekday2().day, SoftwareArchitecture.getWeekday2()._HOUR, SoftwareArchitecture.getWeekday2()._MINUTE + thirdNotificationTrigger);
        }

        if(userCourses.contains(programmingFundamentals.getName())){
            scheduleThirdNotification(context, getNotification(context, "Survey after " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday1().day, programmingFundamentals.getWeekday1()._HOUR, programmingFundamentals.getWeekday1()._MINUTE + thirdNotificationTrigger);
            scheduleThirdNotification(context, getNotification(context, "Survey after " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday2().day, programmingFundamentals.getWeekday2()._HOUR, programmingFundamentals.getWeekday2()._MINUTE + thirdNotificationTrigger);
            scheduleThirdNotification(context, getNotification(context, "Survey after " + programmingFundamentals.getName() + " lecture!"), programmingFundamentals.getWeekday3().day, programmingFundamentals.getWeekday3()._HOUR, programmingFundamentals.getWeekday3()._MINUTE + thirdNotificationTrigger);
        }

    }

}
