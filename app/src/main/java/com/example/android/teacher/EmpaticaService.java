package com.example.android.teacher;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;
import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract;
import com.example.android.teacher.data.Sensors.AccelereometerSensor;
import com.example.android.teacher.data.Sensors.BloodVolumePressureSensor;
import com.example.android.teacher.data.Sensors.EdaSensor;
import com.example.android.teacher.data.Sensors.TemperatureSensor;
import com.jjoe64.graphview.series.DataPoint;

import static android.R.attr.x;
import static android.R.attr.y;
import static com.example.android.teacher.R.id.bvp;
import static com.example.android.teacher.R.id.deviceName;
import static com.example.android.teacher.R.id.status;

/**
 * Created by shkurtagashi on 31.01.17.
 */

public class EmpaticaService extends Service implements EmpaDataDelegate, EmpaStatusDelegate{

    int mStartMode;

    DatabaseHelper teacherDbHelper;
    SQLiteDatabase db;


    // Empatica connect and streaming
    private static final int REQUEST_ENABLE_BT = 1;
    private EmpaStatus deviceStatus;
    private EmpaDeviceManager deviceManager;
    private static String EMPATICA_API_KEY = null; // "eded959cc2054b3da99abce90a43871f"; // TODO insert your API Key here



    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Empatica Service Started", Toast.LENGTH_LONG).show();

        teacherDbHelper = new DatabaseHelper(this);
        db = teacherDbHelper.getWritableDatabase();

        if(getE4ApiKey() != null){
            EMPATICA_API_KEY = getE4ApiKey();
            getDeviceManager();
        }else{
            setUpE4Data();
        }

        return mStartMode;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //DEVICE MANAGER DISCONNECT
        deviceManager.cleanUp();
        Toast.makeText(this, "Empatica Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void didReceiveGSR(final float gsr, final double timestamp) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                teacherDbHelper.addEdaSensorValues(new EdaSensor(gsr, timestamp), db);
            }
        }).start();

    }

    @Override
    public void didReceiveBVP(final float bvp, final double timestamp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teacherDbHelper.addBvpSensorValues(new BloodVolumePressureSensor(bvp, timestamp), db);

            }
        }).start();
    }

    @Override
    public void didReceiveIBI(float v, double v1) {

    }

    @Override
    public void didReceiveTemperature(final float temp, final double timestamp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teacherDbHelper.addTempSensorValues(new TemperatureSensor(temp, timestamp), db);
            }
        }).start();

    }

    @Override
    public void didReceiveAcceleration(final int x, final int y, final int z, final double timestamp) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                teacherDbHelper.addAccSensorValues(new AccelereometerSensor(x, y, z, timestamp), db);

            }
        }).start();

    }

    @Override
    public void didReceiveBatteryLevel(float v, double v1) {

    }

    @Override
    public void didUpdateStatus(EmpaStatus status) {
        // Update the UI
        //updateLabel(status.name());

        // The device manager is ready for use
        if (status == EmpaStatus.READY) {
            deviceStatus = EmpaStatus.READY;
            Toast.makeText(this, "READY", Toast.LENGTH_SHORT).show();

        } else if (status == EmpaStatus.CONNECTED) {
            deviceStatus = EmpaStatus.CONNECTED;
            Toast.makeText(this, "CONNECTED", Toast.LENGTH_SHORT).show();

        } else if (status == EmpaStatus.DISCONNECTED) {
            deviceStatus = EmpaStatus.DISCONNECTED;
            //updateLabel("DISCONNECTED");
            Toast.makeText(this, "DISCONNECTED", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void didUpdateSensorStatus(EmpaSensorStatus empaSensorStatus, EmpaSensorType empaSensorType) {

    }

    @Override
    public void didDiscoverDevice(BluetoothDevice bluetoothDevice, String deviceName, int rssi, boolean allowed) {
        // Check if the discovered device can be used with your API key. If allowed is always false,
        // the device is not linked with your API key.

        if (allowed) {
            // Stop scanning. The first allowed device will do.
            String currentE4Name = getE4Name();
            if(deviceName.equals("Empatica E4 - " + currentE4Name)){
                deviceManager.stopScanning();
            }
            try {
                // Connect to the device
                deviceManager.connectDevice(bluetoothDevice);
               // updateLabel(deviceName);
            } catch (ConnectionNotAllowedException e) {
                // This should happen only if you try to connect when allowed == false.
                Toast.makeText(this, "Sorry, you can't connect to this device", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void didRequestEnableBluetooth() {

    }

    public EmpaDeviceManager getDeviceManager(){
        if (deviceManager == null){
            //initialize it
            // Create a new EmpaDeviceManager. MainActivity is both its data and status delegate.
            deviceManager = new EmpaDeviceManager(getApplicationContext(), this, this);
            deviceManager.authenticateWithAPIKey(EMPATICA_API_KEY);

        }
        return deviceManager;

    }

    public String getE4Name(){
        String name;

        DatabaseHelper usersDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                E4DataContract.E4DataEntry.COLUMN_E4_NAME

        };

        Cursor cursor = db.query(
                E4DataContract.E4DataEntry.TABLE_NAME_EMPATICA_E4,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            name = cursor.getString(cursor.getColumnIndex("empatica_name"));
            return name;
        }

        cursor.close();

        return null;
    }

    public String getE4ApiKey(){
        String apiKey = null;

        DatabaseHelper usersDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                E4DataContract.E4DataEntry.COLUMN_API_KEY

        };

        Cursor cursor = db.query(
                E4DataContract.E4DataEntry.TABLE_NAME_EMPATICA_E4,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            apiKey = cursor.getString(cursor.getColumnIndex("api_key"));
        }

        cursor.close();
        return apiKey;
    }

    public void setUpE4Data(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.enterE4Data)
                .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent enterE4data = new Intent(getApplicationContext(), EmpaticaActivity.class);
                        startActivity(enterE4data);
                    }
                })
                .setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog disagreeAlertDialog = builder.create();
        disagreeAlertDialog.show();
    }


}
