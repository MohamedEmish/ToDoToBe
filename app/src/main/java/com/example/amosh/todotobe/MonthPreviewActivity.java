package com.example.amosh.todotobe;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

public class MonthPreviewActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_preview);


        final MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.month_periview_calender);
//        calendarView.setTopbarVisible(false);

        String yearString = getIntent().getStringExtra("year");
        String monthName = getIntent().getStringExtra("month");
        String dayString = getIntent().getStringExtra("day");
        String monthNumber = getIntent().getStringExtra("monthNumber");


        TextView monthTextView = (TextView) findViewById(R.id.month_preview_month_name);
        monthTextView.setText(monthName);

        TextView yearTextView = (TextView) findViewById(R.id.month_preview_year);
        yearTextView.setText(yearString);

        final TextView dayTab = (TextView) findViewById(R.id.month_preview_day_tab);
        final TextView weekTab = (TextView) findViewById(R.id.month_preview_week_tab);
        final TextView monthTab = (TextView) findViewById(R.id.month_preview_month_tab);

        dayTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayTab.setPaintFlags(dayTab.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                weekTab.setPaintFlags(0);
                monthTab.setPaintFlags(0);
            }
        });

        weekTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekTab.setPaintFlags(weekTab.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                dayTab.setPaintFlags(0);
                monthTab.setPaintFlags(0);
            }
        });

        monthTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthTab.setPaintFlags(monthTab.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                weekTab.setPaintFlags(0);
                dayTab.setPaintFlags(0);
            }
        });

        ImageView menu_icon = (ImageView) findViewById(R.id.month_preview_menu_icon);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.month_preview_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.month_preview_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_close:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    case R.id.nav_home:
                        break;
                    case R.id.nav_overview:
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(MonthPreviewActivity.this, MyGroupsActivity.class);
                        startActivity(groupsActivity);
                    case R.id.nav_lists:
                        break;
                    case R.id.nav_profile:
                        break;
                    case R.id.nav_timeline:
                        break;
                    case R.id.nav_settings:
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(MonthPreviewActivity.this, SignInActivity.class);
                        startActivity(signInActivity);
                }
                return true;
            }
        });

    }
}
