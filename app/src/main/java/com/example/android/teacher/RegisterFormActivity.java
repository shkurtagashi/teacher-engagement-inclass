package com.example.android.teacher;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract;
import com.example.android.teacher.data.User.UsersContract.UserEntry;

import android.provider.Settings.Secure;

import java.util.ArrayList;

import static android.R.attr.id;
import static com.example.android.teacher.R.id.age;
import static com.example.android.teacher.R.id.course;
import static com.example.android.teacher.R.id.faculty;
import static com.example.android.teacher.R.id.gender;
import static com.example.android.teacher.R.id.programme;
import static com.example.android.teacher.R.id.status;
import static com.example.android.teacher.R.id.username;


public class RegisterFormActivity extends AppCompatActivity {
    private static final String TAG = "RegisterFormActivity";

    DatabaseHelper dbHelper;

    private EditText usernameEditText;
    private Spinner facultyOptions;
    private Spinner ageOptions;
    private RadioGroup genderOptions;
    private Spinner programOptions;
    private Spinner courseOptions;
    private Spinner statusOptions;
    private Spinner teachingExperienceOptions;
    private LinearLayout otherStatusArea;
    private EditText otherStatusValue;

    private Button submitRegistrationFormButton;
    private Button cancelRegistrationFormButton;
    private Button chooseCourseButton;

    public String username;

    private String genderSelection = UserEntry.GENDER_MALE;
    private String ageSelection = UserEntry.AGE_50_ABOVE;
    private String facultySelection = UserEntry.FACULTY_OTHER;
    private String programmeSelection = UsersContract.UserEntry.PROGRAMME_OTHER;
    private String courseSelection = UserEntry.COURSE_OTHER;
    private String statusSelection = UserEntry.STATUS_OTHER;
    private String teachingExpSelection = UserEntry.TEACHING_EXPERIENCE_5_10;

    private String android_id;

    private ArrayList<Integer> selectedCourses;
    private String selectedCoursesString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        dbHelper = new DatabaseHelper(this);
        android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

        usernameEditText = (EditText) findViewById(R.id.username_value);
        ageOptions = (Spinner) findViewById(R.id.age_range);
        genderOptions = (RadioGroup) findViewById(R.id.genderRadioButtons);
        facultyOptions = (Spinner) findViewById(R.id.faculty_title);
        programOptions = (Spinner) findViewById(R.id.program_title);
        courseOptions = (Spinner) findViewById(R.id.course_choices);
        statusOptions = (Spinner) findViewById(R.id.status_choices);
        teachingExperienceOptions = (Spinner) findViewById(R.id.teaching_experience);

        otherStatusArea = (LinearLayout) findViewById(R.id.other_status_area);
        otherStatusValue = (EditText) findViewById(R.id.other_status);

        chooseCourseButton = (Button)findViewById(R.id.courses_button);


        setUpGenderRadioButtons();
        setUpAgeSpinner();
        setUpFacultiesSpinner();
        setUpProgrammeSpinner();
        setUpCourseButton();
        setUpStatusSpinner();
        setUpTeachingExperienceSpinner();


        submitRegistrationFormButton = (Button)findViewById(R.id.submit_button);
        submitRegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                Log.v("RegistrationActivity", username);

                for(int course: selectedCourses){
                    switch (course){
                        case 0:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Information Security"; //Change it to my courses
                            }else{
                                selectedCoursesString += ", Information Security";
                            }
                            break;
                        case 1:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Cyber Communication";
                            }else{
                                selectedCoursesString += ", Cyber Communication";
                            }
                            break;
                        case 2:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Software Architecture and Design";
                            }else{
                                selectedCoursesString += ", Software Architecture and Design";
                            }
                            break;
                        case 3:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Course 4";
                            }else{
                                selectedCoursesString += ", Course 4";
                            }
                            break;
                    }
                }
                System.out.println("Selected courses: " + selectedCoursesString);

                User user = new User();
                user._android_id = android_id;
                user._username = username;
                user._age = ageSelection;
                user._gender = genderSelection;
                user._faculty = facultySelection;
                user._programme = programmeSelection;
                user._courses = selectedCoursesString;
                user._status = statusSelection;
                user._teachingExperience = teachingExpSelection;
                user._agreement = "No";

                Log.v(TAG, user._username);
                UserData._username = user._username;

