package com.example.android.teacher.UserAccount;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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

import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.HelpActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract;

import java.util.ArrayList;

import static com.example.android.teacher.HomeActivity.admin_password;
import static com.example.android.teacher.R.id.username;

public class EditRegistrationActivity extends AppCompatActivity {
    private Button updateButton;
    private DatabaseHelper dbHelper;

    private TextView usernameTextView;
    private Spinner facultyOptions;
    private Spinner ageOptions;
    private Button chooseCourseButton;
    private RadioGroup genderOptions;
    private Spinner programOptions;
    private Spinner statusOptions;
    private Spinner teachingExperienceOptions;

    private String genderSelection;
    private String ageSelection;
    private String facultySelection;
    private String programmeSelection;
    private String statusSelection;
    private String teachingExpSelection;

    private String password = "";

    private ArrayList<Integer> selectedCourses = null;
    private String selectedCoursesString = "";


    User u1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration);

        dbHelper = new DatabaseHelper(this);
        u1 = new User();
        u1 = dbHelper.getUserInformation(UserData._username);

        updateButton = (Button) findViewById(R.id.update_button);
        usernameTextView = (TextView) findViewById(R.id.username_value);
        usernameTextView.setText(UserData._username);

        ageOptions = (Spinner) findViewById(R.id.age_range);
        genderOptions = (RadioGroup) findViewById(R.id.genderRadioButtons);
        facultyOptions = (Spinner) findViewById(R.id.faculty_title);
        programOptions = (Spinner) findViewById(R.id.program_title);
        chooseCourseButton = (Button)findViewById(R.id.courses_button);
        statusOptions = (Spinner) findViewById(R.id.status_choices);
        teachingExperienceOptions = (Spinner) findViewById(R.id.teaching_experience);


        setUpGenderRadioButtons();
        setUpAgeSpinner();
        setUpFacultiesSpinner();
        setUpProgrammeSpinner();
        setUpCourseButton();
        setUpStatusSpinner();
        setUpTeachingExperienceSpinner();


        //Update database with this user
        updateButton = (Button)findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting Course Title for each selected checkbox
                setUpCoursesNames(selectedCourses);

//                if(genderSelection.equals("")){
//                    Toast.makeText(getApplicationContext(), "Please select your Gender!", Toast.LENGTH_LONG).show();
//                    return;
//                }

                if(ageSelection.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select your Age!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(facultySelection.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select the Faculty where you are working!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(programmeSelection.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select a Programme!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(selectedCoursesString.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select at least one course in which you are doing this study!", Toast.LENGTH_LONG).show();
                    return;
                }


                if(statusSelection.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select your Status at University!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(teachingExpSelection.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select your experience in teaching!", Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User();
                user.setUsername(UserData._username);
                user.setAge(ageSelection);
                user.setGender(genderSelection);
                user.setCourse(selectedCoursesString);
                user.setFaculty(facultySelection);
                user.setProgramme(programmeSelection);
                user.setTeachingExperience(teachingExpSelection);
                user.setStatus(statusSelection);

                UserData._username = user.getUsername();
                UserData._selectedCourses = user.getCourse();
                dbHelper.updateUserRegistration(user);

                Toast.makeText(getApplicationContext(), "You have successfully updated profile with username: " + user.getUsername() + ", age: " + user.getAge() +
                                ", gender: " + user.getGender() + ", faculty: " + user.getFaculty() + ", programme " + user.getProgramme() +
                                ", course " + user.getCourse() + ", status " + user.getStatus() + " and with teaching experience of: " + user.getTeachingExperience(),
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
                    default:
                        genderSelection = "";
                }
            }
        });



    }

    /*
* Courses Button
*/
    private void setUpCourseButton(){
        selectedCourses = new ArrayList<Integer>();

        chooseCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditRegistrationActivity.this);
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

    }

    public void setUpCoursesNames(ArrayList<Integer> selectedCourses){

        for(int course: selectedCourses){
            switch (course){
                case 0:
                    if(selectedCoursesString.equals("")){
                        selectedCoursesString += "Information Security";
                    }else{
                        selectedCoursesString += " - Information Security";
                    }
                    break;
                case 1:
                    if(selectedCoursesString.equals("")){
                        selectedCoursesString += "Cyber Communication";
                    }else{
                        selectedCoursesString += " - Cyber Communication";
                    }
                    break;
                case 2:
                    if(selectedCoursesString.equals("")){
                        selectedCoursesString += "Software Architecture and Design";
                    }else{
                        selectedCoursesString += " - Software Architecture and Design";
                    }
                    break;
                case 3:
                    if(selectedCoursesString.equals("")){
                        selectedCoursesString += "Linear Algebra";
                    }else{
                        selectedCoursesString += " - Linear Algebra";
                    }
                case 4:
                    if(selectedCoursesString.equals("")){
                        selectedCoursesString += "Programming Fundamentals 2";
                    }else{
                        selectedCoursesString += " - Programming Fundamentals 2";
                    }
                    break;
            }
        }
        System.out.println("Selected courses: " + selectedCoursesString);

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
                    if(selection.equals("Select Item")) {
                        ageSelection = "";
                    } else if (selection.equals(getString(R.string.range20to30))) {
                        ageSelection = UsersContract.UserEntry.AGE_20_30;
                    } else if(selection.equals(getString(R.string.range30to40))){
                        ageSelection = UsersContract.UserEntry.AGE_30_40;
                    } else if (selection.equals(getString(R.string.range40to50))) {
                        ageSelection = UsersContract.UserEntry.AGE_40_50;
                    } else {
                        ageSelection = UsersContract.UserEntry.AGE_50_ABOVE;
                    }
                }
            }


            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ageSelection = ""; // Unknown
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
                    if(selection.equals("Select Item")) {
                        facultySelection = "";
                    } else if (selection.equals(getString(R.string.informatics))) {
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

                    //user.setFaculty(facultySelection);

                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultySelection = ""; // Unknown
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
                    if(selection.equals("Select Item")) {
                        programmeSelection = "";
                    } else if (selection.equals(getString(R.string.bachelor))) {
                        programmeSelection = UsersContract.UserEntry.PROGRAMME_BACHELOR;
                    } else if(selection.equals(getString(R.string.master))){
                        programmeSelection = UsersContract.UserEntry.PROGRAMME_MASTER;
                    } else {
                        programmeSelection = UsersContract.UserEntry.PROGRAMME_OTHER;
                    }
                }

                //user.setProgramme(programmeSelection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                programmeSelection = ""; // Unknown
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
                    if(selection.equals("Select Item")) {
                        statusSelection = "";
                    } else if (selection.equals(getString(R.string.full_professor))) {
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
                        statusSelection = UsersContract.UserEntry.STATUS_OTHER; // Unknown
                    }
                }
                //user.setStatus(statusSelection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                statusSelection = ""; // Unknown
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
                    if(selection.equals("Select Item")) {
                        teachingExpSelection = "";
                    } else if (selection.equals(getString(R.string.teachingExperience0to5))) {
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

//                user.setTeachingExperience(teachingExpSelection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                teachingExpSelection = "";
            }
        });

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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditRegistrationActivity.this);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditRegistrationActivity.this);
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
