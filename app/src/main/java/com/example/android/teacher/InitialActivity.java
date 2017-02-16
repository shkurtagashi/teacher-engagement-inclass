package com.example.android.teacher;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.User.UsersContract.UserEntry;

import java.io.File;

public class InitialActivity extends AppCompatActivity {
    private static final String TAG = "InitialActivity";


    //private String android_id;
    public DatabaseHelper teacherDBhelper;
    public String android_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //Check the amount of storage needed for database and other files
        //analyseStorage(this);

        Intent intent;

        //Check Database, if user already registered direct to HomeActivity, otherwise direct to Register Form Activity
        if (checkAndroidID() && checkAgreementForm()) {
            intent = new Intent(this, HomeActivity.class);

        }else if(checkAndroidID() && !checkAgreementForm()){
            intent = new Intent(this, AgreementFormActivity.class);
        } else {
            intent = new Intent(this, RegisterFormActivity.class);
        }
        startActivity(intent);
        finish();
    }

    public boolean checkAndroidID(){
        boolean exists;

        DatabaseHelper usersDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntry._ID
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserEntry._ID + " = ?";
        String[] selectionArgs = { android_id };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserEntry._ID + " ASC";

        Cursor cursor = db.query(
                UserEntry.TABLE_NAME_USERS,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        if(cursor.getCount() > 0){
            exists = true;
        } else{
            exists = false;
        }

        cursor.close();
        return exists;
    }

    public boolean checkAgreementForm(){
        String agreementForm;

        boolean agreed = false;

        DatabaseHelper usersDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntry.COLUMN_AGREEMENT
        };

        // Filter results WHERE "_ID" = 'android_id'
        String selection = UserEntry._ID + " = ?";
        String[] selectionArgs = { android_id };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserEntry._ID + " ASC";

        Cursor cursor = db.query(
                UserEntry.TABLE_NAME_USERS,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        if (cursor.moveToFirst()) { // if Cursor is not empty
            agreementForm = cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_AGREEMENT));

            if (agreementForm != null) {
                if(agreementForm.equals(UserEntry.AGREEMENT_ACCEPTED)){
                    agreed = true;
                }
                else if(agreementForm.equals(UserEntry.AGREEMENT_NOT_ACCEPTED)){
                    agreed = false;
                }
            }
        }


        cursor.close();
        return agreed;
    }


    public void analyseStorage(Context context) {
        long totalSize = 0;
        File appBaseFolder = context.getFilesDir().getParentFile();
        for (File f: appBaseFolder.listFiles()) {
            if (f.isDirectory()) {
                long dirSize = browseFiles(f);
                totalSize += dirSize;
                Log.d("STORAGE", f.getPath() + " uses " + dirSize + " bytes");
            } else {
                totalSize += f.length();
            }
        }
        Log.d("STORAGE", "App uses " + totalSize + " total bytes");
    }

    private long browseFiles(File dir) {
        long dirSize = 0;
        for (File f: dir.listFiles()) {
            dirSize += f.length();
            //Log.d(STORAGE_TAG, dir.getAbsolutePath() + "/" + f.getName() + " weighs " + f.length());
            if (f.isDirectory()) {
                dirSize += browseFiles(f);
            }
        }
        return dirSize;
    }
}
