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

public class OverviewActivity extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_layout);

        username = getIntent().getStringExtra("name");

        FloatingActionButton summary = (FloatingActionButton) findViewById(R.id.overview_fab);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent summary = new Intent(OverviewActivity.this, SummaryChartActivity.class);
                summary.putExtra("name", username);
                startActivity(summary);
            }
        });


        ImageView menu_icon = (ImageView) findViewById(R.id.overview_menu_icon);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.overview_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.overview_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        Intent homeActivity = new Intent(OverviewActivity.this, MainScreenActivity.class);
                        homeActivity.putExtra("name", username);
                        startActivity(homeActivity);
                        break;
                    case R.id.nav_overview:
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(OverviewActivity.this, MyGroupsActivity.class);
                        groupsActivity.putExtra("name", username);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        Intent listsActivity = new Intent(OverviewActivity.this, ListsActivity.class);
                        String category = "";
                        listsActivity.putExtra("category", category);
                        listsActivity.putExtra("name", username);
                        startActivity(listsActivity);
                        break;
                    case R.id.nav_profile:
                        Intent profileActivity = new Intent(OverviewActivity.this, ProfileActivity.class);
                        profileActivity.putExtra("name", username);
                        startActivity(profileActivity);
                        break;
                    case R.id.nav_timeline:
                        Intent timelineActivity = new Intent(OverviewActivity.this, TimelineActivity.class);
                        timelineActivity.putExtra("name", username);
                        startActivity(timelineActivity);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(OverviewActivity.this, SettingsActivity.class);
                        settingsActivity.putExtra("name", username);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(OverviewActivity.this, SignInActivity.class);
                        SaveSharedPreference.clearUserName(OverviewActivity.this);
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
}
