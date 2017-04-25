package com.example.android.teacher.data.RemoteDataStorage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.android.teacher.R;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.LocalDataStorage.SQLiteController;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.android.teacher.HomeActivity.androidID;
import static com.example.android.teacher.R.drawable.timer;

/**
 * Created by shkurtagashi on 04.03.17.
 */

public class UploadAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("I am in UPLOAD ALARM receiver.");
        setAlarm(context, 2);
        //Start UploadService
        Intent intent1 = new Intent(context, AlarmService.class);
        context.startService(intent1);
    }
    public void setAlarm(Context context, int requestCode){

        Intent intent = new Intent(context, UploadAlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+86400000, PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_ONE_SHOT)); //86 400 000 (1 day)

    }
}
