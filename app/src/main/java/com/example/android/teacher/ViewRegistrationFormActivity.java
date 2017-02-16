package com.example.android.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;

import static com.example.android.teacher.R.id.username;

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

    private String username;



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


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplicationContext(), EditRegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });


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
}
