package com.example.android.teacher.Sensors.RealtimeFragments;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empatica.empalink.config.EmpaStatus;
import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.EmpaticaE4.EmpaticaService;
import com.example.android.teacher.EmpaticaE4.ViewEmpaticaActivity;
import com.example.android.teacher.HelpActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.Sensors.Fragments.SimpleFragmentPagerAdapter;
import com.example.android.teacher.UserAccount.ChooseAccountActivity;
import com.example.android.teacher.UserAccount.RegisterFormActivity;
import com.example.android.teacher.UserAccount.ViewRegistrationFormActivity;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.User.UserData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import static com.example.android.teacher.HomeActivity.admin_password;
import static com.example.android.teacher.R.id.accLayout;
import static com.example.android.teacher.R.id.bvpLayout;
import static com.example.android.teacher.R.id.edaLayout;
import static com.example.android.teacher.R.id.home;
import static com.example.android.teacher.R.id.startSessionButton;
import static com.example.android.teacher.R.id.tempLayout;
import static com.example.android.teacher.R.layout.fragment_home;

public class MainSensorDataActivity extends AppCompatActivity {
    //implements EmpaStatusDelegate, EmpaDataDelegate

    private static final String TAG = "MainSensorDataActivity";
    DatabaseHelper teacherDbHelper;
    SQLiteDatabase db;

    String username;
    public String password = "";
    public String batteryLevel = "";


    // Empatica connect and streaming
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 1;

    public static int edaLayoutVisibility;


//    private static String EMPATICA_API_KEY = null; // "eded959cc2054b3da99abce90a43871f"; // TODO insert your API Key here


    //private Button allSensorsButton;
    private Button bluetoothButton;
    private Button startSessionButton2;
    private Button batteryStatusButton;
    private Button sessionLengthButton;

    BroadcastReceiver batteryReceiver;

    private Button homeButton;
    private Button edaButton;
    private Button tempButton;
    private Button accButton;
    private Button bvpButton;


    private RelativeLayout edaLayout;
    private RelativeLayout homeLayout;
    private RelativeLayout tempLayout;
    private RelativeLayout accLayout;
    private RelativeLayout bvpLayout;

    ArrayList<String> edaList;




    private LineGraphSeries<DataPoint> edaSeries;
    private double lastXeda = 0;

    //TextView show data
//    private TextView edaValue;
//    BroadcastReceiver edaReceiver;

    //When we open the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sensor_data);

        username = UserData._username;

        teacherDbHelper = new DatabaseHelper(this);
        db = teacherDbHelper.getWritableDatabase();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        edaSeries = new LineGraphSeries<DataPoint>();
//        edaButton = (Button)findViewById(R.id.edaButton);
//        homeButton = (Button)findViewById(R.id.homeButton);
//        accButton = (Button)findViewById(R.id.accButton);
//        tempButton = (Button)findViewById(R.id.tempButton);
//        bvpButton = (Button)findViewById(R.id.bvpButton);

//        edaLayout = (RelativeLayout) findViewById(R.id.edaLayout);
//        homeLayout = (RelativeLayout) findViewById(R.id.homeLayout);
//        tempLayout = (RelativeLayout) findViewById(R.id.tempLayout);
//        accLayout = (RelativeLayout) findViewById(R.id.accLayout);
//        bvpLayout = (RelativeLayout) findViewById(R.id.bvpLayout);

//        edaLayoutVisibility = edaLayout.getVisibility();



