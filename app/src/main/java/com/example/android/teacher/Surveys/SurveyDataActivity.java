package com.example.android.teacher.Surveys;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aware.Aware;
import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;
import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.HelpActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.UserAccount.ChooseAccountActivity;
import com.example.android.teacher.UserAccount.RegisterFormActivity;
import com.example.android.teacher.UserAccount.ViewRegistrationFormActivity;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.User.UserData;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.android.teacher.HomeActivity.admin_password;

public class SurveyDataActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private Button generalSurveyButton;
    private Button pamButton;
    private Button postLectureSurveyButton;

    int expirationThreshold = 420;
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_data);

        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
        generalSurveyButton = (Button) findViewById(R.id.general_survey_button);
        postLectureSurveyButton = (Button) findViewById(R.id.postlecture_survey_button);
        pamButton = (Button) findViewById(R.id.pam_button);

        pamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    ESMFactory factory = new ESMFactory();

                    ESM_PAM q1 = new ESM_PAM();
                    q1.setTitle("PAM")
                            .setInstructions("Pick the closest to how you feel now!")
                            .setSubmitButton("Done")
                            .setExpirationThreshold(60*expirationThreshold);

                    factory.addESM(q1);
                    ESM.queueESM(getApplicationContext(), factory.build());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        postLectureSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    ESMFactory factory = new ESMFactory();

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.setInstructions("I loved teaching this lecture.");
                    esmRadio1.setTitle("Post Lecture Survey (1/5)");


                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.setInstructions("I was excited about teaching this lecture.");
                    esmRadio2.setTitle("Post Lecture Survey (2/5)");


                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.setInstructions("I felt happy while teaching this lecture.");
                    esmRadio3.setTitle("Post Lecture Survey (3/5)");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.setInstructions("I found teaching this lecture fun.");
                    esmRadio4.setTitle("Post Lecture Survey (4/5)");

                    ESM_Freetext esmFreeText = new ESM_Freetext();
                    esmFreeText.setTitle("Post Lecture Survey")
                            .setSubmitButton("Finish")
                            .setExpirationThreshold(60*expirationThreshold)
                            .setInstructions("Please describe the moment(s) during which you felt particularly engaged");


                    ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
                    esmQuickAnswer.addQuickAnswer("Yes")
                            .addQuickAnswer("No")
                            .setTitle("Post Lecture Survey (5/5)")
                            .setExpirationThreshold(60*expirationThreshold)
                            .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                            .addFlow("Yes", esmFreeText.build());


                    ArrayList<ESM_Radio> esms = new ArrayList<>();
                    esms.add(esmRadio1);
                    esms.add(esmRadio2);
                    esms.add(esmRadio3);
                    esms.add(esmRadio4);


                    for (ESM_Radio esmRadio : esms) {
                        esmRadio.addRadio("Strongly Agree")
                                .addRadio("Agree")
                                .addRadio("Neutral")
                                .addRadio("Disagree")
                                .addRadio("Strongly Disagree")
                                .setSubmitButton("Next")
                                .setExpirationThreshold(60*expirationThreshold);
                    }

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
        });




        generalSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    ESMFactory factory = new ESMFactory();

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.setInstructions("At my work, I feel bursting with energy.");
                    esmRadio1.setTitle("General Survey (1/21)");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.setInstructions("At my job, I feel strong and vigorous.");
                    esmRadio2.setTitle("General Survey (2/21)");


                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.setInstructions("I am enthusiastic about my job.");
                    esmRadio3.setTitle("General Survey (3/21)");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.setInstructions("My job inspires me.");
                    esmRadio4.setTitle("General Survey (4/21)");


                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.setInstructions("When I get up in the morning I feel like going to work.");
                    esmRadio5.setTitle("General Survey (5/21)");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.setInstructions("I feel happy when I am working intensively.");
                    esmRadio6.setTitle("General Survey (6/21)");

                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.setInstructions("I am proud of the work that I do.");
                    esmRadio7.setTitle("General Survey (7/21)");


                    ESM_Radio esmRadio8 = new ESM_Radio();
                    esmRadio8.setInstructions("I am immersed in my work.");
                    esmRadio8.setTitle("General Survey (8/21)");

                    ESM_Radio esmRadio9 = new ESM_Radio();
                    esmRadio9.setInstructions("I get carried away when I am working.");
                    esmRadio9.setTitle("General Survey (9/21)");


                    ESM_Radio esmRadio10 = new ESM_Radio();
                    esmRadio10.setInstructions("I love teaching.");
                    esmRadio10.setTitle("General Survey (10/21)");


                    ESM_Radio esmRadio11 = new ESM_Radio();
                    esmRadio11.setInstructions("I am excited about teaching.");
                    esmRadio11.setTitle("General Survey (11/21)");


                    ESM_Radio esmRadio12 = new ESM_Radio();
                    esmRadio12.setInstructions("I feel happy while teaching.");
                    esmRadio12.setTitle("General Survey (12/21)");


                    ESM_Radio esmRadio13 = new ESM_Radio();
                    esmRadio13.setInstructions("I find teaching fun.");
                    esmRadio13.setTitle("General Survey (13/21)");


                    ESM_Radio esmRadio14 = new ESM_Radio();
                    esmRadio14.setInstructions("While teaching I pay a lot of attention to my work.");
                    esmRadio14.setTitle("General Survey (14/21)");


                    ESM_Radio esmRadio15 = new ESM_Radio();
                    esmRadio15.setInstructions("While teaching I really throw myself into my work.");
                    esmRadio15.setTitle("General Survey (13/17)");


                    ESM_Radio esmRadio16 = new ESM_Radio();
                    esmRadio16.setInstructions("While teaching, I work with intensity.");
                    esmRadio16.setTitle("General Survey (16/21)");


                    ESM_Radio esmRadio17 = new ESM_Radio();
                    esmRadio17.setTitle("General Survey (17/21)")
                            .setInstructions("I try my hardest to perform well while teaching.");

                    ESM_Radio esmRadio18 = new ESM_Radio();
                    esmRadio18.setTitle("General Survey (18/21)")
                            .setInstructions("In class, I care about the problems of my students.");


                    ESM_Radio esmRadio19 = new ESM_Radio();
                    esmRadio19.setTitle("General Survey (19/21)")
                            .setInstructions("In class, I am empathetic towards my students.");


                    ESM_Radio esmRadio20 = new ESM_Radio();
                    esmRadio20.setTitle("General Survey (20/21)")
                            .setInstructions("In class, I am aware of my student's feelings.");


                    ESM_Radio esmRadio21 = new ESM_Radio();
                    esmRadio21.setTitle("General Survey (21/21)")
                            .setInstructions("In class, I show warmth to my students.");


                    ArrayList<ESM_Radio> esms = new ArrayList<>();
                    esms.add(esmRadio1);
                    esms.add(esmRadio2);
                    esms.add(esmRadio3);
                    esms.add(esmRadio4);
                    esms.add(esmRadio5);
                    esms.add(esmRadio6);
                    esms.add(esmRadio7);
                    esms.add(esmRadio8);
                    esms.add(esmRadio9);
                    esms.add(esmRadio10);
                    esms.add(esmRadio11);
                    esms.add(esmRadio12);
                    esms.add(esmRadio13);
                    esms.add(esmRadio14);
                    esms.add(esmRadio15);
                    esms.add(esmRadio16);
                    esms.add(esmRadio17);
                    esms.add(esmRadio18);
                    esms.add(esmRadio19);
                    esms.add(esmRadio20);
                    esms.add(esmRadio21);

                    for (ESM_Radio esmRadio : esms) {
                        esmRadio.addRadio("Always")
                                .addRadio("Very Often")
                                .addRadio("Often")
                                .addRadio("Sometimes")
                                .addRadio("Rareley")
                                .addRadio("Almost Never")
                                .addRadio("Never")
                                .setSubmitButton("Next")
                                .setExpirationThreshold(60*expirationThreshold);
                    }

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);
                    factory.addESM(esmRadio7);
                    factory.addESM(esmRadio8);
                    factory.addESM(esmRadio9);
                    factory.addESM(esmRadio10);
                    factory.addESM(esmRadio11);
                    factory.addESM(esmRadio12);
                    factory.addESM(esmRadio13);
                    factory.addESM(esmRadio14);
                    factory.addESM(esmRadio15);
                    factory.addESM(esmRadio16);
                    factory.addESM(esmRadio17);
                    factory.addESM(esmRadio18);
                    factory.addESM(esmRadio19);
                    factory.addESM(esmRadio20);
                    factory.addESM(esmRadio21);


                    ESM.queueESM(getApplicationContext(), factory.build());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });






