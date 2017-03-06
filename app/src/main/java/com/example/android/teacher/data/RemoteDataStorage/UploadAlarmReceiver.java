package com.example.android.teacher.data.RemoteDataStorage;

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
    Timer timer;

    SwitchDriveController switchDriveController;
    SQLiteController localController;
    DatabaseHelper dbHelper;

    @Override
    public void onReceive(final Context arg0, Intent arg1) {
        // For our recurring task, we'll just display a message
        int MINUTES = 30; //The delay in minutes


        dbHelper = new DatabaseHelper(arg0);
        switchDriveController = new SwitchDriveController(arg0.getString(R.string.server_address), arg0.getString(R.string.token), arg0.getString(R.string.password));
        localController = new SQLiteController(arg0);
        final Uploader uploader = new Uploader(androidID, switchDriveController, localController, dbHelper);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                ConnectivityManager connectivityManager = (ConnectivityManager) arg0.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                //If there is WiFi connection when Alarm is triggered then UPLOAD data
                if (wifi.isConnected()) {
                    Log.v("Blla blla", "WIFI CONNECTED");
                    int response1 = uploader.uploadUsersTable();
                    //Upload Local Tables - eda, acc, bvp, temp
                    int response2 = uploader.upload();
                    //Upload Data from Esms table
                    int response3 = uploader.uploadAware(arg0);

                    if(response1 == 200 && response2 == 200 && response3 == 200){
                        timer.cancel();
                        timer.purge();
                        Log.v("UPLOAD TEST", "DATA UPLOADED, TIMER CACELED");
                    }else{
                        Log.v("UPLOAD TEST", "DATA NOT UPLOADED, TIMER NOT CANCELED");
                    }
                }else{
                    Log.v("UPLOAD TEST", "wifi not connected, TIMER NOT CANCELED");
                }
            }
        }, 0, 1000*60*MINUTES);
    }
}