//                User u = new User(user._android_id, user._username, user._gender, user._age, user._faculty, user._programme, user._courses, user._status, user._teachingExperience, user._agreement);

                dbHelper.addUser(user);
                Toast.makeText(getApplicationContext(), "You have successfully created profile with username: " + username + ", age: " + ageSelection +
                        ", gender: " + genderSelection + ", faculty: " + facultySelection + ", programme " + programmeSelection +
                        ", course " + selectedCoursesString + ", status " + statusSelection + " and with teaching experience of: " + teachingExpSelection,
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent (getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        cancelRegistrationFormButton = (Button)findViewById(R.id.cancel_button);
        cancelRegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                finish();
            }
        });

    }


    /*
    *   Gender Radio Buttons
     */
    private void setUpGenderRadioButtons(){

        genderOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radiobutton_female:
                        genderSelection = UserEntry.GENDER_FEMALE;
                        break;
                    case R.id.radiobutton_male:
                        genderSelection = UserEntry.GENDER_MALE;
                        break;
                }
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
                        ageSelection = UserEntry.AGE_20_30;
                    } else if(selection.equals(getString(R.string.range30to40))){
                        ageSelection = UserEntry.AGE_30_40;
                    } else if (selection.equals(getString(R.string.range40to50))) {
                        ageSelection = UserEntry.AGE_40_50;
                    } else {
                        ageSelection = UserEntry.AGE_50_ABOVE;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ageSelection = UserEntry.AGE_50_ABOVE; // Unknown
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
                        facultySelection = UserEntry.FACULTY_INFORMATICS;
                    } else if(selection.equals(getString(R.string.economics))){
                        facultySelection = UserEntry.FACULTY_ECONOMICS;
                    } else if (selection.equals(getString(R.string.communication_sciences))) {
                        facultySelection = UserEntry.FACULTY_COMMUNICATION_SCIENCES;
                    } else if(selection.equals(getString(R.string.academy_of_architecture))){
                        facultySelection = UserEntry.FACULTY_ACADEMY_OF_ARCHITECTURE;

                    } else {
                        facultySelection = UserEntry.FACULTY_OTHER;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultySelection = UserEntry.FACULTY_OTHER; // Unknown
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
                        programmeSelection = UserEntry.PROGRAMME_BACHELOR;
                    } else if(selection.equals(getString(R.string.master))){
                        programmeSelection = UserEntry.PROGRAMME_MASTER;
                    } else {
                        programmeSelection = UserEntry.PROGRAMME_OTHER;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                programmeSelection = UserEntry.PROGRAMME_OTHER; // Unknown
            }
        });

    }

    /*
    * Course spinner
    */
    private void setUpCourseButton(){
        chooseCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCourses = new ArrayList<Integer>();

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterFormActivity.this);
                builder.setTitle("Choose your courses")
                        .setMultiChoiceItems(R.array.course_choices, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                    selectedCourses.add(which);
                                    System.out.println("Selected course: " + which);
                                }else if(selectedCourses.contains(which)){
                                    selectedCourses.remove(Integer.valueOf(which));
                                }
                            }
                        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Selected courses are: "+ selectedCourses);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

