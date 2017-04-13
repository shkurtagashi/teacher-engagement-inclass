package com.example.android.teacher.Sensors.RealtimeFragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.teacher.EmpaticaE4.EmpaticaService;
import com.example.android.teacher.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccelFragment extends Fragment {

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;

    private LineGraphSeries<DataPoint> accXseries;
    private LineGraphSeries<DataPoint> accYseries;
    private LineGraphSeries<DataPoint> accZseries;

    private double lastXacc = 0;
    private double lastYacc = 0;
    private double lastZacc = 0;

    private TextView xValueTextView;
    private TextView yValueTextView;
    private TextView zValueTextView;

    GraphView accGraph;

    BroadcastReceiver accXReceiver;
    BroadcastReceiver accYReceiver;
    BroadcastReceiver accZReceiver;


    public AccelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accel, container, false);

//        xValueTextView = (TextView) rootView.findViewById(R.id.x_value);
//        yValueTextView = (TextView) rootView.findViewById(R.id.y_value);
//        zValueTextView = (TextView) rootView.findViewById(R.id.z_value);

        accXseries = new LineGraphSeries<DataPoint>();
        accYseries = new LineGraphSeries<DataPoint>();
        accZseries = new LineGraphSeries<DataPoint>();

        setUpACCGraph(rootView);

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accXReceiver), new IntentFilter(EmpaticaService.ACCX_RESULT));
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accYReceiver), new IntentFilter(EmpaticaService.ACCY_RESULT));
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accZReceiver), new IntentFilter(EmpaticaService.ACCZ_RESULT));
    }

    @Override
    public void onResume(){
        super.onResume();

        accXReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String x = intent.getStringExtra(EmpaticaService.ACC_X);

//                mTimer1 = new Runnable() {
//                    @Override
//                    public void run() {
                        accXseries.appendData(new DataPoint(lastXacc++, Double.parseDouble(x)), false, 70); //64 used 10 for this
//                    }
//                };
//                mHandler.postDelayed(mTimer1, 4000);
            }
        };

        accYReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String y = intent.getStringExtra(EmpaticaService.ACC_Y);

               // mTimer1 = new Runnable() {
//                    @Override
//                    public void run() {
                        accYseries.appendData(new DataPoint(lastYacc++, Double.parseDouble(y)), false, 70);
//                    }
//                };
//                mHandler.postDelayed(mTimer1, 4000);

            }
        };

        accZReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String z = intent.getStringExtra(EmpaticaService.ACC_Z);

//                mTimer1 = new Runnable() {
//                    @Override
//                    public void run() {
                        accZseries.appendData(new DataPoint(lastZacc++, Double.parseDouble(z)), false, 70);
//                    }
//                };
//                mHandler.postDelayed(mTimer1, 4000);
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accXReceiver), new IntentFilter(EmpaticaService.ACCX_RESULT));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accYReceiver), new IntentFilter(EmpaticaService.ACCY_RESULT));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accZReceiver), new IntentFilter(EmpaticaService.ACCZ_RESULT));
    }

    @Override
    public void onStop(){
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(accZReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(accYReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(accXReceiver);

        super.onStop();
    }

    public void setUpACCGraph(View rootView) {
        accGraph = (GraphView) rootView.findViewById(R.id.graphforacc);

        accXseries.setTitle("X");
        accXseries.setColor(Color.parseColor("#F44336"));
        accXseries.setDrawDataPoints(true);
        accXseries.setDataPointsRadius(10);
        accXseries.setThickness(8);

        accYseries.setTitle("Y");
        accYseries.setColor(Color.parseColor("#4CAF50"));
        accYseries.setDrawDataPoints(true);
        accYseries.setDataPointsRadius(10);
        accYseries.setThickness(8);

        accZseries.setTitle("Z");
        accZseries.setColor(Color.parseColor("#FFC107"));
        accZseries.setDrawDataPoints(true);
        accZseries.setDataPointsRadius(10);
        accZseries.setThickness(8);

        accGraph.getLegendRenderer().setVisible(true);
        accGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        accGraph.getLegendRenderer().setBackgroundColor(Color.parseColor("#EEEEEE"));

        accGraph.addSeries(accXseries);
        accGraph.addSeries(accYseries);
        accGraph.addSeries(accZseries);


        Viewport accViewport = accGraph.getViewport();
        accViewport.setYAxisBoundsManual(true);
        accViewport.setMinY(-100);
        accViewport.setMaxY(100);
        accViewport.setScrollable(true);
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

