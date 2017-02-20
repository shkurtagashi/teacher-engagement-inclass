package com.example.android.teacher.data.User;

import android.provider.BaseColumns;

import com.example.android.teacher.R;

import static android.R.attr.name;
import static com.github.mikephil.charting.animation.Easing.EasingOption.Linear;

/**
 * Created by shkurtagashi on 14.12.16.
 */

public final class UsersContract {

    // Private constructor to prevent someone from accidentally instantiating the contract class,
    private UsersContract() {}

    /* Inner class that defines the table contents */
    public static final class UserEntry implements BaseColumns {

        /*
        * Users - Table and Columns declaration
         */
        public final static String TABLE_NAME_USERS = "users";

        public final static String _ID = "id";
        public final static String ANDROID_ID = "android_id";
        public final static String USERNAME = "username";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_AGE = "age";
        public final static String COLUMN_FACULTY = "faculty";
        public final static String COLUMN_PROGRAMME = "programme";
        public final static String COLUMN_COURSE = "course";
        public final static String COLUMN_STATUS = "status";
        public final static String COLUMN_TEACHING_EXPERIENCE = "teachingExperience";
        public final static String COLUMN_AGREEMENT = "agreementForm";


        /**
         * Possible values for the age of the user
         */
        public static final String AGE_20_30 = "20-30";
        public static final String AGE_30_40 = "30-40";
        public static final String AGE_40_50 = "40-50";
        public static final String AGE_50_ABOVE = "50 and above";


        /**
         * Possible values for the gender of the user
         */
        public static final String GENDER_MALE = "M";
        public static final String GENDER_FEMALE = "F";

        public static final String FACULTY_OTHER = "Other";
        public static final String FACULTY_INFORMATICS = "Informatics";
        public static final String FACULTY_ECONOMICS = "Economics";
        public static final String FACULTY_COMMUNICATION_SCIENCES = "Communication Sciences";
        public static final String FACULTY_ACADEMY_OF_ARCHITECTURE = "Academy of Architecture";

        public static final String PROGRAMME_OTHER = "Other";
        public static final String PROGRAMME_BACHELOR = "Bachelor";
        public static final String PROGRAMME_MASTER = "Master";

        public static final String COURSE_OTHER = "Other";
        public static final String COURSE_1 = "Information Security";
        public static final String COURSE_2 = "Cyber Communication";
        public static final String COURSE_3 = "Software Architecture and Design";
        public static final String COURSE_4 = "Linear Algebra";
        public static final String COURSE_5 = "Programming Fundamentals 2";


        public static final String STATUS_OTHER = "Other";
        public static final String STATUS_FULL_PROFESSOR = "Full Professor";
        public static final String STATUS_ASSOCIATE_PROFESSOR = "Associate Professor";
        public static final String STATUS_ASSISTANT_PROFESSOR = "Assistant Professor";
        public static final String STATUS_ADJUNCT_PROFESSOR = "Adjunct Professor";
        public static final String STATUS_POST_DOC = "Post-doc";
        public static final String STATUS_PHD_STUDENT = "Ph.D. Student";
        public static final String STATUS_ASSISTANT = "Assistant";

        public static final String TEACHING_EXPERIENCE_0_5 = "0 to 5 years";
        public static final String TEACHING_EXPERIENCE_5_10 = "5 to 10 years";
        public static final String TEACHING_EXPERIENCE_10_20 = "10 to 20 years";
        public static final String TEACHING_EXPERIENCE_20_30 = "20 to 30 years";
        public static final String TEACHING_EXPERIENCE_30_ABOVE = "30 and above";

        public static final String AGREEMENT_ACCEPTED = "Yes";
        public static final String AGREEMENT_NOT_ACCEPTED = "No";


        public static String[] getColumns(){
            String[] columns = {_ID, ANDROID_ID, USERNAME, COLUMN_GENDER, COLUMN_AGE, COLUMN_FACULTY, COLUMN_PROGRAMME, COLUMN_COURSE, COLUMN_STATUS, COLUMN_TEACHING_EXPERIENCE, COLUMN_AGREEMENT};

            return columns;
        }



    }


}
