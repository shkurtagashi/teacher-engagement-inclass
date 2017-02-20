package com.example.android.teacher;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import java.io.*;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;
import com.example.android.teacher.data.Sensors.AccSensorContract;
import com.example.android.teacher.data.Sensors.BvpSensorContract;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.Sensors.EdaSensorContract;
import com.example.android.teacher.data.Sensors.TempSensorContract;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import static com.example.android.teacher.R.id.bvp;
import static com.example.android.teacher.R.id.ibi;


public class GraphActivity extends AppCompatActivity implements EmpaDataDelegate, EmpaStatusDelegate {
    private static final String TAG = "GraphActivity";

//    //Dropbox data needed
//    final static private String APP_KEY = "43f759ui6587zrc";
//    final static private String APP_SECRET = "fa65pecxb67rgyb";
//    DropboxAPI<AndroidAuthSession> mDBApi;


    // Empatica connect and streaming
    private static final int REQUEST_ENABLE_BT = 1;
    private static final long STREAMING_TIME = 1000000; // Stops streaming 10 seconds after connection

    private static final String EMPATICA_API_KEY = "eded959cc2054b3da99abce90a43871f"; // TODO insert your API Key here

    private EmpaDeviceManager deviceManager;

    private TextView accel_xLabel;
    private TextView accel_yLabel;
    private TextView accel_zLabel;
    private TextView bvpLabel;
    private TextView edaLabel;
    private TextView ibiLabel;
    private TextView temperatureLabel;
    private TextView batteryLabel;
    private TextView statusLabel;
    private TextView deviceNameLabel;
    private RelativeLayout dataCnt;

    //Graph EDA
    private LineGraphSeries<DataPoint> edaSeries;
    private double lastXeda = 0;

    //Graph BVP
    private LineGraphSeries<DataPoint> bvpSeries;
    private double lastXbvp = 0;

    //Graph ACCEL
    private LineGraphSeries<DataPoint> accXseries;
    private LineGraphSeries<DataPoint> accYseries;
    private LineGraphSeries<DataPoint> accZseries;
    private double lastXacc = 0;
    private double lastYacc = 0;
    private double lastZacc = 0;

    //Graph TEMP
    private LineGraphSeries<DataPoint> tempSeries;
    private double lastXtemp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

//        // Dropbox callback method
//        initialize_session();


        //Initialize vars that reference UI components
        statusLabel = (TextView) findViewById(R.id.status);
        dataCnt = (RelativeLayout) findViewById(R.id.dataArea);
        accel_xLabel = (TextView) findViewById(R.id.accel_x);
        accel_yLabel = (TextView) findViewById(R.id.accel_y);
        accel_zLabel = (TextView) findViewById(R.id.accel_z);
        bvpLabel = (TextView) findViewById(bvp);
        edaLabel = (TextView) findViewById(R.id.eda);
        ibiLabel = (TextView) findViewById(ibi);
        temperatureLabel = (TextView) findViewById(R.id.temperature);
        batteryLabel = (TextView) findViewById(R.id.battery);
        deviceNameLabel = (TextView) findViewById(R.id.deviceName);

        // Create a new EmpaDeviceManager. MainActivity is both its data and status delegate.
        deviceManager = new EmpaDeviceManager(getApplicationContext(), this, this);
        deviceManager.authenticateWithAPIKey(EMPATICA_API_KEY);


