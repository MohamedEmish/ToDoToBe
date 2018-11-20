package com.example.amosh.todotobe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class SummaryChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_chart);

        GraphView graph = (GraphView) findViewById(R.id.summary_graph_view);

        BarGraphSeries<DataPoint> overdue = new BarGraphSeries<>(getDataPointOverdue());
        BarGraphSeries<DataPoint> snoozed = new BarGraphSeries<>(getDataPointSnoozed());
        BarGraphSeries<DataPoint> completed = new BarGraphSeries<>(getDataPointCompleted());


        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(30);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(12);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

        graph.getGridLabelRenderer().setNumHorizontalLabels(9);
        graph.getGridLabelRenderer().setNumVerticalLabels(0);
        graph.getGridLabelRenderer().setVerticalLabelsColor(0);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(0);


        graph.addSeries(overdue);
        graph.addSeries(snoozed);
        graph.addSeries(completed);

        overdue.setSpacing(70);
        snoozed.setSpacing(70);
        completed.setSpacing(70);


        overdue.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return getResources().getColor(R.color.light_purple);
            }
        });

        snoozed.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return getResources().getColor(R.color.orange);
            }
        });

        completed.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return getResources().getColor(R.color.green);
            }
        });


    }

    private DataPoint[] getDataPointOverdue() {
        DataPoint[] dp = new DataPoint[]
                {
                        new DataPoint(1, 8),
                        new DataPoint(4, 6),
                        new DataPoint(7, 7),
                        new DataPoint(10, 9),
                        new DataPoint(13, 5),
                        new DataPoint(16, 3),
                        new DataPoint(19, 6),
                        new DataPoint(22, 6),
                        new DataPoint(25, 11),
                        new DataPoint(28, 9)


                };
        return (dp);

    }

    private DataPoint[] getDataPointSnoozed() {
        DataPoint[] dp = new DataPoint[]
                {
                        new DataPoint(1, 7),
                        new DataPoint(4, 5),
                        new DataPoint(7, 6),
                        new DataPoint(10, 8),
                        new DataPoint(13, 4),
                        new DataPoint(16, 2),
                        new DataPoint(19, 5),
                        new DataPoint(22, 5),
                        new DataPoint(25, 10),
                        new DataPoint(28, 8)


                };
        return (dp);

    }

    private DataPoint[] getDataPointCompleted() {
        DataPoint[] dp = new DataPoint[]
                {
                        new DataPoint(1, 4),
                        new DataPoint(4, 4),
                        new DataPoint(7, 5),
                        new DataPoint(10, 7),
                        new DataPoint(13, 4),
                        new DataPoint(16, 1.5),
                        new DataPoint(19, 2),
                        new DataPoint(22, 4.5),
                        new DataPoint(25, 6),
                        new DataPoint(28, 5)


                };
        return (dp);

    }
}