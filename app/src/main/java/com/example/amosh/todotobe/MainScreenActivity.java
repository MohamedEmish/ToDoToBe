package com.example.amosh.todotobe;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amosh.todotobe.Adapters.EventCursorAdapter;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.Data.UsersContract;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class MainScreenActivity extends AppCompatActivity {

    TextView greet;
    ImageView profilePic;
    ImageView searchIcon;
    EditText searchEditText;
    String searchText;
    FloatingActionButton fab;
    MaterialCalendarView calendarView;

    EventCursorAdapter eCursorAdapter;

    String userName;
    MyUsersDbHelper usersDbHelper;

    public static final int PICK_IMAGE_REQUEST = 0;
    Uri imageUri;
    String mImageUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        userName = getIntent().getStringExtra("name");
        setProfilePic(userName);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        setGreetMsg(hour);
        usersDbHelper = new MyUsersDbHelper(this);

        // Find the ListView which will be populated with the unit data
        ListView eventListView = (ListView) findViewById(R.id.main_screen_list_View);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        eventListView.setEmptyView(emptyView);

        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.main_screen_calender);

        int daySelected = calendarView.getCurrentDate().getDay();
        String day = String.valueOf(daySelected);

        Cursor cursor = usersDbHelper.readEvent(userName, day);
        eCursorAdapter = new EventCursorAdapter(this, cursor);
        eventListView.setAdapter(eCursorAdapter);


        fab = (FloatingActionButton) findViewById(R.id.main_screen_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent(userName);
            }
        });
        searchEditText = (EditText) findViewById(R.id.main_screen_search_text);

        searchIcon = (ImageView) findViewById(R.id.main_screen_search_button);
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


        calendarView = (MaterialCalendarView) findViewById(R.id.main_screen_calender);

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

        final NavigationView navigationView = (NavigationView) findViewById(R.id.main_screen_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent mainScreenActivity = new Intent(MainScreenActivity.this, MainScreenActivity.class);
                        mainScreenActivity.putExtra("name", userName);
                        startActivity(mainScreenActivity);
                        break;
                    case R.id.nav_overview:
                        Intent overviewActivity = new Intent(MainScreenActivity.this, OverviewActivity.class);
                        overviewActivity.putExtra("name", userName);
                        startActivity(overviewActivity);
                        break;
                    case R.id.nav_groups:
                        Intent groupsActivity = new Intent(MainScreenActivity.this, MyGroupsActivity.class);
                        groupsActivity.putExtra("name", userName);
                        startActivity(groupsActivity);
                        break;
                    case R.id.nav_lists:
                        break;
                    case R.id.nav_profile:
                        break;
                    case R.id.nav_timeline:
                        Intent timelineActivity = new Intent(MainScreenActivity.this, TimelineActivity.class);
                        timelineActivity.putExtra("name", userName);
                        startActivity(timelineActivity);
                        break;
                    case R.id.nav_settings:
                        Intent settingsActivity = new Intent(MainScreenActivity.this, SettingsActivity.class);
                        settingsActivity.putExtra("name", userName);
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

    private void openGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                imageUri = resultData.getData();
                profilePic.setImageURI(imageUri);
                profilePic.invalidate();
                mImageUriString = imageUri.toString();
                usersDbHelper.updateImage(userName, mImageUriString);
            }

        }
    }


    private void searchForEvent(String searchText) {
        // TODO : // search method
        Toast.makeText(MainScreenActivity.this, "SEARCHING FOR " + searchText, Toast.LENGTH_SHORT).show();
    }

    private void setProfilePic(String userName) {
        profilePic = (ImageView) findViewById(R.id.main_screen_user_pic);
        usersDbHelper = new MyUsersDbHelper(MainScreenActivity.this);
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();
        Cursor cursor = usersDbHelper.readUser(userName);
        if (cursor.moveToFirst()) {
            String imageUriString = cursor.getString(cursor.getColumnIndex(UsersContract.UsersEntry.COLUMN_IMAGE));
            if (imageUriString.equals("")) {
                profilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_8c5c19d99c));
            } else {
                Uri imageUri = Uri.parse(imageUriString);
                Picasso.with(MainScreenActivity.this).load(imageUri).into(profilePic);
            }
        } else {
            Toast.makeText(MainScreenActivity.this, "NO DATA AT THE CURSOR", Toast.LENGTH_SHORT).show();
        }


    }

    private void setGreetMsg(int hour) {
        greet = (TextView) findViewById(R.id.main_screen_greet_msg);

        if (hour >= 12 && hour < 17) {
            greet.setText(R.string.main_screen_good_A);
        } else if (hour >= 17 && hour < 21) {
            greet.setText(R.string.main_screen_good_E);
        } else if (hour >= 21 && hour < 24) {
            greet.setText(R.string.main_screen_good_N);
        } else {
            greet.setText(R.string.main_screen_good_M);
        }
    }

    private void addNewEvent(String name) {
        Intent ADDActivity = new Intent(MainScreenActivity.this, AddRemainderActivity.class);
        ADDActivity.putExtra("name", name);
        startActivity(ADDActivity);
    }

    private void calenderTitleClick() {

        calendarView = (MaterialCalendarView) findViewById(R.id.main_screen_calender);

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

        Intent monthPreview = new Intent(MainScreenActivity.this, com.example.amosh.todotobe.Fragments.MonthPreviewActivity.class);

        monthPreview.putExtra("year", yearString);
        monthPreview.putExtra("month", finalMonthName);
        monthPreview.putExtra("day", dayString);
        monthPreview.putExtra("monthNumber", month);
        monthPreview.putExtra("name", userName);


        startActivity(monthPreview);
    }


}