        // EDA Graph
        setUpEDAGraph();
        setUpBVPGraph();
        setUpTempGraph();
        setUpACCGraph();


    }

    public void setUpEDAGraph() {
        GraphView edaGraph = (GraphView) findViewById(R.id.graph_eda);
        edaSeries = new LineGraphSeries<DataPoint>();
        edaSeries.setTitle("Electrodermal Activity");
//        edaGraph.getLegendRenderer().setVisible(true);
//        edaGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//        edaGraph.getLegendRenderer().setBackgroundColor(Color.parseColor("#EEEEEE"));
        edaSeries.setColor(Color.parseColor("#03A9F4"));
        edaSeries.setDrawBackground(true);
        edaSeries.setBackgroundColor(Color.parseColor("#B3E5FC"));
        edaSeries.setDrawDataPoints(true);
        edaSeries.setDataPointsRadius(10);
        edaSeries.setThickness(8);
        edaGraph.addSeries(edaSeries);
        Viewport viewport = edaGraph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(4);
        viewport.setScrollable(true);
    }

    public void setUpBVPGraph() {

        // BVP graph
        GraphView bvpGraph = (GraphView) findViewById(R.id.graph_bvp);
        bvpSeries = new LineGraphSeries<DataPoint>();
        bvpSeries.setTitle("Blood Volume Pressure");
        bvpSeries.setColor(Color.parseColor("#F44336"));
        //bvpSeries.setDrawDataPoints(true);
        //bvpSeries.setDataPointsRadius(10);
        //bvpSeries.setThickness(8);
        // bvpSeries.setDrawBackground(true);
        //bvpSeries.setBackgroundColor(Color.parseColor("#FFCDD2"));
        bvpGraph.addSeries(bvpSeries);
        Viewport bvpViewport = bvpGraph.getViewport();
        bvpViewport.setYAxisBoundsManual(true);
        bvpViewport.setMinY(-700);
        bvpViewport.setMaxY(700);
        bvpViewport.setScrollable(true);

    }

    public void setUpTempGraph() {

        // Temp Graph
        GraphView tempGraph = (GraphView) findViewById(R.id.graph_temp);
        tempSeries = new LineGraphSeries<DataPoint>();
        tempSeries.setTitle("Temperature");
//        edaGraph.getLegendRenderer().setVisible(true);
//        edaGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//        edaGraph.getLegendRenderer().setBackgroundColor(Color.parseColor("#EEEEEE"));
        tempSeries.setColor(Color.parseColor("#F44336"));
        tempSeries.setDrawBackground(true);
        tempSeries.setBackgroundColor(Color.parseColor("#FFCDD2"));
        tempSeries.setDrawDataPoints(true);
        tempSeries.setDataPointsRadius(10);
        tempSeries.setThickness(8);
        tempGraph.addSeries(tempSeries);
        Viewport viewport4 = tempGraph.getViewport();
        viewport4.setYAxisBoundsManual(true);
        viewport4.setMinY(0);
        viewport4.setMaxY(100);
        viewport4.setScrollable(true);

    }

    public void setUpACCGraph() {

        // ACC graph
        GraphView accGraph = (GraphView) findViewById(R.id.graph_acc);
        accXseries = new LineGraphSeries<DataPoint>();
        accXseries.setTitle("X");
        accXseries.setColor(Color.parseColor("#F44336"));
        accXseries.setDrawDataPoints(true);
        accXseries.setDataPointsRadius(10);
        accXseries.setThickness(8);


        accYseries = new LineGraphSeries<DataPoint>();
        accYseries.setTitle("Y");
        accYseries.setColor(Color.parseColor("#4CAF50"));
        accYseries.setDrawDataPoints(true);
        accYseries.setDataPointsRadius(10);
        accYseries.setThickness(8);

        accZseries = new LineGraphSeries<DataPoint>();
        accZseries.setTitle("Z");
        accZseries.setColor(Color.parseColor("#FFC107"));
        accZseries.setDrawDataPoints(true);
        accZseries.setDataPointsRadius(10);
        accZseries.setThickness(8);

        accGraph.getLegendRenderer().setVisible(true);
        accGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        accGraph.getLegendRenderer().setBackgroundColor(Color.parseColor("#EEEEEE"));

        accGraph.addSeries(accXseries);
        accGraph.addSeries(accYseries);
        accGraph.addSeries(accZseries);


        Viewport accViewport = accGraph.getViewport();
        accViewport.setYAxisBoundsManual(true);
        accViewport.setMinY(-100);
        accViewport.setMaxY(100);
        accViewport.setScrollable(true);
    }


    /**
     * Handles resolution callbacks.
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // The user chose not to enable Bluetooth
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            // TO DO: You should deal with this
            return;
        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    /**
     * Called when activity gets invisible. Connection to Drive service needs to
     * be disconnected as soon as an activity is invisible.
     */
    @Override
    protected void onPause() {
        super.onPause();
        //deviceManager.stopScanning();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        deviceManager.cleanUp();
    }

    @Override
    public void didDiscoverDevice(BluetoothDevice bluetoothDevice, String deviceName, int rssi, boolean allowed) {
        // Check if the discovered device can be used with your API key. If allowed is always false,
        // the device is not linked with your API key. Please check your developer area at
        // https://www.empatica.com/connect/developer.php
        if (allowed) {
            // Stop scanning. The first allowed device will do.
            deviceManager.stopScanning();
            try {
                // Connect to the device
                deviceManager.connectDevice(bluetoothDevice);
                updateLabel(deviceNameLabel, "To: " + deviceName);
            } catch (ConnectionNotAllowedException e) {
                // This should happen only if you try to connect when allowed == false.
                Toast.makeText(GraphActivity.this, "Sorry, you can't connect to this device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void didRequestEnableBluetooth() {
        // Request the user to enable Bluetooth
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }


    @Override
    public void didUpdateSensorStatus(EmpaSensorStatus status, EmpaSensorType type) {
        // No need to implement this right now
    }

    @Override
    public void didUpdateStatus(EmpaStatus status) {
        // Update the UI
        updateLabel(statusLabel, status.name());

        // The device manager is ready for use
        if (status == EmpaStatus.READY) {
            updateLabel(statusLabel, status.name() + " - Turn on your device");
            // Start scanning
            deviceManager.startScanning();
            // The device manager has established a connection
        } else if (status == EmpaStatus.CONNECTED) {
            // Stop streaming after STREAMING_TIME
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    dataCnt.setVisibility(View.VISIBLE);
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Disconnect device
                            deviceManager.disconnect();
                        }
                    }, STREAMING_TIME);
                }
            });
            // The device manager disconnected from a device
        } else if (status == EmpaStatus.DISCONNECTED) {
            updateLabel(deviceNameLabel, "");
        }
    }

    @Override
    public void didReceiveAcceleration(final int x, final int y, final int z, final double timestamp) {
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        insertACCdata(x, y, z, timestamp);
                        accXseries.appendData(new DataPoint(lastXacc++, x), false, 10);
                        accYseries.appendData(new DataPoint(lastYacc++, y), false, 10);
                        accZseries.appendData(new DataPoint(lastZacc++, z), false, 10);

                    }
                });
            }
        }).start();
    }

    @Override
    public void didReceiveBVP(final float bvp, final double timestamp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //insertBVPdata(bvp, timestamp);
                        bvpSeries.appendData(new DataPoint(lastXbvp++, bvp), false, 100);
                    }
                });
            }
        }).start();
    }

    @Override
    public void didReceiveBatteryLevel(float battery, double timestamp) {
        //updateLabel(batteryLabel, String.format("%.0f %%", battery * 100));
    }


    @Override
    public void didReceiveGSR(final float gsr, final double timestamp) {
        //updateLabel(edaLabel, "" + gsr);
        //lastXeda = timestamp;


        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        insertEDAdata(gsr, timestamp);
                        edaSeries.appendData(new DataPoint(lastXeda++, gsr), false, 10);
                    }
                });
            }
        }).start();


    }


    @Override
    public void didReceiveIBI(float ibi, double timestamp) {

    }

    @Override
    public void didReceiveTemperature(final float temp, final double timestamp) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // we add 100 new entries
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        insertTEMPdata(temp, timestamp);
                        tempSeries.appendData(new DataPoint(lastXtemp++, temp), false, 10);
                    }
                });
            }
        }).start();

    }

    // Update a label with some text, making sure this is run in the UI thread
    private void updateLabel(final TextView label, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                label.setText(text);
            }
        });
    }