//        generalSurveyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                awareIntent = new Intent(getApplicationContext(), Aware.class);
//                startService(awareIntent);
//
//                Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
//
//                String generalEsmString = "[{'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'1. I find teaching full of meaning and purpose. I find teaching full of meaning and purpose. I find teaching full of meaning and purpose. I find teaching full of meaning and purpose. I find teaching full of meaning and purpose.', 'esm_instructions':'Choose the appropriate radio button value: Choose the appropriate radio button value: Choose the appropriate radio button value: Choose the appropriate radio button value:', 'esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'2. The time flies when I am teaching.', 'esm_instructions':'Choose the appropriate radio button value:', 'esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'3. I am enthusiastic about teaching.', 'esm_instructions':'Choose the appropriate radio button value:', 'esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'4. When I teach, I forget everything else around me.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next','esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'5. I feel happy when I work intensively.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'6. I am proud of the work I did.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'7. I try my hardest to perform well while teaching.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'8. I love teaching.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'9. I am excited about teaching.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'10. I feel happy while teaching.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'11. I find teaching fun.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'12. While teaching I pay a lot of attention to my lecture.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next','esm_expiration_threshold':0, 'esm_notification_timeout':60, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'13. While teaching I really throw myself into the lecture.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'14. While teaching I work with intensity.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'15. At my work I feel bursting with energy.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'16. When I get up I feel like going to work.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Next', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_RADIO + ", 'esm_title':'17. At my work I am very resilient mentally.', 'esm_instructions':'Choose the appropriate radio button value:','esm_radios':['Strongly Disagree','Disagree','Neutral','Agree','Strongly Agree'], 'esm_submit':'Finish', 'esm_expiration_threshold':0, 'esm_notification_timeout':60 , 'esm_trigger':'AWARE Tester'}}]";
//
//
//
//
//                //Queue the ESM to be displayed when possible
//                Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
//                esm.putExtra(ESM.EXTRA_ESM, generalEsmString);
//                sendBroadcast(esm);
//
//                String[] tableColumns = {"timestamp", "esm_status", "esm_user_answer"};
//                String[] tableArguments = {};
//                Cursor data = getContentResolver().query(ESM_Provider.ESM_Data.CONTENT_URI, tableColumns, "",tableArguments,"");
//
//                ArrayList<String> questions = new ArrayList<String>();
//
//
//
//                if(data != null && data.getCount() > 0){
//
//                    //check also esm_status
//
//                    data.moveToFirst();
//                    timestampFirst = data.getDouble(data.getColumnIndex("timestamp"));
//                    Log.v("SURVEYS", timestampFirst + "");
//
//                    data.moveToLast();
//
//                    Log.v("AWARE ROWS", "" + data.getCount());
//
//
//                    for(int i = 1; i <= 17; i++){
//                        questions.add(data.getString(data.getColumnIndex("esm_user_answer")));
//                        data.moveToPrevious();
//                    }
//
//                    GeneralSurvey generalSurvey = new GeneralSurvey();
//                    generalSurvey._timestamp = timestampFirst;
//                    generalSurvey._android_id = androidID;
//                    generalSurvey._question17 = questions.get(0);
//                    generalSurvey._question16 = questions.get(1);
//                    generalSurvey._question15 = questions.get(2);
//                    generalSurvey._question14 = questions.get(3);
//                    generalSurvey._question13 = questions.get(4);
//                    generalSurvey._question12 = questions.get(5);
//                    generalSurvey._question11 = questions.get(6);
//                    generalSurvey._question10 = questions.get(7);
//                    generalSurvey._question9 = questions.get(8);
//                    generalSurvey._question8 = questions.get(9);
//                    generalSurvey._question7 = questions.get(10);
//                    generalSurvey._question6 = questions.get(11);
//                    generalSurvey._question5 = questions.get(12);
//                    generalSurvey._question4 = questions.get(13);
//                    generalSurvey._question3 = questions.get(14);
//                    generalSurvey._question2 = questions.get(15);
//                    generalSurvey._question1 = questions.get(16);
//
//                    dbHelper.addGeneralSurveyAnswers(generalSurvey);
//
//                    data.close();
//
//                }
//
//
//                System.out.println("database");
//                for(GeneralSurvey gen: dbHelper.getAllGeneralSurveys()){
//                    System.out.println(gen._id + ", " + gen._android_id + ", " + gen._timestamp + ", " + gen._question1+", "
//                            + gen._question2+", "+gen._question3+", "+gen._question4+", "+ gen._question5+", "+gen._question6+", "
//                            +gen._question7+", "+gen._question8+", "+ gen._question9+", "+gen._question10+", "+gen._question11 + " , "
//                    + gen._question12 + " , " + gen._question13 + " , " + gen._question14 + " , " + gen._question15 + " , " + gen._question16 + " , " + gen._question17);
//                }
//
//            }
//        });

        String[] tableColumns = {"timestamp", "esm_json", "esm_status", "esm_user_answer"};
        String[] tableArguments = {};
        Cursor data = getContentResolver().query(ESM_Provider.ESM_Data.CONTENT_URI, tableColumns, "", tableArguments,"");

        if(data != null && data.getCount() > 0){
            data.moveToFirst();
            for(int i = 1; i <= data.getCount(); i++){
//                Log.v("SURVEYSS", data.getString(data.getColumnIndex("timestamp")));
//                Log.v("SURVEYSS", data.getString(data.getColumnIndex("esm_status")));
                Log.v("SURVEYSS", data.getString(data.getColumnIndex("timestamp")) + data.getString(data.getColumnIndex("esm_user_answer")));


            }

        }
        data.close();

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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SurveyDataActivity.this);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SurveyDataActivity.this);
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
