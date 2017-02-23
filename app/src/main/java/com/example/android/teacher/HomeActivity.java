package com.example.android.teacher;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.aware.Aware;
import com.empatica.empalink.EmpaDeviceManager;
import com.example.android.teacher.Courses.MyScheduler;
import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.data.RemoteDataStorage.SwitchDriveController;
import com.example.android.teacher.Sensors.MainSensorDataActivity;
import com.example.android.teacher.Sensors.SensorDataActivity;
import com.example.android.teacher.Surveys.StudentSurveyDataActivity;
import com.example.android.teacher.Surveys.SurveyDataActivity;
import com.example.android.teacher.UserAccount.AgreementFormActivity;
import com.example.android.teacher.UserAccount.ChooseAccountActivity;
import com.example.android.teacher.UserAccount.RegisterFormActivity;
import com.example.android.teacher.UserAccount.ViewRegistrationFormActivity;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.LocalDataStorage.LocalDbUtility;
import com.example.android.teacher.data.LocalDataStorage.LocalTables;
import com.example.android.teacher.data.LocalDataStorage.SQLiteController;
import com.example.android.teacher.data.RemoteDataStorage.Uploader;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity{


    // Empatica connect and streaming
//    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 3;
    private static final int REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 4;


    public DatabaseHelper dbHelper;

    public static final String admin_password = "te2017";
    public String password = "";

    private TextView userTextView;
    public String username;


    Calendar calendar;
    String weekday;
    SimpleDateFormat dayFormat;
    int month;
    int dayOfMonth;

    MyScheduler scheduler;
    public static String androidID;

    SwitchDriveController switchDriveController;
    SQLiteController localController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_home.xml layout file
        setContentView(R.layout.activity_home);

        androidID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        dbHelper = new DatabaseHelper(this);

        userTextView = (TextView) findViewById(R.id.welcome_user);
        TextView sensorData = (TextView) findViewById(R.id.sensor_data);
        TextView surveyData = (TextView) findViewById(R.id.survey_data);
        TextView realTimeStreaming = (TextView) findViewById(R.id.real_time_streaming);
        TextView studentSurveyData = (TextView) findViewById(R.id.student_survey_data);


        /*
        *   Set up Home page in different cases: # users in database equals to 0, 1 or more
         */
        if(dbHelper.getUsersCount() == 0){
            UserData._username = null;
            UserData._selectedCourses = null;

            userTextView.append(" " + "\n Please create an account first");
            sensorData.setEnabled(false);
            sensorData.setBackground(getResources().getDrawable(R.drawable.chartsdisabled));

            surveyData.setEnabled(false);
            surveyData.setBackground(getResources().getDrawable(R.drawable.surveysdisabled));

            realTimeStreaming.setEnabled(false);
            realTimeStreaming.setBackground(getResources().getDrawable(R.drawable.empadisabled));

            studentSurveyData.setEnabled(false);
            studentSurveyData.setBackground(getResources().getDrawable(R.drawable.classdisabled));

        }else if(dbHelper.getUsersCount() == 1){
            List<User> users = dbHelper.getAllUsers();
            for(User u: users){
                UserData._username = u.getUsername();
                UserData._selectedCourses = u.getCourse();
                userTextView.append(" " + UserData._username);
                Log.v("HomeActivity2", UserData._username);
            }
        }else if(UserData._username == null && dbHelper.getUsersCount() > 1){
            userTextView.append(" " + "\n Please choose an account first");

            sensorData.setEnabled(false);
            sensorData.setBackground(getResources().getDrawable(R.drawable.chartsdisabled));

            surveyData.setEnabled(false);
            surveyData.setBackground(getResources().getDrawable(R.drawable.surveysdisabled));

            realTimeStreaming.setEnabled(false);
            realTimeStreaming.setBackground(getResources().getDrawable(R.drawable.empadisabled));

            studentSurveyData.setEnabled(false);
            studentSurveyData.setBackground(getResources().getDrawable(R.drawable.classdisabled));
        }else{
            if(dbHelper.getUserInformation(UserData._username)._agreement.equals("No")){
                Intent agreementForm = new Intent(HomeActivity.this, AgreementFormActivity.class);
                startActivity(agreementForm);
            }

            userTextView.append(" " + UserData._username);
        }

        checkPermissions();

        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

            //Start Aware services
            Intent startAware = new Intent(this, Aware.class);
            startService(startAware);
            Aware.startESM(this);
            Aware.startScheduler(this);

            triggerSchedulers();
        }



        /******* Sensor Data Category ******/
        sensorData.setOnClickListener(new View.OnClickListener(){

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view){
                //Create new intent to open the @link NumbersActivity
                Intent sensorDataIntent = new Intent(HomeActivity.this, SensorDataActivity.class);
                startActivity(sensorDataIntent);
            }
        });