//    private int convertUnixToDate(double timestamp){
//        int ts = (int)timestamp;
//        Date date = new Date(ts*1000L); // *1000 is to convert seconds to milliseconds
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-1")); // give a timezone reference for formating (see comment at the bottom
//        String formattedDate = sdf.format(date);
//        int newDate = (int)formattedDate;
//        return newDate;
//
//    }

    public void insertEDAdata(float gsr, double timestamp) {
        Log.v("GraphActivity", "inserting EDA DATAAAAA");

        DatabaseHelper edaDbHelper = new DatabaseHelper(this);

        //Gets the data repository in write mode
        SQLiteDatabase db = edaDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP, timestamp);
        values.put(EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_VALUE, gsr);

        long newRowId = db.insert(EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA, null, values);
        if (newRowId == -1) {
            Log.v(TAG, "Error saving temp data");
        } else {
            Log.v(TAG, "GSR data: " + gsr + "saved for timestamp: " + timestamp);
        }
    }


    public void insertTEMPdata(float temp, double timestamp) {
        Log.v("GraphActivity", "inserting TEMP DATAAAAA");

        DatabaseHelper tempDbHelper = new DatabaseHelper(this);

        //Gets the data repository in write mode
        SQLiteDatabase db = tempDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TempSensorContract.TempSensorDataEntry.COLUMN_TEMP_TIMESTAMP, timestamp);
        values.put(TempSensorContract.TempSensorDataEntry.COLUMN_TEMP_VALUE, temp);

        long newRowId = db.insert(TempSensorContract.TempSensorDataEntry.TABLE_NAME_TEMP_DATA, null, values);
        if (newRowId == -1) {
            Log.v(TAG, "Error saving temp data");
        } else {
            Log.v(TAG, "Temp data: " + temp + "saved for timestamp: " + timestamp);
        }
    }

    public void insertBVPdata(float bvp, double timestamp) {
        Log.v("GraphActivity", "inserting BVP DATAAAAA");

        DatabaseHelper bvpDbHelper = new DatabaseHelper(this);

        //Gets the data repository in write mode
        SQLiteDatabase db = bvpDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BvpSensorContract.BvpSensorDataEntry.COLUMN_BVP_TIMESTAMP, timestamp);
        values.put(BvpSensorContract.BvpSensorDataEntry.COLUMN_BVP_VALUE, bvp);

        long newRowId = db.insert(BvpSensorContract.BvpSensorDataEntry.TABLE_NAME_BVP_DATA, null, values);
        if (newRowId == -1) {
            Log.v(TAG, "Error saving BVP data");
        } else {
            Log.v(TAG, "BVP data: " + bvp + "saved for timestamp: " + timestamp);
        }
    }

    public void insertACCdata(int x, int y, int z, double timestamp) {
        Log.v("GraphActivity", "inserting ACC DATAAAAA");

        DatabaseHelper accDbHelper = new DatabaseHelper(this);

        //Gets the data repository in write mode
        SQLiteDatabase db = accDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AccSensorContract.AccSensorDataEntry.COLUMN_ACC_TIMESTAMP, timestamp);
        values.put(AccSensorContract.AccSensorDataEntry.COLUMN_ACC_X_VALUE, x);
        values.put(AccSensorContract.AccSensorDataEntry.COLUMN_ACC_Y_VALUE, y);
        values.put(AccSensorContract.AccSensorDataEntry.COLUMN_ACC_Z_VALUE, z);

        long newRowId = db.insert(AccSensorContract.AccSensorDataEntry.TABLE_NAME_ACC_DATA, null, values);
        if (newRowId == -1) {
            Log.v(TAG, "Error saving ACC data");
        } else {
            Log.v(TAG, "X: " + x + " Y: " + y + " Z: " + z + "saved for timestamp: " + timestamp);
        }
    }