//        ArrayAdapter<CharSequence> coursesAdapter = ArrayAdapter.createFromResource(this,
//                R.array.course_choices, android.R.layout.simple_spinner_item);
//        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        courseOptions.setAdapter(coursesAdapter);
//
//        // Set the integer courseSelection to the constant values
//        courseOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selection = (String) parent.getItemAtPosition(position);
//                if (!TextUtils.isEmpty(selection)) {
//                    if (selection.equals(getString(R.string.course_1))) {
//                        courseSelection = UserEntry.COURSE_1;
//                    } else if(selection.equals(getString(R.string.course_2))) {
//                        courseSelection = UserEntry.COURSE_2;
//                    } else if (selection.equals(getString(R.string.course_3))) {
//                        courseSelection = UserEntry.COURSE_3;
//                    } else if(selection.equals(getString(R.string.course_4))){
//                        courseSelection = UserEntry.COURSE_4;
//                    } else {
//                        courseSelection = UserEntry.COURSE_OTHER;
//                    }
//                }
//            }
//
//            // Because AdapterView is an abstract class, onNothingSelected must be defined
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                courseSelection = UserEntry.COURSE_OTHER; // Unknown
//            }
//        });

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
                        statusSelection = UserEntry.STATUS_FULL_PROFESSOR;
                    } else if(selection.equals(getString(R.string.associate_professor))) {
                        statusSelection = UserEntry.STATUS_ASSOCIATE_PROFESSOR;
                    } else if(selection.equals(getString(R.string.assistant_professor))){
                        statusSelection = UserEntry.STATUS_ASSISTANT_PROFESSOR;
                    } else if(selection.equals(getString(R.string.adjunct_professor))){
                        statusSelection = UserEntry.STATUS_ADJUNCT_PROFESSOR;
                    } else if(selection.equals(getString(R.string.post_doc))){
                        statusSelection = UserEntry.STATUS_POST_DOC;
                    } else if(selection.equals(getString(R.string.phd_student))){
                        statusSelection = UserEntry.STATUS_PHD_STUDENT;
                    } else if(selection.equals(getString(R.string.assistant))){
                        statusSelection = UserEntry.STATUS_ASSISTANT;
                    } else {
                        //otherStatusArea.setVisibility(View.VISIBLE);
                        statusSelection = UserEntry.STATUS_OTHER; // Unknown

                        //statusSelection =  otherStatusValue.getText().toString();
                        //Log.v(TAG, statusSelection);
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                statusSelection = UserEntry.STATUS_OTHER; // Unknown
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
                        teachingExpSelection = UserEntry.TEACHING_EXPERIENCE_0_5;
                    } else if(selection.equals(getString(R.string.teachingExperience5to10))){
                        teachingExpSelection = UserEntry.TEACHING_EXPERIENCE_5_10;
                    } else if(selection.equals(getString(R.string.teachingExperience10to20))){
                        teachingExpSelection = UserEntry.TEACHING_EXPERIENCE_10_20;
                    } else if(selection.equals(getString(R.string.teachingExperience20to30))){
                        teachingExpSelection = UserEntry.TEACHING_EXPERIENCE_20_30;
                    } else {
                        teachingExpSelection = UserEntry.TEACHING_EXPERIENCE_30_ABOVE;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                teachingExpSelection = UserEntry.TEACHING_EXPERIENCE_5_10;
            }
        });

    }

//    private void insertData(){
//
//        DatabaseHelper usersDbHelper = new DatabaseHelper(this);
//
//        //Gets the data repository in write mode
//        SQLiteDatabase db = usersDbHelper.getWritableDatabase();
//
//
//        // Create a ContentValues object where column names are the keys,
//        // and user attributes from the editor are the values.
//        ContentValues values = new ContentValues();
//        values.put(UserEntry._ID, android_id);
//        values.put(UserEntry.USERNAME, username);
//        values.put(UserEntry.COLUMN_GENDER, genderSelection);
//        values.put(UserEntry.COLUMN_AGE, ageSelection);
//        values.put(UserEntry.COLUMN_FACULTY, facultySelection);
//        values.put(UserEntry.COLUMN_PROGRAMME, programmeSelection);
//        values.put(UserEntry.COLUMN_COURSE, courseSelection);
//        values.put(UserEntry.COLUMN_STATUS, statusSelection);
//        values.put(UserEntry.COLUMN_TEACHING_EXPERIENCE, teachingExpSelection);
//
//        long newRowId = db.insert(UserEntry.TABLE_NAME_USERS, null, values);
//        if(newRowId == -1){
//            Toast.makeText(this,"Error saving user", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this,"User saved with row id: " + android_id + ", username: " + username + ", gender: " + genderSelection + ", age: " + ageSelection +
//                    ", faculty: " + facultySelection + ", programme: " + programmeSelection +
//                    ", course:" + courseSelection + ", status: " + statusSelection + "teaching experience of: " + teachingExpSelection,
//                    Toast.LENGTH_LONG).show();
//        }
//
//
//    }


}

