package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

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
                    case R.id.nav_close:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    case R.id.nav_home:
                        Intent mainScreenActivity = new Intent(SettingsActivity.this, MainScreenActivity.class);
                        startActivity(mainScreenActivity);
                        break;
                    case R.id.nav_overview:
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(SettingsActivity.this, MyGroupsActivity.class);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        break;
                    case R.id.nav_profile:
                        break;
                    case R.id.nav_timeline:
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(settingsActivity);
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
