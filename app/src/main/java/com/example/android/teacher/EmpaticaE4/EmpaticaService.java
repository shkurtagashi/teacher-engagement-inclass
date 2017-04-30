package com.example.android.teacher.EmpaticaE4;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;
import com.example.android.teacher.R;
import com.example.android.teacher.Sensors.RealtimeFragments.EdaFragment;
import com.example.android.teacher.Sensors.RealtimeFragments.MainSensorDataActivity;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract;
import com.example.android.teacher.data.Sensors.AccelereometerSensor;
import com.example.android.teacher.data.Sensors.BloodVolumePressureSensor;
import com.example.android.teacher.data.Sensors.EdaSensor;
import com.example.android.teacher.data.Sensors.TemperatureSensor;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static android.R.id.message;
import static com.example.android.teacher.R.drawable.timer;
import static com.example.android.teacher.R.id.bvp;
import static com.example.android.teacher.R.id.deviceName;
import static com.example.android.teacher.R.id.eda;
import static com.example.android.teacher.R.id.startSessionButton;
import static com.example.android.teacher.R.id.startSessionButton2;

/**
 * Created by shkurtagashi on 31.01.17.
 */

public class EmpaticaService extends Service implements EmpaDataDelegate, EmpaStatusDelegate{
    //private boolean mRunning;

    DatabaseHelper teacherDbHelper;
    SQLiteDatabase db;

    // Empatica connect and streaming
    private static final int REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private EmpaDeviceManager deviceManager = null;
    private EmpaStatus deviceStatus;
    private static String EMPATICA_API_KEY = null; // "eded959cc2054b3da99abce90a43871f"; // TODO insert your API Key here

    LocalBroadcastManager edaBroadcaster;
    LocalBroadcastManager tempBroadcaster;
    LocalBroadcastManager bvpBroadcaster;
    LocalBroadcastManager accXBroadcaster;
    LocalBroadcastManager accYBroadcaster;
    LocalBroadcastManager accZBroadcaster;
    LocalBroadcastManager batteryBroadcaster;
    LocalBroadcastManager statusBroadcaster;


    static final public String EDA_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED";
    static final public String TEMP_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED2";
    static final public String BVP_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED3";
    static final public String ACCX_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED4";
    static final public String ACCY_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED5";
    static final public String ACCZ_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED6";
    static final public String BATTERY_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED7";
    static final public String STATUS_RESULT = "com.controlj.copame.backend.EmpaticaService.REQUEST_PROCESSED8";

    static final public String EDA = "com.controlj.copame.backend.EmpaticaService.EDA";
    static final public String TEMP = "com.controlj.copame.backend.EmpaticaService.TEMP";
    static final public String BVP = "com.controlj.copame.backend.EmpaticaService.BVP";
    static final public String ACC_X = "com.controlj.copame.backend.EmpaticaService.ACC_X";
    static final public String ACC_Y = "com.controlj.copame.backend.EmpaticaService.ACC_Y";
    static final public String ACC_Z = "com.controlj.copame.backend.EmpaticaService.ACC_Z";
    static final public String BATTERY = "com.controlj.copame.backend.EmpaticaService.BATTERY";
    static final public String DEVICE_STATUS = "com.controlj.copame.backend.EmpaticaService.DEVICE_STATUS";

    static final public int ACC_BUFFER_CAPACITY = 32;
    static final public int BVP_BUFFER_CAPACITY = 64;
    static final public int EDA_BUFFER_CAPACITY = 8;
    static final public int TEMP_BUFFER_CAPACITY = 32;

    private List<String> accXBuffer;
    private List<String> accYBuffer;
    private List<String> accZBuffer;
    private List<String> bvpBuffer;
    private List<String> edaBuffer;
    private List<String> tempBuffer;


    Handler handler;
//
    @Override
    public void onCreate() {
        // Handler will get associated with the current thread,
        // which is the main thread.
        handler = new Handler();

        teacherDbHelper = new DatabaseHelper(getApplicationContext());
        db = teacherDbHelper.getWritableDatabase();
        EMPATICA_API_KEY = getE4ApiKey();

        accXBuffer = new ArrayList<String>(ACC_BUFFER_CAPACITY);
        accYBuffer = new ArrayList<String>(ACC_BUFFER_CAPACITY);
        accZBuffer = new ArrayList<String>(ACC_BUFFER_CAPACITY);
        bvpBuffer = new ArrayList<String>(BVP_BUFFER_CAPACITY);
        edaBuffer = new ArrayList<String>(EDA_BUFFER_CAPACITY);
        tempBuffer = new ArrayList<String>(TEMP_BUFFER_CAPACITY);

        edaBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
        bvpBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
        tempBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
        accXBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
        accYBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
        accZBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
        batteryBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
        statusBroadcaster = LocalBroadcastManager.getInstance(getApplicationContext());

        super.onCreate();
    }

    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

//    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Empatica Service Started", Toast.LENGTH_LONG).show();

//        teacherDbHelper = new DatabaseHelper(this);
//        db = teacherDbHelper.getWritableDatabase();

