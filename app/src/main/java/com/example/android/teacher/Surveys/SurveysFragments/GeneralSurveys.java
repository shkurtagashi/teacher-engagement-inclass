package com.example.android.teacher.Surveys.SurveysFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;
import com.example.android.teacher.R;
import com.example.android.teacher.data.User.UserData;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralSurveys extends Fragment {

    private Button generalSurveyButton;

    int expirationThreshold = 420;


    public GeneralSurveys() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_general_surveys, container, false);

        generalSurveyButton = (Button) rootView.findViewById(R.id.general_survey_button);

        setUpGeneralSurveyButton();

        return rootView;
    }

    private void setUpGeneralSurveyButton() {

        generalSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    ESMFactory factory = new ESMFactory();

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.setInstructions("At my work, I feel bursting with energy.");
                    esmRadio1.setTitle("General Survey - " + UserData._selectedCourses + " (1/21)");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.setInstructions("At my job, I feel strong and vigorous.");
                    esmRadio2.setTitle("General Survey - " + UserData._selectedCourses + " (2/21)");


                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.setInstructions("I am enthusiastic about my job.");
                    esmRadio3.setTitle("General Survey - " + UserData._selectedCourses + " (3/21)");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.setInstructions("My job inspires me.");
                    esmRadio4.setTitle("General Survey - " + UserData._selectedCourses + " (4/21)");


                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.setInstructions("When I get up in the morning I feel like going to work.");
                    esmRadio5.setTitle("General Survey - " + UserData._selectedCourses + " (5/21)");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.setInstructions("I feel happy when I am working intensively.");
                    esmRadio6.setTitle("General Survey - " + UserData._selectedCourses + " (6/21)");

                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.setInstructions("I am proud of the work that I do.");
                    esmRadio7.setTitle("General Survey - " + UserData._selectedCourses + " (7/21)");


                    ESM_Radio esmRadio8 = new ESM_Radio();
                    esmRadio8.setInstructions("I am immersed in my work.");
                    esmRadio8.setTitle("General Survey - " + UserData._selectedCourses + " (8/21)");

                    ESM_Radio esmRadio9 = new ESM_Radio();
                    esmRadio9.setInstructions("I get carried away when I am working.");
                    esmRadio9.setTitle("General Survey - " + UserData._selectedCourses + " (9/21)");


                    ESM_Radio esmRadio10 = new ESM_Radio();
                    esmRadio10.setInstructions("I love teaching.");
                    esmRadio10.setTitle("General Survey - " + UserData._selectedCourses + " (10/21)");


                    ESM_Radio esmRadio11 = new ESM_Radio();
                    esmRadio11.setInstructions("I am excited about teaching.");
                    esmRadio11.setTitle("General Survey - " + UserData._selectedCourses + " (11/21)");


                    ESM_Radio esmRadio12 = new ESM_Radio();
                    esmRadio12.setInstructions("I feel happy while teaching.");
                    esmRadio12.setTitle("General Survey - " + UserData._selectedCourses + " (12/21)");


                    ESM_Radio esmRadio13 = new ESM_Radio();
                    esmRadio13.setInstructions("I find teaching fun.");
                    esmRadio13.setTitle("General Survey - " + UserData._selectedCourses + " (13/21)");


                    ESM_Radio esmRadio14 = new ESM_Radio();
                    esmRadio14.setInstructions("While teaching I pay a lot of attention to my work.");
                    esmRadio14.setTitle("General Survey - " + UserData._selectedCourses + " (14/21)");


                    ESM_Radio esmRadio15 = new ESM_Radio();
                    esmRadio15.setInstructions("While teaching I really throw myself into my work.");
                    esmRadio15.setTitle("General Survey - " + UserData._selectedCourses + " (15/21)");


                    ESM_Radio esmRadio16 = new ESM_Radio();
                    esmRadio16.setInstructions("While teaching, I work with intensity.");
                    esmRadio16.setTitle("General Survey - " + UserData._selectedCourses + " (16/21)");


                    ESM_Radio esmRadio17 = new ESM_Radio();
                    esmRadio17.setTitle("General Survey - " + UserData._selectedCourses + " (17/21)")
                            .setInstructions("I try my hardest to perform well while teaching.");

                    ESM_Radio esmRadio18 = new ESM_Radio();
                    esmRadio18.setTitle("General Survey - " + UserData._selectedCourses + " (18/21)")
                            .setInstructions("In class, I care about the problems of my students.");


                    ESM_Radio esmRadio19 = new ESM_Radio();
                    esmRadio19.setTitle("General Survey - " + UserData._selectedCourses + " (19/21)")
                            .setInstructions("In class, I am empathetic towards my students.");


                    ESM_Radio esmRadio20 = new ESM_Radio();
                    esmRadio20.setTitle("General Survey - " + UserData._selectedCourses + " (20/21)")
                            .setInstructions("In class, I am aware of my student's feelings.");


                    ESM_Radio esmRadio21 = new ESM_Radio();
                    esmRadio21.setTitle("General Survey - " + UserData._selectedCourses + " (21/21)")
                            .setInstructions("In class, I show warmth to my students.");

                    ESM_QuickAnswer esm22 = new ESM_QuickAnswer();
                    esm22.setTitle("Thank you message");
                    esm22.setInstructions("Thank you for your participation!");



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
                    factory.addESM(esm22);


                    ESM.queueESM(getActivity().getApplicationContext(), factory.build());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
