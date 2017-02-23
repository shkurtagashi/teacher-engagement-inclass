package com.example.android.teacher.Courses;

import android.content.Context;

import com.aware.Aware;
import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;
import com.aware.utils.Scheduler;
import com.aware.utils.Scheduler.Schedule;
import com.example.android.teacher.data.User.UserData;

import org.json.JSONException;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static com.example.android.teacher.R.id.course;

/**
 * Created by shkurtagashi on 11.02.17.
 */

public class MyScheduler {
    /*
    Test Case 1 - Two different Courses in one day

    Test Case 2 - One course but two different lectures in one day
     */

    private int firstPamTrigger = -1; //Minutes before the lecture

    private int firstESMTrigger = 2; //Minutes after the lecture starts
    private int secondPamTrigger = 1; //Minutes after the lecture starts
    private int thirdPamTrigger = 3; //Minutes after the lecture starts
    private int secondESMTrigger = 4; //Minutes after the lecture starts

    private int firstPamThreshold = 3; //Keep the notification alive x minutes after it fires- UNTIL 7 PM

    private int secondPamThreshold = 3; //Minutes after it fires
    private int firstESMThreshold = 45; //Minutes after it fires

    private int thirdPamThreshold = 60; //Minutes after it fires
    private int secondESMThreshold = 60; //Minutes after it fires

    //Programming Fundamentals 2 - Monday, Wednesday, Friday 10:30 - 12:15
    private Weekday mondayProgrammingFundamentals2 = new Weekday(10, 30, "Monday");
    private Weekday wednesdayProgrammingFundamentals2 = new Weekday(10, 30, "Wednesday");
    private Weekday fridayProgrammingFundamentals3 = new Weekday(10, 30, "Friday");
    private Course programmingFundamentals = new Course(mondayProgrammingFundamentals2, wednesdayProgrammingFundamentals2, fridayProgrammingFundamentals3, "Programming Fundamentals 2");


    //Linear Algebra - Monday, Wednesday 8:30 - 10:15
    private Weekday mondayLinearAlgebra = new Weekday(8, 30, "Monday");
    private Weekday wednesdayLinearAlgebra = new Weekday(8, 30, "Wednesday");
    private Course linearAlgebra = new Course(mondayLinearAlgebra, wednesdayLinearAlgebra, "Linear Algebra");

    //Information Security on Monday from 13:30 - 17:15
    private Weekday mondayInformationSecurity1 = new Weekday(22, 26, "Tuesday"); //13-30 Monday
    private Weekday mondayInformationSecurity2 = new Weekday(15, 28, "Tuesday"); // 15:30 Monday
    private Course InformationSecurity = new Course(mondayInformationSecurity1, mondayInformationSecurity2, "Information Security");

    //Cyber Communication on Tuesday, Wednesday, Thursday from 10:30 - 12:15
    private Weekday tuesdayCyberCommunication = new Weekday(10, 30, "Tuesday");
    private Weekday wednesdayCyberCommunication = new Weekday(10, 30, "Wednesday");
    private Weekday thursdayCyberCommunication = new Weekday(10, 30, "Thursday");
    private Course CyberCommunication = new Course(tuesdayCyberCommunication, wednesdayCyberCommunication, thursdayCyberCommunication, "Cyber Communication");

    //Software Architecture on Tuesday and Thursday from 13:30 - 15:15
    private Weekday tuesdaySoftwareArchitecture = new Weekday(13, 30, "Tuesday");
    private Weekday thursdaySoftwareArchitecture = new Weekday(16, 2, "Thursday");
    private Course SoftwareArchitecture = new Course(tuesdaySoftwareArchitecture, thursdaySoftwareArchitecture, "Software Architecture and Design");



    ////Create first PAM 15 minutes before the lecture starts and remove notification 15 after it starts
    //Take the course from the logged in user, the day from the Calendar
    public void createFirstPAM(Context context, String userCourses){
        try {

            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM");
            q1.setSubmitButton("Done");
            q1.setNotificationRetry(3);
            q1.setNotificationTimeout(60*firstPamThreshold); //Different in x cases

            factory.addESM(q1);

            if(userCourses.contains(linearAlgebra.getName())){
                q1.setInstructions("Pick the closest to how you feel now before the " + linearAlgebra.getName() + " lecture!");
                Schedule first_pam = new Schedule("first_pam" + UserData._username);
                first_pam.addWeekday(linearAlgebra.getWeekday1()._DAY);
                first_pam.addHour(linearAlgebra.getWeekday1()._HOUR);
                first_pam.addMinute(linearAlgebra.getWeekday1()._MINUTE + firstPamTrigger);

                first_pam.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam);

                Schedule first_pam0 = new Schedule("first_pam0" + UserData._username);
                first_pam0.addWeekday(linearAlgebra.getWeekday2()._DAY);
                first_pam0.addHour(linearAlgebra.getWeekday2()._HOUR);
                first_pam0.addMinute(linearAlgebra.getWeekday2()._MINUTE + firstPamTrigger);

                first_pam0.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam0.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam0.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam0);
            }


