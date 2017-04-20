package com.example.android.teacher.Courses;

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
import android.widget.Toast;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONException;

import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.EmpaticaE4.ViewEmpaticaActivity;
import com.example.android.teacher.HelpActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.UserAccount.ChooseAccountActivity;
import com.example.android.teacher.UserAccount.RegisterFormActivity;
import com.example.android.teacher.UserAccount.ViewRegistrationFormActivity;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.User.UserData;

public class QuestionnaireActivity extends AppCompatActivity {
    Intent intent;
    String course;
    String questionnaire;

    public DatabaseHelper dbHelper;

    public static final String admin_password = "te2017";
    public String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        dbHelper = new DatabaseHelper(this);

        //Define home button
        Button goHome = (Button)findViewById(R.id.goHome);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();
            }
        });


        intent = getIntent();
        course = intent.getExtras().getString("course");
        questionnaire = intent.getExtras().getString("questionnaire");


        System.out.println("I am in Questionnaire Activity");
        System.out.println("Course in QuestionnaireActivity: "+course);
        System.out.println("Questionnaire in QuestionnaireActivity: "+ questionnaire);



        if(course.equals("MondayLA") || course.equals("WednesdayLA")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Linear Algebra","Pre-lecture PAM", 40);
                finish();
            }
//            if(questionnaire.equals("SecondPAM")){
//                createPAM("after the first part of", "Linear Algebra","Post-lecture PAM", 40);
//                finish();
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                createPAM("after the second part of", "Linear Algebra","Post-lecture PAM", 40);
//                finish();
//            }

            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Linear Algebra", 40);
                finish();
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Linear Algebra", 40);
                finish();
            }
        }

        if(course.equals("MondayPF") || course.equals("WednesdayPF") || course.equals("FridayPF")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Programming Fundamentals 2","Pre-lecture PAM", 40);
                finish();

            }
//            if(questionnaire.equals("SecondPAM")){
//                createPAM("after the first part of", "Programming Fundamentals 2","Post-lecture PAM", 40);
//                finish();
//
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                createPAM("after the second part of", "Programming Fundamentals 2","Post-lecture PAM", 40);
//                finish();
//
//            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Programming Fundamentals 2", 40);
                finish();

            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Programming Fundamentals 2", 40);
                finish();

            }
        }

        if(course.equals("TuesdayCC") || course.equals("WednesdayCC") || course.equals("ThursdayCC")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Cyber Communication","Pre-lecture PAM", 40);
                finish();

            }
//            if(questionnaire.equals("SecondPAM")){
//                createPAM("after the first part of", "Cyber Communication","Post-lecture PAM", 40);
//                finish();
//
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                createPAM("after the second part of", "Cyber Communication","Post-lecture PAM", 40);
//                finish();
//            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Cyber Communication", 40);
                finish();
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Cyber Communication", 40);
                finish();
            }
        }

        if(course.equals("MondayInf1") || course.equals("MondayInf1")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Information Security","Pre-lecture PAM", 40);
                finish();
            }
//            if(questionnaire.equals("SecondPAM")){
//                createPAM("after the first part of", "Information Security","Post-lecture PAM", 40);
//                finish();
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                createPAM("after the second part of", "Information Security","Post-lecture PAM", 40);
//                finish();
//            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Information Security", 40);
                finish();
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Information Security", 40);
                finish();
            }
        }

        if(course.equals("TuesdaySAD") || course.equals("ThursdaySAD")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Software Architecture and Design","Pre-lecture PAM", 40);
                finish();
            }

//            if(questionnaire.equals("SecondPAM")){
//                createPAM("after the first part of", "Software Architecture and Design","Post-lecture PAM", 40);
//                finish();
//            }
//            if(questionnaire.equals("ThirdPAM")){
//                createPAM("after the second part of", "Software Architecture and Design","Post-lecture PAM", 40);
//                finish();
//            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Software Architecture and Design", 40);
                finish();
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Software Architecture and Design", 40);
                finish();
            }
        }
    }



    public void createPAM(String moment, String course,String title, int expirationThreshold){
        try {
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle(title)
                    .setInstructions("Pick the closest to how you feel now "+moment+" " + course + "!")
                    .setSubmitButton("Done")
                    .setExpirationThreshold(60*expirationThreshold); //setNotificationRetry(3) - number of times to retry notification if it expires

            factory.addESM(q1);
            ESM.queueESM(getApplicationContext(), factory.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void createPostLecture(String moment, String course, int expirationThreshold){
        try {
            ESMFactory factory = new ESMFactory();

            ESM_PAM pam = new ESM_PAM();
            pam.setTitle("Survey about the "+moment+" of "+course+" (1/6)") // moment - "first part"; course - "Linear Algebra"
                    .setInstructions("Pick the closest to how you feel now in " + course + "!")
                    .setSubmitButton("Done")
                    .setExpirationThreshold(60*expirationThreshold); //setNotificationRetry(3) - number of times to retry notification if it expires


            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("Survey about the "+moment+" of "+course+" (2/6)") // moment - "first part"; course - "Linear Algebra"
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("I loved teaching this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (3/6)")
                    .setInstructions("I was excited about teaching this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (4/6)")
                    .setInstructions("I felt happy while teaching this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (5/6)")
                    .setInstructions("I found teaching this lecture fun.")
                    .setSubmitButton("Next");

            ESM_Freetext esmFreeText = new ESM_Freetext();
            esmFreeText.setTitle("Post Lecture Survey")
                    .setSubmitButton("Finish")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("Please describe the moment(s) during which you felt particularly engaged");


            ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
            esmQuickAnswer.addQuickAnswer("Yes")
                    .addQuickAnswer("No")
                    .setTitle("Post Lecture Survey (6/6)")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                    .addFlow("Yes", esmFreeText.build());


            factory.addESM(pam);
            factory.addESM(esmRadio1);
            factory.addESM(esmRadio2);
            factory.addESM(esmRadio3);
            factory.addESM(esmRadio4);
            factory.addESM(esmQuickAnswer);

            ESM.queueESM(getApplicationContext(), factory.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        MenuItem delete_account = menu.findItem(R.id.delete_account);
        MenuItem help = menu.findItem(R.id.action_help);
        MenuItem deviceSettings = menu.findItem(R.id.action_e4_settings);



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
                if(dbHelper.getUsersCount() < 1) {
                    startActivity(new Intent(this, RegisterFormActivity.class));
                    finish();
                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuestionnaireActivity.this);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuestionnaireActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionnaireActivity.this);
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
                                dbHelper.deleteUser(UserData._username);
                                Toast.makeText(getApplicationContext(), "You have successfully deleted account: " + UserData._username, Toast.LENGTH_LONG).show();
                                UserData._username = null;
                            }
                        });
                AlertDialog disagreeAlertDialog = builder.create();
                disagreeAlertDialog.show();

                return true;

            // Respond to a click on the "E4 settings" menu option
            case R.id.action_e4_settings:
                if(dbHelper.getEmpaticaE4Count() == 0){
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
}
