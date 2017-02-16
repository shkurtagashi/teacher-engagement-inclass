package com.example.android.teacher;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract;

import static com.example.android.teacher.R.id.age;

public class EditRegistrationActivity extends AppCompatActivity {
    private Button updateButton;
    private DatabaseHelper dbHelper;

    private TextView usernameTextView;
    private Spinner facultyOptions;
    private Spinner ageOptions;
    private RadioGroup genderOptions;
    private Spinner programOptions;
    private Spinner courseOptions;
    private Spinner statusOptions;
    private Spinner teachingExperienceOptions;
    private LinearLayout otherStatusArea;
    private EditText otherStatusValue;



    private String username;
    private String android_id;
//    private String genderSelection = UsersContract.UserEntry.GENDER_MALE;
//    private String ageSelection = UsersContract.UserEntry.AGE_50_ABOVE;
//    private String facultySelection = UsersContract.UserEntry.FACULTY_OTHER;
//    private String programmeSelection = UsersContract.UserEntry.PROGRAMME_OTHER;
//    private String courseSelection = UsersContract.UserEntry.COURSE_OTHER;
//    private String statusSelection = UsersContract.UserEntry.STATUS_OTHER;
//    private String teachingExpSelection = UsersContract.UserEntry.TEACHING_EXPERIENCE_5_10;

    private String genderSelection;
    private String ageSelection;
    private String facultySelection;
    private String programmeSelection;
    private String courseSelection;
    private String statusSelection;
    private String teachingExpSelection;
    public User user;

