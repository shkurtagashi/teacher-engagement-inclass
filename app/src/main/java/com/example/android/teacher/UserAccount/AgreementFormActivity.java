package com.example.android.teacher.UserAccount;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Radio;
import com.example.android.teacher.EmpaticaE4.EmpaticaActivity;
import com.example.android.teacher.HelpActivity;
import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.User.UserData;
import com.example.android.teacher.data.User.UsersContract.UserEntry;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.android.teacher.HomeActivity.admin_password;


public class AgreementFormActivity extends AppCompatActivity{
    private static final String TAG = "AgreementFormActivity";

    private Button agree_button;
    private Button disagree_button;

    private String agreementSelection = UserEntry.AGREEMENT_ACCEPTED;
    private String username;
    private String courses;
    private String password = "";


    DatabaseHelper dbHelper;



//    private String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_agreement_form layout file
        setContentView(R.layout.activity_agreement_form);

        dbHelper = new DatabaseHelper(this);

        username = UserData._username;
        courses = UserData._selectedCourses;

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
//                try {
//
//                    ESMFactory factory = new ESMFactory();
//
//                    ESM_Radio esmRadio1 = new ESM_Radio();
//                    esmRadio1.setInstructions("At my work, I feel bursting with energy.");
//                    esmRadio1.setTitle("General Survey (1/21)");
//
//                    ESM_Radio esmRadio2 = new ESM_Radio();
//                    esmRadio2.setInstructions("At my job, I feel strong and vigorous.");
//                    esmRadio2.setTitle("General Survey (2/21)");
//
//
//                    ESM_Radio esmRadio3 = new ESM_Radio();
//                    esmRadio3.setInstructions("I am enthusiastic about my job.");
//                    esmRadio3.setTitle("General Survey (3/21)");
//
//                    ESM_Radio esmRadio4 = new ESM_Radio();
//                    esmRadio4.setInstructions("My job inspires me.");
//                    esmRadio4.setTitle("General Survey (4/21)");
//
//
//                    ESM_Radio esmRadio5 = new ESM_Radio();
//                    esmRadio5.setInstructions("When I get up in the morning I feel like going to work.");
//                    esmRadio5.setTitle("General Survey (5/21)");
//
//                    ESM_Radio esmRadio6 = new ESM_Radio();
//                    esmRadio6.setInstructions("I feel happy when I am working intensively.");
//                    esmRadio6.setTitle("General Survey (6/21)");
//
//                    ESM_Radio esmRadio7 = new ESM_Radio();
//                    esmRadio7.setInstructions("I am proud of the work that I do.");
//                    esmRadio7.setTitle("General Survey (7/21)");
//
//
//                    ESM_Radio esmRadio8 = new ESM_Radio();
//                    esmRadio8.setInstructions("I am immersed in my work.");
//                    esmRadio8.setTitle("General Survey (8/21)");
//
//                    ESM_Radio esmRadio9 = new ESM_Radio();
//                    esmRadio9.setInstructions("I get carried away when I am working.");
//                    esmRadio9.setTitle("General Survey (9/21)");
//
//
//                    ESM_Radio esmRadio10 = new ESM_Radio();
//                    esmRadio10.setInstructions("I love teaching.");
//                    esmRadio10.setTitle("General Survey (10/21)");
//
//
//                    ESM_Radio esmRadio11 = new ESM_Radio();
//                    esmRadio11.setInstructions("I am excited about teaching.");
//                    esmRadio11.setTitle("General Survey (11/21)");
//
//
//                    ESM_Radio esmRadio12 = new ESM_Radio();
//                    esmRadio12.setInstructions("I feel happy while teaching.");
//                    esmRadio12.setTitle("General Survey (12/21)");
//
//
//                    ESM_Radio esmRadio13 = new ESM_Radio();
//                    esmRadio13.setInstructions("I find teaching fun.");
//                    esmRadio13.setTitle("General Survey (13/21)");
//
//
//                    ESM_Radio esmRadio14 = new ESM_Radio();
//                    esmRadio14.setInstructions("While teaching I pay a lot of attention to my work.");
//                    esmRadio14.setTitle("General Survey (14/21)");
//
//
//                    ESM_Radio esmRadio15 = new ESM_Radio();
//                    esmRadio15.setInstructions("While teaching I really throw myself into my work.");
//                    esmRadio15.setTitle("General Survey (13/17)");
//
//
//                    ESM_Radio esmRadio16 = new ESM_Radio();
//                    esmRadio16.setInstructions("While teaching, I work with intensity.");
//                    esmRadio16.setTitle("General Survey (16/21)");
//
//
//                    ESM_Radio esmRadio17 = new ESM_Radio();
//                    esmRadio17.setTitle("General Survey (17/21)")
//                            .setInstructions("I try my hardest to perform well while teaching.");
//
//                    ESM_Radio esmRadio18 = new ESM_Radio();
//                    esmRadio18.setTitle("General Survey (18/21)")
//                            .setInstructions("In class, I care about the problems of my students.");
//
//
//                    ESM_Radio esmRadio19 = new ESM_Radio();
//                    esmRadio19.setTitle("General Survey (19/21)")
//                            .setInstructions("In class, I am empathetic towards my students.");
//
//
//                    ESM_Radio esmRadio20 = new ESM_Radio();
//                    esmRadio20.setTitle("General Survey (20/21)")
//                            .setInstructions("In class, I am aware of my student's feelings.");
//
//
//                    ESM_Radio esmRadio21 = new ESM_Radio();
//                    esmRadio21.setTitle("General Survey (21/21)")
//                            .setInstructions("In class, I show warmth to my students.");
//
//
//                    ArrayList<ESM_Radio> esms = new ArrayList<>();
//                    esms.add(esmRadio1);
//                    esms.add(esmRadio2);
//                    esms.add(esmRadio3);
//                    esms.add(esmRadio4);
//                    esms.add(esmRadio5);
//                    esms.add(esmRadio6);
//                    esms.add(esmRadio7);
//                    esms.add(esmRadio8);
//                    esms.add(esmRadio9);
//                    esms.add(esmRadio10);
//                    esms.add(esmRadio11);
//                    esms.add(esmRadio12);
//                    esms.add(esmRadio13);
//                    esms.add(esmRadio14);
//                    esms.add(esmRadio15);
//                    esms.add(esmRadio16);
//                    esms.add(esmRadio17);
//                    esms.add(esmRadio18);
//                    esms.add(esmRadio19);
//                    esms.add(esmRadio20);
//                    esms.add(esmRadio21);
//
//                    for (ESM_Radio esmRadio : esms) {
//                        esmRadio.addRadio("Always")
//                                .addRadio("Very Often")
//                                .addRadio("Often")
//                                .addRadio("Sometimes")
//                                .addRadio("Rareley")
//                                .addRadio("Almost Never")
//                                .addRadio("Never")
//                                .setSubmitButton("Next");
//                    }
//                    esmRadio21.setSubmitButton("Finish");
//
//                    factory.addESM(esmRadio1);
//                    factory.addESM(esmRadio2);
//                    factory.addESM(esmRadio3);
//                    factory.addESM(esmRadio4);
//                    factory.addESM(esmRadio5);
//                    factory.addESM(esmRadio6);
//                    factory.addESM(esmRadio7);
//                    factory.addESM(esmRadio8);
//                    factory.addESM(esmRadio9);
//                    factory.addESM(esmRadio10);
//                    factory.addESM(esmRadio11);
//                    factory.addESM(esmRadio12);
//                    factory.addESM(esmRadio13);
//                    factory.addESM(esmRadio14);
//                    factory.addESM(esmRadio15);
//                    factory.addESM(esmRadio16);
//                    factory.addESM(esmRadio17);
//                    factory.addESM(esmRadio18);
//                    factory.addESM(esmRadio19);
//                    factory.addESM(esmRadio20);
//                    factory.addESM(esmRadio21);
//
//
//                    ESM.queueESM(getApplicationContext(), factory.build());
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

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

