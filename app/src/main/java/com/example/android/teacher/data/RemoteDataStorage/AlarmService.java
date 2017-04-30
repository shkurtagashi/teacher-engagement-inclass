package com.example.android.teacher.data.RemoteDataStorage;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.android.teacher.Courses.QuestionnaireActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.LocalDataStorage.SQLiteController;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.android.teacher.HomeActivity.androidID;
import static com.example.android.teacher.R.id.course;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class AlarmService extends IntentService {
    Timer timer;

    SwitchDriveController switchDriveController;
    SQLiteController localController;
    DatabaseHelper dbHelper;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int MINUTES = 30; //The delay in minutes

        Log.v("AlarmService", "I am in AlarmService");


        dbHelper = new DatabaseHelper(getApplicationContext());
        switchDriveController = new SwitchDriveController(getString(R.string.server_address), getString(R.string.token), getString(R.string.password));
        localController = new SQLiteController(getApplicationContext());
        final Uploader uploader = new Uploader(androidID, switchDriveController, localController, dbHelper);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                //If there is WiFi connection when Alarm is triggered then UPLOAD data
                if (wifi.isConnected()) {
                    Log.v("Blla blla", "WIFI CONNECTED");
                    int response1 = uploader.uploadUsersTable();
                    //Upload Local Tables - eda, acc, bvp, temp
                    int response2 = uploader.upload();
                    //Upload Data from Esms table
                    int response3 = uploader.uploadAware(getApplicationContext());

                    if(response1 == 200 || response2 == 200 || response3 == 200){
                        timer.cancel();
                        timer.purge();
                        Log.v("UPLOAD TEST", "DATA UPLOADED, TIMER CANCELED");
                        setNotification(getApplicationContext(), "SWITCHdrive Upload", "Teachify data was successfully uploaded remotely on SWITCHdrive!", 1234512345);
                    }
                }else{
                    Log.v("UPLOAD TEST NO WIFI", "TIMER NOT CANCELED, BUT TRY AGAIN TO UPLOAD");
                    int response1 = uploader.uploadUsersTable();
                    //Upload Local Tables - eda, acc, bvp, temp
                    int response2 = uploader.upload();
                    //Upload Data from Esms table
                    int response3 = uploader.uploadAware(getApplicationContext());

                    if(response1 == 200 || response2 == 200 || response3 == 200){
                        timer.cancel();
                        timer.purge();
                        Log.v("UPLOAD TEST NO WIFI", "DATA UPLOADED, TIMER CANCELED");
                        setNotification(getApplicationContext(), "SWITCHdrive Upload", "Teachify data was successfully uploaded remotely on SWITCHdrive!", 1234512345);
                    }
                }
            }
        }, 0, 1000*60*MINUTES);
    }

    public void setNotification(Context context, String title, String content, int notificationID){
        Random rand = new Random();
        int code = rand.nextInt(100000000);
        System.out.println("code: "+code);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        Intent intent = new Intent(context, HomeActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(code, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.logotest);

        //System.out.println("in setNotification");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, builder.build());

    }

}
