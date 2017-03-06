package com.example.android.teacher.data.RemoteDataStorage;

/**
 *
 * Code partially contributed from Luca Dotti
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aware.providers.ESM_Provider;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.LocalDataStorage.LocalDbUtility;
import com.example.android.teacher.data.LocalDataStorage.LocalStorageController;
import com.example.android.teacher.data.LocalDataStorage.LocalTables;
import com.example.android.teacher.data.Sensors.AccSensorContract;
import com.example.android.teacher.data.Sensors.BvpSensorContract;
import com.example.android.teacher.data.Sensors.EdaSensorContract;
import com.example.android.teacher.data.Sensors.TempSensorContract;
import com.example.android.teacher.data.User.UserData;

import java.util.*;
import java.text.SimpleDateFormat;

public class Uploader {
    private DatabaseHelper dbHelper;
    private RemoteStorageController switchDriveController;
    private LocalStorageController localController;
    private LocalTables tableToClean;
    private String userId;

    public Uploader(String userId, RemoteStorageController remoteController, LocalStorageController localController, DatabaseHelper dbHelper) {
        this.switchDriveController = remoteController;
        this.localController = localController;
        //the start table to clean
        tableToClean = LocalTables.values()[0];
        //user id is the phone id
        this.userId = userId;
        this.dbHelper = dbHelper;
    }

    /**
     * Upload function to upload Users tables' data to Switch Drive
     * Local Tables: Users, Eda, Acc, Bvp, Temp
     *
     */
    public int uploadUsersTable(){
        int response = 0;
        //number of tables
        Cursor c;
        //current table to clean
        LocalTables currTable;
        String fileName;

        currTable = LocalTables.values()[0];

        //build name of file to upload
        fileName = buildFileName(currTable);

        //get all data currently in the table
        c = getRecords(currTable);

        if (c.getCount() > 0) {
            c.moveToFirst();

            //upload the data to the server
            response = switchDriveController.upload(fileName, toCSV(c, currTable));

            //if the file was put, delete records and update the arrays
            if (response >= 200 && response <= 207) {
                response = 200;
            } else {
                response = 404;
                Log.d("DATA UPLOAD SERVICE", "Owncould's response: " + Integer.toString(response));
            }
        }

        return response;
    }


    /**
     * Upload function to upload local tables' data to Switch Drive
     * Local Tables: Users, Eda, Acc, Bvp, Temp
     *
     */
    public int upload(){
        int response = 0;
        //number of tables
        int nbTableToClean = LocalTables.values().length;
        int i = 1; //Start it from EDA table not USERS table **********************
        Cursor c;
        //current table to clean
        LocalTables currTable;
        String fileName;

        while(i < nbTableToClean) {
            currTable = LocalTables.values()[i];

            //build name of file to upload
            fileName = buildFileName(currTable);

            //get all data currently in the table
            c = getRecords(currTable);

            if (c.getCount() > 0) {
                c.moveToFirst();

                //upload the data to the server
                response = switchDriveController.upload(fileName, toCSV(c, currTable));

                //if the file was put, delete records and update the arrays
                if (response >= 200 && response <= 207) {
                    response = 200;
                    //delete from the db the records where id > startId and id <= endId
                    if(currTable.name() == "TABLE_NAME_ACC_DATA"){
                        dbHelper.deleteAllFromTable(AccSensorContract.AccSensorDataEntry.TABLE_NAME_ACC_DATA);
                    }else if(currTable.name().equals("TABLE_NAME_BVP_DATA")){
                        dbHelper.deleteAllFromTable(BvpSensorContract.BvpSensorDataEntry.TABLE_NAME_BVP_DATA);
                    }else if(currTable.name().equals("TABLE_NAME_EDA_DATA")){
                        dbHelper.deleteAllFromTable(EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA);
                    }else if(currTable.name().equals("TABLE_NAME_TEMP_DATA")){
                        dbHelper.deleteAllFromTable(TempSensorContract.TempSensorDataEntry.TABLE_NAME_TEMP_DATA);
                    }
                } else {
                    response=404;
                    Log.d("DATA UPLOAD SERVICE", "Owncould's response: " + Integer.toString(response));
                }
            }
            i++;
        }

        return response;
    }

    /**
     * Upload function to upload Aware Framework ESM sensor data from their Content Provider to Switch Drive
     *
     * @param context
     */
    public int uploadAware(Context context){
        int response = 0;
        Cursor c;
        String fileName;

        //build name of file to upload
        fileName = buildAwareFileName("esms");

        //get all data currently in the table
        c = getAwareRecords(context);

        if (c.getCount() > 0) {
            c.moveToFirst();

            //upload the data to the server
            response = switchDriveController.upload(fileName, toCSV(c));

            //if the file was put, delete records and update the arrays
            if (response >= 200 && response <= 207) {
                //delete from the db the records where id > startId and id <= endId
                deleteAwareRecords(context);
                response = 200;
            } else {
                response = 404;
                Log.d("DATA UPLOAD SERVICE", "Owncould's response: " + Integer.toString(response));
            }
        }
        return response;
    }

    /**
     * Generate the csv data from the given cursor (Local Tables)
     *
     * @param records
     * @param table
     * @return
     */
    private String toCSV(Cursor records, LocalTables table) {
        String csv = "";
        String[] columns = LocalDbUtility.getTableColumns(table);

        for(int i = 0; i < columns.length; i++) {
            csv += columns[i] + ",";
        }

        csv = csv.substring(0, csv.length()-1);
        csv += "\n";

        do {
            for(int i = 0; i < columns.length; i++) {
                csv += records.getString(i) + ",";
            }
            csv = csv.substring(0, csv.length()-1);
            csv += "\n";
        } while(records.moveToNext());
        csv = csv.substring(0, csv.length()-1);

        return csv;
    }


    /**
     * Generate the csv data from the given cursor (Aware Table).
     *
     * @param records
     * @return
     */
    private String toCSV(Cursor records) {
        String csv = "";
        String[] columns = {ESM_Provider.ESM_Data._ID, ESM_Provider.ESM_Data.TIMESTAMP, ESM_Provider.ESM_Data.JSON,
                ESM_Provider.ESM_Data.STATUS, ESM_Provider.ESM_Data.ANSWER};

        for(int i = 0; i < columns.length; i++) {
            csv += columns[i] + ",";
        }

        csv = csv.substring(0, csv.length()-1);
        csv += "\n";

        do {
            for(int i = 0; i < columns.length; i++) {
                csv += records.getString(i) + ",";
            }
            csv = csv.substring(0, csv.length()-1);
            csv += "\n";
        } while(records.moveToNext());
        csv = csv.substring(0, csv.length()-1);

        return csv;
    }

    /**
     * Build the file name.
     *
     * <subjectid>_<date>_<table>_<username>.csv
     *
     * @param table
     * @return
     */
    private String buildFileName(LocalTables table) {
        //get current date
        String today = buildDate();
        String fileName = today + "_" + UserData._username + "_" + userId + "_" + LocalDbUtility.getTableName(table) + ".csv";
        return fileName;
    }

    /**
     * Build the file name.
     *
     * <subjectid>_<date>_esms_<username>.csv
     *
     * @param awareTable
     * @return
     */
    private String buildAwareFileName(String awareTable) {
        //get current date
        String today = buildDate();
        String fileName = today + "_" + UserData._username + "_" + userId + "_" + awareTable +  ".csv";
        return fileName;

    }

    /*
    * Utility function to get the string representation of the today date.
    * @return
    */
    private String buildDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM-dd-yyyy");
        return mdformat.format(calendar.getTime());
    }

    /*
    * Build the query to select all records from the given local table.
    * @param table
     */
    private Cursor getRecords(LocalTables table) {
        String query = "SELECT * FROM " + LocalDbUtility.getTableName(table);
        return localController.rawQuery(query, null);
    }

    /*
    * Build the query to select all records from the Aware "esms" table.
    * @param table
     */
    private Cursor getAwareRecords(Context context) {
        String[] tableColumns = {"_id", "timestamp", "esm_json", "esm_status", "esm_user_answer"};
        String[] tableArguments = {};

        return context.getContentResolver().query(ESM_Provider.ESM_Data.CONTENT_URI, tableColumns, "", tableArguments,"");

    }

    /*
    * Build the query to select all records from the Aware "esms" table.
    * @param table
     */
    private void deleteAwareRecords(Context context) {
        String[] selectionArgs = {};

        context.getContentResolver().delete(ESM_Provider.ESM_Data.CONTENT_URI, "", selectionArgs);
    }

    /**
     * Build the query to remove the records from the given table, where primary key id in ]start, end].
     *
     * @param table
     * @param start
     * @param end
     */
    private void removeRecords(LocalTables table, int start, int end) {
        Log.d("UPLOAD DATA SERVICE", "Removing from " + Integer.toString(start) + " to " + Integer.toString(end));

        String clause = LocalDbUtility.getTableColumns(table)[0] + " > " + Integer.toString(start) + " AND " +
                LocalDbUtility.getTableColumns(table)[0] + " <= " + Integer.toString(end);
        localController.delete(LocalDbUtility.getTableName(table), clause);
    }
}