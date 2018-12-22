package com.example.amosh.todotobe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amosh.todotobe.Adapters.MonthPreviewPagerAdapter;
import com.example.amosh.todotobe.ListsActivity;
import com.example.amosh.todotobe.MainScreenActivity;
import com.example.amosh.todotobe.MyGroupsActivity;
import com.example.amosh.todotobe.OverviewActivity;
import com.example.amosh.todotobe.ProfileActivity;
import com.example.amosh.todotobe.R;
import com.example.amosh.todotobe.SaveSharedPreference;
import com.example.amosh.todotobe.SettingsActivity;
import com.example.amosh.todotobe.SignInActivity;
import com.example.amosh.todotobe.TimelineActivity;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class MonthPreviewActivity extends AppCompatActivity {

    String username;
    String yearString;
    int yearNumber;
    String monthName;
    int monthNumber;
    String dayString;
    int dayNumber;

    MaterialCalendarView weekCalendarView;
    MaterialCalendarView monthCalendarView;

    ViewPager viewPager;
    TabLayout tabLayout;
    TabItem dayTab;
    TabItem weekTab;
    TabItem monthTab;

    TextView monthTextView;
    TextView yearTextView;
    ImageView menu_icon;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    MonthPreviewPagerAdapter monthPreviewPagerAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_preview);

        username = getIntent().getStringExtra("name");

        weekCalendarView = (MaterialCalendarView) findViewById(R.id.month_periview_week_calender);
        monthCalendarView = (MaterialCalendarView) findViewById(R.id.month_preview_month_calender);

        yearString = getIntent().getStringExtra("yearString");
        yearNumber = Integer.valueOf(getIntent().getStringExtra("yearNumber"));
        monthName = getIntent().getStringExtra("monthName");
        monthNumber = Integer.valueOf(getIntent().getStringExtra("monthNumber"));
        dayString = getIntent().getStringExtra("dayString");
        dayNumber = Integer.valueOf(getIntent().getStringExtra("dayNumber"));


        tabLayout = (TabLayout) findViewById(R.id.month_preview_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.month_preview_view_pager);
        dayTab = (TabItem) findViewById(R.id.month_preview_day_tab);
        weekTab = (TabItem) findViewById(R.id.month_preview_week_tab);
        monthTab = (TabItem) findViewById(R.id.month_preview_month_tab);

        monthPreviewPagerAdapter = new MonthPreviewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(monthPreviewPagerAdapter);
        viewPager.setCurrentItem(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    viewPager.setCurrentItem(0);
                } else if (tab.getPosition() == 1) {
                    viewPager.setCurrentItem(1);
                } else if (tab.getPosition() == 2) {
                    viewPager.setCurrentItem(2);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        monthTextView = (TextView) findViewById(R.id.month_preview_month_name);
        monthTextView.setText(monthName);

        yearTextView = (TextView) findViewById(R.id.month_preview_year);
        yearTextView.setText(yearString);


        menu_icon = (ImageView) findViewById(R.id.month_preview_menu_icon);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.month_preview_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.month_preview_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent homeActivity = new Intent(MonthPreviewActivity.this, MainScreenActivity.class);
                        homeActivity.putExtra("name", username);
                        startActivity(homeActivity);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(MonthPreviewActivity.this, OverviewActivity.class);
                        overviewActivity.putExtra("name", username);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(MonthPreviewActivity.this, MyGroupsActivity.class);
                        groupsActivity.putExtra("name", username);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        Intent listsActivity = new Intent(MonthPreviewActivity.this, ListsActivity.class);
                        String category = "";
                        listsActivity.putExtra("category", category);
                        listsActivity.putExtra("name", username);
                        startActivity(listsActivity);
                        break;
                    case R.id.nav_profile:
                        Intent profileActivity = new Intent(MonthPreviewActivity.this, ProfileActivity.class);
                        profileActivity.putExtra("name", username);
                        startActivity(profileActivity);
                        break;
                    case R.id.nav_timeline:
                        Intent timelineActivity = new Intent(MonthPreviewActivity.this, TimelineActivity.class);
                        timelineActivity.putExtra("name", username);
                        startActivity(timelineActivity);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(MonthPreviewActivity.this, SettingsActivity.class);
                        settingsActivity.putExtra("name", username);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(MonthPreviewActivity.this, SignInActivity.class);
                        SaveSharedPreference.clearUserName(MonthPreviewActivity.this);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });

    }

    public String getUserName() {
        return username;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public String getDayString() {
        return dayString;
    }

    public String getYearString() {
        return yearString;
    }

    public String getMonthName() {
        return monthName;
    }

    public String getMonthNumberString() {
        return String.valueOf(monthNumber);
    }

}