        initEmpaticaDeviceManager();

        Intent notificationIntent = new Intent(this, EmpaticaService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.empa)
                .setContentIntent(pendingIntent)
                .setTicker("BLLA BLLA")
                .setContentTitle("Realtime streaming")
                .setContentText("The realtime streaming of sensor data from Empatica E4 is running.")
                .build();

        startForeground(717038, notification);


        return START_STICKY;
    }

    private void initEmpaticaDeviceManager() {
        // Create a new EmpaDeviceManager. MainActivity is both its data and status delegate.
        deviceManager = new EmpaDeviceManager(getApplicationContext(), this, this);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            // Initialize the Device Manager using your API key. You need to have Internet access at this point.
            deviceManager.authenticateWithAPIKey(EMPATICA_API_KEY);
        }else{
            Toast.makeText(EmpaticaService.this, "Sorry, you need WiFi connection to connect to Empatica E4!", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        //DEVICE MANAGER DISCONNECT
//        deviceManager.cleanUp();
//        deviceManager.disconnect();
//        stopForeground(true);
//        stopSelf();
        Toast.makeText(EmpaticaService.this, "Empatica Service stopped successfully!", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


//    public class LocalBinder extends Binder {
//        EmpaticaService getService() {
//            return EmpaticaService.this;
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        // After using a given device, you should make sure that BluetoothGatt.close() is called
//        // such that resources are cleaned up properly.  In this particular example, close() is
//        // invoked when the UI is disconnected from the Service.
//        close();
//        return super.onUnbind(intent);
//    }
//
//    private final IBinder mBinder = new LocalBinder();
//


    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (deviceManager == null) {
            return;
        }
        deviceManager.disconnect();
        deviceManager = null;
    }

    @Override
    public void didReceiveGSR(final float gsr, final double timestamp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teacherDbHelper.addEdaSensorValues(new EdaSensor(gsr, timestamp), db);
//                sendEdaResult(gsr+"");
                 if (edaBuffer.size() < EDA_BUFFER_CAPACITY) {
                     edaBuffer.add(gsr + "");
                 } else {
                    sendEdaResult(edaBuffer);
                    edaBuffer.clear();
                 }
            }
        }).start();
    }

    @Override
    public void didReceiveBVP(final float bvp, final double timestamp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teacherDbHelper.addBvpSensorValues(new BloodVolumePressureSensor(bvp, timestamp), db);
//                sendBvpResult(bvp + "");
                if (bvpBuffer.size() < BVP_BUFFER_CAPACITY) {
                    bvpBuffer.add(bvp + "");
                } else {
                    sendBvpResult(bvpBuffer);
                    bvpBuffer.clear();
                }
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
                sendTempResult(temp+"");
//                if (tempBuffer.size() < TEMP_BUFFER_CAPACITY) {
//                    tempBuffer.add(temp + "");
//                } else {
//                    sendTempResult(tempBuffer);
//                    tempBuffer.clear();
//                }
            }
        }).start();


    }

    @Override
    public void didReceiveAcceleration(final int x, final int y, final int z, final double timestamp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teacherDbHelper.addAccSensorValues(new AccelereometerSensor(x, y, z, timestamp), db);
//                sendAccXResult(x+"");
//                sendAccYResult(y+"");
//                sendAccZResult(z+"");
                if (accXBuffer.size() < ACC_BUFFER_CAPACITY) {
                    accXBuffer.add(x + "");
                } else {
                    sendAccXResult(accXBuffer);
                    accXBuffer.clear();
                }

                if (accYBuffer.size() < ACC_BUFFER_CAPACITY) {
                    accYBuffer.add(y + "");
                } else {
                    sendAccYResult(accYBuffer);
                    accYBuffer.clear();
                }

                if (accZBuffer.size() < ACC_BUFFER_CAPACITY) {
                    accZBuffer.add(z + "");
                } else {
                    sendAccZResult(accZBuffer);
                    accZBuffer.clear();
                }
            }
        }).start();
    }

    @Override
    public void didReceiveBatteryLevel(float v, double v1) {
        //sendBatteryResult(v * 100 + "%");
        //Log.v("BATTERYYYYY", v * 100 + "%");
    }

    @Override
    public void didUpdateStatus(EmpaStatus status) {

        // Update the UI
        updateLabel(status.name());
        sendStatusResult(status.name());

        if (deviceManager != null && status == EmpaStatus.READY) {
            deviceManager.startScanning();
        }

    }

    @Override
    public void didUpdateSensorStatus(EmpaSensorStatus empaSensorStatus, EmpaSensorType empaSensorType) {

    }


    @Override
    public void didDiscoverDevice(BluetoothDevice bluetoothDevice, String deviceName, int rssi, boolean allowed) {
        // Check if the discovered device can be used with your API key. If allowed is always false,
//        // the device is not linked with your API key.
//
        if (allowed) {
            // Stop scanning. The first allowed device will do.
            String currentE4Name = getE4Name();
            if(deviceName.equals("Empatica E4 - " + currentE4Name)){
                //deviceManager.stopScanning();
                try {
                    // Connect to the device
                    deviceManager.connectDevice(bluetoothDevice);
                    updateLabel(deviceName);
                } catch (ConnectionNotAllowedException e) {
                    // This should happen only if you try to connect when allowed == false.
                    Toast.makeText(EmpaticaService.this, "Sorry, you can't connect to this device", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EmpaticaService.this);
            alertDialog.setTitle("Non-valid API Key");
            alertDialog.setMessage("The API Key provided is not linked with your Empatica E4 . Please provide a valid API Key! Do you want to continue?");

            alertDialog.setNegativeButton(R.string.yes,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), EditEmpaticaActivity.class);
                            startActivity(intent);
                        }
                    });

            alertDialog.setPositiveButton(R.string.no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
//                            stopSelf();
                        }
                    });
            alertDialog.show();

        }

    }

    @Override
    public void didRequestEnableBluetooth() {
        // Request the user to enable Bluetooth
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        this.startActivity(enableBtIntent);

    }

    //    public void sendMessage(String result, String extra, String message, LocalBroadcastManager broadcaster) {
