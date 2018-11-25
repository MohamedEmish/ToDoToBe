package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class MainScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);


        final MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.main_screen_calender);

        calendarView.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calenderTitleClick();
            }

        });

        ImageView menu_icon = (ImageView) findViewById(R.id.main_screen_menu_icon);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.main_screen_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_sceen_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent mainScreenActivity = new Intent(MainScreenActivity.this, MainScreenActivity.class);
                        startActivity(mainScreenActivity);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(MainScreenActivity.this, OverviewActivity.class);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(MainScreenActivity.this, MyGroupsActivity.class);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        Intent ADDActivity = new Intent(MainScreenActivity.this, AddRemainderActivity.class);
                        startActivity(ADDActivity);
                        break;
                    case R.id.nav_profile:
                        break;
                    case R.id.nav_timeline:
                        Intent summary = new Intent(MainScreenActivity.this, SummaryChartActivity.class);
                        startActivity(summary);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(MainScreenActivity.this, SettingsActivity.class);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(MainScreenActivity.this, SignInActivity.class);
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


    }

    private void calenderTitleClick() {

        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.main_screen_calender);

        CalendarDay selected = calendarView.getCurrentDate();

        int month = selected.getMonth();

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

        int year = selected.getYear();
        String yearString = String.valueOf(year).trim();

        int day = selected.getDay();
        String dayString = String.valueOf(day);

        String finalMonthName = monthName;

        Intent monthPreview = new Intent(MainScreenActivity.this, MonthPreviewActivity.class);

        monthPreview.putExtra("year", yearString);
        monthPreview.putExtra("month", finalMonthName);
        monthPreview.putExtra("day", dayString);
        monthPreview.putExtra("monthNumber", month);


        startActivity(monthPreview);
    }


}
