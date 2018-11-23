package com.example.amosh.todotobe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class SummaryChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_chart);

        BarChart barChart = (BarChart) findViewById(R.id.summary_bar_chart_graph);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, new float[]{5, 2, 1}));
        entries.add(new BarEntry(2, new float[]{4.5f, 2, 1}));
        entries.add(new BarEntry(3, new float[]{5.5f, 2, 1}));
        entries.add(new BarEntry(4, new float[]{6.5f, 2, 2}));
        entries.add(new BarEntry(5, new float[]{4, 2, 1}));
        entries.add(new BarEntry(6, new float[]{3, 2, 1}));
        entries.add(new BarEntry(7, new float[]{3, 3, 1}));
        entries.add(new BarEntry(8, new float[]{5, 1, 1}));
        entries.add(new BarEntry(9, new float[]{6, 3, 1}));
        entries.add(new BarEntry(10, new float[]{5, 3, 1}));

        ArrayList<String> labels = new ArrayList<>();
        labels.add("1");
        labels.add("3");
        labels.add("6");
        labels.add("9");
        labels.add("12");
        labels.add("15");
        labels.add("18");
        labels.add("21");
        labels.add("24");
        labels.add("27");

        BarDataSet barDataSet = new BarDataSet(entries, null);

        int[] colorClassArray = new int[]{getResources().getColor(R.color.green),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.light_purple)};


        BarData data = new BarData(barDataSet);

        barChart.setData(data);

        barDataSet.setColors(colorClassArray);
        barDataSet.setDrawValues(false);

        barChart.animateY(2500);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);

        barChart.setDescription(null);

        XAxis xAxis = barChart.getXAxis();

        barChart.setFitBars(true);

        data.setBarWidth(0.2f);

        // Y left axis
        barChart.getAxisLeft().setEnabled(false);

        // Y right axis
        barChart.getAxisRight().setEnabled(false);

        // X axis

        barChart.getXAxis().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        // color guide
        barChart.getLegend().setEnabled(false);


    }

    private ArrayList<BarEntry> completed() {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(1, 5));
        entries.add(new BarEntry(2, 4));
        entries.add(new BarEntry(3, 5));
        entries.add(new BarEntry(4, 6));
        entries.add(new BarEntry(5, 4));
        entries.add(new BarEntry(6, 3));
        entries.add(new BarEntry(7, 3));
        entries.add(new BarEntry(8, 5));
        entries.add(new BarEntry(9, 7));
        entries.add(new BarEntry(10, 5));
        return entries;

    }
}