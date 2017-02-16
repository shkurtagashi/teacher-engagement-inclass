package com.example.android.teacher.PhotoGrid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shkurtagashi on 22.01.17.
 */

public class GridReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        System.out.println("GridReceiver is fired at: " + currentDateandTime);
        Toast.makeText(context, "Alarm fired at: " + currentDateandTime, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(context, PhotoGridActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
