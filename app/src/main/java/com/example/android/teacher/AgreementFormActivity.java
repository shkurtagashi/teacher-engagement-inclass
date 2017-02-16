package com.example.android.teacher;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract.UserEntry;

import static com.example.android.teacher.R.id.username;


public class AgreementFormActivity extends AppCompatActivity{
    private static final String TAG = "AgreementFormActivity";

    private Button agree_button;
    private Button disagree_button;

    private String agreementSelection = UserEntry.AGREEMENT_ACCEPTED;
    String username;



//    private String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_agreement_form layout file
        setContentView(R.layout.activity_agreement_form);

        username = UserData._username;

        //Find the View that shows the Agree button
        agree_button = (Button)findViewById(R.id.agree_button);

        //Find the View that shows the Agree button
        disagree_button = (Button)findViewById(R.id.disagree_button);


        setUpAgreeButton();
        setUpDisagreeButton();

    }


    public void setUpAgreeButton(){
        //Set a click listener on agree button view
        agree_button.setOnClickListener(new View.OnClickListener(){

            // The code in this method will be executed when the Agree Button is clicked on.
            @Override
            public void onClick(View v){
                //Save the agreement form answer
                agreementSelection = UserEntry.AGREEMENT_ACCEPTED;
                updateUserTableData();
                //Create a new intent to open the Home Activity
                Intent registrationFormIntent = new Intent (getApplicationContext(), HomeActivity.class);
                startActivity(registrationFormIntent);
                finish();
            }

        });
    }

    public void setUpDisagreeButton(){
        //Set a click listener on disagree button view
        disagree_button.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View v) {
                agreementSelection = UserEntry.AGREEMENT_NOT_ACCEPTED;
                updateUserTableData();
                AlertDialog.Builder builder = new AlertDialog.Builder(AgreementFormActivity.this);
                builder.setMessage(R.string.disagree_text)
                        .setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                System.exit(0);
                            }
                        });
                AlertDialog disagreeAlertDialog = builder.create();
                disagreeAlertDialog.show();
            }
        });
    }




    public void updateUserTableData(){

        DatabaseHelper usersDbHelper = new DatabaseHelper(this);

        //Gets the data repository in write mode
        SQLiteDatabase db = usersDbHelper.getWritableDatabase();


        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_AGREEMENT, agreementSelection);


        // Which row to update, based on the ID
        String selection = UserEntry.USERNAME + " = ?";
        String[] selectionArgs = { username };

        int count = db.update(
               UserEntry.TABLE_NAME_USERS,
                values,
                selection,
                selectionArgs);

        if(count == -1){
            Toast.makeText(this,"Error saving agreement selection" + count, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Agreement form answer saved for user with username: " + username + ", agreement: " + agreementSelection, Toast.LENGTH_SHORT).show();
        }


    }
}
