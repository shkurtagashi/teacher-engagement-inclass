package com.example.android.teacher.data.PamDatabase;

import android.provider.BaseColumns;

/**
 * Created by shkurtagashi on 22.01.17.
 */

public final class PAMContract {
    private PAMContract(){}

    /* Inner class that defines the table contents */
    public static final class PAMDataEntry implements BaseColumns {

        //PAM Table and column's names
        public final static String TABLE_NAME_PAM_DATA = "pamdata";


        public final static String _PAM_ID = BaseColumns._ID;
        public final static String PAM_DATE = "Date";
        public final static String ANDROID_ID = "AndroidID";
        public final static String COURSE_TITLE = "CourseTitle";
        public final static String ANSWER = "PAMAnswer";

    }

}
