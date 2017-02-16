package com.example.android.teacher;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.empatica.empalink.EmpaDeviceManager;
import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract.E4DataEntry;
import com.example.android.teacher.data.User.UsersContract;

public class HelpActivity extends AppCompatActivity{
    private static final String TAG = "HelpActivity";

    private String android_id;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final long STREAMING_TIME = 1000000; // Stops streaming 1000000 seconds after connection


    private static final String EMPATICA_API_KEY = "eded959cc2054b3da99abce90a43871f"; // TODO insert your API Key here
    //private String EMPATICA_API_KEY;

    private EmpaDeviceManager deviceManager;

    private CheckBox step1;
    private CheckBox step3;
    private CheckBox step7;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        // Create a new EmpaDeviceManager. MainActivity is both its data and status delegate.
        //deviceManager = new EmpaDeviceManager(getApplicationContext(), this, this);

        // Initialize the Device Manager using your API key. You need to have Internet access at this point.
//        deviceManager.authenticateWithAPIKey(EMPATICA_API_KEY);



        step1 = (CheckBox) findViewById(R.id.step1CheckBox);
        step1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step1.setClickable(true);
                Intent i = new Intent (getApplicationContext(), EmpaticaActivity.class);
                startActivity(i);
            }
        });

        step3 = (CheckBox) findViewById(R.id.step3CheckBox);
        step3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step3.isChecked()) {
                    SharedPreferences settings = getSharedPreferences("prefs_name", 0);
                    settings.edit().putBoolean("check", true).commit();
                }
                step3.setClickable(true);
                // Request the user to enable Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        });

        step7 = (CheckBox) findViewById(R.id.step7CheckBox);
        step7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step7.setClickable(true);
                Intent i = new Intent (getApplicationContext(), SurveyDataActivity.class);
                startActivity(i);
            }
        });

    }

//    @Override
//    public void didDiscoverDevice(BluetoothDevice bluetoothDevice, String deviceName, int rssi, boolean allowed) {
//        // Check if the discovered device can be used with your API key. If allowed is always false,
//        // the device is not linked with your API key. Please check your developer area at
//        // https://www.empatica.com/connect/developer.php
//        if (allowed) {
//            // Stop scanning. The first allowed device will do.
//            deviceManager.stopScanning();
//            try {
//                // Connect to the device
//                deviceManager.connectDevice(bluetoothDevice);
//                //updateLabel(deviceNameLabel, "To: " + deviceName);
//                Log.v(TAG, "device name");
//            } catch (ConnectionNotAllowedException e) {
//                // This should happen only if you try to connect when allowed == false.
//                Toast.makeText(HelpActivity.this, "Sorry, you can't connect to this device", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    public void didRequestEnableBluetooth() {
//
//    }
//
//    @Override
//    public void didUpdateSensorStatus(EmpaSensorStatus status, EmpaSensorType type) {
//        // No need to implement this right now
//    }
//
//    @Override
//    public void didUpdateStatus(EmpaStatus status) {
//        // Update the UI
//        //updateLabel(statusLabel, status.name());
//        Log.v(TAG, status.name());
//
//        // The device manager is ready for use
//        if (status == EmpaStatus.READY) {
//            //updateLabel(statusLabel, status.name() + " - Turn on your device");
//            Log.v(TAG,status.name() + "- Turn on your device");
//            //progressDialog = ProgressDialog.show(this, "Ready - Switch on your device","Waiting for the device" , true);
//            //Toast.makeText(this,"Ready - Switch on your device", Toast.LENGTH_SHORT).show();
//            // Start scanning
//            deviceManager.startScanning();
//            // The device manager has established a connection
//        } else if (status == EmpaStatus.CONNECTED) {
//           // progressDialog = ProgressDialog.show(this, "Connected","Starting to get sensor data" , true);
//            //Toast.makeText(this,"Connected to Empatica E4 Name", Toast.LENGTH_SHORT).show();
//            // Stop streaming after STREAMING_TIME
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    //dataCnt.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            // Disconnect device
//                            deviceManager.disconnect();
//                        }
//                    }, STREAMING_TIME);
//                }
//            });
//            // The device manager disconnected from a device
//        } else if (status == EmpaStatus.DISCONNECTED) {
//            //updateLabel(deviceNameLabel, "");
//            //progressDialog = ProgressDialog.show(this, "Disconnected","Stopped getting sensor data" , true);
//            //Toast.makeText(this,"Disconnected", Toast.LENGTH_SHORT).show();
//            Log.v(TAG, "DISCONNECTED");
//        }
//    }

    public String checkEmpaticaE4Device(){
        String apiKey = "";
        DatabaseHelper e4DbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = e4DbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                E4DataEntry.COLUMN_E4_NAME,
                E4DataEntry.COLUMN_API_KEY
        };

        // Filter results WHERE "_ID" = 'android_id'
        String selection = E4DataEntry._ID + " = ?";
        String[] selectionArgs = { android_id };


        Cursor cursor = db.query(
                UsersContract.UserEntry.TABLE_NAME_USERS,                     // The table to query
                projection,                                   // The columns to return
                selection,                                        // The columns for the WHERE clause
                selectionArgs,                                       // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (cursor.moveToFirst()) { // if Cursor is not empty
            apiKey = cursor.getString(cursor.getColumnIndex(E4DataEntry.COLUMN_API_KEY));

        }
        cursor.close();
        return apiKey;


    }


//    @Override
//    public void didReceiveAcceleration(int x, int y, int z, double timestamp) {}
//
//    @Override
//    public void didReceiveBVP(float bvp, double timestamp) {}
//
//    @Override
//    public void didReceiveBatteryLevel(float battery, double timestamp) {}
//
//    @Override
//    public void didReceiveGSR(final float gsr, double timestamp) {}
//
//    @Override
//    public void didReceiveIBI(float ibi, double timestamp) {}
//
//    @Override
//    public void didReceiveTemperature(float temp, double timestamp) {}

//    private boolean getFromSP(String key){
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Teacher", android.content.Context.MODE_PRIVATE);
//        return preferences.getBoolean(key, false);
//    }
//    private void saveInSp(String key,boolean value){
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Teacher", android.content.Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(key, value);
//        editor.commit();
//    }

}