            if (userCourses.contains(InformationSecurity.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before the " + InformationSecurity.getName() + " lecture!");

                Schedule first_pam1 = new Schedule("first_pam1" + UserData._username);
                first_pam1.addWeekday(InformationSecurity.getWeekday1()._DAY);
                first_pam1.addHour(InformationSecurity.getWeekday1()._HOUR);
                first_pam1.addMinute(InformationSecurity.getWeekday1()._MINUTE + firstPamTrigger);

                first_pam1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                first_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam1);

                Schedule first_pam2 = new Schedule("first_pam2" + UserData._username);
                first_pam2.addHour(InformationSecurity.getWeekday2()._HOUR);
                first_pam2.addMinute(InformationSecurity.getWeekday2()._MINUTE + firstPamTrigger);
                first_pam2.addWeekday(InformationSecurity.getWeekday2()._DAY);

                first_pam2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                first_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam2);
            }

            if (userCourses.contains((CyberCommunication.getName()))) {
                q1.setInstructions("Pick the closest to how you feel now before the " + CyberCommunication.getName() + " lecture!");

                Schedule first_pam3 = new Schedule("first_pam3"+UserData._username);
                first_pam3.addHour(CyberCommunication.getWeekday1()._HOUR);
                first_pam3.addMinute(CyberCommunication.getWeekday1()._MINUTE + firstPamTrigger);
                first_pam3.addWeekday(CyberCommunication.getWeekday1()._DAY);

                first_pam3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                first_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam3);


                Schedule first_pam4 = new Schedule("first_pam4"+UserData._username);
                first_pam4.addHour(CyberCommunication.getWeekday2()._HOUR);
                first_pam4.addMinute(CyberCommunication.getWeekday2()._MINUTE + firstPamTrigger);
                first_pam4.addWeekday(CyberCommunication.getWeekday2()._DAY);

                first_pam4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                first_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam4);

                Schedule first_pam5 = new Schedule("first_pam5"+UserData._username);
                first_pam5.addHour(CyberCommunication.getWeekday3()._HOUR);
                first_pam5.addMinute(CyberCommunication.getWeekday3()._MINUTE + firstPamTrigger);
                first_pam5.addWeekday(CyberCommunication.getWeekday3()._DAY);

                first_pam5.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam5);
            }

            if (userCourses.contains(SoftwareArchitecture.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + SoftwareArchitecture.getName() + "!");
                Schedule first_pam6 = new Schedule("first_pam6" + UserData._username);
                first_pam6.addHour(SoftwareArchitecture.getWeekday1()._HOUR);
                first_pam6.addMinute(SoftwareArchitecture.getWeekday1()._MINUTE + firstPamTrigger);
                first_pam6.addWeekday(SoftwareArchitecture.getWeekday1()._DAY);

                first_pam6.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam6);


                Schedule first_pam7 = new Schedule("first_pam7" + UserData._username);
                first_pam7.addHour(SoftwareArchitecture.getWeekday2()._HOUR);
                first_pam7.addMinute(SoftwareArchitecture.getWeekday2()._MINUTE + firstPamTrigger);
                first_pam7.addWeekday(SoftwareArchitecture.getWeekday2()._DAY);

                first_pam7.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, first_pam7);
            }

            if(userCourses.contains(programmingFundamentals.getName())){
                q1.setInstructions("Pick the closest to how you feel right now before the " + programmingFundamentals.getName() + " lecture");

                Schedule first_pam8 = new Schedule("first_pam8" + UserData._username);
                first_pam8.addWeekday(programmingFundamentals.getWeekday1()._DAY);
                first_pam8.addHour(programmingFundamentals.getWeekday1()._HOUR);
                first_pam8.addMinute(programmingFundamentals.getWeekday1()._MINUTE + firstPamTrigger);

                first_pam8.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam8.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam8);

                Schedule first_pam9 = new Schedule("first_pam9" + UserData._username);
                first_pam9.addWeekday(programmingFundamentals.getWeekday2()._DAY);
                first_pam9.addHour(programmingFundamentals.getWeekday2()._HOUR);
                first_pam9.addMinute(programmingFundamentals.getWeekday2()._MINUTE + firstPamTrigger);

                first_pam9.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam9.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam9);

                Schedule first_pam10 = new Schedule("first_pam10" + UserData._username);
                first_pam10.addWeekday(programmingFundamentals.getWeekday3()._DAY);
                first_pam10.addHour(programmingFundamentals.getWeekday3()._HOUR);
                first_pam10.addMinute(programmingFundamentals.getWeekday3()._MINUTE + firstPamTrigger);

                first_pam10.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_pam10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_pam10.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_pam10);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ////Create second PAM 5 minutes before the beak and remove notification 5 after break
    public void createSecondPAM(Context context, String userCourses){
        try {

            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM");
            q1.setSubmitButton("Done");
            q1.setNotificationTimeout(60*secondPamThreshold); //How long does the notification last?

//            ESM_QuickAnswer esmQuickAnswer2 = new ESM_QuickAnswer();
//            esmQuickAnswer2.addQuickAnswer("Finish")
//                    .setTitle("Cookies on the way ...")
//                    .setExpirationThreshold(60*firstPamThreshold)
//                    .setInstructions("Thank you very much for your participation!");


            factory.addESM(q1);
//            factory.addESM(esmQuickAnswer2);


            if(userCourses.contains(linearAlgebra.getName())){
                q1.setInstructions("Pick the closest to how you feel now after first part of " + linearAlgebra.getName() + " lecture!");
                Schedule second_pam1 = new Schedule("second_pam1" + UserData._username);
                second_pam1.addWeekday(linearAlgebra.getWeekday1()._DAY);
                second_pam1.addHour(linearAlgebra.getWeekday1()._HOUR);
                second_pam1.addMinute(linearAlgebra.getWeekday1()._MINUTE + secondPamTrigger);

                second_pam1.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam1);

                Schedule second_pam2 = new Schedule("second_pam2" + UserData._username);
                second_pam2.addWeekday(linearAlgebra.getWeekday2()._DAY);
                second_pam2.addHour(linearAlgebra.getWeekday2()._HOUR);
                second_pam2.addMinute(linearAlgebra.getWeekday2()._MINUTE + secondPamTrigger);

                second_pam2.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam2);
            }

            if(userCourses.contains(InformationSecurity.getName())){
                q1.setInstructions("Pick the closest to how you feel now after first part of " + InformationSecurity.getName() + " lecture!");

                Schedule second_pam3 = new Schedule("second_pam3"+UserData._username);

                second_pam3.addWeekday(mondayInformationSecurity1._DAY);
                second_pam3.addHour(mondayInformationSecurity1._HOUR);
                second_pam3.addMinute(mondayInformationSecurity1._MINUTE + secondPamTrigger);

                second_pam3.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam3);

                Schedule second_pam4 = new Schedule("second_pam4"+UserData._username);

                second_pam4.addWeekday(InformationSecurity.getWeekday2()._DAY);
                second_pam4.addHour(InformationSecurity.getWeekday2()._HOUR);
                second_pam4.addMinute(InformationSecurity.getWeekday2()._MINUTE + secondPamTrigger);

                second_pam4.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam4);
            }

            if(userCourses.contains(CyberCommunication.getName())){
                q1.setInstructions("Pick the closest to how you feel now after first part of " + CyberCommunication.getName() + "!");

                Schedule second_pam5 = new Schedule("second_pam5"+UserData._username);
                second_pam5.addWeekday(CyberCommunication.getWeekday1()._DAY);
                second_pam5.addHour(CyberCommunication.getWeekday1()._HOUR);
                second_pam5.addMinute(CyberCommunication.getWeekday1()._MINUTE + secondPamTrigger);

                second_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                second_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, second_pam5);


                Schedule second_pam6 = new Schedule("second_pam6" + UserData._username);
                second_pam6.addWeekday(CyberCommunication.getWeekday2()._DAY);
                second_pam6.addHour(CyberCommunication.getWeekday2()._HOUR);
                second_pam6.addMinute(CyberCommunication.getWeekday2()._MINUTE + secondPamTrigger);

                second_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                second_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, second_pam6);


                Schedule second_pam7 = new Schedule("second_pam7" + UserData._username);
                second_pam7.addWeekday(CyberCommunication.getWeekday3()._DAY);
                second_pam7.addHour(CyberCommunication.getWeekday3()._HOUR);
                second_pam7.addMinute(CyberCommunication.getWeekday3()._MINUTE + secondPamTrigger);

                second_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                second_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, second_pam7);


            }

            if(userCourses.contains(SoftwareArchitecture.getName())){
                q1.setInstructions("Pick the closest to how you feel now after first part of " + SoftwareArchitecture.getName() + " lecture!");

                Schedule second_pam8 = new Schedule("second_pam8" + UserData._username);
                second_pam8.addWeekday(SoftwareArchitecture.getWeekday1()._DAY);
                second_pam8.addHour(SoftwareArchitecture.getWeekday1()._HOUR);
                second_pam8.addMinute(SoftwareArchitecture.getWeekday1()._MINUTE + secondPamTrigger);

                second_pam8.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam8.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam8);
                Schedule second_pam9 = new Schedule("second_pam9" + UserData._username);
                second_pam9.addWeekday(SoftwareArchitecture.getWeekday2()._DAY);
                second_pam9.addHour(SoftwareArchitecture.getWeekday2()._HOUR);
                second_pam9.addMinute(SoftwareArchitecture.getWeekday2()._MINUTE + secondPamTrigger);

                second_pam9.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam9.addActionExtra(ESM.EXTRA_ESM, factory.build());
                Scheduler.saveSchedule(context, second_pam9);

            }

            if(userCourses.contains(programmingFundamentals.getName())){
                q1.setInstructions("Pick the closest to how you feel right now after the first lecture of " + programmingFundamentals.getName() + "!");

                Schedule second_pam10 = new Schedule("second_pam10" + UserData._username);
                second_pam10.addWeekday(programmingFundamentals.getWeekday1()._DAY);
                second_pam10.addHour(programmingFundamentals.getWeekday1()._HOUR);
                second_pam10.addMinute(programmingFundamentals.getWeekday1()._MINUTE + secondPamTrigger);

                second_pam10.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam10.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam10);

                Schedule second_pam11 = new Schedule("second_pam11" + UserData._username);
                second_pam11.addWeekday(programmingFundamentals.getWeekday2()._DAY);
                second_pam11.addHour(programmingFundamentals.getWeekday2()._HOUR);
                second_pam11.addMinute(programmingFundamentals.getWeekday2()._MINUTE + secondPamTrigger);

                second_pam11.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam11.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam11);

                Schedule second_pam12 = new Schedule("second_pam12" + UserData._username);
                second_pam12.addWeekday(programmingFundamentals.getWeekday3()._DAY);
                second_pam12.addHour(programmingFundamentals.getWeekday3()._HOUR);
                second_pam12.addMinute(programmingFundamentals.getWeekday3()._MINUTE + secondPamTrigger);

                second_pam12.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam12.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam12);
            }

