package com.example.android.teacher.data.Sensors;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.aware.providers.ESM_Provider;


/**
 * Created by shkurtagashi on 06.01.17.
 */

public final class EdaSensorContract {

    private EdaSensorContract(){}

    /* Inner class that defines the table contents */
    public static final class EdaSensorDataEntry implements BaseColumns {
        /*
       * Sensor Data - Table and Columns declaration
        */
        public final static String TABLE_NAME_EDA_DATA = "edadata";

        public final static String _EDA_ID = BaseColumns._ID;
        public final static String COLUMN_EDA_TIMESTAMP = "timestamp";
        public final static String COLUMN_EDA_VALUE = "eda_value";

        public static String[] getColumns(){
            String[] columns = {_EDA_ID, COLUMN_EDA_TIMESTAMP, COLUMN_EDA_VALUE};

            return columns;
        }

    }

}
