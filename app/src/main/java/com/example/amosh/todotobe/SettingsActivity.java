package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        username = getIntent().getStringExtra("name");

        ImageView menu_icon = (ImageView) findViewById(R.id.settings_menu_icon);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.settings_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.settings_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent homeActivity = new Intent(SettingsActivity.this, MainScreenActivity.class);
                        homeActivity.putExtra("name", username);
                        startActivity(homeActivity);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(SettingsActivity.this, OverviewActivity.class);
                        overviewActivity.putExtra("name", username);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(SettingsActivity.this, MyGroupsActivity.class);
                        groupsActivity.putExtra("name", username);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        Intent listsActivity = new Intent(SettingsActivity.this, ListsActivity.class);
                        String category = "";
                        listsActivity.putExtra("category", category);
                        listsActivity.putExtra("name", username);
                        startActivity(listsActivity);
                        break;
                    case R.id.nav_profile:
                        Intent profileActivity = new Intent(SettingsActivity.this, ProfileActivity.class);
                        profileActivity.putExtra("name", username);
                        startActivity(profileActivity);
                        break;
                    case R.id.nav_timeline:
                        Intent timelineActivity = new Intent(SettingsActivity.this, TimelineActivity.class);
                        timelineActivity.putExtra("name", username);
                        startActivity(timelineActivity);
                        break;
                    case R.id.nav_settings:
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(SettingsActivity.this, SignInActivity.class);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });
    }
}