//            factory.addESM(q1);
//            factory.addESM(esmQuickAnswer2);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ////Create second PAM 5 minutes before the beak and remove notification 5 after break
    public void createThirdPAM(Context context, String userCourses){
        try {

            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM");
            q1.setSubmitButton("Done");
            q1.setNotificationTimeout(60*thirdPamThreshold); //How long does the notification last?

//            ESM_QuickAnswer esmQuickAnswer2 = new ESM_QuickAnswer();
//            esmQuickAnswer2.addQuickAnswer("Finish")
//                    .setTitle("Cookies on the way ...")
//                    .setExpirationThreshold(60*firstPamThreshold)
//                    .setInstructions("Thank you very much for your participation!");


            factory.addESM(q1);
//            factory.addESM(esmQuickAnswer2);


            if(userCourses.contains(linearAlgebra.getName())){
                q1.setInstructions("Pick the closest to how you feel now after second part of " + linearAlgebra.getName() + " lecture!");
                Schedule third_pam1 = new Schedule("third_pam1" + UserData._username);
                third_pam1.addWeekday(linearAlgebra.getWeekday1()._DAY);
                third_pam1.addHour(linearAlgebra.getWeekday1()._HOUR);
                third_pam1.addMinute(linearAlgebra.getWeekday1()._MINUTE + thirdPamTrigger);

                third_pam1.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                third_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, third_pam1);

                Schedule third_pam2 = new Schedule("third_pam2" + UserData._username);
                third_pam2.addWeekday(linearAlgebra.getWeekday2()._DAY);
                third_pam2.addHour(linearAlgebra.getWeekday2()._HOUR);
                third_pam2.addMinute(linearAlgebra.getWeekday2()._MINUTE + thirdPamTrigger);

                third_pam2.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                third_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, third_pam2);
            }

            if(userCourses.contains(InformationSecurity.getName())){
                q1.setInstructions("Pick the closest to how you feel now after the second part of " + InformationSecurity.getName() + " lecture!");

                Schedule third_pam3 = new Schedule("third_pam3"+UserData._username);

                third_pam3.addWeekday(mondayInformationSecurity1._DAY);
                third_pam3.addHour(mondayInformationSecurity1._HOUR);
                third_pam3.addMinute(mondayInformationSecurity1._MINUTE + thirdPamTrigger);

                third_pam3.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                third_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, third_pam3);

                Schedule third_pam4 = new Schedule("third_pam4"+UserData._username);
                third_pam4.addWeekday(InformationSecurity.getWeekday2()._DAY);
                third_pam4.addHour(InformationSecurity.getWeekday2()._HOUR);
                third_pam4.addMinute(InformationSecurity.getWeekday2()._MINUTE + thirdPamTrigger);
                third_pam4.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                third_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, third_pam4);
            }

            if(userCourses.contains(CyberCommunication.getName())){
                q1.setInstructions("Pick the closest to how you feel now after second part of " + CyberCommunication.getName() + "!");

                Schedule third_pam5 = new Schedule("third_pam5"+UserData._username);
                third_pam5.addWeekday(CyberCommunication.getWeekday1()._DAY);
                third_pam5.addHour(CyberCommunication.getWeekday1()._HOUR);
                third_pam5.addMinute(CyberCommunication.getWeekday1()._MINUTE + thirdPamTrigger);

                third_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                third_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, third_pam5);


                Schedule third_pam6 = new Schedule("third_pam6" + UserData._username);
                third_pam6.addWeekday(CyberCommunication.getWeekday2()._DAY);
                third_pam6.addHour(CyberCommunication.getWeekday2()._HOUR);
                third_pam6.addMinute(CyberCommunication.getWeekday2()._MINUTE + thirdPamTrigger);

                third_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                third_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, third_pam6);


                Schedule third_pam7 = new Schedule("third_pam7" + UserData._username);
                third_pam7.addWeekday(CyberCommunication.getWeekday3()._DAY);
                third_pam7.addHour(CyberCommunication.getWeekday3()._HOUR);
                third_pam7.addMinute(CyberCommunication.getWeekday3()._MINUTE + thirdPamTrigger);

                third_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                third_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, third_pam7);


            }

            if(userCourses.contains(SoftwareArchitecture.getName())){
                q1.setInstructions("Pick the closest to how you feel now after second part of " + SoftwareArchitecture.getName() + " lecture!");

                Schedule second_pam8 = new Schedule("second_pam8" + UserData._username);
                second_pam8.addWeekday(SoftwareArchitecture.getWeekday1()._DAY);
                second_pam8.addHour(SoftwareArchitecture.getWeekday1()._HOUR);
                second_pam8.addMinute(SoftwareArchitecture.getWeekday1()._MINUTE + thirdPamTrigger);

                second_pam8.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam8.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_pam8);
                Schedule second_pam9 = new Schedule("second_pam9" + UserData._username);
                second_pam9.addWeekday(SoftwareArchitecture.getWeekday2()._DAY);
                second_pam9.addHour(SoftwareArchitecture.getWeekday2()._HOUR);
                second_pam9.addMinute(SoftwareArchitecture.getWeekday2()._MINUTE + thirdPamTrigger);

                second_pam9.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_pam9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_pam9.addActionExtra(ESM.EXTRA_ESM, factory.build());
                Scheduler.saveSchedule(context, second_pam9);

            }

            if(userCourses.contains(programmingFundamentals.getName())){
                q1.setInstructions("Pick the closest to how you feel right now after the second lecture of " + programmingFundamentals.getName() + "!");

                Schedule third_pam10 = new Schedule("third_pam10" + UserData._username);
                third_pam10.addWeekday(programmingFundamentals.getWeekday1()._DAY);
                third_pam10.addHour(programmingFundamentals.getWeekday1()._HOUR);
                third_pam10.addMinute(programmingFundamentals.getWeekday1()._MINUTE + secondPamTrigger);

                third_pam10.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                third_pam10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam10.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, third_pam10);

                Schedule third_pam11 = new Schedule("third_pam11" + UserData._username);
                third_pam11.addWeekday(programmingFundamentals.getWeekday2()._DAY);
                third_pam11.addHour(programmingFundamentals.getWeekday2()._HOUR);
                third_pam11.addMinute(programmingFundamentals.getWeekday2()._MINUTE + secondPamTrigger);

                third_pam11.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                third_pam11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam11.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, third_pam11);

                Schedule third_pam12 = new Schedule("third_pam12" + UserData._username);
                third_pam12.addWeekday(programmingFundamentals.getWeekday3()._DAY);
                third_pam12.addHour(programmingFundamentals.getWeekday3()._HOUR);
                third_pam12.addMinute(programmingFundamentals.getWeekday3()._MINUTE + secondPamTrigger);

                third_pam12.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                third_pam12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                third_pam12.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, third_pam12);
            }

