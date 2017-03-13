package com.example.android.teacher.data.RemoteDataStorage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.Sensors.AccSensorContract;
import com.example.android.teacher.data.Sensors.BvpSensorContract;
import com.example.android.teacher.data.Sensors.EdaSensorContract;
import com.example.android.teacher.data.Sensors.TempSensorContract;

/**
 * Created by shkurtagashi on 13.03.17.
 */

public class DeleteAlarmReceiver extends BroadcastReceiver {
    DatabaseHelper dbHelper;

    @Override
    public void onReceive(final Context arg0, Intent arg1) {
        // For our recurring task, we'll just display a message

        dbHelper = new DatabaseHelper(arg0);
        dbHelper.deleteAllFromTable(AccSensorContract.AccSensorDataEntry.TABLE_NAME_ACC_DATA);
        dbHelper.deleteAllFromTable(TempSensorContract.TempSensorDataEntry.TABLE_NAME_TEMP_DATA);
        dbHelper.deleteAllFromTable(EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA);
        dbHelper.deleteAllFromTable(BvpSensorContract.BvpSensorDataEntry.TABLE_NAME_BVP_DATA);

    }
}