    User u1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration);

        username = UserData._username;
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        dbHelper = new DatabaseHelper(this);
        u1 = new User();
        u1 = dbHelper.getUserInformation(username);


        updateButton = (Button) findViewById(R.id.update_button);
        usernameTextView = (TextView) findViewById(R.id.username_value);
        usernameTextView.setText(username);

        ageOptions = (Spinner) findViewById(R.id.age_range);
        genderOptions = (RadioGroup) findViewById(R.id.genderRadioButtons);
        facultyOptions = (Spinner) findViewById(R.id.faculty_title);
        programOptions = (Spinner) findViewById(R.id.program_title);
        courseOptions = (Spinner) findViewById(R.id.course_choices);
        statusOptions = (Spinner) findViewById(R.id.status_choices);
        teachingExperienceOptions = (Spinner) findViewById(R.id.teaching_experience);


        setUpGenderRadioButtons();
        setUpAgeSpinner();
        setUpFacultiesSpinner();
        setUpProgrammeSpinner();
        setUpCourseSpinner();
        setUpStatusSpinner();
        setUpTeachingExperienceSpinner();

        //Get all new selected values from layout   //Create user object with those values

        user = new User();
        user.setUsername(username);

        //Update database with this user
        //FIX THISSS
        updateButton = (Button)findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.updateUserRegistration(user);
                Toast.makeText(getApplicationContext(), "You have successfully updated profile with username: " + username + ", age: " + ageSelection +
                                ", gender: " + genderSelection + ", faculty: " + facultySelection + ", programme " + programmeSelection +
                                ", course " + courseSelection + ", status " + statusSelection + " and with teaching experience of: " + teachingExpSelection,
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent (getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /*
*   Gender Radio Buttons
 */
    private void setUpGenderRadioButtons(){

        genderOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_female:
                        genderSelection = UsersContract.UserEntry.GENDER_FEMALE;
                        break;
                    case R.id.radio_male:
                        genderSelection = UsersContract.UserEntry.GENDER_MALE;
                        break;
                }
                user.setGender(genderSelection);
            }
        });



    }

    /*
    * Age range spinner
    */
    private void setUpAgeSpinner(){

        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_range_choices, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageOptions.setAdapter(ageAdapter);

        // Set the integer ageSelection to the constant values
        ageOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.range20to30))) {
                        ageSelection = UsersContract.UserEntry.AGE_20_30;
                    } else if(selection.equals(getString(R.string.range30to40))){
                        ageSelection = UsersContract.UserEntry.AGE_30_40;
                    } else if (selection.equals(getString(R.string.range40to50))) {
                        ageSelection = UsersContract.UserEntry.AGE_40_50;
                    } else {
                        ageSelection = UsersContract.UserEntry.AGE_50_ABOVE;
                    }
                    user.setAge(ageSelection);
                }
            }


            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                ageSelection = UsersContract.UserEntry.AGE_50_ABOVE; // Unknown
            }
        });
    }

    /*
    * Faculties options spinner
     */
    private void setUpFacultiesSpinner(){

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> facultyOptionsAdapter = ArrayAdapter.createFromResource(this,
                R.array.faculty_title_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        facultyOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        facultyOptions.setAdapter(facultyOptionsAdapter);

        // Set the integer facultySelection to the constant values
        facultyOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.informatics))) {
                        facultySelection = UsersContract.UserEntry.FACULTY_INFORMATICS;
                    } else if(selection.equals(getString(R.string.economics))){
                        facultySelection = UsersContract.UserEntry.FACULTY_ECONOMICS;
                    } else if (selection.equals(getString(R.string.communication_sciences))) {
                        facultySelection = UsersContract.UserEntry.FACULTY_COMMUNICATION_SCIENCES;
                    } else if(selection.equals(getString(R.string.academy_of_architecture))){
                        facultySelection = UsersContract.UserEntry.FACULTY_ACADEMY_OF_ARCHITECTURE;
                    } else {
                        facultySelection = UsersContract.UserEntry.FACULTY_OTHER;
                    }

                    user.setFaculty(facultySelection);

                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                facultySelection = UsersContract.UserEntry.FACULTY_OTHER; // Unknown
            }
        });


    }


    /*
    * Programme spinner
    */
    private void setUpProgrammeSpinner(){
        ArrayAdapter<CharSequence> programsAdapter = ArrayAdapter.createFromResource(this,
                R.array.program_title_choices, android.R.layout.simple_spinner_item);
        programsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programOptions.setAdapter(programsAdapter);

        // Set the integer programmeSelection to the constant values
        programOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.bachelor))) {
                        programmeSelection = UsersContract.UserEntry.PROGRAMME_BACHELOR;
                    } else if(selection.equals(getString(R.string.master))){
                        programmeSelection = UsersContract.UserEntry.PROGRAMME_MASTER;
                    } else {
                        programmeSelection = UsersContract.UserEntry.PROGRAMME_OTHER;
                    }
                }

                user.setProgramme(programmeSelection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                programmeSelection = UsersContract.UserEntry.PROGRAMME_OTHER; // Unknown
            }
        });

    }

    /*
    * Course spinner
    */
    private void setUpCourseSpinner(){
        ArrayAdapter<CharSequence> coursesAdapter = ArrayAdapter.createFromResource(this,
                R.array.course_choices, android.R.layout.simple_spinner_item);
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseOptions.setAdapter(coursesAdapter);

        // Set the integer courseSelection to the constant values
        courseOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.course_1))) {
                        courseSelection = UsersContract.UserEntry.COURSE_1;
                    } else if(selection.equals(getString(R.string.course_2))) {
                        courseSelection = UsersContract.UserEntry.COURSE_2;
                    } else if (selection.equals(getString(R.string.course_3))) {
                        courseSelection = UsersContract.UserEntry.COURSE_3;
                    } else if(selection.equals(getString(R.string.course_4))){
                        courseSelection = UsersContract.UserEntry.COURSE_4;
                    } else {
                        courseSelection = UsersContract.UserEntry.COURSE_OTHER;
                    }
                }

                user.setCourse(courseSelection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                courseSelection = UsersContract.UserEntry.COURSE_OTHER; // Unknown
            }
        });

    }


    /*
    * Status spinner
    */
    private void setUpStatusSpinner(){

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_choices, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusOptions.setAdapter(statusAdapter);

        // Set the integer statusSelection to the constant values
        statusOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.full_professor))) {
                        statusSelection = UsersContract.UserEntry.STATUS_FULL_PROFESSOR;
                    } else if(selection.equals(getString(R.string.associate_professor))) {
                        statusSelection = UsersContract.UserEntry.STATUS_ASSOCIATE_PROFESSOR;
                    } else if(selection.equals(getString(R.string.assistant_professor))){
                        statusSelection = UsersContract.UserEntry.STATUS_ASSISTANT_PROFESSOR;
                    } else if(selection.equals(getString(R.string.adjunct_professor))){
                        statusSelection = UsersContract.UserEntry.STATUS_ADJUNCT_PROFESSOR;
                    } else if(selection.equals(getString(R.string.post_doc))){
                        statusSelection = UsersContract.UserEntry.STATUS_POST_DOC;
                    } else if(selection.equals(getString(R.string.phd_student))){
                        statusSelection = UsersContract.UserEntry.STATUS_PHD_STUDENT;
                    } else if(selection.equals(getString(R.string.assistant))){
                        statusSelection = UsersContract.UserEntry.STATUS_ASSISTANT;
                    } else {
                        //otherStatusArea.setVisibility(View.VISIBLE);
                        statusSelection = UsersContract.UserEntry.STATUS_OTHER; // Unknown

                        //statusSelection =  otherStatusValue.getText().toString();
                        //Log.v(TAG, statusSelection);
                    }
                }
                user.setStatus(statusSelection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                statusSelection = UsersContract.UserEntry.STATUS_OTHER; // Unknown
            }
        });

    }

    /*
    * Teaching Experience
    */
    private void setUpTeachingExperienceSpinner(){

        ArrayAdapter<CharSequence> teachingExperienceAdapter = ArrayAdapter.createFromResource(this,
                R.array.teaching_experience_choices, android.R.layout.simple_spinner_item);
        teachingExperienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teachingExperienceOptions.setAdapter(teachingExperienceAdapter);

        // Set the integer teachingExpSelection to the constant values
        teachingExperienceOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.teachingExperience0to5))) {
                        teachingExpSelection = UsersContract.UserEntry.TEACHING_EXPERIENCE_0_5;
                    } else if(selection.equals(getString(R.string.teachingExperience5to10))){
                        teachingExpSelection = UsersContract.UserEntry.TEACHING_EXPERIENCE_5_10;
                    } else if(selection.equals(getString(R.string.teachingExperience10to20))){
                        teachingExpSelection = UsersContract.UserEntry.TEACHING_EXPERIENCE_10_20;
                    } else if(selection.equals(getString(R.string.teachingExperience20to30))){
                        teachingExpSelection = UsersContract.UserEntry.TEACHING_EXPERIENCE_20_30;
                    } else {
                        teachingExpSelection = UsersContract.UserEntry.TEACHING_EXPERIENCE_30_ABOVE;
                    }
                }

                user.setTeachingExperience(teachingExpSelection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                teachingExpSelection = UsersContract.UserEntry.TEACHING_EXPERIENCE_5_10;
            }
        });

    }
}
