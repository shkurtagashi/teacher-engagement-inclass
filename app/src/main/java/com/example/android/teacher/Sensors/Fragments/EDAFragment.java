package com.example.android.teacher.Sensors.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.teacher.R;
import com.example.android.teacher.data.Sensors.EdaSensor;
import com.example.android.teacher.data.LocalDataStorage.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.R.attr.entries;
import static com.example.android.teacher.R.id.eda;
import static com.example.android.teacher.data.Sensors.EdaSensor.date;
import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 */
public class EDAFragment extends Fragment {
    DatabaseHelper teacherDbHelper;
    List<EdaSensor> edaValues;

    LineChart chart;

    TextView minEdaValue;
    TextView maxEdaValue;
    TextView avgEdaValue;

    Button saveEdaChart;


    // Find Peaks for EDA and show them
    //Session duration?


    public EDAFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_eda_data, container, false);

        chart = (LineChart) rootView.findViewById(R.id.chart);

        minEdaValue = (TextView) rootView.findViewById(R.id.minEda_value);
        maxEdaValue = (TextView) rootView.findViewById(R.id.maxEda_value);
        avgEdaValue = (TextView) rootView.findViewById(R.id.avgEda_value);

        saveEdaChart = (Button) rootView.findViewById(R.id.save_eda_chart);

        teacherDbHelper = new DatabaseHelper(getContext());

        edaValues = teacherDbHelper.getAllEdaSensorValues();

        if(!teacherDbHelper.isLastSession()){
            edaValues = teacherDbHelper.getLastEdaSensorValues();
            Log.v("EDA FRAGGG", "FALSEE");
        }


        setUpEdaGraph(edaValues);
        setUpSaveEdaButton();

        minEdaValue.setText(min(edaValues));
        avgEdaValue.setText(calculateAverage(edaValues));
        maxEdaValue.setText(max(edaValues));

        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    public void setUpEdaGraph(List<EdaSensor> edaSensorValues){

        if(edaSensorValues.size() == 0){
            chart.setNoDataText("No data to be shown");
        }else{
            List<Entry> entries = new ArrayList<Entry>();

            for (EdaSensor eda : edaSensorValues) {
                entries.add(new Entry(Float.parseFloat(String.valueOf(eda.getTimestamp())), eda.getValue()));
            }

            LineDataSet dataSet = new LineDataSet(entries, "GSR value through Time for session on:" + date); // add entries to dataset
            dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(Color.parseColor("#FF80CBC4"));
            dataSet.setColor(Color.parseColor("#FF009688"));

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
//            Description description = new Description();
//            description.setText("EDA data from the last session");
            chart.setData(lineData);
            chart.getDescription().setEnabled(false);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            chart.setDrawGridBackground(false);
            //chart.setBackgroundColor(Color.parseColor("#E3F2FD"));
            chart.setTouchEnabled(true);
            chart.setPinchZoom(true);
            chart.setDoubleTapToZoomEnabled(true);
            chart.animateX(2000);
            chart.setHorizontalScrollBarEnabled(true);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis yAxisLeft = chart.getAxisLeft();
            YAxis yAxisRight = chart.getAxisRight();

            yAxisRight.setEnabled(false);

            yAxisLeft.setAxisMinimum(0f); // start at zero
            yAxisLeft.setAxisMaximum(4f); // the axis maximum is 100


            // get the legend (only possible after setting data)
            Legend l = chart.getLegend();

            // modify the legend ...
            l.setForm(Legend.LegendForm.LINE);
            chart.invalidate();


        }
    }

    public void setUpSaveEdaButton(){
        saveEdaChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                chart.saveToGallery(formattedDate + "_eda", 100);
                Toast.makeText(getContext(), "The EDA graph was successfully saved on your phone's Gallery.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String min(List<EdaSensor> edaSensorValues){
        String min = " ";

        List<Float> values = new ArrayList<Float>();

        for (EdaSensor eda : edaSensorValues) {
            values.add(eda.getValue());
        }

        if(!edaSensorValues.isEmpty()) {
            min = Collections.min(values) + "";
            min = min.substring(0,3);
            return min;

        }

        return "N/A";
    }


    public String max(List<EdaSensor> edaSensorValues){
        String max = " ";
        List<Float> values = new ArrayList<Float>();

        for (EdaSensor eda : edaSensorValues) {
            values.add(eda.getValue());
        }

        if(!edaSensorValues.isEmpty()) {
            max = Collections.max(values) + "";
            max = max.substring(0, 5);
            return max;
        }

        return "N/A";


    }

    public String calculateAverage(List<EdaSensor> edaSensorValues) {
        Float sum = 0.0f;
        String avg = " ";

        List<Float> values = new ArrayList<Float>();

        for (EdaSensor eda : edaSensorValues) {
            values.add(eda.getValue());
        }

        if(!edaSensorValues.isEmpty()) {
            for (Float eda : values) {
                sum += eda;
            }
            avg = sum/edaSensorValues.size() + " ";
            avg = avg.substring(0,5);
            return avg;
        }

        return "N/A";
    }


    /*
    * Converts Unix epoch time to human readable date
     */
//    public String convertEpochToReadableDate(String epoch){
//        epoch.replaceAll(".$", "");
//        int epochConverted = Integer.parseInt(epoch.substring(0,16));
//
//         return new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(epochConverted * 1000));
//    }







}
