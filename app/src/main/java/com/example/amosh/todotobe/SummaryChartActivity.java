package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class SummaryChartActivity extends AppCompatActivity {
    String username;
    TextView usernameTxt, completedTxt, snoozedTxt, overduedTxt, monthTxt, yearTxt;
    MyUsersDbHelper usersDbHelper;
    List<Events> complete;
    List<Events> overdue;
    List<Events> snooze;

    String month;
    String year;

    int dayC1_2 = 0, dayC3_5 = 0, dayC6_8 = 0, dayC9_11 = 0, dayC12_14 = 0, dayC15_17 = 0, dayC18_20 = 0, dayC21_23 = 0, dayC24_26 = 0, dayC_27_31 = 0;
    int dayS1_2 = 0, dayS3_5 = 0, dayS6_8 = 0, dayS9_11 = 0, dayS12_14 = 0, dayS15_17 = 0, dayS18_20 = 0, dayS21_23 = 0, dayS24_26 = 0, dayS_27_31 = 0;
    int dayO1_2 = 0, dayO3_5 = 0, dayO6_8 = 0, dayO9_11 = 0, dayO12_14 = 0, dayO15_17 = 0, dayO18_20 = 0, dayO21_23 = 0, dayO24_26 = 0, dayO_27_31 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_chart);

        usersDbHelper = new MyUsersDbHelper(this);

        username = getIntent().getStringExtra("name");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");

        usernameTxt = (TextView) findViewById(R.id.summary_username);
        usernameTxt.setText(username);


        yearTxt = (TextView) findViewById(R.id.summary_year_text);
        monthTxt = (TextView) findViewById(R.id.summary_month_text);

        yearTxt.setText(year);
        monthTxt.setText(getMonthName(Integer.parseInt(month)));
        monthTxt.setAllCaps(true);


        overdue = usersDbHelper.readEventListStateNoDay(username, month, year, String.valueOf(EventsContract.EventsEntry.STATE_OVERDUE));
        overduedTxt = (TextView) findViewById(R.id.summary_overdue_text);
        overduedTxt.setText(String.valueOf(overdue.size()));

        complete = usersDbHelper.readEventListStateNoDay(username, month, year, String.valueOf(EventsContract.EventsEntry.STATE_COMPLETED));
        completedTxt = (TextView) findViewById(R.id.summary_completed_text);
        completedTxt.setText(String.valueOf(complete.size()));

        snooze = usersDbHelper.readEventListStateNoDay(username, month, year, String.valueOf(EventsContract.EventsEntry.STATE_SNOOZED));
        snoozedTxt = (TextView) findViewById(R.id.summary_snoozed_text);
        snoozedTxt.setText(String.valueOf(snooze.size()));

        FloatingActionButton overview = (FloatingActionButton) findViewById(R.id.summary_fab);
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overview = new Intent(SummaryChartActivity.this, OverviewActivity.class);
                overview.putExtra("name", username);
                startActivity(overview);
            }
        });

        ImageView menu_icon = (ImageView) findViewById(R.id.summary_menu_icon);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.summary_chart_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.summary_chart_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent homeActivity = new Intent(SummaryChartActivity.this, MainScreenActivity.class);
                        homeActivity.putExtra("name", username);
                        startActivity(homeActivity);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(SummaryChartActivity.this, OverviewActivity.class);
                        overviewActivity.putExtra("name", username);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(SummaryChartActivity.this, MyGroupsActivity.class);
                        groupsActivity.putExtra("name", username);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        Intent listsActivity = new Intent(SummaryChartActivity.this, ListsActivity.class);
                        String category = "";
                        listsActivity.putExtra("category", category);
                        listsActivity.putExtra("name", username);
                        startActivity(listsActivity);
                        break;
                    case R.id.nav_profile:
                        Intent profileActivity = new Intent(SummaryChartActivity.this, ProfileActivity.class);
                        profileActivity.putExtra("name", username);
                        startActivity(profileActivity);
                        break;
                    case R.id.nav_timeline:
                        Intent timelineActivity = new Intent(SummaryChartActivity.this, TimelineActivity.class);
                        timelineActivity.putExtra("name", username);
                        startActivity(timelineActivity);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(SummaryChartActivity.this, SettingsActivity.class);
                        settingsActivity.putExtra("name", username);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(SummaryChartActivity.this, SignInActivity.class);
                        SaveSharedPreference.clearUserName(SummaryChartActivity.this);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        ImageView close = (ImageView) header.findViewById(R.id.nav_head_close);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        for (int i = 0; i <= 31; i++) {
            List<Events> listC, listS, listO;
            listO = usersDbHelper.readEventList(username, String.valueOf(i), month, year, String.valueOf(EventsContract.EventsEntry.STATE_OVERDUE));
            listS = usersDbHelper.readEventList(username, String.valueOf(i), month, year, String.valueOf(EventsContract.EventsEntry.STATE_SNOOZED));
            listC = usersDbHelper.readEventList(username, String.valueOf(i), month, year, String.valueOf(EventsContract.EventsEntry.STATE_COMPLETED));
            switch (i) {
                case 1:
                    dayC1_2 += listC.size();
                    dayS1_2 += listS.size();
                    dayO1_2 += listO.size();
                    break;
                case 2:
                    dayC1_2 += listC.size();
                    dayS1_2 += listS.size();
                    dayO1_2 += listO.size();
                    break;
                case 3:
                    dayC3_5 += listC.size();
                    dayS3_5 += listS.size();
                    dayO3_5 += listO.size();
                    break;
                case 4:
                    dayC3_5 += listC.size();
                    dayS3_5 += listS.size();
                    dayO3_5 += listO.size();
                    break;
                case 5:
                    dayC3_5 += listC.size();
                    dayS3_5 += listS.size();
                    dayO3_5 += listO.size();
                    break;
                case 6:
                    dayC6_8 += listC.size();
                    dayS6_8 += listS.size();
                    dayO6_8 += listO.size();
                    break;
                case 7:
                    dayC6_8 += listC.size();
                    dayS6_8 += listS.size();
                    dayO6_8 += listO.size();
                    break;
                case 8:
                    dayC6_8 += listC.size();
                    dayS6_8 += listS.size();
                    dayO6_8 += listO.size();
                    break;
                case 9:
                    dayC9_11 += listC.size();
                    dayS9_11 += listS.size();
                    dayO9_11 += listO.size();
                    break;
                case 10:
                    dayC9_11 += listC.size();
                    dayS9_11 += listS.size();
                    dayO9_11 += listO.size();
                    break;
                case 11:
                    dayC9_11 += listC.size();
                    dayS9_11 += listS.size();
                    dayO9_11 += listO.size();
                    break;
                case 12:
                    dayC12_14 += listC.size();
                    dayS12_14 += listS.size();
                    dayO12_14 += listO.size();
                    break;
                case 13:
                    dayC12_14 += listC.size();
                    dayS12_14 += listS.size();
                    dayO12_14 += listO.size();
                    break;
                case 14:
                    dayC12_14 += listC.size();
                    dayS12_14 += listS.size();
                    dayO12_14 += listO.size();
                    break;
                case 15:
                    dayC15_17 += listC.size();
                    dayS15_17 += listS.size();
                    dayO15_17 += listO.size();
                    break;
                case 16:
                    dayC15_17 += listC.size();
                    dayS15_17 += listS.size();
                    dayO15_17 += listO.size();
                    break;
                case 17:
                    dayC15_17 += listC.size();
                    dayS15_17 += listS.size();
                    dayO15_17 += listO.size();
                    break;
                case 18:
                    dayC18_20 += listC.size();
                    dayS18_20 += listS.size();
                    dayO18_20 += listO.size();
                    break;
                case 19:
                    dayC18_20 += listC.size();
                    dayS18_20 += listS.size();
                    dayO18_20 += listO.size();
                    break;
                case 20:
                    dayC18_20 += listC.size();
                    dayS18_20 += listS.size();
                    dayO18_20 += listO.size();
                    break;
                case 21:
                    dayC21_23 += listC.size();
                    dayS21_23 += listS.size();
                    dayO21_23 += listO.size();
                    break;
                case 22:
                    dayC21_23 += listC.size();
                    dayS21_23 += listS.size();
                    dayO21_23 += listO.size();
                    break;
                case 23:
                    dayC21_23 += listC.size();
                    dayS21_23 += listS.size();
                    dayO21_23 += listO.size();
                    break;
                case 24:
                    dayC24_26 += listC.size();
                    dayS24_26 += listS.size();
                    dayO24_26 += listO.size();
                    break;
                case 25:
                    dayC24_26 += listC.size();
                    dayS24_26 += listS.size();
                    dayO24_26 += listO.size();
                    break;
                case 26:
                    dayC24_26 += listC.size();
                    dayS24_26 += listS.size();
                    dayO24_26 += listO.size();
                    break;
                case 27:
                    dayC_27_31 += listC.size();
                    dayS_27_31 += listS.size();
                    dayO_27_31 += listO.size();
                    break;
                case 28:
                    dayC_27_31 += listC.size();
                    dayS_27_31 += listS.size();
                    dayO_27_31 += listO.size();
                    break;
                case 29:
                    dayC_27_31 += listC.size();
                    dayS_27_31 += listS.size();
                    dayO_27_31 += listO.size();
                    break;
                case 30:
                    dayC_27_31 += listC.size();
                    dayS_27_31 += listS.size();
                    dayO_27_31 += listO.size();
                    break;
                case 31:
                    dayC_27_31 += listC.size();
                    dayS_27_31 += listS.size();
                    dayO_27_31 += listO.size();
                    break;
            }
        }

        BarChart barChart = (BarChart) findViewById(R.id.summary_bar_chart_graph);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, new float[]{dayC1_2, dayS1_2, dayO1_2}));
        entries.add(new BarEntry(2, new float[]{dayC3_5, dayS3_5, dayO3_5}));
        entries.add(new BarEntry(3, new float[]{dayC6_8, dayS6_8, dayO6_8}));
        entries.add(new BarEntry(4, new float[]{dayC9_11, dayS9_11, dayO9_11}));
        entries.add(new BarEntry(5, new float[]{dayC12_14, dayS12_14, dayO12_14}));
        entries.add(new BarEntry(6, new float[]{dayC15_17, dayS15_17, dayO15_17}));
        entries.add(new BarEntry(7, new float[]{dayC18_20, dayS18_20, dayO18_20}));
        entries.add(new BarEntry(8, new float[]{dayC21_23, dayS21_23, dayO21_23}));
        entries.add(new BarEntry(9, new float[]{dayC24_26, dayS24_26, dayO24_26}));
        entries.add(new BarEntry(10, new float[]{dayC_27_31, dayS_27_31, dayO_27_31}));

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

    private String getMonthName(int month) {

        String monthName = "";

        if (month == 1) {
            monthName = "January";
        }
        if (month == 2) {
            monthName = "February";
        }
        if (month == 3) {
            monthName = "March";
        }
        if (month == 4) {
            monthName = "April";
        }
        if (month == 5) {
            monthName = "May";
        }
        if (month == 6) {
            monthName = "June";
        }
        if (month == 7) {
            monthName = "July";
        }
        if (month == 8) {
            monthName = "August";
        }
        if (month == 9) {
            monthName = "September";
        }
        if (month == 10) {
            monthName = "October";
        }
        if (month == 11) {
            monthName = "November";
        }
        if (month == 12) {
            monthName = "December";
        }

        return monthName;
    }

}