        //edaValuesOther = new ArrayList<String>();
//        setUpEdaButton();
//        setUpHomeButton();
//        setUpEDAGraph();

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager3);

        // Create an adapter that knows which fragment should be shown on each page
        RealtimeFragmentPagerAdapter adapter = new RealtimeFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sensor_realtime_data_tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        bluetoothButton = (Button) findViewById(R.id.bluetoothButton);
        startSessionButton2 = (Button) findViewById(R.id.startSessionButton2);
        batteryStatusButton = (Button) findViewById(R.id.batteryButton);
        sessionLengthButton = (Button) findViewById(R.id.sessionLengthButton);


        if(getE4ApiKey() == null){
            setUpE4Data();
        }

        setUpBluetoothButton();
        setUpStartButton();
        setUpSessionLengthButton();
        setUpBatteryButton();
    }

    /**
     * Handles resolution callbacks.
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // The user chose not to enable Bluetooth
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(MainSensorDataActivity.this, "Please, turn on Bluetooth to be able to start the real-time session.", Toast.LENGTH_SHORT).show();
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart(){
        super.onStart();
//        LocalBroadcastManager.getInstance(this).registerReceiver((edaReceiver), new IntentFilter(EmpaticaService.EDA_RESULT));
//        LocalBroadcastManager.getInstance(this).registerReceiver((batteryReceiver), new IntentFilter(EmpaticaService.BATTERY_RESULT));
    }

    @Override
    protected void onStop(){
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(batteryReceiver);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(edaReceiver);
        super.onStop();
    }


    private void setUpBatteryButton() {
        batteryStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainSensorDataActivity.this, "Empatica E4 battery % not available!", Toast.LENGTH_SHORT).show();

//                if(batteryLevel.equals("") || batteryLevel.isEmpty() || batteryLevel.equals(null)){
//                }else{
//                    Toast.makeText(MainSensorDataActivity.this,"Device has " + batteryLevel + "battery!", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.example.android.teacher.EmpaticaE4.EmpaticaService".equals(service.service.getClassName())) {
                Log.v("Service Running", "trueee");
                return true;
            }
        }
        Log.v("Service Running", "falsee");
        return false;
    }

    public void setUpStartButton() {
        final Intent i = new Intent(getApplicationContext(), EmpaticaService.class);

        startSessionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isServiceRunning()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
                        builder.setMessage("Are you sure you want to stop session?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //STOP SERVICE
                                        stopService(i);
                                        startSessionButton2.setBackgroundResource(R.drawable.circled_play);
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
                }else{
                    getApplicationContext().startService(i);
                    startSessionButton2.setBackgroundResource(R.drawable.stop4);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_COARSE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    //initEmpaticaDeviceManager();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    new AlertDialog.Builder(this)
                            .setTitle("Permission required")
                            .setMessage("Without this permission bluetooth low energy devices cannot be found, allow it in order to connect to the device.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        //initEmpaticaDeviceManager();
                                    } else {
                                        // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("Exit application", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // without permission exit is the only way
                                    finish();
                                }
                            })
                            .show();
                }
                break;
        }
    }

//    public void setUpStartStopButton() {
//        statusReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                final String deviceStatus = intent.getStringExtra(EmpaticaService.DEVICE_STATUS);
//
//                if(!deviceStatus.equals(null)) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            if(deviceStatus.equals("READY") || deviceStatus.equals("DISCOVERING") || deviceStatus.equals("DISCONNECTING") || deviceStatus.equals("DISCONNECTED")){
////                                startSessionButton2.setBackgroundResource(R.drawable.circled_play);
////                            }else
//                            if (deviceStatus.equals("CONNECTING") || deviceStatus.equals("CONNECTED")) {
//                                startSessionButton2.setBackgroundResource(R.drawable.stop4);
//                            }
//                        }
//                    });
//                }
//
//            }
//        };
//    }


//    public void setUpStartSessionButton(){
//        final Intent i = new Intent(MainSensorDataActivity.this, EmpaticaService.class);
//
//        startSessionButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(getDeviceStatus() != null){
//                    if(getDeviceStatus().equals("READY")){
//                        //START SERVICE
//                        MainSensorDataActivity.this.startService(i);
//
//                    }else if(getDeviceStatus().equals("CONNECTED")){
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
//                        builder.setMessage("Are you sure you want to stop session?")
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        //STOP SERVICE
//                                        stopService(i);
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog disagreeAlertDialog = builder.create();
//                        disagreeAlertDialog.show();
//
//                    }
//                }
//            }});
//    }



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

    private void setUpSessionLengthButton(){}

    // Update a label with some text, making sure this is run in the UI thread
    private void updateLabel(final TextView label, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                label.setText(text);
            }
        });
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

        MenuItem choose_account = menu.findItem(R.id.choose_account);
        MenuItem manage_account = menu.findItem(R.id.manage_account);
        MenuItem log_out = menu.findItem(R.id.log_out);
        MenuItem delete_account = menu.findItem(R.id.delete_account);
        MenuItem help = menu.findItem(R.id.action_help);
        MenuItem deviceSettings = menu.findItem(R.id.action_e4_settings);



        if(teacherDbHelper.getUsersCount() < 2)
        {
            choose_account.setVisible(false);
            log_out.setVisible(false);
        }
        else
        {
            choose_account.setVisible(true);
            if(UserData._username == null){
                log_out.setVisible(false);
            }
            log_out.setVisible(true);
        }

        if(UserData._username != null){
            manage_account.setVisible(true);
            delete_account.setVisible(true);
            help.setVisible(true);
            deviceSettings.setVisible(true);
        }else{
            manage_account.setVisible(false);
            delete_account.setVisible(false);
            help.setVisible(false);
            deviceSettings.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            //Respond to a click on "Create Account" menu option
            case R.id.add_account:
                if(teacherDbHelper.getUsersCount() < 1) {
                    startActivity(new Intent(this, RegisterFormActivity.class));
                    finish();
                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainSensorDataActivity.this);
                    alertDialog.setTitle("ADMIN PASSWORD");
                    alertDialog.setMessage("Please enter Admin Password to create a new user");

                    final EditText input = new EditText(getApplicationContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setTextColor(Color.WHITE);
                    input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    input.setGravity(Gravity.CENTER);
                    alertDialog.setView(input);
                    alertDialog.setIcon(R.drawable.key);

                    alertDialog.setNegativeButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    password = input.getText().toString();
                                    if (admin_password.equals(password)) {
                                        Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), RegisterFormActivity.class);
                                        startActivityForResult(intent, 0);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Password! You cannot create new user.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    alertDialog.setPositiveButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }

                return true;

            //Respond to a click on "Choose Account" menu option
            case R.id.choose_account:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainSensorDataActivity.this);
                alertDialog.setTitle("ADMIN PASSWORD");
                alertDialog.setMessage("Please enter Admin Password to choose a user");

                final EditText input = new EditText(getApplicationContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setTextColor(Color.WHITE);
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                input.setGravity(Gravity.CENTER);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.key);

                alertDialog.setNegativeButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                password = input.getText().toString();
                                if (admin_password.equals(password)) {
                                    Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ChooseAccountActivity.class);
                                    startActivityForResult(intent, 0);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Wrong Password! You cannot choose account.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                alertDialog.setPositiveButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

                return true;

            // Respond to a click on the "Manage Account" menu option
            case R.id.manage_account:
                i = new Intent (this, ViewRegistrationFormActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.delete_account:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
                builder.setMessage("Are you sure you want to delete " + UserData._username + " account?")
                        .setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                teacherDbHelper.deleteUser(UserData._username);
                                Toast.makeText(getApplicationContext(), "You have successfully deleted account: " + UserData._username, Toast.LENGTH_LONG).show();
                                UserData._username = null;
                            }
                        });
                AlertDialog disagreeAlertDialog = builder.create();
                disagreeAlertDialog.show();

                return true;

                // Respond to a click on the "E4 settings" menu option
            case R.id.action_e4_settings:
                if(teacherDbHelper.getEmpaticaE4Count() == 0){
                    i = new Intent (this, EmpaticaActivity.class);
                    startActivity(i);
                }else{
                    i = new Intent (this, ViewEmpaticaActivity.class);
                    startActivity(i);
                }

                return true;

            //Respond to a click on the "Log out" menu option
            case R.id.log_out:
                UserData._username = null;
                UserData._selectedCourses = null;
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;


            //Respond to a click on the "Help" menu option
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (HomeActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //    @Override
//    public void didReceiveGSR(final float gsr, final double timestamp) {
////     we're going to simulate real time with thread that append data to the graph
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                teacherDbHelper.addEdaSensorValues(new EdaSensor(gsr, timestamp), db);
//                if(edaLayout.getVisibility() == View.VISIBLE) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            edaSeries.appendData(new DataPoint(lastXeda++, gsr), false, 10);
//                            updateLabel(edaValue, "" + gsr);
//                        }
//                    });
//                }
//            }
//        }).start();
//    }

    //    @Override
//    public void didReceiveBVP(final float bvp, final double timestamp) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                teacherDbHelper.addBvpSensorValues(new BloodVolumePressureSensor(bvp, timestamp), db);
//                if(bvpLayout.getVisibility() == View.VISIBLE) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            bvpSeries.appendData(new DataPoint(lastXbvp++, bvp), false, 100);
//                        }
//                    });
//                }
//            }
//        }).start();
//    }


//    @Override
//    public void didReceiveAcceleration(final int x, final int y, final int z, final double timestamp) {
//        // we're going to simulate real time with thread that append data to the graph
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                teacherDbHelper.addAccSensorValues(new AccelereometerSensor(x, y, z, timestamp), db);
//                if(accLayout.getVisibility() == View.VISIBLE) {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            accXseries.appendData(new DataPoint(lastXacc++, x), false, 10);
//                            updateLabel(xValueTextView, "" + x);
//                            accYseries.appendData(new DataPoint(lastYacc++, y), false, 10);
//                            updateLabel(yValueTextView, "" + y);
//                            accZseries.appendData(new DataPoint(lastZacc++, z), false, 10);
//                            updateLabel(zValueTextView, "" + z);
//
//                        }
//                    });
//                }
//            }
//        }).start();
//    }
//
//    @Override
//    public void didReceiveBatteryLevel(final float battery, double timestamp) {
//        batteryStatusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateLabel(String.format("%.0f %%", battery * 100));
//
//            }
//        });
//    }
//
//
//    @Override
//    public void didReceiveIBI(float ibi, double timestamp) {}
//
//    @Override
//    public void didReceiveTemperature(final float temp, final double timestamp) {
////        updateLabel(tempValueTextView, "" + temp);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // we add 100 new entries
//                teacherDbHelper.addTempSensorValues(new TemperatureSensor(temp, timestamp), db);
//                if(tempLayout.getVisibility() == View.VISIBLE) {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            updateLabel(tempValueTextView, "" + temp);
//
//                        }
//                    });
//                }
//            }
//        }).start();
//    }

//    public void setUpStartSessionButton(){
//        startSessionButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainSensorDataActivity.this, EmpaticaService.class);
//                MainSensorDataActivity.this.stopService(i);
//
////                if(isServiceRunning){
////                    stopService(i);
////                }else{
////                    startService(i);
////
////                }
//            }
//        });
//
//        startSessionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(deviceStatus == EmpaStatus.READY){
//                    //START SERVICE
//                    updateLabel(deviceStatus.name() + " - Switch ON your device");
//                    deviceManager.startScanning();
//
////                    startService(startSessionButton);
//                }else if(deviceStatus == EmpaStatus.CONNECTED){
//                    updateLabel(deviceStatus.name());
//
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
//                    builder.setMessage("Are you sure you want to stop session?")
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //STOP SERVICE
//                                    //stopService(startSessionButton);
//                                    deviceManager.disconnect();
//                                    dialog.cancel();
//
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog disagreeAlertDialog = builder.create();
//                    disagreeAlertDialog.show();
//                }
//
//
//            }});
//
//        startSessionButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(deviceStatus == "READY"){
//                    //START SERVICE
//                    updateLabel(deviceStatus + " - Please Switch ON your device");
//                    // Start scanning
//                    deviceManager.startScanning();
//
//                }else if(deviceStatus == EmpaStatus.CONNECTED){
//                    updateLabel(deviceStatus.name());
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
//                    builder.setMessage("Are you sure you want to stop session?")
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //STOP SERVICE
//                                    //stopService(startSessionButton);
//                                    deviceManager.disconnect();
//                                    dialog.cancel();
//
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog disagreeAlertDialog = builder.create();
//                    disagreeAlertDialog.show();
//
//                }
//
//            }});
//    }

//    public String getE4Name(){
//        String name;
//
//        DatabaseHelper usersDbHelper = new DatabaseHelper(this);
//        SQLiteDatabase db = usersDbHelper.getReadableDatabase();
//
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//                E4DataContract.E4DataEntry.COLUMN_E4_NAME
//
//        };
//
//
//
//        Cursor cursor = db.query(
//                E4DataContract.E4DataEntry.TABLE_NAME_EMPATICA_E4,                     // The table to query
//                projection,                               // The columns to return
//                null,                                // The columns for the WHERE clause
//                null,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                null                                 // The sort order
//        );
//
//        if(cursor.getCount() > 0){
//            cursor.moveToFirst();
//            name = cursor.getString(cursor.getColumnIndex("empatica_name"));
//            return name;
//        }
//
//        cursor.close();
//
//        return null;
//    }

    //    private void initEmpaticaDeviceManager() {
//        // Android 6 (API level 23) now require ACCESS_COARSE_LOCATION permission to use BLE
////        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_PERMISSION_ACCESS_COARSE_LOCATION);
////        } else {
//            // Create a new EmpaDeviceManager. MainActivity is both its data and status delegate.
//            deviceManager = new EmpaDeviceManager(getApplicationContext(), this, this);
//
//            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//            if (mWifi.isConnected()) {
//                // Initialize the Device Manager using your API key. You need to have Internet access at this point.
//                deviceManager.authenticateWithAPIKey(EMPATICA_API_KEY);
//            }else{
//                Toast.makeText(MainSensorDataActivity.this, "Sorry, you need WiFi connection to connect to Empatica E4!", Toast.LENGTH_SHORT).show();
//            }
//        //}
//    }

//    @Override
//    public void didDiscoverDevice(BluetoothDevice bluetoothDevice, String deviceName, int rssi, boolean allowed) {
////        // Check if the discovered device can be used with your API key. If allowed is always false,
////        // the device is not linked with your API key.
////
//        if (allowed) {
//            // Stop scanning. The first allowed device will do.
//            String currentE4Name = getE4Name();
//            if(deviceName.equals("Empatica E4 - " + currentE4Name)){
//                deviceManager.stopScanning();
//            }
//            try {
//                // Connect to the device
//                deviceManager.connectDevice(bluetoothDevice);
//                updateLabel(deviceName);
//            } catch (ConnectionNotAllowedException e) {
//                // This should happen only if you try to connect when allowed == false.
//                Toast.makeText(MainSensorDataActivity.this, "Sorry, you can't connect to this device", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else{
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainSensorDataActivity.this);
//            alertDialog.setTitle("Non-valid API Key");
//            alertDialog.setMessage("The API Key provided is not linked with your Empatica E4 . Please provide a valid API Key! Do you want to continue?");
//
//            alertDialog.setNegativeButton(R.string.yes,
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(getApplicationContext(), EditEmpaticaActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//            alertDialog.setPositiveButton(R.string.no,
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                            finish();
//                        }
//                    });
//            alertDialog.show();
//
//        }
//    }
//
//    @Override
//    public void didRequestEnableBluetooth() {
////        // Request the user to enable Bluetooth
//        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//    }
//
//
//
//
//    @Override
//    public void didUpdateSensorStatus(EmpaSensorStatus status, EmpaSensorType type) {
//        // No need to implement this right now
//    }
//
//    @Override
//    public void didUpdateStatus(final EmpaStatus status) {
//
//        // Update the UI
//        updateLabel(status.name());
//
//        // The device manager is ready for use
//        if (status == EmpaStatus.READY) {
//            deviceStatus = EmpaStatus.READY;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    startSessionButton.setBackgroundResource(R.drawable.circled_play);
//                    startSessionButton2.setBackgroundResource(R.drawable.circled_play);
//                }
//            });
//        } else if (status == EmpaStatus.CONNECTED) {
//            deviceStatus = EmpaStatus.CONNECTED;
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    startSessionButton.setBackgroundResource(R.drawable.stop4);
//                    startSessionButton2.setBackgroundResource(R.drawable.stop4);
//                }
//            });
//        } else if (status == EmpaStatus.DISCONNECTED) {
//            deviceStatus = EmpaStatus.DISCONNECTED;
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    startSessionButton.setBackgroundResource(R.drawable.circled_play);
//                    startSessionButton2.setBackgroundResource(R.drawable.circled_play);
//                }
//            });
//
//            updateLabel("DISCONNECTED");
//        }else if(status == EmpaStatus.DISCONNECTING){
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    startSessionButton.setBackgroundResource(R.drawable.circled_play);
//                    startSessionButton2.setBackgroundResource(R.drawable.circled_play);
//                }
//            });
//        }else if(status == EmpaStatus.CONNECTING){
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    startSessionButton.setBackgroundResource(R.drawable.stop4);
//                    startSessionButton2.setBackgroundResource(R.drawable.stop4);
//                }
//            });
//        }else{
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    startSessionButton.setBackgroundResource(R.drawable.circled_play);
//                    startSessionButton2.setBackgroundResource(R.drawable.circled_play);
//                }
//            });
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if(deviceStatus == EmpaStatus.CONNECTED){
//            AlertDialog.Builder builder = new AlertDialog.Builder(MainSensorDataActivity.this);
//            builder.setMessage("Are you sure u want to stop?")
//                    .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            deviceManager.disconnect();
//                            finish();
//                        }
//                    })
//                    .setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//            AlertDialog disagreeAlertDialog = builder.create();
//            disagreeAlertDialog.show();
//        }else{
//            super.onBackPressed();
//        }
//    }
    private void setUpHomeButton() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accLayout.setVisibility(View.GONE);
                tempLayout.setVisibility(View.GONE);
                edaLayout.setVisibility(View.GONE);
                bvpLayout.setVisibility(View.GONE);
                homeLayout.setVisibility(View.VISIBLE);
                edaLayoutVisibility = View.GONE;

            }
        });
    }

    // Update a message, making sure this is run in the UI thread
//    private void updateLabel(final String text) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainSensorDataActivity.this, text, Toast.LENGTH_SHORT).show();            }
//        });
//    }
//
//    public void setUpTempButton(){
//        tempButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                homeLayout.setVisibility(View.GONE);
//                edaLayout.setVisibility(View.GONE);
//                bvpLayout.setVisibility(View.GONE);
//                accLayout.setVisibility(View.GONE);
//                tempLayout.setVisibility(View.VISIBLE);
//            }
//        });
//
//    }

    public void setUpEdaButton(){
        edaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeLayout.setVisibility(View.GONE);
                tempLayout.setVisibility(View.GONE);
                bvpLayout.setVisibility(View.GONE);
                accLayout.setVisibility(View.GONE);
                edaLayoutVisibility = View.VISIBLE;
                edaLayout.setVisibility(View.VISIBLE);

            }
        });

    }

//    private void setUpTempGraph() {
//        tempReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String temp = intent.getStringExtra(EmpaticaService.TEMP);
//                if(temp != null)
//                updateLabel(tempValueTextView, temp);
//            }
//        };
//
//    }

    public void setUpEDAGraph() {

        GraphView edaGraph = (GraphView) findViewById(R.id.graphforeda);
//        edaSeries = new LineGraphSeries<DataPoint>();
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

//        edaReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
////                String eda = intent.getStringExtra(EmpaticaService.EDA);
////                edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(eda)), false, 10);
//
//                edaList = intent.getStringArrayListExtra(EmpaticaService.EDA);
//
//                Log.v("MainSensorData", edaList.size() + "");
//
//                for (int i = 0; i < edaList.size(); i++) {
//                    edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(edaList.get(i))), false, 10);
//                }
//
//            }
//        };
    }

//    public void setUpBVPGraph() {
//
//        GraphView bvpGraph = (GraphView) findViewById(R.id.graphforbvp);
////        bvpSeries = new LineGraphSeries<DataPoint>();
//        bvpSeries.setTitle("Blood Volume Pressure");
//        bvpSeries.setColor(Color.parseColor("#F44336"));
//        //bvpSeries.setDrawDataPoints(true);
//        //bvpSeries.setDataPointsRadius(10);
//        //bvpSeries.setThickness(8);
//        // bvpSeries.setDrawBackground(true);
//        //bvpSeries.setBackgroundColor(Color.parseColor("#FFCDD2"));
//        bvpGraph.addSeries(bvpSeries);
//        Viewport bvpViewport = bvpGraph.getViewport();
//        bvpViewport.setYAxisBoundsManual(true);
//        bvpViewport.setMinY(-700);
//        bvpViewport.setMaxY(700);
//        bvpViewport.setScrollable(true);
//
//        bvpReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String bvp = intent.getStringExtra(EmpaticaService.BVP);
//                if(bvp != null)
//                    bvpSeries.appendData(new DataPoint(lastXbvp++, Double.parseDouble(bvp)), false, 10);
//            }
//        };
//    }
//
//    public void setUpBvpButton(){
//        bvpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                homeLayout.setVisibility(View.GONE);
//                tempLayout.setVisibility(View.GONE);
//                edaLayout.setVisibility(View.GONE);
//                accLayout.setVisibility(View.GONE);
//                bvpLayout.setVisibility(View.VISIBLE);
//            }
//        });
//
//    }
//
//    public void setUpACCGraph() {
//
//        GraphView accGraph = (GraphView) findViewById(R.id.graphforacc);
////        accXseries = new LineGraphSeries<DataPoint>();
//        accXseries.setTitle("X");
//        accXseries.setColor(Color.parseColor("#F44336"));
//        accXseries.setDrawDataPoints(true);
//        accXseries.setDataPointsRadius(10);
//        accXseries.setThickness(8);
//
//
////        accYseries = new LineGraphSeries<DataPoint>();
//        accYseries.setTitle("Y");
//        accYseries.setColor(Color.parseColor("#4CAF50"));
//        accYseries.setDrawDataPoints(true);
//        accYseries.setDataPointsRadius(10);
//        accYseries.setThickness(8);
//
////        accZseries = new LineGraphSeries<DataPoint>();
//        accZseries.setTitle("Z");
//        accZseries.setColor(Color.parseColor("#FFC107"));
//        accZseries.setDrawDataPoints(true);
//        accZseries.setDataPointsRadius(10);
//        accZseries.setThickness(8);
//
//        accGraph.getLegendRenderer().setVisible(true);
//        accGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//        accGraph.getLegendRenderer().setBackgroundColor(Color.parseColor("#EEEEEE"));
//
//        accGraph.addSeries(accXseries);
//        accGraph.addSeries(accYseries);
//        accGraph.addSeries(accZseries);
//
//
//        Viewport accViewport = accGraph.getViewport();
//        accViewport.setYAxisBoundsManual(true);
//        accViewport.setMinY(-100);
//        accViewport.setMaxY(100);
//        accViewport.setScrollable(true);
//
//        accXReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String x = intent.getStringExtra(EmpaticaService.ACC_X);
//                if(x != null)
//                    accXseries.appendData(new DataPoint(lastXacc++, Double.parseDouble(x)), false, 10);
//                    updateLabel(xValueTextView, x);
//
//                }
//            };
//
//        accYReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String y = intent.getStringExtra(EmpaticaService.ACC_Y);
//                if(y != null)
//                    accYseries.appendData(new DataPoint(lastYacc++, Double.parseDouble(y)), false, 10);
//                    updateLabel(yValueTextView, y);
//            }
//        };
//
//        accZReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String z = intent.getStringExtra(EmpaticaService.ACC_Z);
//                if(z != null)
//                    accZseries.appendData(new DataPoint(lastZacc++, Double.parseDouble(z)), false, 10);
//                    updateLabel(zValueTextView, z);
//            }
//        };
//    }

//    public void setUpAccButton(){
//        accButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                homeLayout.setVisibility(View.GONE);
//                tempLayout.setVisibility(View.GONE);
//                edaLayout.setVisibility(View.GONE);
//                bvpLayout.setVisibility(View.GONE);
//                accLayout.setVisibility(View.VISIBLE);
//
//
//            }
//        });
//
//    }

}