//            factory.addESM(q1);
//            factory.addESM(esmQuickAnswer2);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ////Create first post lecture survey
    public void createFirstPostLectureESM(Context context, String userCourses) {
        try {

            ESMFactory factory = new ESMFactory();

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.setInstructions("I loved teaching this lecture.");
            esmRadio1.setTitle("Post Lecture Survey (1/5)");


            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.setInstructions("I was excited about teaching this lecture.");
            esmRadio2.setTitle("Post Lecture Survey (2/5)");


            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.setInstructions("I felt happy while teaching this lecture.");
            esmRadio3.setTitle("Post Lecture Survey (3/5)");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.setInstructions("I found teaching this lecture fun.");
            esmRadio4.setTitle("Post Lecture Survey (4/5)");

            ESM_Freetext esmFreeText = new ESM_Freetext();
            esmFreeText.setTitle("Post Lecture Survey")
                    .setSubmitButton("Finish")
                    .setExpirationThreshold(60*firstESMThreshold)
                    .setInstructions("Please describe the moment(s) during which you felt particularly engaged");


            ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
            esmQuickAnswer.addQuickAnswer("Yes")
                    .addQuickAnswer("No")
                    .setTitle("Post Lecture Survey (5/5)")
                    .setExpirationThreshold(60*firstESMThreshold)
                    .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                    .addFlow("Yes", esmFreeText.build());



            ArrayList<ESM_Radio> esms = new ArrayList<>();
            esms.add(esmRadio1);
            esms.add(esmRadio2);
            esms.add(esmRadio3);
            esms.add(esmRadio4);

            for (ESM_Radio esmRadio : esms) {
                esmRadio.addRadio("Strongly Agree")
                        .addRadio("Agree")
                        .addRadio("Neutral")
                        .addRadio("Disagree")
                        .addRadio("Strongly Disagree")
                        .setSubmitButton("Next")
                        .setTitle("Survey about first lecture");
            }

            factory.addESM(esmRadio1);
            factory.addESM(esmRadio2);
            factory.addESM(esmRadio3);
            factory.addESM(esmRadio4);
            factory.addESM(esmQuickAnswer);




            if (userCourses.contains(linearAlgebra.getName())) {
                esmRadio1.setTitle("Survey about the first lecture in " + linearAlgebra.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the first lecture in " + linearAlgebra.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the first lecture in " + linearAlgebra.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the first lecture in " + linearAlgebra.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the first lecture in " + linearAlgebra.getName());
                esmFreeText.setTitle("Survey about the first lecture in " + linearAlgebra.getName() + " (5/5)");

                Schedule first_esm1 = new Schedule("first_esm1" + UserData._username);

                first_esm1.addWeekday(linearAlgebra.getWeekday1()._DAY);
                first_esm1.addHour(linearAlgebra.getWeekday1()._HOUR);
                first_esm1.addMinute(linearAlgebra.getWeekday1()._MINUTE + firstESMTrigger);

                first_esm1.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm1);

                Schedule first_esm2 = new Schedule("first_esm2" + UserData._username);

                first_esm2.addWeekday(linearAlgebra.getWeekday2()._DAY);
                first_esm2.addHour(linearAlgebra.getWeekday2()._HOUR);
                first_esm2.addMinute(linearAlgebra.getWeekday2()._MINUTE + firstESMTrigger);

                first_esm2.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm2);
            }

            if (userCourses.contains(InformationSecurity.getName())) {
                esmRadio1.setTitle("Survey about the first lecture in " + InformationSecurity.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the first lecture in " + InformationSecurity.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the first lecture in " + InformationSecurity.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the first lecture in " + InformationSecurity.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the first lecture in " + InformationSecurity.getName());
                esmFreeText.setTitle("Survey about the first lecture in " + InformationSecurity.getName() + " (5/5)");

                Schedule first_esm3 = new Schedule("first_esm3" + UserData._username);
                first_esm3.addWeekday(mondayInformationSecurity1._DAY);
                first_esm3.addHour(mondayInformationSecurity1._HOUR);
                first_esm3.addMinute(mondayInformationSecurity1._MINUTE + firstESMTrigger);

                first_esm3.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm3);

                Schedule first_esm4 = new Schedule("first_esm4" + UserData._username);
                first_esm4.addWeekday(mondayInformationSecurity2._DAY);
                first_esm4.addHour(mondayInformationSecurity2._HOUR);
                first_esm4.addMinute(mondayInformationSecurity2._MINUTE + firstESMTrigger);

                first_esm4.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm4);
            }

            if (userCourses.contains(CyberCommunication.getName())) {
                esmRadio1.setTitle("Survey about the first lecture in " + CyberCommunication.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the first lecture in " + CyberCommunication.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the first lecture in " + CyberCommunication.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the first lecture in " + CyberCommunication.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the first lecture in " + CyberCommunication.getName());
                esmFreeText.setTitle("Survey about the first lecture in " + CyberCommunication.getName() + " (5/5)");

                Schedule first_esm5 = new Schedule("first_esm5" + UserData._username);
                first_esm5.addWeekday(CyberCommunication.getWeekday1()._DAY);
                first_esm5.addHour(CyberCommunication.getWeekday1()._HOUR);
                first_esm5.addMinute(CyberCommunication.getWeekday1()._MINUTE + firstESMTrigger);
                first_esm5.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm5.addActionExtra(ESM.EXTRA_ESM, factory.build());
                Scheduler.saveSchedule(context, first_esm5);

                Schedule first_esm6 = new Schedule("first_esm6" + UserData._username);
                first_esm6.addWeekday(CyberCommunication.getWeekday2()._DAY);
                first_esm6.addHour(CyberCommunication.getWeekday2()._HOUR);
                first_esm6.addMinute(CyberCommunication.getWeekday2()._MINUTE + firstESMTrigger);
                first_esm6.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm6.addActionExtra(ESM.EXTRA_ESM, factory.build());
                Scheduler.saveSchedule(context, first_esm6);

                Schedule first_esm7 = new Schedule("first_esm7" + UserData._username);
                first_esm7.addWeekday(CyberCommunication.getWeekday3()._DAY);
                first_esm7.addHour(CyberCommunication.getWeekday3()._HOUR);
                first_esm7.addMinute(CyberCommunication.getWeekday3()._MINUTE + firstESMTrigger);
                first_esm7.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm7);

            }

            if (userCourses.contains(SoftwareArchitecture.getName())) {
                esmRadio1.setTitle("Survey about the first lecture in " + SoftwareArchitecture.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the first lecture in " + SoftwareArchitecture.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the first lecture in " + SoftwareArchitecture.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the first lecture in " + SoftwareArchitecture.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the first lecture in " + SoftwareArchitecture.getName());
                esmFreeText.setTitle("Survey about the first lecture in " + SoftwareArchitecture.getName() + " (5/5)");

                Schedule first_esm8 = new Schedule("first_esm8" + UserData._username);
                first_esm8.addWeekday(SoftwareArchitecture.getWeekday1()._DAY);
                first_esm8.addHour(SoftwareArchitecture.getWeekday1()._HOUR);
                first_esm8.addMinute(SoftwareArchitecture.getWeekday1()._MINUTE + firstESMTrigger);
                first_esm8.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm8.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm8);

                Schedule first_esm9 = new Schedule("first_esm9" + UserData._username);
                first_esm9.addWeekday(SoftwareArchitecture.getWeekday2()._DAY);
                first_esm9.addHour(SoftwareArchitecture.getWeekday2()._HOUR);
                first_esm9.addMinute(SoftwareArchitecture.getWeekday2()._MINUTE + firstESMTrigger);
                first_esm9.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm9.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm9);

            }

            if (userCourses.contains(programmingFundamentals.getName())) {
                esmRadio1.setTitle("Survey about the first lecture in " + programmingFundamentals.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the first lecture in " + programmingFundamentals.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the first lecture in " + programmingFundamentals.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the first lecture in " + programmingFundamentals.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the first lecture in " + programmingFundamentals.getName());
                esmFreeText.setTitle("Survey about the first lecture in " + programmingFundamentals.getName() + " (5/5)");

                Schedule first_esm10 = new Schedule("first_esm10" + UserData._username);
                first_esm10.addWeekday(programmingFundamentals.getWeekday1()._DAY);
                first_esm10.addHour(programmingFundamentals.getWeekday1()._HOUR);
                first_esm10.addMinute(programmingFundamentals.getWeekday1()._MINUTE + firstESMTrigger);
                first_esm10.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm10.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm10);

                Schedule first_esm11 = new Schedule("first_esm11" + UserData._username);
                first_esm11.addWeekday(programmingFundamentals.getWeekday2()._DAY);
                first_esm11.addHour(programmingFundamentals.getWeekday2()._HOUR);
                first_esm11.addMinute(programmingFundamentals.getWeekday2()._MINUTE + firstESMTrigger);
                first_esm11.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm11.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm11);

                Schedule first_esm12 = new Schedule("first_esm12" + UserData._username);
                first_esm12.addWeekday(programmingFundamentals.getWeekday3()._DAY);
                first_esm12.addHour(programmingFundamentals.getWeekday3()._HOUR);
                first_esm12.addMinute(programmingFundamentals.getWeekday3()._MINUTE + firstESMTrigger);
                first_esm12.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm12.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm12);

            }

