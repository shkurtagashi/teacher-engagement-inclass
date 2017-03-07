package com.example.android.teacher;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.empatica.empalink.EmpaDeviceManager;
import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.Surveys.SurveyDataActivity;
import com.example.android.teacher.UserAccount.ChooseAccountActivity;
import com.example.android.teacher.UserAccount.RegisterFormActivity;
import com.example.android.teacher.UserAccount.ViewRegistrationFormActivity;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract.E4DataEntry;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract;

import static com.example.android.teacher.HomeActivity.admin_password;

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

    String password = "";
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        dbHelper = new DatabaseHelper(this);

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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HelpActivity.this);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HelpActivity.this);
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



}
