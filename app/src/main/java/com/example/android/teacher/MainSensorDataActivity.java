package com.example.android.teacher;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.android.teacher.data.EmpaticaE4.E4DataContract;
import com.example.android.teacher.data.Sensors.AccelereometerSensor;
import com.example.android.teacher.data.Sensors.BloodVolumePressureSensor;
import com.example.android.teacher.data.Sensors.EdaSensor;
import com.example.android.teacher.data.Sensors.TemperatureSensor;
import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract.E4DataEntry;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainSensorDataActivity extends AppCompatActivity implements EmpaStatusDelegate, EmpaDataDelegate{
    private static final String TAG = "MainSensorDataActivity";

    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 3;

    DatabaseHelper teacherDbHelper;
    SQLiteDatabase db;

    String username;

    // Empatica connect and streaming
    private static final int REQUEST_ENABLE_BT = 1;
    private EmpaStatus deviceStatus;
    private EmpaDeviceManager deviceManager;
    private static String EMPATICA_API_KEY = null; // "eded959cc2054b3da99abce90a43871f"; // TODO insert your API Key here

    private Button edaButton;
    private Button tempButton;
    private Button bvpButton;
    private Button accButton;


    //private RelativeLayout allSensorsLayout;
    private RelativeLayout edaLayout;
    private RelativeLayout tempLayout;
    private RelativeLayout bvpLayout;
    private RelativeLayout accLayout;


    //private Button allSensorsButton;
    private Button bluetoothButton;
    private Button startSessionButton;
    private Button batteryStatusButton;
    private Button sessionLengthButton;


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

    //TextView show data
    private TextView edaValue;
    private TextView tempValueTextView;
    private TextView xValueTextView;
    private TextView yValueTextView;
    private TextView zValueTextView;



    //When we open the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sensor_data);

        username = UserData._username;

        teacherDbHelper = new DatabaseHelper(this);
        db = teacherDbHelper.getWritableDatabase();

        if(getE4ApiKey() != null){
            EMPATICA_API_KEY = getE4ApiKey();
            getDeviceManager();
        }else{
            setUpE4Data();
        }


        //allSensorsButton = (Button) findViewById(R.id.allButton);
        edaButton = (Button) findViewById(R.id.edaButton);
        tempButton = (Button) findViewById(R.id.tempButton);
        bvpButton = (Button) findViewById(R.id.bvpButton);
        accButton = (Button) findViewById(R.id.accButton);

        //allSensorsLayout = (RelativeLayout) findViewById(allSensorsLayout);
        edaLayout = (RelativeLayout) findViewById(R.id.edaLayout);
        tempLayout = (RelativeLayout) findViewById(R.id.tempLayout);
        bvpLayout = (RelativeLayout) findViewById(R.id.bvpLayout);
        accLayout = (RelativeLayout) findViewById(R.id.accLayout);

        edaValue = (TextView) findViewById(R.id.eda_value);
        tempValueTextView = (TextView) findViewById(R.id.temperature_value);
        xValueTextView = (TextView) findViewById(R.id.x_value);
        yValueTextView = (TextView) findViewById(R.id.y_value);
        zValueTextView = (TextView) findViewById(R.id.z_value);


        bluetoothButton = (Button) findViewById(R.id.bluetoothButton);
        startSessionButton = (Button) findViewById(R.id.startSessionButton);
        batteryStatusButton = (Button) findViewById(R.id.batteryButton);
        sessionLengthButton = (Button) findViewById(R.id.sessionLengthButton);


        setUpEDAGraph();
        setUpBVPGraph();
        setUpACCGraph();

        setUpEdaButton();
        setUpTempButton();
        setUpBvpButton();
        setUpAccButton();

        setUpBluetoothButton();
        setUpStartSessionButton();
        setUpSessionLengthButton();
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

    /**
     * Handles resolution callbacks.
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // The user chose not to enable Bluetooth
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(MainSensorDataActivity.this, "Please, turn on Bluetooth to be able to start the real-time session.", Toast.LENGTH_SHORT).show();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        if(deviceStatus == EmpaStatus.CONNECTED){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
            builder.setMessage("Are you sure u want to stop?")
                    .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deviceManager.disconnect();
                            finish();
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
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

    }


    @Override
    protected void onRestart(){
        super.onRestart();
    }

    //After dismiss the dialog or back button from dialog
    @Override
    protected void onResume(){

        super.onResume();
        checkForPemissions();

    }

    //When back button pressed and exit the app onPause(), onStop(), onDestroy()

    //When home button pressed onPause(), onStop()
    //When any dialog open on screen onPause()
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
    protected void onStop(){
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //deviceManager.cleanUp();
    }

    private void checkForPemissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            } else {
                Toast.makeText(getApplicationContext(), "Please grant access to Coarse Location Permission!", Toast.LENGTH_LONG).show();
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please grant access to Coarse Location Permission!", Toast.LENGTH_LONG).show();
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            } else {
                Toast.makeText(getApplicationContext(), "Please grant Storage Permission from Settings!", Toast.LENGTH_LONG).show();
            }
        }
    }


    // After call end onResume()
    // When phone screen off onPaused() --> onStop()
    // When screen is turned back on onRestart() --> onStart() --> onResume()

    @Override
    public void didDiscoverDevice(BluetoothDevice bluetoothDevice, String deviceName, int rssi, boolean allowed) {
//        // Check if the discovered device can be used with your API key. If allowed is always false,
//        // the device is not linked with your API key.
//
        if (allowed) {
            // Stop scanning. The first allowed device will do.
            String currentE4Name = getE4Name();
            if(deviceName.equals("Empatica E4 - " + currentE4Name)){
                deviceManager.stopScanning();
            }
            try {
                // Connect to the device
                deviceManager.connectDevice(bluetoothDevice);
                updateLabel(deviceName);
            } catch (ConnectionNotAllowedException e) {
                // This should happen only if you try to connect when allowed == false.
                Toast.makeText(MainSensorDataActivity.this, "Sorry, you can't connect to this device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void didRequestEnableBluetooth() {
//        // Request the user to enable Bluetooth
//        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    public void setUpBluetoothButton(){
        //Set a click listener on disagree button view
        bluetoothButton.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    // Device does not support Bluetooth
                }

                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                else if(mBluetoothAdapter.isEnabled()){
                    Toast.makeText(MainSensorDataActivity.this, "Bluetooth already on", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void didUpdateSensorStatus(EmpaSensorStatus status, EmpaSensorType type) {
        // No need to implement this right now
    }

    @Override
    public void didUpdateStatus(final EmpaStatus status) {
        // Update the UI
        updateLabel(status.name());

        // The device manager is ready for use
        if (status == EmpaStatus.READY) {
            deviceStatus = EmpaStatus.READY;
        } else if (status == EmpaStatus.CONNECTED) {
            deviceStatus = EmpaStatus.CONNECTED;
        } else if (status == EmpaStatus.DISCONNECTED) {
            deviceStatus = EmpaStatus.DISCONNECTED;
            updateLabel("DISCONNECTED");
        }
    }

    public void setUpStartSessionButton(){
//        //Set a click listener on disagree button view
        startSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deviceStatus == EmpaStatus.READY){
                    //START SERVICE
                    updateLabel(deviceStatus.name() + " - Turn on your device");
                    // Start scanning
                    deviceManager.startScanning();
//                    startService(startSessionButton);
                }else if(deviceStatus == EmpaStatus.CONNECTED){
                    updateLabel(deviceStatus.name());

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
                    builder.setMessage("Are you sure you want to stop session?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //STOP SERVICE
                                    //stopService(startSessionButton);
                                    deviceManager.disconnect();
                                    dialog.cancel();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog disagreeAlertDialog = builder.create();
                    disagreeAlertDialog.show();
                }

            }});
    }


    @Override
    public void didReceiveAcceleration(final int x, final int y, final int z, final double timestamp) {
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {

                teacherDbHelper.addAccSensorValues(new AccelereometerSensor(x, y, z, timestamp), db);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        accXseries.appendData(new DataPoint(lastXacc++, x), false, 10);
                        updateLabel(xValueTextView, "" + x);
                        accYseries.appendData(new DataPoint(lastYacc++, y), false, 10);
                        updateLabel(yValueTextView, "" + y);
                        accZseries.appendData(new DataPoint(lastZacc++, z), false, 10);
                        updateLabel(zValueTextView, "" + z);

                    }
                });
            }
        }).start();
    }

    @Override
    public void didReceiveBatteryLevel(final float battery, double timestamp) {
        batteryStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLabel(String.format("%.0f %%", battery * 100));

            }
        });
    }



    private void setUpSessionLengthButton(){

    }


    @Override
    public void didReceiveIBI(float ibi, double timestamp) {}

    @Override
    public void didReceiveTemperature(final float temp, final double timestamp) {
        updateLabel(tempValueTextView, "" + temp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // we add 100 new entries
                teacherDbHelper.addTempSensorValues(new TemperatureSensor(temp, timestamp), db);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        updateLabel(tempValueTextView, "" + temp);

                    }
                });
            }
        }).start();

    }

    // Update a message, making sure this is run in the UI thread
    private void updateLabel(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainSensorDataActivity.this, text, Toast.LENGTH_SHORT).show();            }
        });
    }

    public void setUpTempButton(){
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edaLayout.setVisibility(View.GONE);
                bvpLayout.setVisibility(View.GONE);
                accLayout.setVisibility(View.GONE);
                tempLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void didReceiveGSR(final float gsr, final double timestamp) {

//     we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                teacherDbHelper.addEdaSensorValues(new EdaSensor(gsr, timestamp), db);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        edaSeries.appendData(new DataPoint(lastXeda++, gsr), false, 10);
                        updateLabel(edaValue, "" + gsr);
                    }
                });
            }
        }).start();


    }

    public void setUpEdaButton(){
        edaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempLayout.setVisibility(View.GONE);
                bvpLayout.setVisibility(View.GONE);
                accLayout.setVisibility(View.GONE);
                edaLayout.setVisibility(View.VISIBLE);

            }
        });

    }

    public void setUpEDAGraph() {
        GraphView edaGraph = (GraphView) findViewById(R.id.graphforeda);
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

    @Override
    public void didReceiveBVP(final float bvp, final double timestamp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teacherDbHelper.addBvpSensorValues(new BloodVolumePressureSensor(bvp, timestamp), db);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        bvpSeries.appendData(new DataPoint(lastXbvp++, bvp), false, 100);
                    }
                });
            }
        }).start();
    }

    public void setUpBVPGraph() {

        GraphView bvpGraph = (GraphView) findViewById(R.id.graphforbvp);
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

    public void setUpBvpButton(){
        bvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempLayout.setVisibility(View.GONE);
                edaLayout.setVisibility(View.GONE);
                accLayout.setVisibility(View.GONE);
                bvpLayout.setVisibility(View.VISIBLE);

            }
        });

    }


    public void setUpACCGraph() {

        GraphView accGraph = (GraphView) findViewById(R.id.graphforacc);
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

    public void setUpAccButton(){
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempLayout.setVisibility(View.GONE);
                edaLayout.setVisibility(View.GONE);
                bvpLayout.setVisibility(View.GONE);
                accLayout.setVisibility(View.VISIBLE);


            }
        });

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

        String selection = E4DataEntry._USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                E4DataContract.E4DataEntry.TABLE_NAME_EMPATICA_E4,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
        builder.setMessage(R.string.enterE4Data)
                .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent enterE4data = new Intent(getApplicationContext(), EmpaticaActivity.class);
                        startActivity(enterE4data);
                        finish();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Manage Account" menu option
            case R.id.manage_account:
                startActivity(new Intent(this, ViewRegistrationFormActivity.class));
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "E4 settings" menu option
            case R.id.action_e4_settings:
                startActivity(new Intent(this, EmpaticaActivity.class));
                return true;
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case R.id.log_out:
                UserData._username = null;
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO implement disagreement after several experiments function
    public void deleteAllRelatedData(){

    }

//    public void startService(View view) {
//        startService(new Intent(getBaseContext(), EmpaticaService.class));
//    }
//
//    // Method to stop the service
//    public void stopService(View view) {
//        stopService(new Intent(getBaseContext(), EmpaticaService.class));
//    }




}
