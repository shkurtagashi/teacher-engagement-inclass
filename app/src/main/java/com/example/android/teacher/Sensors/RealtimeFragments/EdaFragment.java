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
import android.widget.Toast;

import com.example.android.teacher.EmpaticaE4.EmpaticaService;
import com.example.android.teacher.R;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.Sensors.EdaSensor;
import com.example.android.teacher.data.Sensors.TemperatureSensor;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.android.teacher.EmpaticaE4.EmpaticaService.EDA_BUFFER_CAPACITY;
import static com.example.android.teacher.R.id.chart;
import static com.example.android.teacher.R.id.eda;

/**
 * A simple {@link Fragment} subclass.
 */
public class EdaFragment extends Fragment {

    Calendar calendar;

    DatabaseHelper teacherDbHelper;


    private LineGraphSeries<DataPoint> edaSeries;
    private double lastXeda = 0;

    //TextView show data
//    private TextView edaValue;
//    BroadcastReceiver edaReceiver;
    BroadcastReceiver statusReceiver;

//    String statusOfDevice;

//    List<String> edaValuesOther;
//    int lastEdaRead = 0;

    public EdaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        calendar = Calendar.getInstance();

        LocalBroadcastManager.getInstance(getContext()).registerReceiver((edaReceiver), new IntentFilter(EmpaticaService.EDA_RESULT));


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_eda, container, false);

        teacherDbHelper = new DatabaseHelper(getContext());

//        edaValue = (TextView) rootView.findViewById(R.id.eda_value);
        edaSeries = new LineGraphSeries<DataPoint>();

        //edaValuesOther = new ArrayList<String>();

        setUpEDAGraph(rootView);

        return rootView;
    }

    BroadcastReceiver edaReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ArrayList<String> edaList = intent.getStringArrayListExtra(EmpaticaService.EDA);

            Log.v("EDA FRAGMENT", edaList.size() + "");

            for (int i = 0; i < edaList.size(); i++) {
                edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(edaList.get(i))), false, 10);
            }
        }
    };



    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(edaReceiver);
        super.onDestroy();
    }


                //edaValuesOther.add(eda);

//                mTimer1 = new Runnable() {
//                    @Override
//                    public void run() {
//                        edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(eda)), false, 10);
//                    }
//                };
//                mHandler.postDelayed(mTimer1, 1000);


//                if(eda != null) {
//                    edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(eda)), false, 10);
//                }

//                    updateLabel(edaValue, eda);

//                ArrayList<String> edaList = intent.getStringArrayListExtra(EmpaticaService.EDA);
//
//                Log.v("EDA FRAGMENT", edaList.size() + "");
//
//                if (!edaList.isEmpty()) {
//                    for (int i = 0; i < edaList.size(); i++) {
//                       // edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(edaList.get(i))), false, 10);
//                        updateLabel(edaValue, edaList.get(i));
//                    }
//                }


//        statusReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                statusOfDevice = intent.getStringExtra(EmpaticaService.DEVICE_STATUS);
//                Log.v("EdaFragg", statusOfDevice);
//
//                if(statusOfDevice.equals("CONNECTED")){
//                    Log.v("EdaFragg", "TRUEE");
//                    mTimer1 = new Runnable() {
//                        @Override
//                        public void run() {
//                            for(int i=lastEdaRead; i<lastEdaRead + 4; i++){
//                                edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(edaValuesOther.get(i))), false, 10);
//                                mHandler.postDelayed(mTimer1, 2000);
//                            }
//                            lastEdaRead+=4;
//                        }
//                    };
//                }else{
//                    Log.v("EdaFragg", "FALSEE");
//                    mHandler.removeCallbacks(mTimer1);
//                }
//            }
//        };

//        LocalBroadcastManager.getInstance(getContext()).registerReceiver((statusReceiver), new IntentFilter(EmpaticaService.STATUS_RESULT));


//        if(statusOfDevice != null && statusOfDevice.equals("CONNECTED")){
//            Log.v("EdaFragg", "TRUEE");
//            mTimer1 = new Runnable() {
//                @Override
//                public void run() {
//                    for(int i=lastEdaRead; i<lastEdaRead + 4; i++){
//                        edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(edaValuesOther.get(i))), false, 10);
//                        mHandler.postDelayed(mTimer1, 1000);
//                    }
//                    lastEdaRead+=4;
//                }
//            };
//        }else{
//            Log.v("EdaFragg", "FALSEE");
//            mHandler.removeCallbacks(mTimer1);
//        }





    public void setUpEDAGraph(View rootView) {
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.MINUTE, 1);
        Date d3 = calendar.getTime();

        GraphView edaGraph = (GraphView) rootView.findViewById(R.id.graphforeda);
//        edaSeries = new LineGraphSeries<DataPoint>();
        edaSeries.setTitle("Electrodermal Activity");
//        edaGraph.getLegendRenderer().setVisible(true);
//        edaGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//        edaGraph.getLegendRenderer().setBackgroundColor(Color.parseColor("#EEEEEE"));
        edaSeries.setColor(Color.parseColor("#03A9F4"));
        edaSeries.setDrawBackground(true);
        edaSeries.setBackgroundColor(Color.parseColor("#B3E5FC"));
        edaSeries.setDrawDataPoints(true);
        edaSeries.setDataPointsRadius(10);
        edaSeries.setThickness(8);
        edaGraph.addSeries(edaSeries);
//        edaGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
//        edaGraph.getGridLabelRenderer().setNumHorizontalLabels(10); // only 4 because of the space
//        // set manual x bounds to have nice steps
//        edaGraph.getViewport().setMinX(d1.getTime());
//        edaGraph.getViewport().setMaxX(d3.getTime());
//        edaGraph.getViewport().setXAxisBoundsManual(true);
//
//        // as we use dates as labels, the human rounding to nice readable numbers
//        // is not necessary
//        edaGraph.getGridLabelRenderer().setHumanRounding(false);

        Viewport viewport = edaGraph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(4);
        viewport.setScrollable(true);

//        edaReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                final String eda = intent.getStringExtra(EmpaticaService.EDA);
//
//                mTimer1 = new Runnable() {
//                        @Override
//                        public void run() {
//                            edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(eda)), false, 10);
//                        }
//                    };
//                    mHandler.postDelayed(mTimer1, 2500);
//
//
////                if(eda != null) {
////                    edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(eda)), false, 10);
////                }
//
////                    updateLabel(edaValue, eda);
//
////                ArrayList<String> edaList = intent.getStringArrayListExtra(EmpaticaService.EDA);
////
////                Log.v("EDA FRAGMENT", edaList.size() + "");
////
////                if (!edaList.isEmpty()) {
////                    for (int i = 0; i < edaList.size(); i++) {
////                       // edaSeries.appendData(new DataPoint(lastXeda++, Double.parseDouble(edaList.get(i))), false, 10);
////                        updateLabel(edaValue, edaList.get(i));
////                    }
////                }
//            }
//        };
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
