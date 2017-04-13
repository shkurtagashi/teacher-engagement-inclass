package com.example.android.teacher.Sensors.RealtimeFragments;


import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.teacher.EmpaticaE4.EmpaticaService;
import com.example.android.teacher.R;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.example.android.teacher.R.id.startSessionButton2;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Button startSessionButton;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        startSessionButton = (Button) rootView.findViewById(R.id.startSessionButton);

        setUpStartSessionButton();

        return rootView;
    }


    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.example.android.teacher.EmpaticaE4.EmpaticaService".equals(service.service.getClassName())) {
                Log.v("Service Running", "trueee");
                return true;
            }
        }
        Log.v("Service Running", "falsee");
        return false;
    }

    public void setUpStartSessionButton() {
        final Intent i = new Intent(getActivity().getApplicationContext(), EmpaticaService.class);

        startSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isServiceRunning()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to stop session?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //STOP SERVICE
                                    getActivity().stopService(i);
                                    startSessionButton.setBackgroundResource(R.drawable.circled_play);
                                    getActivity().findViewById(R.id.startSessionButton2).setBackgroundResource(R.drawable.circled_play);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog disagreeAlertDialog = builder.create();
                    disagreeAlertDialog.show();
                }else{
                    getActivity().startService(i);
                    startSessionButton.setBackgroundResource(R.drawable.stop4);
                    getActivity().findViewById(R.id.startSessionButton2).setBackgroundResource(R.drawable.stop4);

                }
            }
        });
    }

}
