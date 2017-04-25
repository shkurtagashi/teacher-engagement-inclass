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
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

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


    public AccelFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accel, container, false);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accXReceiver), new IntentFilter(EmpaticaService.ACCX_RESULT));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accYReceiver), new IntentFilter(EmpaticaService.ACCY_RESULT));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((accZReceiver), new IntentFilter(EmpaticaService.ACCZ_RESULT));


//        xValueTextView = (TextView) rootView.findViewById(R.id.x_value);
//        yValueTextView = (TextView) rootView.findViewById(R.id.y_value);
//        zValueTextView = (TextView) rootView.findViewById(R.id.z_value);

        accXseries = new LineGraphSeries<DataPoint>();
        accYseries = new LineGraphSeries<DataPoint>();
        accZseries = new LineGraphSeries<DataPoint>();

        setUpACCGraph(rootView);

        return rootView;

    }

    BroadcastReceiver accXReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> xList = intent.getStringArrayListExtra(EmpaticaService.ACC_X);

            for (int i = 0; i < xList.size(); i++) {
                accZseries.appendData(new DataPoint(lastZacc++, Double.parseDouble(xList.get(i))), false, 10);
            }
        }
    };

    BroadcastReceiver accYReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> yList = intent.getStringArrayListExtra(EmpaticaService.ACC_Y);

            for (int i = 0; i < yList.size(); i++) {
                accZseries.appendData(new DataPoint(lastZacc++, Double.parseDouble(yList.get(i))), false, 10);


            }
        }
    };

    BroadcastReceiver accZReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> zList = intent.getStringArrayListExtra(EmpaticaService.ACC_Z);

            for (int i = 0; i < zList.size(); i++) {
                accZseries.appendData(new DataPoint(lastZacc++, Double.parseDouble(zList.get(i))), false, 10);
            }
        }
    };



    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(accZReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(accYReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(accXReceiver);

        super.onDestroy();
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

