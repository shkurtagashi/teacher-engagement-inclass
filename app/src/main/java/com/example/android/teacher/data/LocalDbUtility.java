package com.example.android.teacher.data;

import com.example.android.teacher.data.Sensors.AccSensorContract;
import com.example.android.teacher.data.Sensors.BvpSensorContract;
import com.example.android.teacher.data.Sensors.EdaSensorContract;
import com.example.android.teacher.data.Sensors.TempSensorContract;
import com.example.android.teacher.data.User.UsersContract;

import static android.R.attr.data;
import static com.example.android.teacher.data.Surveys.GeneralSurveyContract.GeneralSurveyDataEntry.TABLE_GENERAL_SURVEY;

/**
 * Created by usi on 18/01/17.
 */

public class LocalDbUtility {
    private final static int DATA_TABLES_COUNT = 5;

    public static int getDataTablesCount() {
        return DATA_TABLES_COUNT;
    }

    public static String getTableName(LocalTables table) {
        switch (table) {
            case TABLE_NAME_EDA_DATA:
                return EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA;
            case TABLE_NAME_TEMP_DATA:
                return TempSensorContract.TempSensorDataEntry.TABLE_NAME_TEMP_DATA;
            case TABLE_NAME_BVP_DATA:
                return BvpSensorContract.BvpSensorDataEntry.TABLE_NAME_BVP_DATA;
            case TABLE_NAME_ACC_DATA:
                return AccSensorContract.AccSensorDataEntry.TABLE_NAME_ACC_DATA;
            case TABLE_NAME_USERS:
                return UsersContract.UserEntry.TABLE_NAME_USERS;
            default:
                return null;
        }
    }

    public static String[] getTableColumns(LocalTables table) {
        switch (table) {
            case TABLE_NAME_EDA_DATA:
                return EdaSensorContract.EdaSensorDataEntry.getColumns();
            case TABLE_NAME_ACC_DATA:
                return AccSensorContract.AccSensorDataEntry.getColumns();
            case TABLE_NAME_TEMP_DATA:
                return TempSensorContract.TempSensorDataEntry.getColumns();
            case TABLE_NAME_BVP_DATA:
                return BvpSensorContract.BvpSensorDataEntry.getColumns();
            case TABLE_NAME_USERS:
                return UsersContract.UserEntry.getColumns();
            default:
                return null;
        }
    }

}
