package com.example.android.teacher.data.Surveys;

import android.provider.BaseColumns;

import static com.example.android.teacher.data.Sensors.TempSensorContract.TempSensorDataEntry.COLUMN_TEMP_TIMESTAMP;
import static com.example.android.teacher.data.Sensors.TempSensorContract.TempSensorDataEntry.COLUMN_TEMP_VALUE;
import static com.example.android.teacher.data.Sensors.TempSensorContract.TempSensorDataEntry._TEMP_ID;

/**
 * Created by shkurtagashi on 29.01.17.
 */

public final class PostLectureSurveyContract {

    private PostLectureSurveyContract(){}

    public static final class PostLectureSurveyDataEntry  implements BaseColumns {

        //Post Lecture Survey Table and Columns names

        public final static String TABLE_POST_LECTURE_SURVEY = "postlecture_survey";

        public final static String _POST_LECTURE_SURVEY_ID = BaseColumns._ID;
        public final static String ANDROID_ID = "andorid_id";
        public final static String TIMESTAMP = "timestamp";
        public final static String QUESTION_1 = "question1";
        public final static String QUESTION_2 = "question2";
        public final static String QUESTION_3 = "question3";
        public final static String QUESTION_4 = "question4";
        public final static String QUESTION_5 = "question5";
        public final static String QUESTION_6 = "question6";


        /**
         * Possible values for the question's answers of the teacher
         */
        public static final int ANSWER_1 = 1;
        public static final int ANSWER_2 = 2;
        public static final int ANSWER_3 = 3;
        public static final int ANSWER_4 = 4;
        public static final int ANSWER_5 = 5;

        public static String[] getColumns(){
            String[] columns = {ANDROID_ID, TIMESTAMP, QUESTION_1, QUESTION_2, QUESTION_3, QUESTION_4, QUESTION_5, QUESTION_6};

            return columns;
        }




    }
}
