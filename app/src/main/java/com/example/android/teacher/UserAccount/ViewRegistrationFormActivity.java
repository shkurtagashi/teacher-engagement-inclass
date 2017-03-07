package com.example.android.teacher.UserAccount;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.HelpActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;

import static com.example.android.teacher.HomeActivity.admin_password;

public class ViewRegistrationFormActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    private Button editButton;
    private Button deleteButton;


    private TextView usernameTextview;
    private TextView ageTextview;
    private TextView genderTextview;
    private TextView facultyTextview;
    private TextView programmeTextview;
    private TextView courseTextview;
    private TextView statusTextview;
    private TextView teachingExperienceTextview;
    private TextView consentForm;


    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registration_form);

        dbHelper = new DatabaseHelper(this);
        username = UserData._username;

        editButton = (Button) findViewById(R.id.edit_button);
        deleteButton = (Button) findViewById(R.id.delete_button);


        usernameTextview = (TextView) findViewById(R.id.username);
        ageTextview = (TextView) findViewById(R.id.age);
        genderTextview = (TextView) findViewById(R.id.gender);
        facultyTextview = (TextView) findViewById(R.id.faculty);
        programmeTextview = (TextView) findViewById(R.id.programme);
        courseTextview = (TextView) findViewById(R.id.course);
        statusTextview = (TextView) findViewById(R.id.status);
        teachingExperienceTextview = (TextView) findViewById(R.id.teaching_experience);
        consentForm = (TextView) findViewById(R.id.consent_form);


        //Get all user info for Android ID
        User user = dbHelper.getUserInformation(username);

        //Set textviews text to results from database
        usernameTextview.setText(user.getUsername());
        ageTextview.setText(user.getAge());
        genderTextview.setText(user.getGender());
        facultyTextview.setText(user.getFaculty());
        programmeTextview.setText(user.getProgramme());
        courseTextview.setText(user.getCourse());
        statusTextview.setText(user.getStatus());
        teachingExperienceTextview.setText(user.getTeachingExperience());

        if(user.getAgreement().equals("Yes")){
            consentForm.setText("AGREED");
        }else if(user.getAgreement().equals("No")){
            consentForm.setText("DISAGREED");
        }else{
            consentForm.setText("Not Available");
        }


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplicationContext(), EditRegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        //On Delete Button Clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show dialog box to ask if they are sure
                    //Yes - delete account and all the data
                    //otherwise - cancel action

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewRegistrationFormActivity.this);
                builder.setMessage(R.string.delete_user_text)
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
                                dbHelper.deleteUser(UserData._username);

                                Toast.makeText(getApplicationContext(), "You have successfully deleted user: " + UserData._username, Toast.LENGTH_LONG).show();
                                UserData._username = null;
                                Intent homeIntent = new Intent (getApplicationContext(), HomeActivity.class);
                                startActivity(homeIntent);
                                finish();
                            }
                        });
                AlertDialog disagreeAlertDialog = builder.create();
                disagreeAlertDialog.show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewRegistrationFormActivity.this);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewRegistrationFormActivity.this);
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
