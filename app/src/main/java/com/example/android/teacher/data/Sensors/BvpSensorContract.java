package com.example.android.teacher.data.Sensors;

import android.provider.BaseColumns;


/**
 * Created by shkurtagashi on 06.01.17.
 */

public final class BvpSensorContract {
    private BvpSensorContract(){}

    /* Inner class that defines the table contents */
    public static final class BvpSensorDataEntry implements BaseColumns {
        /*
       * Blood Volume Pressure Sensor Data - Table and Columns declaration
        */
        public final static String TABLE_NAME_BVP_DATA = "bvpdata";
        public final static String _BVP_ID = BaseColumns._ID;
        public final static String COLUMN_BVP_TIMESTAMP = "timestamp";
        public final static String COLUMN_BVP_VALUE = "bvp_value";

        public static String[] getColumns(){
            String[] columns = {_BVP_ID, COLUMN_BVP_TIMESTAMP, COLUMN_BVP_VALUE};

            return columns;
        }
    }
}
