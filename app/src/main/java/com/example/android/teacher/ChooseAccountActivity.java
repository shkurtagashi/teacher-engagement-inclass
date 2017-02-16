package com.example.android.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.teacher.data.DatabaseHelper;
import com.example.android.teacher.data.User.User;
import com.example.android.teacher.data.User.UserData;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.button;
import static android.R.attr.layout_marginBottom;
import static android.R.attr.layout_marginLeft;
import static android.R.attr.layout_marginRight;
import static android.R.attr.paddingBottom;
import static android.R.attr.paddingTop;
import static android.R.attr.password;
import static android.R.id.list;
import static java.security.AccessController.getContext;

public class ChooseAccountActivity extends AppCompatActivity{

//    LinearLayout linearLayout;
//    List<User> users;

//    Button userButton;
//    String admin_password = "admin";
//    String password = "";
//    public User user;

    ListView profilesList;
    ArrayAdapter<String> adapter;
    DatabaseHelper dbHelper;
    String androidID;
    ArrayList<String> usernameList;
    TextView usernameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

        dbHelper = new DatabaseHelper(this);


        //Populate clickable list on click of the "Search" button
        profilesList = (ListView)findViewById(R.id.profiles_list);
        profilesList.setClickable(true);

//        user = new User();

//        linearLayout = (LinearLayout) findViewById(R.id.users_layout);

        usernameList = new ArrayList<String>();
        for(User user: dbHelper.getAllUsers()){
            usernameList.add(user._username);
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.black_item_list,usernameList);
        profilesList.setAdapter(adapter);
        profilesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData._username = profilesList.getItemAtPosition(position).toString();
                Log.v("Choose Activity", UserData._username);

                User registration = new User();
                for(User reg: dbHelper.getAllUsers()){
                    if(reg._username.equals(UserData._username)){
                        registration = reg;
                    }
                }
                UserData._selectedCourses = registration._courses;
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();
            }
        });




//        for (User u : users) {
//            userButton = createButton();
//            userButton.setText(u.getUsername());
//            user.setCourse(u.getCourse());
//            linearLayout.addView(userButton);
//        }
//
//        View v;
//        for (int i = 0; i < linearLayout.getChildCount(); i++) {
//            v = linearLayout.getChildAt(i);
//            if (v instanceof Button) v.setOnClickListener(this);
//        }



    }


//    public Button createButton(){
//        Button button = new Button(this);
//        button.setWidth(linearLayout.getWidth());
//        button.setPadding(0, 60, 0, 60);
//
//        return button;
//    }

//    @Override
//    public void onClick(View v) {
//        Button b = (Button)v;
//        user.setUsername(b.getText().toString());
//        final User u = dbHelper.getUserInformation(user.getUsername());
//
//        if(dbHelper.getUsersCount() <= 1){
//            if(u.getAgreement().equals("Yes")){
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivityForResult(intent, 0);
//            }else{
//                Intent intent = new Intent(getApplicationContext(), AgreementFormActivity.class);
//                startActivityForResult(intent, 0);
//            }
//
//        }else{
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChooseAccountActivity.this);
//            alertDialog.setTitle("ADMIN PASSWORD");
//            alertDialog.setMessage("Enter Admin Password");
//
//            final EditText input = new EditText(getApplicationContext());
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
//            input.setLayoutParams(lp);
//            input.setTextColor(Color.BLACK);
//            input.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            input.setGravity(Gravity.CENTER);
//            alertDialog.setView(input);
//            alertDialog.setIcon(R.drawable.key);
//
//            alertDialog.setNegativeButton(R.string.yes,
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            password = input.getText().toString();
//                            if (admin_password.equals(password)) {
//                                Log.v("ChooseAccountActivity", user.getUsername());
//                                Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
//                                if(u.getAgreement().equals("Yes")){
//                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                                    startActivityForResult(intent, 0);
//                                }else{
//                                    Intent intent = new Intent(getApplicationContext(), AgreementFormActivity.class);
//                                    startActivityForResult(intent, 0);
//                                }
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Wrong Password! Cannot log in as user: " + userButton.getText().toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//            alertDialog.setPositiveButton(R.string.no,
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//            alertDialog.show();
//        }
//
//
//
//
//    }
}
