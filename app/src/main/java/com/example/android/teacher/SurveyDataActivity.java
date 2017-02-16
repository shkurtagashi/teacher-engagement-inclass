package com.example.android.teacher;


import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aware.Aware;
import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;
import com.example.android.teacher.data.DatabaseHelper;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import static android.R.attr.factor;
import static com.aware.ui.esms.ESM_Likert.esm_likert_max;
import static com.example.android.teacher.R.id.course;

public class SurveyDataActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private Button generalSurveyButton;
    private Button pamButton;
    private Button postLectureSurveyButton;

    int expirationThreshold = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_data);


        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
        Aware.joinStudy(this, "https://api.awareframework.com/index.php/webservice/index/1098/CySyIhauY1Yw");


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
                            .setExpirationThreshold(60*expirationThreshold); //setNotificationRetry(3) - number of times to retry notification if it expires

                    ESM_QuickAnswer q2 = new ESM_QuickAnswer();
                    q2.addQuickAnswer("Finish")
                            .setTitle("Cookies on the way ...")
                            .setInstructions("Thank you very much for your participation!");

                    factory.addESM(q1);
                    factory.addESM(q2);
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
                    esmRadio1.setInstructions("I felt confident with the topic I explained in this lecture.");
                    esmRadio1.setTitle("Post Lecture Survey (1/7)");


                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.setInstructions("I like the topic I explained in this lecture.");
                    esmRadio2.setTitle("Post Lecture Survey (2/7)");


                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.setInstructions("I was anxious while I was explaining this lecture.");
                    esmRadio3.setTitle("Post Lecture Survey (3/7)");


                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.setInstructions("I loved teaching this lecture.");
                    esmRadio4.setTitle("Post Lecture Survey (4/7)");


                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.setInstructions("I was excited about teaching this lecture.");
                    esmRadio5.setTitle("Post Lecture Survey (5/7)");


                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.setInstructions("I felt happy while I was teaching this lecture.");
                    esmRadio6.setTitle("Post Lecture Survey (6/7)");

                    ESM_QuickAnswer esmQuickAnswer2 = new ESM_QuickAnswer();
                    esmQuickAnswer2.addQuickAnswer("Finish")
                            .setTitle("Cookies on the way ...")
                            .setExpirationThreshold(60*expirationThreshold)
                            .setInstructions("Thank you very much for your participation!");


                    ESM_Freetext esmFreeText = new ESM_Freetext();
                    esmFreeText.setTitle("Post Lecture Survey")
                            .setSubmitButton("Next")
                            .setExpirationThreshold(60*expirationThreshold)
                            .setInstructions("Please describe the moment(s) during which you felt particularly engaged");


                    ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
                    esmQuickAnswer.addQuickAnswer("Yes")
                            .addQuickAnswer("No")
                            .setTitle("Post Lecture Survey (7/7)")
                            .setExpirationThreshold(60*expirationThreshold)
                            .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                            .addFlow("Yes", esmFreeText.build())
                            .addFlow("No", esmQuickAnswer2.build());


                    ArrayList<ESM_Radio> esms = new ArrayList<>();
                    esms.add(esmRadio1);
                    esms.add(esmRadio2);
                    esms.add(esmRadio3);
                    esms.add(esmRadio4);
                    esms.add(esmRadio5);
                    esms.add(esmRadio6);

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
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);
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
                    esmRadio1.setInstructions("I find teaching full of meaning and purpose.");
                    esmRadio1.setTitle("General Survey (1/17)");


                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.setInstructions("The time flies when I am teaching.");
                    esmRadio2.setTitle("General Survey (2/17)");


                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.setInstructions("I am enthusiastic about teaching.");
                    esmRadio3.setTitle("General Survey (3/17)");


                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.setInstructions("When I teach, I forget everything else around me.");
                    esmRadio4.setTitle("General Survey (4/17)");


                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.setInstructions("I feel happy when I work intensively.");
                    esmRadio5.setTitle("General Survey (5/17)");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.setInstructions("I am proud of the work I did.");
                    esmRadio6.setTitle("General Survey (6/17)");


                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.setInstructions("I try my hardest to perform well while teaching.");
                    esmRadio7.setTitle("General Survey (7/17)");


                    ESM_Radio esmRadio8 = new ESM_Radio();
                    esmRadio8.setInstructions("I love teaching.");
                    esmRadio8.setTitle("General Survey (8/17)");


                    ESM_Radio esmRadio9 = new ESM_Radio();
                    esmRadio9.setInstructions("I am excited about teaching.");
                    esmRadio9.setTitle("General Survey (9/17)");


                    ESM_Radio esmRadio10 = new ESM_Radio();
                    esmRadio10.setInstructions("I feel happy while teaching.");
                    esmRadio10.setTitle("General Survey (10/17)");


                    ESM_Radio esmRadio11 = new ESM_Radio();
                    esmRadio11.setInstructions("I find teaching fun.");
                    esmRadio11.setTitle("General Survey (11/17)");


                    ESM_Radio esmRadio12 = new ESM_Radio();
                    esmRadio12.setInstructions("While teaching I pay a lot of attention to my lecture.");
                    esmRadio12.setTitle("General Survey (12/17)");


                    ESM_Radio esmRadio13 = new ESM_Radio();
                    esmRadio13.setInstructions("While teaching I really throw myself into the lecture.");
                    esmRadio13.setTitle("General Survey (13/17)");


                    ESM_Radio esmRadio14 = new ESM_Radio();
                    esmRadio14.setInstructions("While teaching I work with intensity.");
                    esmRadio14.setTitle("General Survey (14/17)");


                    ESM_Radio esmRadio15 = new ESM_Radio();
                    esmRadio15.setInstructions("At my work I feel bursting with energy.");
                    esmRadio15.setTitle("General Survey (15/17)");


                    ESM_Radio esmRadio16 = new ESM_Radio();
                    esmRadio16.setInstructions("When I get up in the morning I feel like going to work.");
                    esmRadio16.setTitle("General Survey (16/17)");


                    ESM_Radio esmRadio17 = new ESM_Radio();
                    esmRadio17.setTitle("General Survey (17/17)")
                            .setInstructions("At my work I am very resilient mentally.");

                    ESM_QuickAnswer q2 = new ESM_QuickAnswer();
                    q2.addQuickAnswer("Finish")
                            .setTitle("Cookies on the way ...")
                            .setInstructions("Thank you very much for your participation!");



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
                    factory.addESM(q2);

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



}