        //Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem choose_account = menu.findItem(R.id.choose_account);
        MenuItem manage_account = menu.findItem(R.id.manage_account);
        MenuItem log_out = menu.findItem(R.id.log_out);

        if(dbHelper.getUsersCount() < 2)
        {
            choose_account.setVisible(false);
            log_out.setVisible(false);
        }
        else
        {
            choose_account.setVisible(true);
            if(UserData._username == null){
                log_out.setVisible(false);
            }

            log_out.setVisible(true);
        }

        if(UserData._username != null){
            manage_account.setVisible(true);
        }else{
            manage_account.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            //Respond to a click on "Create Account" menu option
            case R.id.add_account:
                if(dbHelper.getUsersCount() < 1) {
                    startActivity(new Intent(this, RegisterFormActivity.class));
                    finish();
                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AgreementFormActivity.this);
                    alertDialog.setTitle("ADMIN PASSWORD");
                    alertDialog.setMessage(" Please enter Admin Password to create a new user");

                    final EditText input = new EditText(getApplicationContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setTextColor(Color.BLACK);
                    input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    input.setGravity(Gravity.CENTER);
                    alertDialog.setView(input);
                    alertDialog.setIcon(R.drawable.key);

                    alertDialog.setNegativeButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    password = input.getText().toString();
                                    if (admin_password.equals(password)) {
                                        Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), RegisterFormActivity.class);
                                        startActivityForResult(intent, 0);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Password! Cannot create new user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    alertDialog.setPositiveButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }

                return true;

            //Respond to a click on "Choose Account" menu option
            case R.id.choose_account:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AgreementFormActivity.this);
                alertDialog.setTitle("ADMIN PASSWORD");
                alertDialog.setMessage(" Please enter Admin Password to choose a user");

                final EditText input = new EditText(getApplicationContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setTextColor(Color.WHITE);
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                input.setGravity(Gravity.CENTER);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.key);

                alertDialog.setNegativeButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                password = input.getText().toString();
                                if (admin_password.equals(password)) {
                                    Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ChooseAccountActivity.class);
                                    startActivityForResult(intent, 0);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Wrong Password! You cannot choose account.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                alertDialog.setPositiveButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

                return true;

            // Respond to a click on the "Manage Account" menu option
            case R.id.manage_account:
                i = new Intent (this, ViewRegistrationFormActivity.class);
                startActivity(i);
                finish();
                return true;

            // Respond to a click on the "E4 settings" menu option
            case R.id.action_e4_settings:
                i = new Intent (this, EmpaticaActivity.class);
                startActivity(i);
                return true;


            //Respond to a click on the "Log out" menu option
            case R.id.log_out:

                UserData._username = null;
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;


            //Respond to a click on the "Help" menu option
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (HomeActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
