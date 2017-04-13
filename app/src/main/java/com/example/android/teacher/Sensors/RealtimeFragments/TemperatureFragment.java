package com.example.android.teacher.Sensors.RealtimeFragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.teacher.EmpaticaE4.EmpaticaService;
import com.example.android.teacher.R;
import com.jjoe64.graphview.series.DataPoint;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;

    private TextView tempValueTextView;

    BroadcastReceiver tempReceiver;

    public TemperatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_temperature, container, false);
        tempValueTextView = (TextView) rootview.findViewById(R.id.temperature_value);

        //setUpTempGraph();

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver((tempReceiver), new IntentFilter(EmpaticaService.TEMP_RESULT));
    }

    @Override
    public void onResume(){
        super.onResume();

        tempReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String temp = intent.getStringExtra(EmpaticaService.TEMP);
                tempValueTextView.setText(temp);
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver((tempReceiver), new IntentFilter(EmpaticaService.TEMP_RESULT));
    }

    @Override
    public void onStop(){
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(tempReceiver);
        super.onStop();
    }


    // Update a label with some text, making sure this is run in the UI thread
    private void updateLabel(final TextView label, final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                label.setText(text);
            }
        });
    }

}
