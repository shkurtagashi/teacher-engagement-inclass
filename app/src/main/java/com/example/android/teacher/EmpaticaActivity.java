package com.example.android.teacher;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.teacher.data.DatabaseHelper;

import com.example.android.teacher.data.EmpaticaE4.E4DataContract.E4DataEntry;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;

import static com.example.android.teacher.R.id.username;


public class EmpaticaActivity extends AppCompatActivity {

    private static final String TAG = "EmpaticaActivity";
    private Button saveEmpaticaSettings;
    private EditText deviceName;
    private EditText apiKey;
    private String android_id;
    private String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empatica);

        //Get phone's unique ID
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        username = UserData._username;

        deviceName = (EditText) findViewById(R.id.edit_e4_name) ;
        apiKey = (EditText) findViewById(R.id.edit_api_key);
        saveEmpaticaSettings = (Button)findViewById(R.id.save_e4_data);

        saveEmpaticaSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUsername()){
                    updateE4Data();
                }
                else{
                    insertE4Data();
                }
//                Intent i = new Intent (getApplicationContext(), HomeActivity.class);
//                startActivity(i);
            }
        });
    }

    public boolean checkUsername(){
        boolean exists;

        DatabaseHelper e4DbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = e4DbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                E4DataEntry._ID
        };

        // Filter results WHERE "_USERNAME" = 'username'
        String selection = E4DataEntry._USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                E4DataEntry.TABLE_NAME_EMPATICA_E4,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if(cursor.getCount() > 0){
            exists = true;
        } else{
            exists = false;
        }

        cursor.close();
        return exists;
    }


    public void insertE4Data(){
        DatabaseHelper empaticaDbHelper = new DatabaseHelper(this);

        //Gets the data repository in write mode
        SQLiteDatabase db = empaticaDbHelper.getWritableDatabase();


        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(E4DataEntry._USERNAME, username);
        values.put(E4DataEntry.COLUMN_E4_NAME, deviceName.getText() + "");
        values.put(E4DataEntry.COLUMN_API_KEY, apiKey.getText() + "");

        //Log.v(TAG, deviceName.getText()+ "");

        long newRowId = db.insert(E4DataEntry.TABLE_NAME_EMPATICA_E4, null, values);
        if(newRowId == -1){
            Toast.makeText(this,"Error saving E4 data", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Empatica device with name " + deviceName.getText()+ "" +
                    " and api_key: " + apiKey.getText() + "" + " for user with username: " + username
                    + " was saved successfully.", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void updateE4Data(){
        DatabaseHelper empaticaDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = empaticaDbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(E4DataEntry.COLUMN_E4_NAME, deviceName.getText() + "");
        values.put(E4DataEntry.COLUMN_API_KEY, apiKey.getText() + "");

        db.update(E4DataEntry.TABLE_NAME_EMPATICA_E4, values, E4DataEntry._USERNAME + "=?", new String[]{username});

        Toast.makeText(this,"Empatica device with name " + deviceName.getText()+ "" + " and api_key: " + apiKey.getText() + "" + " for device with id: " + android_id
                        + " was updated successfully.", Toast.LENGTH_LONG).show();

        finish();

    }

}
