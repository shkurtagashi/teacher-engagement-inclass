package com.example.android.teacher.Sensors.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.teacher.R;
import com.example.android.teacher.data.Sensors.TemperatureSensor;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.components.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempFragment extends Fragment{

    DatabaseHelper teacherDbHelper;
    List<TemperatureSensor> tempValues;
    TextView avgTempValue;

    LineChart chart;

    Button saveTempChart;


    public TempFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_temp, container, false);
        avgTempValue = (TextView) rootView.findViewById(R.id.temperature_value);
        chart = (LineChart) rootView.findViewById(R.id.temp_chart);
        saveTempChart = (Button) rootView.findViewById(R.id.save_temp_chart);


        teacherDbHelper = new DatabaseHelper(getContext());
        tempValues = teacherDbHelper.getAllTempSensorValues();

        setUpTempChart(tempValues);
        setUpSaveGraphButton();

        avgTempValue.setText(calculateAverage(tempValues));

        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    public void setUpTempChart(List<TemperatureSensor> tempSensorValues){
        if(tempSensorValues.size() == 0){
            chart.setNoDataText("No data to be shown");
        }else {
            List<Entry> entries = new ArrayList<Entry>();

            for (TemperatureSensor temp : tempSensorValues) {
                // turn your data into Entry objects
                entries.add(new Entry(Float.parseFloat(String.valueOf(temp.getTimestamp())), temp.getValue()));
                //Log.v("bitchhhhhh", eda.getValue() + "");
            }

            LineDataSet dataSet = new LineDataSet(entries, "Skin Temperature value through Time for session on: date"); // add entries to dataset

            dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(Color.parseColor("#4CAF50"));
            dataSet.setColor(Color.parseColor("#4CAF50"));
            dataSet.setDrawCircles(false);
            //dataSet.setCircleColor(Color.BLUE);
            //dataSet.setCircleRadius(3f);
//            dataSet.setDrawCircleHole(true);
//            dataSet.setCircleColorHole(dataSet.getColor());
//




//            dataSet.enableDashedLine(10f, 5f, 0f);
//            dataSet.enableDashedHighlightLine(10f, 5f, 0f);
            dataSet.setLineWidth(1f);
            dataSet.setValueTextSize(9f);
            dataSet.setFormLineWidth(1f);
//            dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            dataSet.setFormSize(15.f);
            LineData lineData = new LineData(dataSet);
            Description description = new Description();
            description.setText("Temperature sensor data from the last session");
            chart.setData(lineData);
            chart.getDescription().setEnabled(false);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            //chart.setBackgroundColor(Color.LTGRAY);
            chart.setTouchEnabled(true);
            chart.animateX(5000);
            chart.setHorizontalScrollBarEnabled(true);


            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis yAxisLeft = chart.getAxisLeft();
            YAxis yAxisRight = chart.getAxisRight();

            yAxisRight.setEnabled(false);

            yAxisLeft.setAxisMinimum(0f); // start at zero
            yAxisLeft.setAxisMaximum(100f); // the axis maximum is 100


            // get the legend (only possible after setting data)
            Legend l = chart.getLegend();
            l.setForm(Legend.LegendForm.LINE);

            chart.invalidate();
        }





    }

    public void setUpSaveGraphButton(){
        saveTempChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart.saveToGallery("temp_chart", 100);
                Toast.makeText(getContext(), "The Temperature graph was successfully saved on your phone's Gallery.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String calculateAverage(List<TemperatureSensor> tempSensorValues) {
        Float sum = 0.0f;
        String avg = " ";


        List<Float> values = new ArrayList<Float>();

        for (TemperatureSensor temp : tempSensorValues) {
            values.add(temp.getValue());
        }

        if(!tempSensorValues.isEmpty()) {
            for (Float eda : values) {
                sum += eda;
            }
            avg = sum/tempSensorValues.size() + " ";
            avg = avg.substring(0,5);
            return avg;
        }

        return "N/A";
    }

}
