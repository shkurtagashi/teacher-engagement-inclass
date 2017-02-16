package com.example.android.teacher;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.Sensors.EdaSensorContract;

public class DatabaseTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        //displayEDAinfo();
        displayDatabaseInfo();

    }

    public void displayDatabaseInfo(){

        DatabaseHelper db = new DatabaseHelper(this);

        TextView edaDisplayView = (TextView) findViewById(R.id.eda_value);
        TextView tempDisplayView = (TextView) findViewById(R.id.temp_value);
        TextView bvpDisplayView = (TextView) findViewById(R.id.bvp_value);
        TextView accDisplayView = (TextView) findViewById(R.id.acc_value);


        edaDisplayView.setText("The eda table contains " + db.getEdaValuesCount() + " values.\n\n");
        tempDisplayView.setText("The temp table contains " + db.getTempValuesCount() + " values.\n\n");
        bvpDisplayView.setText("The bvp table contains " + db.getBvpValuesCount() + " values.\n\n");
        accDisplayView.setText("The acc table contains " + db.getAccValuesCount() + " values.\n\n");

    }

    public void displayEDAinfo(){

        DatabaseHelper edaDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = edaDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                EdaSensorContract.EdaSensorDataEntry._EDA_ID,
                EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP,
                EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_VALUE
        };



        Cursor cursor = db.query(
                EdaSensorContract.EdaSensorDataEntry.TABLE_NAME_EDA_DATA,                     // The table to query
                projection,                                   // The columns to return
                null,                                        // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        TextView displayView = (TextView) findViewById(R.id.eda_value);


        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The eda table contains " + cursor.getCount() + " values.\n\n");
            displayView.append(EdaSensorContract.EdaSensorDataEntry._EDA_ID + " - " +
                    EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP + " - " +
                    EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_VALUE + "\n");

            // Figure out the index of each column
            int idIndex = cursor.getColumnIndex(EdaSensorContract.EdaSensorDataEntry._EDA_ID);
            int timestampIndex = cursor.getColumnIndex(EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_TIMESTAMP);
            int valueIndex = cursor.getColumnIndex(EdaSensorContract.EdaSensorDataEntry.COLUMN_EDA_VALUE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idIndex);
                String currentTimestamp = cursor.getString(timestampIndex);
                String currentValue = cursor.getString(valueIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentTimestamp + " - " +
                        currentValue));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }


    }
}

