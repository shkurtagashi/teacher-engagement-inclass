package com.example.android.teacher.data.Surveys;

import android.provider.BaseColumns;

import static com.example.android.teacher.data.User.UsersContract.UserEntry.COLUMN_AGE;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.COLUMN_AGREEMENT;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.COLUMN_COURSE;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.COLUMN_FACULTY;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.COLUMN_GENDER;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.COLUMN_PROGRAMME;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.COLUMN_TEACHING_EXPERIENCE;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.USERNAME;

/**
 * Created by shkurtagashi on 26.01.17.
 */

public final class GeneralSurveyContract {

    private GeneralSurveyContract(){}

    public static final class GeneralSurveyDataEntry  implements BaseColumns {

        //General Survey Table and Columns names

        public final static String TABLE_GENERAL_SURVEY = "general_survey";

        public final static String _GENERAL_SURVEY_ID = BaseColumns._ID;
        public final static String ANDROID_ID = "andorid_id";
        public final static String TIMESTAMP = "timestamp";
        public final static String QUESTION_1 = "question1";
        public final static String QUESTION_2 = "question2";
        public final static String QUESTION_3 = "question3";
        public final static String QUESTION_4 = "question4";
        public final static String QUESTION_5 = "question5";
        public final static String QUESTION_6 = "question6";
        public final static String QUESTION_7 = "question7";
        public final static String QUESTION_8 = "question8";
        public final static String QUESTION_9 = "question9";
        public final static String QUESTION_10 = "question10";
        public final static String QUESTION_11= "question11";
        public final static String QUESTION_12 = "question12";
        public final static String QUESTION_13 = "question13";
        public final static String QUESTION_14 = "question14";
        public final static String QUESTION_15 = "question15";
        public final static String QUESTION_16 = "question16";
        public final static String QUESTION_17 = "question17";


        /**
         * Possible values for the question's answers of the teacher
         */
        public static final int ANSWER_1 = 1;
        public static final int ANSWER_2 = 2;
        public static final int ANSWER_3 = 3;
        public static final int ANSWER_4 = 4;
        public static final int ANSWER_5 = 5;


        public static String[] getColumns(){
            String[] columns = {_GENERAL_SURVEY_ID, ANDROID_ID, TIMESTAMP, QUESTION_1, QUESTION_2, QUESTION_3};

            return columns;
        }

    }
}