//            factory.addESM(esmRadio1);
//            factory.addESM(esmRadio2);
//            factory.addESM(esmRadio3);
//            factory.addESM(esmRadio4);
//            factory.addESM(esmQuickAnswer);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ////Create first post lecture survey
    public void createSecondPostLectureESM(Context context, String userCourses) {
        try {

            ESMFactory factory = new ESMFactory();

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.setInstructions("I loved teaching this lecture.");
            esmRadio1.setTitle("Post Lecture Survey (1/5)");


            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.setInstructions("I was excited about teaching this lecture.");
            esmRadio2.setTitle("Post Lecture Survey (2/5)");


            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.setInstructions("I felt happy while teaching this lecture.");
            esmRadio3.setTitle("Post Lecture Survey (3/5)");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.setInstructions("I found teaching this lecture fun.");
            esmRadio4.setTitle("Post Lecture Survey (4/5)");

            ESM_Freetext esmFreeText = new ESM_Freetext();
            esmFreeText.setTitle("Post Lecture Survey")
                    .setSubmitButton("Finish")
                    .setExpirationThreshold(60*firstESMThreshold)
                    .setInstructions("Please describe the moment(s) during which you felt particularly engaged");


            ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
            esmQuickAnswer.addQuickAnswer("Yes")
                    .addQuickAnswer("No")
                    .setTitle("Post Lecture Survey (5/5)")
                    .setExpirationThreshold(60*firstESMThreshold)
                    .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                    .addFlow("Yes", esmFreeText.build());


            ArrayList<ESM_Radio> esms = new ArrayList<>();
            esms.add(esmRadio1);
            esms.add(esmRadio2);
            esms.add(esmRadio3);
            esms.add(esmRadio4);

            for (ESM_Radio esmRadio : esms) {
                esmRadio.addRadio("Strongly Agree")
                        .addRadio("Agree")
                        .addRadio("Neutral")
                        .addRadio("Disagree")
                        .addRadio("Strongly Disagree")
                        .setSubmitButton("Next");
            }

            factory.addESM(esmRadio1);
            factory.addESM(esmRadio2);
            factory.addESM(esmRadio3);
            factory.addESM(esmRadio4);
            factory.addESM(esmQuickAnswer);

            if (userCourses.contains(linearAlgebra.getName())) {
                esmRadio1.setTitle("Survey about the second lecture in " + linearAlgebra.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the second lecture in " + linearAlgebra.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the second lecture in " + linearAlgebra.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the second lecture in " + linearAlgebra.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the second lecture in " + linearAlgebra.getName());
                esmFreeText.setTitle("Survey about the second lecture in " + linearAlgebra.getName() + " (5/5)");

                Schedule first_esm1 = new Schedule("second_esm1" + UserData._username);

                first_esm1.addWeekday(linearAlgebra.getWeekday1()._DAY);
                first_esm1.addHour(linearAlgebra.getWeekday1()._HOUR);
                first_esm1.addMinute(linearAlgebra.getWeekday1()._MINUTE + secondESMTrigger);

                first_esm1.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm1);

                Schedule first_esm2 = new Schedule("second_esm2" + UserData._username);

                first_esm2.addWeekday(linearAlgebra.getWeekday2()._DAY);
                first_esm2.addHour(linearAlgebra.getWeekday2()._HOUR);
                first_esm2.addMinute(linearAlgebra.getWeekday2()._MINUTE + secondESMTrigger);

                first_esm2.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm2);
            }

            if (userCourses.contains(InformationSecurity.getName())) {
                esmRadio1.setTitle("Survey about the second lecture in " + InformationSecurity.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the second lecture in " + InformationSecurity.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the second lecture in " + InformationSecurity.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the second lecture in " + InformationSecurity.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the second lecture in " + InformationSecurity.getName());
                esmFreeText.setTitle("Survey about the second lecture in " + InformationSecurity.getName() + " (5/5)");

                Schedule first_esm3 = new Schedule("second_esm3" + UserData._username);
                first_esm3.addWeekday(mondayInformationSecurity1._DAY);
                first_esm3.addHour(mondayInformationSecurity1._HOUR);
                first_esm3.addMinute(mondayInformationSecurity1._MINUTE + secondESMTrigger);

                first_esm3.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm3);

                Schedule first_esm4 = new Schedule("second_esm4" + UserData._username);
                first_esm4.addWeekday(mondayInformationSecurity2._DAY);
                first_esm4.addHour(mondayInformationSecurity2._HOUR);
                first_esm4.addMinute(mondayInformationSecurity2._MINUTE + secondESMTrigger);

                first_esm4.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm4);
            }

            if (userCourses.contains(CyberCommunication.getName())) {
                esmRadio1.setTitle("Survey about the second lecture in " + CyberCommunication.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the second lecture in " + CyberCommunication.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the second lecture in " + CyberCommunication.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the second lecture in " + CyberCommunication.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the second lecture in " + CyberCommunication.getName());
                esmFreeText.setTitle("Survey about the second lecture in " + CyberCommunication.getName() + " (5/5)");

                Schedule first_esm5 = new Schedule("second_esm5" + UserData._username);
                first_esm5.addWeekday(CyberCommunication.getWeekday1()._DAY);
                first_esm5.addHour(CyberCommunication.getWeekday1()._HOUR);
                first_esm5.addMinute(CyberCommunication.getWeekday1()._MINUTE + secondESMTrigger);
                first_esm5.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm5.addActionExtra(ESM.EXTRA_ESM, factory.build());
                Scheduler.saveSchedule(context, first_esm5);

                Schedule first_esm6 = new Schedule("second_esm6" + UserData._username);
                first_esm6.addWeekday(CyberCommunication.getWeekday2()._DAY);
                first_esm6.addHour(CyberCommunication.getWeekday2()._HOUR);
                first_esm6.addMinute(CyberCommunication.getWeekday2()._MINUTE + secondESMTrigger);
                first_esm6.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm6.addActionExtra(ESM.EXTRA_ESM, factory.build());
                Scheduler.saveSchedule(context, first_esm6);

                Schedule first_esm7 = new Schedule("second_esm7" + UserData._username);
                first_esm7.addWeekday(CyberCommunication.getWeekday3()._DAY);
                first_esm7.addHour(CyberCommunication.getWeekday3()._HOUR);
                first_esm7.addMinute(CyberCommunication.getWeekday3()._MINUTE + secondESMTrigger);
                first_esm7.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm7);

            }

            if (userCourses.contains(SoftwareArchitecture.getName())) {
                esmRadio1.setTitle("Survey about the second lecture in " + SoftwareArchitecture.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the second lecture in " + SoftwareArchitecture.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the second lecture in " + SoftwareArchitecture.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the second lecture in " + SoftwareArchitecture.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the second lecture in " + SoftwareArchitecture.getName());
                esmFreeText.setTitle("Survey about the second lecture in " + SoftwareArchitecture.getName() + " (5/5)");

                Schedule first_esm8 = new Schedule("second_esm8" + UserData._username);
                first_esm8.addWeekday(SoftwareArchitecture.getWeekday1()._DAY);
                first_esm8.addHour(SoftwareArchitecture.getWeekday1()._HOUR);
                first_esm8.addMinute(SoftwareArchitecture.getWeekday1()._MINUTE + secondESMTrigger);
                first_esm8.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm8.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm8);

                Schedule first_esm9 = new Schedule("second_esm9" + UserData._username);
                first_esm9.addWeekday(SoftwareArchitecture.getWeekday2()._DAY);
                first_esm9.addHour(SoftwareArchitecture.getWeekday2()._HOUR);
                first_esm9.addMinute(SoftwareArchitecture.getWeekday2()._MINUTE + secondESMTrigger);
                first_esm9.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                first_esm9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                first_esm9.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, first_esm9);

            }

            if (userCourses.contains(programmingFundamentals.getName())) {
                esmRadio1.setTitle("Survey about the second lecture in " + programmingFundamentals.getName() + " (1/5)");
                esmRadio2.setTitle("Survey about the second lecture in " + programmingFundamentals.getName() + " (2/5)");
                esmRadio3.setTitle("Survey about the second lecture in " + programmingFundamentals.getName() + " (3/5)");
                esmRadio4.setTitle("Survey about the second lecture in " + programmingFundamentals.getName() + " (4/5)");
                esmQuickAnswer.setTitle("Survey about the second lecture in " + programmingFundamentals.getName());
                esmFreeText.setTitle("Survey about the second lecture in " + programmingFundamentals.getName() + " (5/5)");

                Schedule second_esm10 = new Schedule("second_esm10" + UserData._username);
                second_esm10.addWeekday(programmingFundamentals.getWeekday1()._DAY);
                second_esm10.addHour(programmingFundamentals.getWeekday1()._HOUR);
                second_esm10.addMinute(programmingFundamentals.getWeekday1()._MINUTE + secondESMTrigger);
                second_esm10.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_esm10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_esm10.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_esm10);

                Schedule second_esm11 = new Schedule("second_esm11" + UserData._username);
                second_esm11.addWeekday(programmingFundamentals.getWeekday2()._DAY);
                second_esm11.addHour(programmingFundamentals.getWeekday2()._HOUR);
                second_esm11.addMinute(programmingFundamentals.getWeekday2()._MINUTE + secondESMTrigger);
                second_esm11.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_esm11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_esm11.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_esm11);

                Schedule second_esm12 = new Schedule("second_esm12" + UserData._username);
                second_esm12.addWeekday(programmingFundamentals.getWeekday3()._DAY);
                second_esm12.addHour(programmingFundamentals.getWeekday3()._HOUR);
                second_esm12.addMinute(programmingFundamentals.getWeekday3()._MINUTE + secondESMTrigger);
                second_esm12.setActionType(Scheduler.ACTION_TYPE_BROADCAST);
                second_esm12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                second_esm12.addActionExtra(ESM.EXTRA_ESM, factory.build());

                Scheduler.saveSchedule(context, second_esm12);

            }

//            factory.addESM(esmRadio1);
//            factory.addESM(esmRadio2);
//            factory.addESM(esmRadio3);
//            factory.addESM(esmRadio4);
//            factory.addESM(esmQuickAnswer);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}