package com.example.android.teacher.data.Sensors;

import android.provider.BaseColumns;

import static android.os.Build.ID;
import static com.example.android.teacher.data.Sensors.EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP;
import static com.example.android.teacher.data.Sensors.EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_VALUE;
import static com.example.android.teacher.data.Sensors.EdaSensorContract.EdaSensorDataEntry._EDA_ID;

/**
 * Created by shkurtagashi on 06.01.17.
 */

public final class TempSensorContract {

    private TempSensorContract(){}

    /* Inner class that defines the table contents */
    public static final class TempSensorDataEntry implements BaseColumns {
        /*
       * Sensor Data - Table and Columns declaration
        */
        public final static String TABLE_NAME_TEMP_DATA = "tempdata";

        public final static String _TEMP_ID = BaseColumns._ID;
        public final static String COLUMN_TEMP_TIMESTAMP = "timestamp";
        public final static String COLUMN_TEMP_VALUE = "temp_value";

        public static String[] getColumns(){
            String[] columns = {_TEMP_ID, COLUMN_TEMP_TIMESTAMP, COLUMN_TEMP_VALUE};

            return columns;
        }
    }
}