//    public File exportDBtoCSV() {
//        File dbFile = getDatabasePath("teacher.db");
//        DatabaseHelper dbhelper = new DatabaseHelper(this);
//
//
//
//        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
//        if (!exportDir.exists()) {
//            exportDir.mkdirs();
//        }
//
//
//
//        File file = new File("edadata.csv");
//        try {
//            file.createNewFile();
//            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
//            SQLiteDatabase db = dbhelper.getReadableDatabase();
//            String[] projection = {
//                    EdaSensorContract.EdaSensorDataEntry._EDA_ID,
//                    EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP,
//                    EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_VALUE
//            };
//
//
//
//            Cursor curCSV = db.query(
//                    EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA,                     // The table to query
//                    projection,                                   // The columns to return
//                    null,                                        // The columns for the WHERE clause
//                    null,                                       // The values for the WHERE clause
//                    null,                                     // don't group the rows
//                    null,                                     // don't filter by row groups
//                    null                                 // The sort order
//            );
//
//            //Cursor curCSV = db.rawQuery("SELECT * FROM edadata", null);
//            csvWrite.writeNext(curCSV.getColumnNames());
//            while (curCSV.moveToNext()) {
//                //Which column you want to exprort
//                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2)};
//                csvWrite.writeNext(arrStr);
//            }
//            String path = file.getPath();
//            Log.v(TAG, "Path of file is: " + path);
//            csvWrite.close();
//            curCSV.close();
//        } catch (Exception sqlEx) {
//            Log.e("GraphActivity", sqlEx.getMessage(), sqlEx);
//        }
//
//        return file;
//    }
//
    private String exportDBtoString(){
        File dbFile = getDatabasePath("teacher.db");
        DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());

        String resultsInCSV = "Id,Timestamp,Value\n";
        try{
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM edadata", null);
            while(curCSV.moveToNext()){
                resultsInCSV += curCSV.getString(0) + "," + curCSV.getString(1) + "," + curCSV.getString(2) + "\n";

            }

        }catch(Exception e){
            Log.e("GraphActivity", "Error getting data from database");
        }

        return resultsInCSV;


    }

    /**
     * Initialize the Session of the Key pair to authenticate with dropbox
     * Start the authentication flow.
     * If Dropbox app is installed, SDK will switch to it otherwise it will fallback to the browser.
     */
