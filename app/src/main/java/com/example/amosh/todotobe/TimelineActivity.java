package com.example.amosh.todotobe;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.amosh.todotobe.Adapters.SectionAdapter;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    String username;
    EditText searchEditText;
    ImageView searchIcon;
    String searchText;
    DrawerLayout mDrawerLayout;
    ImageView menu_icon;
    NavigationView navigationView;

    FloatingActionButton fab;

    RelativeLayout emptyView;

    RecyclerView listView;
    SectionAdapter sCursorAdapter;
    MyUsersDbHelper usersDbHelper;
    ArrayList<String> eventsDays;
    LinearLayoutManager linearLayoutManager;
    ImageView close;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);

        username = getIntent().getStringExtra("name");
        usersDbHelper = new MyUsersDbHelper(this);

        emptyView = (RelativeLayout) findViewById(R.id.timeline_empty_view);

        listView = (RecyclerView) findViewById(R.id.timeline_list);
        linearLayoutManager = new LinearLayoutManager(this);
        Cursor cursor = usersDbHelper.readEventSection(username);
        sCursorAdapter = new SectionAdapter(this, cursor, username);
        listView.addItemDecoration(
                new DividerItemDecoration(this, linearLayoutManager.getOrientation()) {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        outRect.setEmpty();
                        // hide the divider for the last child
                    }
                }
        );
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(sCursorAdapter);

        if (cursor.getCount() == 0) {
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        // Search TextView and Function
        searchEditText = (EditText) findViewById(R.id.timeline_search_text);
        searchIcon = (ImageView) findViewById(R.id.timeline_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEditText.getVisibility() == View.GONE) {
                    searchEditText.setVisibility(View.VISIBLE);
                } else {
                    searchText = searchEditText.getText().toString().trim();
                    if (searchText.length() == 0) {
                        searchEditText.setVisibility(View.GONE);
                    } else {
                        searchForEvent(searchText);
                    }
                }
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.timeline_add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreClick();
            }
        });


        // Navigation Drawer image and items
        menu_icon = (ImageView) findViewById(R.id.timeline_menu_icon);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.timeline_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.timeline_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent home = new Intent(TimelineActivity.this, MainScreenActivity.class);
                        home.putExtra("name", username);
                        startActivity(home);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(TimelineActivity.this, OverviewActivity.class);
                        overviewActivity.putExtra("name", username);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(TimelineActivity.this, MyGroupsActivity.class);
                        groupsActivity.putExtra("name", username);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        break;
                    case R.id.nav_profile:
                        Intent profileActivity = new Intent(TimelineActivity.this, ProfileActivity.class);
                        profileActivity.putExtra("name", username);
                        startActivity(profileActivity);
                        break;
                    case R.id.nav_timeline:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(TimelineActivity.this, SettingsActivity.class);
                        settingsActivity.putExtra("name", username);
                        startActivity(settingsActivity);
                        break;
                    case R.id.nav_logout:
                        Intent signInActivity = new Intent(TimelineActivity.this, SignInActivity.class);
                        startActivity(signInActivity);
                        break;
                }
                return true;
            }
        });

        header = navigationView.getHeaderView(0);
        close = (ImageView) header.findViewById(R.id.nav_head_close);

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

    private void moreClick() {
        // TODO : // more on click
        Toast.makeText(TimelineActivity.this, "ha .. looking for more .. \n scroll down :D " + searchText, Toast.LENGTH_SHORT).show();

    }

    // search function
    private void searchForEvent(String searchText) {
        // TODO : // search method
        Toast.makeText(TimelineActivity.this, "SEARCHING FOR " + searchText, Toast.LENGTH_SHORT).show();
    }

}
