package com.example.android.teacher.data.Sensors;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import static com.example.android.teacher.data.Surveys.GeneralSurveyContract.GeneralSurveyDataEntry._GENERAL_SURVEY_ID;

/**
 * Created by shkurtagashi on 06.01.17.
 */

public final class AccSensorContract {
    private AccSensorContract(){}

    /* Inner class that defines the table contents */
    public static final class AccSensorDataEntry implements BaseColumns {
        /*
       * Accelereometer Sensor Data - Table and Columns declaration
        */
        public final static String TABLE_NAME_ACC_DATA = "accdata";
        public final static String _ACC_ID = BaseColumns._ID;
        public final static String COLUMN_ACC_TIMESTAMP = "timestamp";
        public final static String COLUMN_ACC_X_VALUE = "acc_x_value";
        public final static String COLUMN_ACC_Y_VALUE = "acc_y_value";
        public final static String COLUMN_ACC_Z_VALUE = "acc_z_value";

        public static String[] getColumns(){
            String[] columns = {_ACC_ID, COLUMN_ACC_TIMESTAMP, COLUMN_ACC_X_VALUE, COLUMN_ACC_Y_VALUE, COLUMN_ACC_Z_VALUE};

            return columns;
        }

    }

}