//    protected void initialize_session() {
//
//        // store app key and secret key
//        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
//        AndroidAuthSession session = new AndroidAuthSession(appKeys);
//
//        // Pass app key pair to the new DropboxAPI object.
//        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
//
//        // MyActivity below should be your activity class name
//        // start authentication.
//        mDBApi.getSession().startOAuth2Authentication(GraphActivity.this);
//
//
//    }

    /**
     * Callback register method to execute the upload method
     *
     * @param view
     */
//    public void uploadFiles(View view) {
//
//        new Upload().execute();
//    }


    /**
     * Asynchronous method to upload any file to dropbox
     */
//    public class Upload extends AsyncTask<String, Void, String> {
//
//        protected void onPreExecute() {
//
//        }
//
//        protected String doInBackground(String... arg0) {
//
//            DropboxAPI.Entry response = null;
//
//            try {
//
////                 Define path of file to be upload
//                String edaTablePath = exportDBtoString();
//                File file = new File(edaTablePath);
//                FileInputStream inputStream = new FileInputStream(file);
//
//
//                //put the file to dropbox
//                response = mDBApi.putFile("/sosero.png", inputStream, file.length(), null, null);
//                Log.e("DbExampleLog", "The uploaded file's rev is: " + response.rev);
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            return response.rev;
//
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result.isEmpty() == false) {
//                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
//                Log.e("DbExampleLog", "The uploaded file's rev is: " + result);
//            }
//        }
//    }
//
//    protected void onResume() {
//        super.onResume();
//
//        if (mDBApi.getSession().authenticationSuccessful()) {
//            try {
//                // Required to complete auth, sets the access token on the session
//                mDBApi.getSession().finishAuthentication();
//
//                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
//            } catch (IllegalStateException e) {
//                Log.i("DbAuthLog", "Error authenticating", e);
//            }
//        }
//    }
//
}