package com.example.android.teacher.Sensors.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.teacher.R;
import com.example.android.teacher.data.Sensors.BloodVolumePressureSensor;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.example.android.teacher.data.Sensors.EdaSensor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodVolumeFragment extends Fragment {

    DatabaseHelper teacherDbHelper;
    List<BloodVolumePressureSensor> bvpValues;

    LineChart chart;

    Button saveBvpChart;



    public BloodVolumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blood_volume, container, false);
        chart = (LineChart) rootView.findViewById(R.id.bvp_chart);
        saveBvpChart = (Button) rootView.findViewById(R.id.save_bvp_chart);

        teacherDbHelper = new DatabaseHelper(getContext());
        bvpValues = teacherDbHelper.getAllBvpSensorValues();


//        if(teacherDbHelper.isLastSession()){
//            bvpValues = teacherDbHelper.getAllBvpSensorValues();
//        }else{
//            bvpValues = teacherDbHelper.getLastBVPSensorValues();
//        }

        setUpBvpGraph(bvpValues);
        setUpSaveButton();

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void setUpBvpGraph(List<BloodVolumePressureSensor> bvpSensorValues){
        if(bvpSensorValues.isEmpty()){
            chart.setNoDataText("No data to be shown");
        }else{

            List<Entry> entries = new ArrayList<Entry>();

            for (BloodVolumePressureSensor bvp : bvpSensorValues) {
                entries.add(new Entry(Float.parseFloat(String.valueOf(bvp.getTimestamp())), bvp.getValue()));
            }

            LineDataSet dataSet = new LineDataSet(entries, "BVP through time for session on: " + EdaSensor.date); // add entries to dataset

            dataSet.setColor(Color.RED);
            dataSet.setDrawCircles(false);
            dataSet.setDrawFilled(false);
            dataSet.setValueTextColor(6);

            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.getDescription().setEnabled(false);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            //chart.setBackgroundColor(Color.parseColor("#E3F2FD"));
            chart.setTouchEnabled(true);
            chart.animateX(5000);
            chart.setHorizontalScrollBarEnabled(true);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis yAxisLeft = chart.getAxisLeft();
            YAxis yAxisRight = chart.getAxisRight();

            yAxisRight.setEnabled(false);

//            yAxisLeft.setAxisMinimum(0f); // start at zero
//            yAxisLeft.setAxisMaximum(4f); // the axis maximum is 100


            // get the legend (only possible after setting data)
            Legend l = chart.getLegend();

            // modify the legend ...
            l.setForm(Legend.LegendForm.LINE);

            chart.invalidate();


        }
    }

    public void setUpSaveButton(){
        saveBvpChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

                chart.saveToGallery(formattedDate + "_bvp", 100);
                Toast.makeText(getContext(), "The Blood Volume Pressure graph was successfully saved on your phone's Gallery.", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
