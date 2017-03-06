package com.example.android.teacher.data.LocalDataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.teacher.data.EmpaticaE4.EmpaticaE4;
import com.example.android.teacher.data.Sensors.AccelereometerSensor;
import com.example.android.teacher.data.Sensors.BloodVolumePressureSensor;
import com.example.android.teacher.data.Sensors.EdaSensor;
import com.example.android.teacher.data.Sensors.EdaSensorContract;
import com.example.android.teacher.data.Sensors.TemperatureSensor;
import com.example.android.teacher.data.EmpaticaE4.E4DataContract.E4DataEntry;
import com.example.android.teacher.data.PamDatabase.PAMClass;
import com.example.android.teacher.data.PamDatabase.PAMContract;
import com.example.android.teacher.data.Surveys.GeneralSurvey;
import com.example.android.teacher.data.Surveys.PostLectureSurvey;
import com.example.android.teacher.data.Surveys.PostLectureSurveyContract.PostLectureSurveyDataEntry;
import com.example.android.teacher.data.Surveys.GeneralSurveyContract.GeneralSurveyDataEntry;
import com.example.android.teacher.data.UploaderUtilityTable;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UsersContract.UserEntry;
import com.example.android.teacher.data.Sensors.EdaSensorContract.EdaSensorDataEntry;
import com.example.android.teacher.data.Sensors.TempSensorContract.TempSensorDataEntry;
import com.example.android.teacher.data.Sensors.AccSensorContract.AccSensorDataEntry;
import com.example.android.teacher.data.Sensors.BvpSensorContract.BvpSensorDataEntry;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.teacher.R.id.username;
import static com.example.android.teacher.data.PamDatabase.PAMContract.PAMDataEntry.TABLE_NAME_PAM_DATA;
import static com.example.android.teacher.data.Surveys.GeneralSurveyContract.GeneralSurveyDataEntry.TABLE_GENERAL_SURVEY;
import static com.example.android.teacher.data.Surveys.PostLectureSurveyContract.PostLectureSurveyDataEntry.TABLE_POST_LECTURE_SURVEY;