//        /******  Teacher Survey Data *******/
        surveyData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent surveyDataIntent = new Intent(HomeActivity.this, SurveyDataActivity.class);
                startActivity(surveyDataIntent);

            }
        });

        /******  Real Time Streaming of Sensor Data *******/
        realTimeStreaming.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent realTimeStreamingIntent = new Intent(HomeActivity.this, MainSensorDataActivity.class);
                startActivity(realTimeStreamingIntent);

            }
        });

        /******  Student Survey Data *******/
        studentSurveyData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent studentSurveyDataIntent = new Intent(HomeActivity.this, StudentSurveyDataActivity.class);
                startActivity(studentSurveyDataIntent);

            }
        });

    }

    private void checkPermissions() {
        // Android 6 (API level 23) now require ACCESS_COARSE_LOCATION permission to use BLE
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_PERMISSION_ACCESS_FINE_LOCATION);
        }else if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
        }else if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        }
    }


    /*
     * Trigger survey schedulers only in this period - February 20th and March 12th
     * Upload all the data Remotely only in this period
     */
    public void triggerSchedulers(){
        scheduler = new MyScheduler();
        dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        calendar = Calendar.getInstance();
        weekday = dayFormat.format(calendar.getTime());
        scheduler = new MyScheduler();
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        if((month == 2 && dayOfMonth >=20 && dayOfMonth <= 29) || (month == 3 && dayOfMonth >=1 && dayOfMonth <= 12)){
            //Trigger PAM and Lecture Surveys
            if(UserData._username != null && UserData._selectedCourses != null) {
                scheduler.createFirstPAM(this, UserData._selectedCourses);
                scheduler.createFirstPostLectureESM(this, UserData._selectedCourses);
                scheduler.createSecondPAM(this, UserData._selectedCourses);
                scheduler.createSecondPostLectureESM(this, UserData._selectedCourses);
                scheduler.createThirdPAM(this, UserData._selectedCourses);
            }

            //Upload the data everyday after 15 oclock
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
                switchDriveController = new SwitchDriveController(getString(R.string.server_address), getString(R.string.token), getString(R.string.password));
                localController = new SQLiteController(this);
                Uploader uploader = new Uploader(androidID, switchDriveController, localController);
                //Upload Local Tables
                uploader.upload();

                //Upload Data from Esms table
                uploader.uploadAware(this);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Fine Location permission required")
                            .setMessage("Without this permission you cannot continue, allow it in order to be able to use the app.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkPermissions();
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

            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Fine Location permission required")
                            .setMessage("Without this permission you cannot continue, allow it in order to be able to use the app.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkPermissions();
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

            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Fine Location permission required")
                            .setMessage("Without this permission you cannot continue, allow it in order to be able to use the app.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkPermissions();
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

            case REQUEST_PERMISSION_ACCESS_COARSE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Coarse Location permission required")
                            .setMessage("Without this permission bluetooth low energy devices cannot be found, allow it in order to connect to Empatica E4 device.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkPermissions();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        if(UserData._username != null){
//            username = UserData._username;
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem choose_account = menu.findItem(R.id.choose_account);
        MenuItem manage_account = menu.findItem(R.id.manage_account);
        MenuItem log_out = menu.findItem(R.id.log_out);

        if(dbHelper.getUsersCount() < 2)
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
        }else{
            manage_account.setVisible(false);
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
                if(dbHelper.getUsersCount() < 1) {
                    startActivity(new Intent(this, RegisterFormActivity.class));
                    finish();
                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                    alertDialog.setTitle("ADMIN PASSWORD");
                    alertDialog.setMessage(" Please enter Admin Password to create a new user");

                    final EditText input = new EditText(getApplicationContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setTextColor(Color.BLACK);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                alertDialog.setTitle("ADMIN PASSWORD");
                alertDialog.setMessage(" Please enter Admin Password to choose a user");

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

            // Respond to a click on the "E4 settings" menu option
            case R.id.action_e4_settings:
                i = new Intent (this, EmpaticaActivity.class);
                startActivity(i);
                return true;


            //Respond to a click on the "Log out" menu option
            case R.id.log_out:

                UserData._username = null;
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
}
