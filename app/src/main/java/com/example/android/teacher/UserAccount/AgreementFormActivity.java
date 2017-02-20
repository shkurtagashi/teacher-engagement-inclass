package com.example.android.teacher.UserAccount;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.HelpActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract.UserEntry;

import static com.example.android.teacher.HomeActivity.admin_password;


public class AgreementFormActivity extends AppCompatActivity{
    private static final String TAG = "AgreementFormActivity";

    private Button agree_button;
    private Button disagree_button;

    private String agreementSelection = UserEntry.AGREEMENT_ACCEPTED;
    private String username;
    private String courses;
    private String password = "";


    DatabaseHelper dbHelper;



//    private String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_agreement_form layout file
        setContentView(R.layout.activity_agreement_form);

        dbHelper = new DatabaseHelper(this);

        username = UserData._username;
        courses = UserData._selectedCourses;

        //Find the View that shows the Agree button
        agree_button = (Button)findViewById(R.id.agree_button);

        //Find the View that shows the Agree button
        disagree_button = (Button)findViewById(R.id.disagree_button);


        setUpAgreeButton();
        setUpDisagreeButton();

    }


    public void setUpAgreeButton(){
        //Set a click listener on agree button view
        agree_button.setOnClickListener(new View.OnClickListener(){

            // The code in this method will be executed when the Agree Button is clicked on.
            @Override
            public void onClick(View v){
                //Save the agreement form answer
                agreementSelection = UserEntry.AGREEMENT_ACCEPTED;
                updateUserTableData();

                //Create a new intent to open the Home Activity
                Intent registrationFormIntent = new Intent (getApplicationContext(), HomeActivity.class);
                startActivity(registrationFormIntent);
                finish();
            }

        });
    }


    public void setUpDisagreeButton(){
        //Set a click listener on disagree button view
        disagree_button.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View v) {
                agreementSelection = UserEntry.AGREEMENT_NOT_ACCEPTED;
                updateUserTableData();
                AlertDialog.Builder builder = new AlertDialog.Builder(AgreementFormActivity.this);
                builder.setMessage(R.string.disagree_text)
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
                                System.exit(0);
                            }
                        });
                AlertDialog disagreeAlertDialog = builder.create();
                disagreeAlertDialog.show();
            }
        });
    }




    public void updateUserTableData(){

        //Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_AGREEMENT, agreementSelection);


        // Which row to update, based on the ID
        String selection = UserEntry.USERNAME + " = ?";
        String[] selectionArgs = { username };

        int count = db.update(
               UserEntry.TABLE_NAME_USERS,
                values,
                selection,
                selectionArgs);

        if(count == -1){
            Toast.makeText(this,"Error saving agreement selection" + count, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Agreement form answer saved for user with username: " + username + ", agreement: " + agreementSelection, Toast.LENGTH_SHORT).show();
        }
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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AgreementFormActivity.this);
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
                                        Toast.makeText(getApplicationContext(), "Wrong Password! Cannot create new user", Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AgreementFormActivity.this);
                alertDialog.setTitle("ADMIN PASSWORD");
                alertDialog.setMessage(" Please enter Admin Password to choose a user");

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
