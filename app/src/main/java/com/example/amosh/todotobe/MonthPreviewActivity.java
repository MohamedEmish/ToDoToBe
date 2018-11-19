package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class MonthPreviewActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_preview);


        final MaterialCalendarView weekCalendarView = (MaterialCalendarView) findViewById(R.id.month_periview_week_calender);
        final MaterialCalendarView monthCalendarView = (MaterialCalendarView) findViewById(R.id.month_periview_month_calender);

//        calendarView.setTopbarVisible(false);

        String yearString = getIntent().getStringExtra("year");
        String monthName = getIntent().getStringExtra("month");
        String dayString = getIntent().getStringExtra("day");
        String monthNumber = getIntent().getStringExtra("monthNumber");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.month_preview_tab_layout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.month_preview_view_pager);
        TabItem dayTab = (TabItem) findViewById(R.id.month_preview_day_tab);
        TabItem weekTab = (TabItem) findViewById(R.id.month_preview_week_tab);
        TabItem monthTab = (TabItem) findViewById(R.id.month_preview_month_tab);

        MonthPreviewPagerAdapter monthPreviewPagerAdapter;
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


        TextView monthTextView = (TextView) findViewById(R.id.month_preview_month_name);
        monthTextView.setText(monthName);

        TextView yearTextView = (TextView) findViewById(R.id.month_preview_year);
        yearTextView.setText(yearString);


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
                        break;
                    case R.id.nav_home:
                        Intent mainScreenActivity = new Intent(MonthPreviewActivity.this, MainScreenActivity.class);
                        startActivity(mainScreenActivity);
                        break;
                    case R.id.nav_overview:
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(MonthPreviewActivity.this, MyGroupsActivity.class);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        break;
                    case R.id.nav_profile:
                        break;
                    case R.id.nav_timeline:
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(MonthPreviewActivity.this, SettingsActivity.class);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(MonthPreviewActivity.this, SignInActivity.class);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });

    }
}