/**
 * Created by shkurtagashi on 18.12.16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /** Name of the database file */
    private static final String DATABASE_NAME = "teacher.db";

    // Table Create Statements
    // String that contains the SQL statement to create the users table
    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME_USERS + " ("
            + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserEntry.ANDROID_ID + " TEXT, "
            + UserEntry.USERNAME + " TEXT, "
            + UserEntry.COLUMN_GENDER + " TEXT, "
            + UserEntry.COLUMN_AGE + " TEXT, "
            + UserEntry.COLUMN_FACULTY + " TEXT, "
            + UserEntry.COLUMN_PROGRAMME + " TEXT, "
            + UserEntry.COLUMN_COURSE + " TEXT, "
            + UserEntry.COLUMN_STATUS + " TEXT, "
            + UserEntry.COLUMN_TEACHING_EXPERIENCE + " TEXT, "
            + UserEntry.COLUMN_AGREEMENT + " TEXT);";

    // String that contains the SQL statement to create the E4 data table
    private static final String SQL_CREATE_EMPATICA_E4_TABLE = "CREATE TABLE " + E4DataEntry.TABLE_NAME_EMPATICA_E4 + " ("
            + E4DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + E4DataEntry.COLUMN_E4_NAME + " TEXT, "
            + E4DataEntry.COLUMN_API_KEY + " TEXT);";


    // String that contains the SQL statement to create the EDA Sensor data table
    private static final String SQL_CREATE_EDA_SENSOR_DATA_TABLE = "CREATE TABLE " + EdaSensorDataEntry.TABLE_NAME_EDA_DATA + " ("
            + EdaSensorDataEntry._EDA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP + " TEXT, "
            + EdaSensorDataEntry.COLUMN_EDA_VALUE + " TEXT);";


    // String that contains the SQL statement to create the TEMP Sensor data table
    private static final String SQL_CREATE_TEMP_SENSOR_DATA_TABLE = "CREATE TABLE " + TempSensorDataEntry.TABLE_NAME_TEMP_DATA + " ("
            + TempSensorDataEntry._TEMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TempSensorDataEntry.COLUMN_TEMP_TIMESTAMP + " TEXT, "
            + TempSensorDataEntry.COLUMN_TEMP_VALUE + " TEXT);";

    // String that contains the SQL statement to create the ACC Sensor data table
    private static final String SQL_CREATE_ACC_SENSOR_DATA_TABLE = "CREATE TABLE " + AccSensorDataEntry.TABLE_NAME_ACC_DATA + " ("
            + AccSensorDataEntry._ACC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AccSensorDataEntry.COLUMN_ACC_TIMESTAMP + " TEXT, "
            + AccSensorDataEntry.COLUMN_ACC_X_VALUE + " TEXT, "
            + AccSensorDataEntry.COLUMN_ACC_Y_VALUE + " TEXT, "
            + AccSensorDataEntry.COLUMN_ACC_Z_VALUE + " TEXT);";

    // String that contains the SQL statement to create the BVP Sensor data table
    private static final String SQL_CREATE_BVP_SENSOR_DATA_TABLE = "CREATE TABLE " + BvpSensorDataEntry.TABLE_NAME_BVP_DATA + " ("
            + BvpSensorDataEntry._BVP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BvpSensorDataEntry.COLUMN_BVP_TIMESTAMP + " TEXT, "
            + BvpSensorDataEntry.COLUMN_BVP_VALUE + " TEXT);";

    // String that contains the SQL statement to create the PAM data table
    private static final String SQL_CREATE_PAM_DATA_TABLE = "CREATE TABLE "+ TABLE_NAME_PAM_DATA + "("
            + PAMContract.PAMDataEntry._PAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PAMContract.PAMDataEntry.PAM_DATE + " TEXT, "
            + PAMContract.PAMDataEntry.ANDROID_ID + " TEXT,"
            + PAMContract.PAMDataEntry.COURSE_TITLE + " TEXT, "
            + PAMContract.PAMDataEntry.ANSWER + " TEXT" + ")";

    // String that contains the SQL statement to create the General Survey data table
    private static final String SQL_CREATE_GENERAL_DATA_TABLE = "CREATE TABLE "+ TABLE_GENERAL_SURVEY + "("
            + GeneralSurveyDataEntry._GENERAL_SURVEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + GeneralSurveyDataEntry.ANDROID_ID + " TEXT, "
            + GeneralSurveyDataEntry.TIMESTAMP + " REAL, "
            + GeneralSurveyDataEntry.QUESTION_1 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_2 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_3 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_4 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_5 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_6 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_7 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_8 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_9 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_10 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_11 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_12 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_13 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_14 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_15 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_16 + " TEXT, "
            + GeneralSurveyDataEntry.QUESTION_17 + " TEXT" + ")";

    // String that contains the SQL statement to create the General Survey data table
    private static final String SQL_CREATE_POSTLECTURE_SURVEY_DATA_TABLE = "CREATE TABLE "+ TABLE_POST_LECTURE_SURVEY + "("
            + PostLectureSurveyDataEntry._POST_LECTURE_SURVEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PostLectureSurveyDataEntry.ANDROID_ID + " TEXT, "
            + PostLectureSurveyDataEntry.TIMESTAMP + " REAL, "
            + PostLectureSurveyDataEntry.QUESTION_1 + " TEXT, "
            + PostLectureSurveyDataEntry.QUESTION_2 + " TEXT, "
            + PostLectureSurveyDataEntry.QUESTION_3 + " TEXT, "
            + PostLectureSurveyDataEntry.QUESTION_4 + " TEXT, "
            + PostLectureSurveyDataEntry.QUESTION_5 + " TEXT, "
            + PostLectureSurveyDataEntry.QUESTION_6 + " TEXT" + ")";

    /**
     * Constructs a new instance of {@link DatabaseHelper}.
     *
     * @param context of the app
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        // Execute the SQL statements
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_EMPATICA_E4_TABLE);
        db.execSQL(SQL_CREATE_EDA_SENSOR_DATA_TABLE);
        db.execSQL(SQL_CREATE_TEMP_SENSOR_DATA_TABLE);
        db.execSQL(SQL_CREATE_BVP_SENSOR_DATA_TABLE);
        db.execSQL(SQL_CREATE_ACC_SENSOR_DATA_TABLE);
        db.execSQL(SQL_CREATE_PAM_DATA_TABLE);
        db.execSQL(SQL_CREATE_GENERAL_DATA_TABLE);
        db.execSQL(SQL_CREATE_POSTLECTURE_SURVEY_DATA_TABLE);
        db.execSQL(UploaderUtilityTable.getCreateQuery());

        insertRecords(db, UploaderUtilityTable.TABLE_UPLOADER_UTILITY, UploaderUtilityTable.getRecords());

    }


    private void insertRecords(SQLiteDatabase db, String tableName, List<ContentValues> records) {
        for(ContentValues record: records) {
            db.insert(tableName, null, record);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
    }


    private static DatabaseHelper dbhelper;

    //getInstance method used where we need instance of RegDatabaseHandler to insert, update or delete data
    public static synchronized DatabaseHelper getInstance(Context context){
        if(dbhelper == null){
            dbhelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbhelper;
    }

    public User getUserInformation(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        User user = new User();

        Cursor cursor = db.query(UserEntry.TABLE_NAME_USERS, new String[]{UserEntry.ANDROID_ID,
                UserEntry.USERNAME, UserEntry.COLUMN_GENDER, UserEntry.COLUMN_AGE,
                UserEntry.COLUMN_FACULTY, UserEntry.COLUMN_PROGRAMME, UserEntry.COLUMN_COURSE,
                UserEntry.COLUMN_STATUS, UserEntry.COLUMN_TEACHING_EXPERIENCE, UserEntry.COLUMN_AGREEMENT},
                UserEntry.USERNAME +"=?", new String[]{String.valueOf(username)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            user = new User(cursor.getString(cursor.getColumnIndex(UserEntry.USERNAME)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_GENDER)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_AGE)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_FACULTY)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_PROGRAMME)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_COURSE)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_STATUS)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_TEACHING_EXPERIENCE)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_AGREEMENT))
            );
            cursor.close();
        }
        return user;
    }

    public void updateUserRegistration(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {

            ContentValues values = new ContentValues();
            values.put(UserEntry.COLUMN_AGE, user.getAge());
            values.put(UserEntry.COLUMN_GENDER, user.getGender());
            values.put(UserEntry.COLUMN_FACULTY, user.getFaculty());
            values.put(UserEntry.COLUMN_PROGRAMME, user.getProgramme());
            values.put(UserEntry.COLUMN_COURSE, user.getCourse());
            values.put(UserEntry.COLUMN_STATUS, user.getStatus());
            values.put(UserEntry.COLUMN_TEACHING_EXPERIENCE, user.getTeachingExperience());

            db.update(UserEntry.TABLE_NAME_USERS, values, UserEntry.USERNAME + " = ?", new String[]{String.valueOf(user.getUsername())});
            db.setTransactionSuccessful();
            System.out.println("USER DATA UPDATED: " + values);
        }catch (SQLException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error while trying to update USER to database" + username);
        } finally {////
            db.endTransaction();
        }
    }

    public String getCourseTitle(String android_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String courseTitle = "";

        String[] projection = {
                UserEntry.COLUMN_COURSE
        };

        // Filter results WHERE "_ID" = 'android_id'
        String selection = UserEntry.ANDROID_ID + " = ?";
        String[] selectionArgs = { android_id };

        Cursor cursor = db.query(
                UserEntry.TABLE_NAME_USERS,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if (cursor.moveToFirst()) { // if Cursor is not empty
            courseTitle = cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_COURSE));
        }

        cursor.close();
        return courseTitle;
    }


    /*
        CREATE
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(UserEntry.ANDROID_ID, user.getAndroidId());
            values.put(UserEntry.USERNAME, user.getUsername());
            values.put(UserEntry.COLUMN_GENDER, user.getGender());
            values.put(UserEntry.COLUMN_AGE, user.getAge());
            values.put(UserEntry.COLUMN_FACULTY, user.getFaculty());
            values.put(UserEntry.COLUMN_PROGRAMME, user.getProgramme());
            values.put(UserEntry.COLUMN_COURSE, user.getCourse());
            values.put(UserEntry.COLUMN_STATUS, user.getStatus());
            values.put(UserEntry.COLUMN_TEACHING_EXPERIENCE, user.getTeachingExperience());
            values.put(UserEntry.COLUMN_AGREEMENT, user.getAgreement());


            db.insertOrThrow(UserEntry.TABLE_NAME_USERS, null, values);
            db.setTransactionSuccessful();
            System.out.println("USER DATA INSERTED: "+ values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error while trying to add USER to database");
        } finally {
            db.endTransaction();
        }
    }

    public void addEmpaticaE4Data(EmpaticaE4 e4) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(E4DataEntry.COLUMN_E4_NAME, e4.getName());
            values.put(E4DataEntry.COLUMN_API_KEY, e4.getApiKey());


            db.insertOrThrow(E4DataEntry.TABLE_NAME_EMPATICA_E4, null, values);
            db.setTransactionSuccessful();
            System.out.println("EMPATICA E4 DATA INSERTED: "+ values);
            Log.v("DatabaseHelper", "New Empatica E4 device with name: " + e4.getName() +
                    " and api key: " + e4.getApiKey());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error while trying to add Empatica E4 to database");
            Log.v("WHATEVERRRR", "Error saving E4 data");

        } finally {
            db.endTransaction();
        }

        db.close(); // Closing database connection
    }


    public void addGeneralSurveyAnswers(GeneralSurvey generalSurvey) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(GeneralSurveyDataEntry.ANDROID_ID, generalSurvey._android_id);
            values.put(GeneralSurveyDataEntry.TIMESTAMP, generalSurvey._timestamp);
            values.put(GeneralSurveyDataEntry.QUESTION_1, generalSurvey._question1);
            values.put(GeneralSurveyDataEntry.QUESTION_2, generalSurvey._question2);
            values.put(GeneralSurveyDataEntry.QUESTION_3, generalSurvey._question3);
            values.put(GeneralSurveyDataEntry.QUESTION_4, generalSurvey._question4);
            values.put(GeneralSurveyDataEntry.QUESTION_5, generalSurvey._question5);
            values.put(GeneralSurveyDataEntry.QUESTION_6, generalSurvey._question6);
            values.put(GeneralSurveyDataEntry.QUESTION_7, generalSurvey._question7);
            values.put(GeneralSurveyDataEntry.QUESTION_8, generalSurvey._question8);
            values.put(GeneralSurveyDataEntry.QUESTION_9, generalSurvey._question9);
            values.put(GeneralSurveyDataEntry.QUESTION_10, generalSurvey._question10);
            values.put(GeneralSurveyDataEntry.QUESTION_11, generalSurvey._question11);
            values.put(GeneralSurveyDataEntry.QUESTION_12, generalSurvey._question12);
            values.put(GeneralSurveyDataEntry.QUESTION_13, generalSurvey._question13);
            values.put(GeneralSurveyDataEntry.QUESTION_14, generalSurvey._question14);
            values.put(GeneralSurveyDataEntry.QUESTION_15, generalSurvey._question15);
            values.put(GeneralSurveyDataEntry.QUESTION_16, generalSurvey._question16);
            values.put(GeneralSurveyDataEntry.QUESTION_17, generalSurvey._question17);

            db.insertOrThrow(TABLE_GENERAL_SURVEY, null, values);
            db.setTransactionSuccessful();
            System.out.println("good "+ values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error while trying to add generalSurvey to database");
        } finally {
            db.endTransaction();
        }
    }


    public void addPostLectureSurveyAnswers(PostLectureSurvey postLectureSurvey) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(PostLectureSurveyDataEntry.ANDROID_ID, postLectureSurvey._android_id);
            values.put(PostLectureSurveyDataEntry.TIMESTAMP, postLectureSurvey._timestamp);
            values.put(PostLectureSurveyDataEntry.QUESTION_1, postLectureSurvey._question1);
            values.put(PostLectureSurveyDataEntry.QUESTION_2, postLectureSurvey._question2);
            values.put(PostLectureSurveyDataEntry.QUESTION_3, postLectureSurvey._question3);
            values.put(PostLectureSurveyDataEntry.QUESTION_4, postLectureSurvey._question4);
            values.put(PostLectureSurveyDataEntry.QUESTION_5, postLectureSurvey._question5);
            values.put(PostLectureSurveyDataEntry.QUESTION_6, postLectureSurvey._question6);

            db.insertOrThrow(TABLE_POST_LECTURE_SURVEY, null, values);
            db.setTransactionSuccessful();
            System.out.println("good "+ values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error while trying to add postlecture survey to database");
        } finally {
            db.endTransaction();
        }
    }

    //Method for getting all data from General Surveys table
    public List<GeneralSurvey> getAllGeneralSurveys(){
        List<GeneralSurvey> generalSurveysList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_GENERAL_SURVEY;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    GeneralSurvey genSurvey = new GeneralSurvey();

                    genSurvey._id = cursor.getInt(cursor.getColumnIndex(GeneralSurveyDataEntry._GENERAL_SURVEY_ID));
                    genSurvey._android_id= cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.ANDROID_ID));
                    genSurvey._timestamp= cursor.getDouble(cursor.getColumnIndex(GeneralSurveyDataEntry.TIMESTAMP));
                    genSurvey._question1 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_1));
                    genSurvey._question2 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_2));
                    genSurvey._question3 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_3));
                    genSurvey._question4 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_4));
                    genSurvey._question5 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_5));
                    genSurvey._question6 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_6));
                    genSurvey._question7 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_7));
                    genSurvey._question8 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_8));
                    genSurvey._question9 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_9));
                    genSurvey._question10 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_10));
                    genSurvey._question11 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_11));
                    genSurvey._question12 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_12));
                    genSurvey._question13 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_13));
                    genSurvey._question14 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_14));
                    genSurvey._question15 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_15));
                    genSurvey._question16 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_16));
                    genSurvey._question17 = cursor.getString(cursor.getColumnIndex(GeneralSurveyDataEntry.QUESTION_17));

                    generalSurveysList.add(genSurvey);
                }while(cursor.moveToNext());

            }
        }catch (Exception e){
            Log.d(LOG_TAG, "Error while trying to get posts from generalSurvey table");
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return generalSurveysList;
    }

    //Method for getting all data from General Surveys table
    public List<PostLectureSurvey> getAllPostlectureSurveys(){
        List<PostLectureSurvey> postLectureSurveyList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_POST_LECTURE_SURVEY;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    PostLectureSurvey postlectureSurvey = new PostLectureSurvey();

                    postlectureSurvey._id = cursor.getInt(cursor.getColumnIndex(PostLectureSurveyDataEntry._POST_LECTURE_SURVEY_ID));
                    postlectureSurvey._android_id= cursor.getString(cursor.getColumnIndex(PostLectureSurveyDataEntry.ANDROID_ID));
                    postlectureSurvey._timestamp= cursor.getDouble(cursor.getColumnIndex(PostLectureSurveyDataEntry.TIMESTAMP));
                    postlectureSurvey._question1 = cursor.getString(cursor.getColumnIndex(PostLectureSurveyDataEntry.QUESTION_1));
                    postlectureSurvey._question2 = cursor.getString(cursor.getColumnIndex(PostLectureSurveyDataEntry.QUESTION_2));
                    postlectureSurvey._question3 = cursor.getString(cursor.getColumnIndex(PostLectureSurveyDataEntry.QUESTION_3));
                    postlectureSurvey._question4 = cursor.getString(cursor.getColumnIndex(PostLectureSurveyDataEntry.QUESTION_4));
                    postlectureSurvey._question5 = cursor.getString(cursor.getColumnIndex(PostLectureSurveyDataEntry.QUESTION_5));
                    postlectureSurvey._question6 = cursor.getString(cursor.getColumnIndex(PostLectureSurveyDataEntry.QUESTION_6));

                    postLectureSurveyList.add(postlectureSurvey);
                }while(cursor.moveToNext());

            }
        }catch (Exception e){
            Log.d(LOG_TAG, "Error while trying to get posts from generalSurvey table");
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return postLectureSurveyList;
    }



    public void addEdaSensorValues(EdaSensor eda, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(EdaSensorDataEntry.COLUMN_EDA_VALUE, eda.getValue());
        values.put(EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP, eda.getTimestamp());
        db.insert(EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA, null, values);

        //db.close(); // Closing database connection
    }

    public void addTempSensorValues(TemperatureSensor temp, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(TempSensorDataEntry.COLUMN_TEMP_VALUE, temp.getValue());
        values.put(TempSensorDataEntry.COLUMN_TEMP_TIMESTAMP, temp.getTimestamp());

        db.insert(TempSensorDataEntry.TABLE_NAME_TEMP_DATA, null, values);
        //db.close(); // Closing database connection
    }


    public void addBvpSensorValues(BloodVolumePressureSensor bvp, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(BvpSensorDataEntry.COLUMN_BVP_VALUE, bvp.getValue());
        values.put(BvpSensorDataEntry.COLUMN_BVP_TIMESTAMP, bvp.getTimestamp());

        db.insert(BvpSensorDataEntry.TABLE_NAME_BVP_DATA, null, values);

        //db.close(); // Closing database connection
    }

    public void addAccSensorValues(AccelereometerSensor acc, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(AccSensorDataEntry.COLUMN_ACC_X_VALUE, acc.getXvalue());
        values.put(AccSensorDataEntry.COLUMN_ACC_Y_VALUE, acc.getYvalue());
        values.put(AccSensorDataEntry.COLUMN_ACC_Z_VALUE, acc.getZvalue());
        values.put(AccSensorDataEntry.COLUMN_ACC_TIMESTAMP, acc.getTimestamp());


        long newRowId = db.insert(AccSensorDataEntry.TABLE_NAME_ACC_DATA, null, values);
        if (newRowId == -1) {
            Log.v("WHATEVERRRR", "Error saving ACC data");
        } else {
            Log.v("WHATEVERRRR", " Acc data: x: " + acc.getXvalue() + ", y:  " + acc.getYvalue() + ", z: " + acc.getZvalue() + " saved for timestamp: " + acc.getTimestamp());
        }

        //db.close(); // Closing database connection
    }

    /*
        READ
     */

    // Getting All Users
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + UserEntry.TABLE_NAME_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User userRow = new User();
                userRow.setAndroidId(cursor.getString(cursor.getColumnIndex(UserEntry.ANDROID_ID)));
                userRow.setUsername(cursor.getString(cursor.getColumnIndex(UserEntry.USERNAME)));
                userRow.setGender(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_GENDER)));
                userRow.setAge(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_AGE)));
                userRow.setFaculty(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_FACULTY)));
                userRow.setProgramme(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_PROGRAMME)));
                userRow.setCourse(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_COURSE)));
                userRow.setStatus(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_STATUS)));
                userRow.setTeachingExperience(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_TEACHING_EXPERIENCE)));
                userRow.setAgreement(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_AGREEMENT)));

                // Adding Eda row to list
                usersList.add(userRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return usersList;
    }

    // Getting All EdaSensorValues
    public List<EdaSensor> getAllEdaSensorValues() {
        List<EdaSensor> edaSensorList = new ArrayList<EdaSensor>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + EdaSensorDataEntry.TABLE_NAME_EDA_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EdaSensor edaRow = new EdaSensor();
                edaRow.setID(Integer.parseInt(cursor.getString(0)));
                edaRow.setTimestamp(Double.parseDouble(cursor.getString(1)));
                edaRow.setValue(Float.parseFloat(cursor.getString(2)));


                // Adding Eda row to list
                edaSensorList.add(edaRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return edaSensorList;
    }

    // Getting All TempSensorValues
    public List<TemperatureSensor> getAllTempSensorValues() {
        List<TemperatureSensor> tempSensorList = new ArrayList<TemperatureSensor>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TempSensorDataEntry.TABLE_NAME_TEMP_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TemperatureSensor tempRow = new TemperatureSensor();
                tempRow.setID(Integer.parseInt(cursor.getString(0)));
                tempRow.setTimestamp(Double.parseDouble(cursor.getString(1)));
                tempRow.setValue(Float.parseFloat(cursor.getString(2)));

                // Adding temperature row to list
                tempSensorList.add(tempRow);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return tempSensorList;
    }

    // Getting All BvpSensorValues
    public List<BloodVolumePressureSensor> getAllBvpSensorValues() {
        List<BloodVolumePressureSensor> bvpSensorList = new ArrayList<BloodVolumePressureSensor>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + BvpSensorDataEntry.TABLE_NAME_BVP_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BloodVolumePressureSensor bvpRow = new BloodVolumePressureSensor();
                bvpRow.setID(Integer.parseInt(cursor.getString(0)));
                bvpRow.setTimestamp(Double.parseDouble(cursor.getString(1)));
                bvpRow.setValue(Float.parseFloat(cursor.getString(2)));

                // Adding Eda row to list
                bvpSensorList.add(bvpRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bvpSensorList;
    }


    // Getting All AccSensorValues
    public List<AccelereometerSensor> getAllAccSensorValues() {
        List<AccelereometerSensor> accSensorList = new ArrayList<AccelereometerSensor>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + AccSensorDataEntry.TABLE_NAME_ACC_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AccelereometerSensor accRow = new AccelereometerSensor();
                accRow.setID(Integer.parseInt(cursor.getString(0)));
                accRow.setTimestamp(Double.parseDouble(cursor.getString(1)));
                accRow.setXvalue(Integer.parseInt(cursor.getString(2)));
                accRow.setYvalue(Integer.parseInt(cursor.getString(3)));
                accRow.setZvalue(Integer.parseInt(cursor.getString(4)));

                accSensorList.add(accRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return accSensorList;
    }

    public EmpaticaE4 getEmpaticaE4() {
        EmpaticaE4 e4 = new EmpaticaE4();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + E4DataEntry.TABLE_NAME_EMPATICA_E4;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            e4.setName(cursor.getString(cursor.getColumnIndex(E4DataEntry.COLUMN_E4_NAME)));
            e4.setApiKey(cursor.getString(cursor.getColumnIndex(E4DataEntry.COLUMN_API_KEY)));
        }

        cursor.close();
        return e4;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + UserEntry.TABLE_NAME_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int number = cursor.getCount();
        cursor.close();

        return number;

    }

    public int getEmpaticaE4Count() {
        String countQuery = "SELECT  * FROM " + E4DataEntry.TABLE_NAME_EMPATICA_E4;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int number = cursor.getCount();
        cursor.close();

        return number;

    }

    // Getting Eda Table rows count
    public int getEdaValuesCount() {
        String countQuery = "SELECT  * FROM " + EdaSensorDataEntry.TABLE_NAME_EDA_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int number = cursor.getCount();
        cursor.close();

        return number;
    }

    // Getting Temp Table rows count
    public int getTempValuesCount() {
        String countQuery = "SELECT  * FROM " + TempSensorDataEntry.TABLE_NAME_TEMP_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int number = cursor.getCount();
        cursor.close();

        return number;
    }

    // Getting BVP Table rows count
    public int getBvpValuesCount() {
        String countQuery = "SELECT  * FROM " + BvpSensorDataEntry.TABLE_NAME_BVP_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int number = cursor.getCount();
        cursor.close();

        return number;
    }

    // Getting Acc Table rows count
    public int getAccValuesCount() {
        String countQuery = "SELECT * FROM " + AccSensorDataEntry.TABLE_NAME_ACC_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int number = cursor.getCount();
        cursor.close();

        return number;
    }

     /*
        DELETE
     */
     public void deleteAllFromTable(String tableName) {
         SQLiteDatabase db = this.getWritableDatabase();

         try{
             db.beginTransaction();
             db.execSQL("DELETE FROM " + tableName + ";");
             db.setTransactionSuccessful();
         }catch (SQLException e){
             Log.d("Database Helper", "Error while deleting table records" + tableName);
         }finally {
             db.endTransaction();
         }
     }

    //Method for deleting one pam from PAM table
    public void deleteUser(String username){
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM "+ UserEntry.TABLE_NAME_USERS +" WHERE username ='"+ username +"'");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d("Database Helper", "Error while trying to delete User data");
        }finally {
            db.endTransaction();
        }
    }





    //Method for inserting registration details in the database
    public void addPAM(PAMClass pam) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(PAMContract.PAMDataEntry.ANDROID_ID, pam._android_id);
            values.put(PAMContract.PAMDataEntry.PAM_DATE, pam._date);
            values.put(PAMContract.PAMDataEntry.COURSE_TITLE, pam._course);
            values.put(PAMContract.PAMDataEntry.ANSWER, pam._pam_answer);

            db.insertOrThrow(TABLE_NAME_PAM_DATA, null, values);
            db.setTransactionSuccessful();
            System.out.println("good "+values);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Database Helper", "Error while trying to add PAM to database");
        } finally {
            db.endTransaction();
        }
    }

    //Method for getting all data from registration table
    public List<PAMClass> getAllPAM(){
        List<PAMClass> PAMList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_PAM_DATA;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    PAMClass pam = new PAMClass();
                    pam._id = cursor.getInt(cursor.getColumnIndex(PAMContract.PAMDataEntry._PAM_ID));
                    pam._date = cursor.getString(cursor.getColumnIndex(PAMContract.PAMDataEntry.PAM_DATE));
                    pam._course = cursor.getString(cursor.getColumnIndex(PAMContract.PAMDataEntry.COURSE_TITLE));
                    pam._android_id= cursor.getString(cursor.getColumnIndex(PAMContract.PAMDataEntry.ANDROID_ID));
                    pam._pam_answer= cursor.getString(cursor.getColumnIndex(PAMContract.PAMDataEntry.ANSWER));

                    PAMList.add(pam);
                }while(cursor.moveToNext());

            }
        }catch (Exception e){
            Log.d("Database helper", "Error while trying to get posts from database");
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return PAMList;
    }

    //Method for deleting one pam from PAM table
    public void deletePAM(int id){
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM "+ TABLE_NAME_PAM_DATA +" WHERE _id ='"+ id +"'");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d("Database Helper", "Error while trying to delete PAM detail");
        }finally {
            db.endTransaction();
        }
    }

    public void updatePAM(int id, String date, String course, String answer){

        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_NAME_PAM_DATA +
                    " SET Date='" + date + "', CourseTitle='" + course + "', PAMAnswer='"+ answer + "' WHERE _id ='" + id + "';");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d("Database Helper", "Error while trying to update PAM detail");
        }finally {
            db.endTransaction();
        }

    }

    public void updateEmpaticaE4(String name, String api_key){
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("UPDATE " + E4DataEntry.TABLE_NAME_EMPATICA_E4 +
                    " SET empatica_name='" + name + "', api_key='" + api_key + "' WHERE id ='" + 1 + "';");
            db.setTransactionSuccessful();
            Log.d("Database Helper", "Success updating Empatica E4 data");

        }catch (SQLException e){
            Log.d("Database Helper", "Error while trying to update Empatica E4 data");
        }finally {
            db.endTransaction();
        }

    }


}
