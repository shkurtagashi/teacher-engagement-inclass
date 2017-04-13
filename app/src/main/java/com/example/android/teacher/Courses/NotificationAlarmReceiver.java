package com.example.android.teacher.Courses;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.Surveys.SurveyDataActivity;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.Sensors.AccSensorContract;
import com.example.android.teacher.data.Sensors.BvpSensorContract;
import com.example.android.teacher.data.Sensors.EdaSensorContract;
import com.example.android.teacher.data.Sensors.TempSensorContract;

import static android.R.attr.id;
import static android.R.id.message;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by shkurtagashi on 16.03.17.
 */

public class NotificationAlarmReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID_1 = "notification-id-1";
    public static String NOTIFICATION_1 = "notification-1";

    public static String NOTIFICATION_ID_2 = "notification-id-2";
    public static String NOTIFICATION_2 = "notification-2";

    public static String NOTIFICATION_ID_3 = "notification-id-3";
    public static String NOTIFICATION_3 = "notification-3";


    NotificationManager notificationManager;

    public void onReceive(Context context, Intent intent) {

//        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification notification = intent.getParcelableExtra(NOTIFICATION);
//
////        Notification notification = new Notification(R.drawable.key, message, 5000);
//
//        Intent notificationIntent = new Intent(context, SurveyDataActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent intent2 = PendingIntent.getActivity(context, 0,
//                notificationIntent, 0);
//
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//
//
//        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
//        notificationManager.notify(id, notification);

//        Intent myIntent = new Intent(context, SurveyDataActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context,
//                0,
//                myIntent,
//                0);

//        myNotification = new NotificationCompat.Builder(context)
//                .setTicker("Notification!")
//                .setWhen(System.currentTimeMillis())
//                .setContentIntent(pendingIntent)
//                .setDefaults(Notification.DEFAULT_SOUND)
//                .setAutoCancel(true)
//                .setSmallIcon(R.drawable.survey)
//                .build();



        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification1 = intent.getParcelableExtra(NOTIFICATION_1);
        int id1 = intent.getIntExtra(NOTIFICATION_ID_1, 1);
        notificationManager.notify(id1, notification1);

//        Notification notification2 = intent.getParcelableExtra(NOTIFICATION_2);
//        int id2 = intent.getIntExtra(NOTIFICATION_ID_2, 2);
//        notificationManager.notify(id2, notification2);
//
//        Notification notification3 = intent.getParcelableExtra(NOTIFICATION_3);
//        int id3 = intent.getIntExtra(NOTIFICATION_ID_3, 3);
//        notificationManager.notify(id3, notification3);
    }
}
