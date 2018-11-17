package com.example.amosh.todotobe;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGroupsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_groups_layout);

        ImageView menu_icon = (ImageView) findViewById(R.id.my_groups_menu_icon);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.my_groups_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.my_groups_navigation_view);
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
                        Intent groupsActivity = new Intent(MyGroupsActivity.this, MyGroupsActivity.class);
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
                        Intent signInActivity = new Intent(MyGroupsActivity.this, SignInActivity.class);
                        startActivity(signInActivity);
                }
                return true;
            }
        });


    }
}