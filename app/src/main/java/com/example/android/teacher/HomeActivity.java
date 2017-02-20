package com.example.android.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
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
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity{

    public DatabaseHelper dbHelper;
    public static final String admin_password = "te2017";
    public String password = "";

    private TextView userTextView;

    public String username;
    public String selectedCourses;

    Calendar calendar;

    MyScheduler scheduler;
    String androidID;

    SwitchDriveController switchDriveController;
    SQLiteController localController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_home.xml layout file
        setContentView(R.layout.activity_home);

        //Start Aware services
//        Intent startAware = new Intent(this, Aware.class);
//        startService(startAware);
        Aware.startESM(this);
        Aware.startScheduler(this);

        //Aware.joinStudy(this, "https://api.awareframework.com/index.php/webservice/index/1098/CySyIhauY1Yw");
//        Intent sync = new Intent(Aware.ACTION_AWARE_SYNC_DATA);
//        sendBroadcast(sync);

        //Toast.makeText(getApplicationContext(), "Syncing data...", Toast.LENGTH_SHORT).show();

        dbHelper = new DatabaseHelper(this);

        userTextView = (TextView) findViewById(R.id.welcome_user);
        TextView sensorData = (TextView) findViewById(R.id.sensor_data);
        TextView surveyData = (TextView) findViewById(R.id.survey_data);
        TextView realTimeStreaming = (TextView) findViewById(R.id.real_time_streaming);
        TextView studentSurveyData = (TextView) findViewById(R.id.student_survey_data);


        //If there is no user in the database - then assign user variable to NULL
        if(dbHelper.getUsersCount() == 0){
            UserData._username = null;
            UserData._selectedCourses = null;

            userTextView.append(" " + "\n Please create an account first");
            sensorData.setEnabled(false);
            sensorData.setBackgroundColor(Color.parseColor("#BDBDBD"));

            surveyData.setEnabled(false);
            surveyData.setBackgroundColor(Color.parseColor("#BDBDBD"));

            realTimeStreaming.setEnabled(false);
            realTimeStreaming.setBackgroundColor(Color.parseColor("#BDBDBD"));

            studentSurveyData.setEnabled(false);
            studentSurveyData.setBackgroundColor(Color.parseColor("#BDBDBD"));

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
            sensorData.setBackgroundColor(Color.parseColor("#BDBDBD"));

            surveyData.setEnabled(false);
            surveyData.setBackgroundColor(Color.parseColor("#BDBDBD"));

            realTimeStreaming.setEnabled(false);
            realTimeStreaming.setBackgroundColor(Color.parseColor("#BDBDBD"));

            studentSurveyData.setEnabled(false);
            studentSurveyData.setBackgroundColor(Color.parseColor("#BDBDBD"));
            Log.v("HomeActivity3", "No username");

        }else{
            if(dbHelper.getUserInformation(UserData._username)._agreement.equals("No")){
                Intent agreementForm = new Intent(HomeActivity.this, AgreementFormActivity.class);
                startActivity(agreementForm);
            }

            userTextView.append(" " + UserData._username);
            Log.v("HomeActivity4", "Else statement" + UserData._username);
        }


        localController = new SQLiteController(this);
        switchDriveController = new SwitchDriveController(getString(R.string.server_address), getString(R.string.token), getString(R.string.password));

        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //Filename as: android_id_username_date
        //Data every week

        //number of tables
//        int nbTableToClean = LocalTables.values().length;
//        int i = 0;
//        Cursor c;
//        //current table to clean
//        LocalTables currTable;
//        String fileName;
//
//        while(i < nbTableToClean) {
//            currTable = LocalTables.values()[i];
//
//            //build name of file to upload
//            fileName = buildFileName(currTable);
//
//            //get all data currently in the table
//            c = getRecords(currTable);
//
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//
//                //upload the data to the server
//                int response = switchDriveController.upload(fileName, toCSV(c, currTable));
//
//                //if the file was put, delete records and update the arrays
//                if (response >= 200 && response <= 207) {
//                    //delete from the db the records where id > startId and id <= endId
//
//                } else {
//                    Log.d("DATA UPLOAD SERVICE", "Owncould's response: " + Integer.toString(response));
//                }
//            }
//
//            i++;
//        }


        //switchDriveController = new SwitchDriveController(getString(R.string.server_address), getString(R.string.token), getString(R.string.password));
        //int a = switchDriveController.upload(androidID, "AAAAA");



        scheduler = new MyScheduler();

        if(UserData._username != null && UserData._selectedCourses != null){
            scheduler.createFirstPAM(this, UserData._selectedCourses);
            scheduler.createFirstPostLectureESM(this, UserData._selectedCourses);
            scheduler.createSecondPAM(this, UserData._selectedCourses);
            scheduler.createSecondPostLectureESM(this, UserData._selectedCourses);
            scheduler.createThirdPAM(this, UserData._selectedCourses);
        }


        /******* Sensor Data Category ******/
        //Find the View that shows the teacher's sensor data category
        //Set a clicklistener on that View
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

    private String toCSV(Cursor records, LocalTables table) {
        String csv = "";
        String[] columns = LocalDbUtility.getTableColumns(table);

        for(int i = 0; i < columns.length; i++) {
            csv += columns[i] + ",";
        }

        csv = csv.substring(0, csv.length()-1);
        csv += "\n";

        do {
            for(int i = 0; i < columns.length; i++) {
                csv += records.getString(i) + ",";
            }
            csv = csv.substring(0, csv.length()-1);
            csv += "\n";
        } while(records.moveToNext());
        csv = csv.substring(0, csv.length()-1);

        return csv;
    }

    private String buildFileName(LocalTables table) {
        //get current date
        String today = buildDate();
        return androidID + "_" + today + "_" + LocalDbUtility.getTableName(table) + "_" + UserData._username + ".csv";
    }

    private String buildDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM-dd-yyyy");
        return mdformat.format(calendar.getTime());
    }

    private Cursor getRecords(LocalTables table) {
        String query = "SELECT * FROM " + LocalDbUtility.getTableName(table);
//                " WHERE " + LocalDbUtility.getTableColumns(table)[0] + " > " + Integer.toString(getRecordId(table));
        return localController.rawQuery(query, null);
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
