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
import com.example.android.teacher.data.Sensors.AccelereometerSensor;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.components.YAxis.AxisDependency;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccFragment extends Fragment {

    DatabaseHelper teacherDbHelper;
    List<AccelereometerSensor> accValues;

    LineChart chart;

    Button saveAccChart;


    public AccFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_acc, container, false);
        chart = (LineChart) rootView.findViewById(R.id.acc_chart);
        saveAccChart = (Button) rootView.findViewById(R.id.save_acc_chart);

        teacherDbHelper = new DatabaseHelper(getContext());
        if(teacherDbHelper.isLastSession()){
            accValues = teacherDbHelper.getAllAccSensorValues();
        }else{
            accValues = teacherDbHelper.getLastAccSensorValues();
        }
        setUpAccGraph(accValues);
        setUpSaveButton();


        return rootView;
    }


    public void setUpAccGraph(List<AccelereometerSensor> accSensorValues){
        if(accSensorValues.size() == 0){
            chart.setNoDataText("No data to be shown");
        }else{

            List<Entry> entriesX = new ArrayList<Entry>();
            List<Entry> entriesY = new ArrayList<Entry>();
            List<Entry> entriesZ = new ArrayList<Entry>();

            List<Entry> XaxisValues = new ArrayList<Entry>();


            for (AccelereometerSensor acc : accSensorValues) {
                entriesX.add(new Entry(Float.parseFloat(String.valueOf(acc.getTimestamp())), acc.getXvalue()));
                entriesY.add(new Entry(Float.parseFloat(String.valueOf(acc.getTimestamp())), acc.getYvalue()));
                entriesZ.add(new Entry(Float.parseFloat(String.valueOf(acc.getTimestamp())), acc.getZvalue()));
                XaxisValues.add(new Entry((Float.parseFloat(String.valueOf(acc.getTimestamp()))), Float.parseFloat(String.valueOf(acc.getTimestamp() + ""))));
            }

            LineDataSet dataSetX = new LineDataSet(entriesX, "X"); // add entries to dataset
            dataSetX.setAxisDependency(AxisDependency.LEFT);

            LineDataSet dataSetY = new LineDataSet(entriesY, "Y"); // add entries to dataset
            dataSetY.setAxisDependency(AxisDependency.LEFT);

            LineDataSet dataSetZ = new LineDataSet(entriesZ, "Z"); // add entries to dataset
            dataSetZ.setAxisDependency(AxisDependency.LEFT);


            dataSetX.setColor(Color.BLUE);
            dataSetX.setDrawCircles(false);
            dataSetX.setDrawFilled(false);
            dataSetX.setValueTextColor(Color.BLACK);


            dataSetY.setColor(Color.RED);
            dataSetY.setDrawCircles(false);
            dataSetY.setDrawFilled(false);
            dataSetY.setValueTextColor(Color.BLACK);

            dataSetZ.setColor(Color.YELLOW);
            dataSetZ.setDrawCircles(false);
            dataSetZ.setDrawFilled(false);
            dataSetZ.setValueTextColor(Color.BLACK);


            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(dataSetX);
            dataSets.add(dataSetY);
            dataSets.add(dataSetZ);

            LineData data = new LineData(dataSets);

            chart.setData(data);

            chart.getDescription().setEnabled(false);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            //chart.setBackgroundColor(Color.parseColor("#FCE4EC"));
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


             //get the legend (only possible after setting data)
            Legend l = chart.getLegend();
            l.setForm(Legend.LegendForm.LINE);


            chart.invalidate();

        }
    }

    public void setUpSaveButton(){
        saveAccChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

                chart.saveToGallery(formattedDate + "_acc", 100);
                Toast.makeText(getContext(), "The Accelereometer graph was successfully saved on your phone's Gallery.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
