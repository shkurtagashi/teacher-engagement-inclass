package com.example.android.teacher.data.LocalDataStorage;

import com.aware.providers.ESM_Provider;

import static com.example.android.teacher.data.PamDatabase.PAMContract.PAMDataEntry.TABLE_NAME_PAM_DATA;
import static com.example.android.teacher.data.Sensors.AccSensorContract.AccSensorDataEntry.TABLE_NAME_ACC_DATA;
import static com.example.android.teacher.data.Sensors.BvpSensorContract.BvpSensorDataEntry.TABLE_NAME_BVP_DATA;
import static com.example.android.teacher.data.Sensors.EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA;
import static com.example.android.teacher.data.Sensors.TempSensorContract.TempSensorDataEntry.TABLE_NAME_TEMP_DATA;
import static com.example.android.teacher.data.Surveys.PostLectureSurveyContract.PostLectureSurveyDataEntry.TABLE_POST_LECTURE_SURVEY;
import static com.example.android.teacher.data.User.UsersContract.UserEntry.TABLE_NAME_USERS;

/**
 * Created by shkurtagashi on 09.02.17.
 */

public enum LocalTables {
    TABLE_NAME_ACC_DATA,
    TABLE_NAME_BVP_DATA,
    TABLE_NAME_EDA_DATA,
    TABLE_NAME_USERS,
    TABLE_NAME_TEMP_DATA
    //    TABLE_NAME_PAM_DATA,
//    TABLE_GENERAL_SURVEY,
//    TABLE_POST_LECTURE_SURVEY;
}
