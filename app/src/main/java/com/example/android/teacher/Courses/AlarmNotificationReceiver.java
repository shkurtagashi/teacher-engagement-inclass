package com.example.android.teacher.Courses;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.android.teacher.R;

import java.util.Calendar;
import java.util.Random;

/**
 * Code Contribution by Danilo Krasic on 4/6/17.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String course = intent.getExtras().get("course").toString();
        String questionnaire = intent.getExtras().get("questionnaire").toString();
        System.out.println("Course: "+intent.getExtras().get("course"));
        System.out.println("Questionnaire: "+intent.getExtras().get("questionnaire"));
        System.out.println("I am in receiver!");

        //here for each case we need to reschedule the alarm for next week, and set the notification

        if(course.equals("MondayLA")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayLA", "FirstPAM", 61);
                setNotification(context, "MondayLA", "FirstPAM", 1000, "Questionnaire!", "Time to answer pre-lecture PAM!", 1000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "MondayLA", "SecondPAM", 62);
//                setNotification(context, "MondayLA", "SecondPAM", 2000, "Questionnaire!", "Time to answer first post-lecture PAM!", 2000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "MondayLA", "ThirdPAM", 63);
//                setNotification(context, "MondayLA", "ThirdPAM", 3000, "Questionnaire!", "Time to answer second post-lecture PAM!", 3000);
//            }

            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayLA", "FirstPostlecture", 64);
                setNotification(context, "MondayLA", "FirstPostlecture", 4000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 4000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayLA", "SecondPostlecture", 65);
                setNotification(context, "MondayLA", "SecondPostlecture", 5000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 5000);
            }
        }

        if(course.equals("WednesdayLA")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "WednesdayLA", "FirstPAM", 66);
                setNotification(context, "WednesdayLA", "FirstPAM", 6000, "Questionnaire!", "Time to answer pre-lecture PAM!", 6000);
            }

//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "WednesdayLA", "SecondPAM", 67);
//                setNotification(context, "WednesdayLA", "SecondPAM", 7000, "Questionnaire!", "Time to answer first post-lecture PAM!", 7000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "WednesdayLA", "ThirdPAM", 68);
//                setNotification(context, "WednesdayLA", "ThirdPAM", 8000, "Questionnaire!", "Time to answer second post-lecture PAM!", 8000);
//            }

            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "WednesdayLA", "FirstPostlecture", 69);
                setNotification(context, "WednesdayLA", "FirstPostlecture", 9000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 9000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "WednesdayLA", "SecondPostlecture", 70);
                setNotification(context, "WednesdayLA", "SecondPostlecture", 10000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 10000);
            }
        }

        if(course.equals("MondayPF")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayPF", "FirstPAM", 71);
                setNotification(context, "MondayPF", "FirstPAM", 11000, "Questionnaire!", "Time to answer pre-lecture PAM!", 11000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "MondayPF", "SecondPAM", 72);
//                setNotification(context, "MondayPF", "SecondPAM", 12000, "Questionnaire!", "Time to answer first post-lecture PAM!", 12000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "MondayPF", "ThirdPAM", 73);
//                setNotification(context, "MondayPF", "ThirdPAM", 13000, "Questionnaire!", "Time to answer second post-lecture PAM!", 13000);
//            }

            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayPF", "FirstPostlecture", 74);
                setNotification(context, "MondayPF", "FirstPostlecture", 14000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 14000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayPF", "SecondPostlecture", 75);
                setNotification(context, "MondayPF", "SecondPostlecture", 15000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 15000);
            }
        }

        if(course.equals("WednesdayPF")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "WednesdayPF", "FirstPAM", 76);
                setNotification(context, "WednesdayPF", "FirstPAM", 16000, "Questionnaire!", "Time to answer pre-lecture PAM!", 16000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "WednesdayPF", "SecondPAM", 77);
//                setNotification(context, "WednesdayPF", "SecondPAM", 17000, "Questionnaire!", "Time to answer first post-lecture PAM!", 17000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "WednesdayPF", "ThirdPAM", 78);
//                setNotification(context, "WednesdayPF", "ThirdPAM", 18000, "Questionnaire!", "Time to answer second post-lecture PAM!", 18000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "WednesdayPF", "FirstPostlecture", 79);
                setNotification(context, "WednesdayPF", "FirstPostlecture", 19000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 19000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "WednesdayPF", "SecondPostlecture", 80);
                setNotification(context, "WednesdayPF", "SecondPostlecture", 20000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 20000);
            }
        }

        if(course.equals("FridayPF")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "FridayPF", "FirstPAM", 81);
                setNotification(context, "FridayPF", "FirstPAM", 21000, "Questionnaire!", "Time to answer pre-lecture PAM!", 21000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "FridayPF", "SecondPAM", 82);
//                setNotification(context, "FridayPF", "SecondPAM", 22000, "Questionnaire!", "Time to answer first post-lecture PAM!", 22000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "FridayPF", "ThirdPAM", 83);
//                setNotification(context, "FridayPF", "ThirdPAM", 23000, "Questionnaire!", "Time to answer second post-lecture PAM!", 23000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "FridayPF", "FirstPostlecture", 84);
                setNotification(context, "FridayPF", "FirstPostlecture", 24000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 24000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "FridayPF", "SecondPostlecture", 85);
                setNotification(context, "FridayPF", "SecondPostlecture", 25000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 25000);
            }
        }

        if(course.equals("TuesdayCC")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "TuesdayCC", "FirstPAM", 86);
                setNotification(context, "TuesdayCC", "FirstPAM", 26000, "Questionnaire!", "Time to answer pre-lecture PAM!", 26000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "TuesdayCC", "SecondPAM", 87);
//                setNotification(context, "TuesdayCC", "SecondPAM", 27000, "Questionnaire!", "Time to answer first post-lecture PAM!", 27000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "TuesdayCC", "ThirdPAM", 88);
//                setNotification(context, "TuesdayCC", "ThirdPAM", 28000, "Questionnaire!", "Time to answer second post-lecture PAM!", 28000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "TuesdayCC", "FirstPostlecture", 89);
                setNotification(context, "TuesdayCC", "FirstPostlecture", 29000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 29000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "TuesdayCC", "SecondPostlecture", 90);
                setNotification(context, "TuesdayCC", "SecondPostlecture", 30000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 30000);
            }
        }

        if(course.equals("WednesdayCC")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "WednesdayCC", "FirstPAM", 91);
                setNotification(context, "WednesdayCC", "FirstPAM", 31000, "Questionnaire!", "Time to answer pre-lecture PAM!", 31000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "WednesdayCC", "SecondPAM", 92);
//                setNotification(context, "WednesdayCC", "SecondPAM", 32000, "Questionnaire!", "Time to answer first post-lecture PAM!", 32000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "WednesdayCC", "ThirdPAM", 93);
//                setNotification(context, "WednesdayCC", "ThirdPAM", 33000, "Questionnaire!", "Time to answer second post-lecture PAM!", 33000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "WednesdayCC", "FirstPostlecture", 94);
                setNotification(context, "WednesdayCC", "FirstPostlecture", 34000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 34000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "WednesdayCC", "SecondPostlecture", 95);
                setNotification(context, "WednesdayCC", "SecondPostlecture", 35000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 35000);
            }
        }

        if(course.equals("ThursdayCC")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "ThursdayCC", "FirstPAM", 96);
                setNotification(context, "ThursdayCC", "FirstPAM", 36000, "Questionnaire!", "Time to answer pre-lecture PAM!", 36000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "ThursdayCC", "SecondPAM", 97);
//                setNotification(context, "ThursdayCC", "SecondPAM", 37000, "Questionnaire!", "Time to answer first post-lecture PAM!", 37000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "ThursdayCC", "ThirdPAM", 98);
//                setNotification(context, "ThursdayCC", "ThirdPAM", 38000, "Questionnaire!", "Time to answer second post-lecture PAM!", 38000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "ThursdayCC", "FirstPostlecture", 99);
                setNotification(context, "ThursdayCC", "FirstPostlecture", 39000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 39000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "ThursdayCC", "SecondPostlecture", 100);
                setNotification(context, "ThursdayCC", "SecondPostlecture", 40000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 40000);
            }
        }


        if(course.equals("MondayInf1")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayInf1", "FirstPAM", 101);
                setNotification(context, "MondayInf1", "FirstPAM", 41000, "Questionnaire!", "Time to answer pre-lecture PAM!", 41000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "MondayInf1", "SecondPAM", 102);
//                setNotification(context, "MondayInf1", "SecondPAM", 42000, "Questionnaire!", "Time to answer first post-lecture PAM!", 42000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "MondayInf1", "ThirdPAM", 103);
//                setNotification(context, "MondayInf1", "ThirdPAM", 43000, "Questionnaire!", "Time to answer second post-lecture PAM!", 33000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayInf1", "FirstPostlecture", 104);
                setNotification(context, "MondayInf1", "FirstPostlecture", 44000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 44000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayInf1", "SecondPostlecture", 105);
                setNotification(context, "MondayInf1", "SecondPostlecture", 45000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 45000);
            }
        }

        if(course.equals("MondayInf2")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayInf2", "FirstPAM", 106);
                setNotification(context, "MondayInf2", "FirstPAM", 46000, "Questionnaire!", "Time to answer pre-lecture PAM!", 46000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "MondayInf2", "SecondPAM", 107);
//                setNotification(context, "MondayInf2", "SecondPAM", 47000, "Questionnaire!", "Time to answer first post-lecture PAM!", 47000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "MondayInf2", "ThirdPAM", 108);
//                setNotification(context, "MondayInf2", "ThirdPAM", 48000, "Questionnaire!", "Time to answer second post-lecture PAM!", 48000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayInf2", "FirstPostlecture", 109);
                setNotification(context, "MondayInf2", "FirstPostlecture", 49000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 49000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayInf2", "SecondPostlecture", 110);
                setNotification(context, "MondayInf2", "SecondPostlecture", 50000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 50000);
            }
        }

        if(course.equals("TuesdaySAD")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "TuesdaySAD", "FirstPAM", 111);
                setNotification(context, "TuesdaySAD", "FirstPAM", 51000, "Questionnaire!", "Time to answer pre-lecture PAM!", 51000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "TuesdaySAD", "SecondPAM", 112);
//                setNotification(context, "TuesdaySAD", "SecondPAM", 52000, "Questionnaire!", "Time to answer first post-lecture PAM!", 52000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "TuesdaySAD", "ThirdPAM", 113);
//                setNotification(context, "TuesdaySAD", "ThirdPAM", 53000, "Questionnaire!", "Time to answer second post-lecture PAM!", 53000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "TuesdaySAD", "FirstPostlecture", 114);
                setNotification(context, "TuesdaySAD", "FirstPostlecture", 54000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 54000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "TuesdaySAD", "SecondPostlecture", 115);
                setNotification(context, "TuesdaySAD", "SecondPostlecture", 55000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 55000);
            }
        }

        if(course.equals("ThursdaySAD")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "ThursdaySAD", "FirstPAM", 116);
                setNotification(context, "ThursdaySAD", "FirstPAM", 56000, "Questionnaire!", "Time to answer pre-lecture PAM!", 56000);
            }
//            if(questionnaire.equals("SecondPAM")){
//                setAlarm(context, "ThursdaySAD", "SecondPAM", 117);
//                setNotification(context, "ThursdaySAD", "SecondPAM", 57000, "Questionnaire!", "Time to answer first post-lecture PAM!", 57000);
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                setAlarm(context, "ThursdaySAD", "ThirdPAM", 118);
//                setNotification(context, "ThursdaySAD", "ThirdPAM", 58000, "Questionnaire!", "Time to answer second post-lecture PAM!", 58000);
//            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "ThursdaySAD", "FirstPostlecture", 119);
                setNotification(context, "ThursdaySAD", "FirstPostlecture", 59000, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 59000);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "ThursdaySAD", "SecondPostlecture", 120);
                setNotification(context, "ThursdaySAD", "SecondPostlecture", 60000, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 60000);
            }
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

    public void setAlarm(Context context, String course, String questionnaire, int requestCode){

        Intent myIntent = new Intent(context, AlarmNotificationReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        myIntent.putExtra("course", course);
        myIntent.putExtra("questionnaire", questionnaire);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode, myIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+604800000, pendingIntent1); //604 800 000 (7 days)
        System.out.println("alarm set for the future");
    }

    public void setNotification(Context context, String course, String questionnaire, int requestCode, String title, String content, int notificationID){
        Random rand = new Random();
        int code = rand.nextInt(100000000);
        System.out.println("code: "+code);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);


        Intent intent = new Intent(context, QuestionnaireActivity.class);
        intent.putExtra("questionnaire", questionnaire);
        intent.putExtra("course", course);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(QuestionnaireActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(code, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.survey);

        //System.out.println("in setNotification");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, builder.build());

    }
}
