package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
                        break;
                    case R.id.nav_home:
                        Intent mainScreenActivity = new Intent(MyGroupsActivity.this, MainScreenActivity.class);
                        startActivity(mainScreenActivity);
                        break;
                    case R.id.nav_overview:
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(MyGroupsActivity.this, MyGroupsActivity.class);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        break;
                    case R.id.nav_profile:
                        break;
                    case R.id.nav_timeline:
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(MyGroupsActivity.this, SettingsActivity.class);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(MyGroupsActivity.this, SignInActivity.class);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });

        CardView shopCardView = (CardView) findViewById(R.id.my_group_shop_card);
        TextView shopItemsNum = (TextView) findViewById(R.id.my_groups_shop_items);
        shopCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ShopActivity = new Intent(MyGroupsActivity.this, ShopActivity.class);
                startActivity(ShopActivity);
            }
        });

        CardView workCardView = (CardView) findViewById(R.id.my_group_work_card);
        TextView workItemsNum = (TextView) findViewById(R.id.my_groups_work_items);
        workCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent WorkActivity = new Intent(MyGroupsActivity.this, WorkActivity.class);
                startActivity(WorkActivity);
            }
        });

        CardView autoCardView = (CardView) findViewById(R.id.my_group_auto_card);
        TextView autoItemsNum = (TextView) findViewById(R.id.my_groups_auto_items);
        autoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AutoActivity = new Intent(MyGroupsActivity.this, AutoActivity.class);
                startActivity(AutoActivity);
            }
        });

        CardView billsCardView = (CardView) findViewById(R.id.my_groups_bills_card);
        TextView billsItemsNum = (TextView) findViewById(R.id.my_groups_bills_items);
        billsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BillsActivity = new Intent(MyGroupsActivity.this, BillsActivity.class);
                startActivity(BillsActivity);
            }
        });

        CardView healthCardView = (CardView) findViewById(R.id.my_group_health_card);
        TextView healthItemsNum = (TextView) findViewById(R.id.my_groups_health_items);
        healthCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HealthActivity = new Intent(MyGroupsActivity.this, HealthActivity.class);
                startActivity(HealthActivity);
            }
        });

        CardView travelCardView = (CardView) findViewById(R.id.my_group_travel_card);
        TextView travelItemsNum = (TextView) findViewById(R.id.my_groups_travel_items);
        travelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TravelActivity = new Intent(MyGroupsActivity.this, TravelActivity.class);
                startActivity(TravelActivity);
            }
        });

    }
}