//        Intent intent = new Intent(result);
//        if(message != null)
//            intent.putExtra(extra, message);
//        broadcaster.sendBroadcast(intent);
//    }

//    public void sendEdaResult(final String message) {
//        final Intent intent = new Intent(EDA_RESULT);
//        if(message != null) {
//            intent.putExtra(EDA, message);
//            edaBroadcaster.sendBroadcastSync(intent);
//        }
//    }

    public void sendEdaResult(final List<String> message) {
        final Intent intent = new Intent(EDA_RESULT);
        if(message != null) {
            intent.putStringArrayListExtra(EDA, (ArrayList<String>) message);
            edaBroadcaster.sendBroadcastSync(intent);
//            Log.v("EmpaticaService", "Eda buffer BROADCASTED" + message);
        }
    }

    public void sendBvpResult(final List<String> message) {
        final Intent intent = new Intent(BVP_RESULT);
        if(message != null) {
            intent.putStringArrayListExtra(BVP, (ArrayList<String>) message);
            bvpBroadcaster.sendBroadcastSync(intent);
//            Log.v("EmpaticaService", "BVP buffer BROADCASTED" + message);
        }
    }

    public void sendTempResult(final String message) {
        final Intent intent = new Intent(TEMP_RESULT);
        if(message != null) {
            intent.putExtra(TEMP, message);
            tempBroadcaster.sendBroadcastSync(intent);
//            Log.v("EmpaticaService", "TEMP buffer BROADCASTED" + message);
        }
    }

    public void sendAccXResult(final List<String> message) {
        final Intent intent = new Intent(ACCX_RESULT);
        if(message != null) {
            intent.putStringArrayListExtra(ACC_X, (ArrayList<String>) message);
            accXBroadcaster.sendBroadcastSync(intent);
        }
    }

    public void sendAccYResult(final List<String> message) {
        final Intent intent = new Intent(ACCY_RESULT);
        if(message != null) {
            intent.putStringArrayListExtra(ACC_Y, (ArrayList<String>) message);
            accYBroadcaster.sendBroadcastSync(intent);
        }
    }
    public void sendAccZResult(final List<String> message) {
        final Intent intent = new Intent(ACCZ_RESULT);
        if(message != null) {
            intent.putStringArrayListExtra(ACC_Z, (ArrayList<String>) message);
            accZBroadcaster.sendBroadcastSync(intent);
        }
    }

    public void sendBatteryResult(String message) {
        final Intent intent = new Intent(BATTERY_RESULT);
        if (message != null) {
            intent.putExtra(BATTERY_RESULT, message);
            batteryBroadcaster.sendBroadcast(intent);
        }
    }


    public void sendStatusResult(String message) {
        Intent intent = new Intent(STATUS_RESULT);
        if(message != null){
            intent.putExtra(DEVICE_STATUS, message);
            statusBroadcaster.sendBroadcast(intent);
        }

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


    // Update a message, making sure this is run in the UI thread
    private void updateLabel(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(EmpaticaService.this, text, Toast.LENGTH_SHORT).show();            }
        });
    }
}
