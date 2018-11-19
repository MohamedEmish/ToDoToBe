package com.example.amosh.todotobe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SummaryChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_chart);

        BarChart barChart = (BarChart) findViewById(R.id.summary_bar_chart_graph);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(3f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(0f, 4));
        entries.add(new BarEntry(15f, 5));
        entries.add(new BarEntry(6f, 6));
        entries.add(new BarEntry(9f, 7));
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));

        BarDataSet barDataSet = new BarDataSet(entries, null);

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

        BarData data = new BarData(labels, barDataSet);

        barChart.setData(data);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);

        barChart.setDescription(null);


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
}