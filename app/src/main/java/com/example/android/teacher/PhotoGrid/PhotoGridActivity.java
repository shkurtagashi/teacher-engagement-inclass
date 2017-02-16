package com.example.android.teacher.PhotoGrid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.teacher.HomeActivity;
import com.example.android.teacher.R;
import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.PamDatabase.PAMClass;

import java.util.Date;

public class PhotoGridActivity extends AppCompatActivity {

    public DatabaseHelper teacherDBhelper;
    public String android_id;
    public String imageDescription;
    public GridView gridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        teacherDBhelper = DatabaseHelper.getInstance(getApplicationContext());
        //teacherDBhelper = new DatabaseHelper(this);

        gridView = (GridView)findViewById(R.id.gridview);

        //Perform the vibration

        //setVibrationPattern();

        //Ask attendance question before the gridview
        //setAttendanceDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoGridActivity.this);
        builder.setMessage(R.string.attend_text)
                .setCancelable(true)
                .setPositiveButton(R.string.attend_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.attend_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        //startActivity(i);
                    }
                });
        AlertDialog refuseDialog = builder.create();
        refuseDialog.setTitle(R.string.attend_title);
        refuseDialog.show();

        //Set up PAM gridview
        //setUpPAM();
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        imageDescription = "Afraid";
                        break;
                    case 1:
                        imageDescription = "Tense";
                        break;
                    case 2:
                        imageDescription = "Excited";
                        break;
                    case 3:
                        imageDescription = "Delighted";
                        break;
                    case 4:
                        imageDescription = "Frustrated";
                        break;
                    case 5:
                        imageDescription = "Angry";
                        break;
                    case 6:
                        imageDescription = "Happy";
                        break;
                    case 7:
                        imageDescription = "Glad";
                        break;
                    case 8:
                        imageDescription = "Miserable";
                        break;
                    case 9:
                        imageDescription = "Sad";
                        break;
                    case 10:
                        imageDescription = "Calm";
                        break;
                    case 11:
                        imageDescription = "Satisfied";
                        break;
                    case 12:
                        imageDescription = "Gloomy";
                        break;
                    case 13:
                        imageDescription = "Tired";
                        break;
                    case 14:
                        imageDescription = "Sleepy";
                        break;
                    case 15:
                        imageDescription = "Serene";
                        break;
                    default:
                        imageDescription = "Error!";
                        break;
                }


                //save the PAM in database
                //Android Id instead of username to store the data about each teacher
                String course = teacherDBhelper.getCourseTitle(android_id);
                PAMClass pam = new PAMClass();
                Date date = new Date();


                //pam._id = ID;
                pam._course = course;
                pam._date = date.toString();
                pam._android_id = android_id;
                pam._pam_answer = imageDescription;

                teacherDBhelper.addPAM(pam);

                Toast.makeText(getApplicationContext(), "PAM choice saved successfully!", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Thank you for your participation!", Toast.LENGTH_SHORT).show();

                finish();

            }
        });

    }

    public void setVibrationPattern(){
        Vibrator mVibrator  = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        int vsLength = 400;
        int bsLength = 100;
        int bbLength = 1000;
        long[] vPattern = {
                0,  vsLength, bsLength, vsLength, bsLength, vsLength,
                bbLength,
                vsLength, bsLength, vsLength, bsLength, vsLength
        };
        mVibrator.vibrate(vPattern, -1);

    }


    public void setAttendanceDialog(){

    }


    public void setUpPAM(){
        //PAM gridview


    }
}
