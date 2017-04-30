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
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class BvpFragment extends Fragment {

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;

    private LineGraphSeries<DataPoint> bvpSeries;
    private double lastXbvp = 0;


    GraphView bvpGraph;


    public BvpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((bvpReceiver), new IntentFilter(EmpaticaService.BVP_RESULT));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bvp, container, false);

        bvpGraph = (GraphView) rootView.findViewById(R.id.graphforbvp);
        bvpSeries = new LineGraphSeries<DataPoint>();

        setUpBVPGraph();

        return rootView;
    }

    BroadcastReceiver bvpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> bvpList = intent.getStringArrayListExtra(EmpaticaService.BVP);
            for (int i = 0; i < bvpList.size(); i++) {
                bvpSeries.appendData(new DataPoint(lastXbvp++, Double.parseDouble(bvpList.get(i))), false, 10);
            }
        }
    };


    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(bvpReceiver);
        super.onDestroy();
    }

    public void setUpBVPGraph() {

//        bvpSeries = new LineGraphSeries<DataPoint>();
        bvpSeries.setTitle("Blood Volume Pressure");
        bvpSeries.setColor(Color.parseColor("#FFE43F3F"));
        //bvpSeries.setDrawDataPoints(true);
        //bvpSeries.setDataPointsRadius(10);
        //bvpSeries.setThickness(8);
        // bvpSeries.setDrawBackground(true);
        //bvpSeries.setBackgroundColor(Color.parseColor("#FFCDD2"));
        bvpGraph.addSeries(bvpSeries);
        Viewport bvpViewport = bvpGraph.getViewport();
        bvpViewport.setYAxisBoundsManual(true);
        bvpViewport.setMinY(-700);
        bvpViewport.setMaxY(700);
        bvpViewport.setScrollable(true);

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
