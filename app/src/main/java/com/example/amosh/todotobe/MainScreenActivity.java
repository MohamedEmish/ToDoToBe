package com.example.amosh.todotobe;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.amosh.todotobe.Adapters.EventAdapter;
import com.example.amosh.todotobe.Adapters.EventDecorator;
import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.Data.UsersContract;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainScreenActivity extends AppCompatActivity {

    TextView greet;
    ImageView profilePic;
    ImageView searchIcon;
    EditText searchEditText;
    String searchText;

    RelativeLayout emptyView;

    FloatingActionButton fab;
    MaterialCalendarView calendarView;

    RecyclerView eventListView;

    int dotColor;

    DrawerLayout mDrawerLayout;
    ImageView close;
    View header;
    NavigationView navigationView;
    ImageView menu_icon;


    EventAdapter eEventAdapter;

    String userName;
    ArrayList<CalendarDay> eventsDays;
    MyUsersDbHelper usersDbHelper;

    public static final int PICK_IMAGE_REQUEST = 0;
    Uri imageUri;
    String mImageUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);

        usersDbHelper = new MyUsersDbHelper(this);
        // Getting signed in username
        userName = getIntent().getStringExtra("name");

        // Getting current date & time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Getting current Date
        Date date = calendar.getTime();

        // setting Date & Time Format
        DateFormat dateFormat = new SimpleDateFormat("h:mm aaa");
        String FormattedTimeDate = dateFormat.format(date);

        DateFormat dateFormat1 = new SimpleDateFormat("MMMM d,yyyy");
        String FormattedDate = dateFormat1.format(date);

        // Getting Day,month and year to be passed to DB and other intents
        int dayToPass = getDateDay(FormattedDate);
        int monthToPass = getDateMonth(FormattedDate);
        int yearToPass = getDateYear(FormattedDate);

        String day = String.valueOf(dayToPass);
        String month = String.valueOf(monthToPass);
        String year = String.valueOf(yearToPass);

        // Setting greet MSG according to current Hour
        setGreetMsg(hour);

        // Setting user profile picture
        setProfilePic(userName);

        // Changing profile picture
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        // Find and set empty view on the recycler View, so that it only shows when the list has 0 items.

        emptyView = (RelativeLayout) findViewById(R.id.main_screen_empty_view);

        // Find the Recycler View which will be populated with the event data
        eventListView = (RecyclerView) findViewById(R.id.main_screen_list_View);
        eventListView.addItemDecoration(new DividerItemDecoration(eventListView.getContext(), DividerItemDecoration.VERTICAL));
        eventListView.setLayoutManager(new LinearLayoutManager(this));

        // Cursor to populate recycler View
        Cursor cursor = usersDbHelper.readEvent(userName, day, month, year);
        if (cursor.getCount() == 0) {
            eventListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            eventListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        eEventAdapter = new EventAdapter(this, cursor);
        eventListView.setAdapter(eEventAdapter);

        // Defining UI calendar
        calendarView = (MaterialCalendarView) findViewById(R.id.main_screen_calender);

        // Set current date selection
        calendarView.setSelectedDate(CalendarDay.from(yearToPass, monthToPass, dayToPass));

        // on day change event list change
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                int newDay = materialCalendarView.getSelectedDate().getDay();
                int newMonth = materialCalendarView.getSelectedDate().getMonth();
                int newYear = materialCalendarView.getSelectedDate().getYear();

                Cursor newCursor = usersDbHelper.readEvent(userName, String.valueOf(newDay), String.valueOf(newMonth), String.valueOf(newYear));
                eEventAdapter = new EventAdapter(MainScreenActivity.this, newCursor);
                eventListView.setAdapter(eEventAdapter);

                if (newCursor.getCount() == 0) {
                    eventListView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    eventListView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }
        });

        // On month tab click
        calendarView.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calenderTitleClick();
            }
        });

        // Decoration of calendar (the dot on events days)
        Cursor eventsCursor = usersDbHelper.readEvent(userName);
        int length = eventsCursor.getCount();
        if (length > 0) {
            eventsDays = new ArrayList<>();
            for (eventsCursor.moveToFirst(); !eventsCursor.isAfterLast(); eventsCursor.moveToNext()) {
                int eventDayNu = eventsCursor.getInt(eventsCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY));
                int eventMonthNu = eventsCursor.getInt(eventsCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH));
                int eventYearNu = eventsCursor.getInt(eventsCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR));

                CalendarDay eventDay = CalendarDay.from(eventYearNu, eventMonthNu, eventDayNu);
                eventsDays.add(eventDay);
            }

            dotColor = getResources().getColor(R.color.colorAccent);
            calendarView.addDecorator(new EventDecorator(dotColor, eventsDays));
        }

        // Adding new event FAB
        fab = (FloatingActionButton) findViewById(R.id.main_screen_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent(userName);
            }
        });

        // Search TextView and Function
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

        // Navigation Drawer image and items
        menu_icon = (ImageView) findViewById(R.id.main_screen_menu_icon);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_screen_drawer_layout);
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        navigationView = (NavigationView) findViewById(R.id.main_screen_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
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
                        Intent profileActivity = new Intent(MainScreenActivity.this, ProfileActivity.class);
                        profileActivity.putExtra("name", userName);
                        startActivity(profileActivity);
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

    // open gallery to get image
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


    // search function
    private void searchForEvent(String searchText) {
        // TODO : // search method
        Toast.makeText(MainScreenActivity.this, "SEARCHING FOR " + searchText, Toast.LENGTH_SHORT).show();
    }

    // Getting profile picture from DB to set
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

    // Setting greet MSG according to hour
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

    // Function to open addRemainder to add new event
    private void addNewEvent(String name) {
        Intent ADDActivity = new Intent(MainScreenActivity.this, AddRemainderActivity.class);
        ADDActivity.putExtra("name", name);
        startActivity(ADDActivity);
    }

    // on month click open detailed list
    private void calenderTitleClick() {

        calendarView = (MaterialCalendarView) findViewById(R.id.main_screen_calender);

        CalendarDay selected = calendarView.getSelectedDate();
        int month = selected.getMonth();

        String monthName = getMonthName(month);

        int year = selected.getYear();
        String yearString = String.valueOf(year).trim();

        int day = selected.getDay();
        String dayString = String.valueOf(day);

        Intent monthPreview = new Intent(MainScreenActivity.this, com.example.amosh.todotobe.Fragments.MonthPreviewActivity.class);

        monthPreview.putExtra("yearString", yearString);
        monthPreview.putExtra("yearNumber", String.valueOf(year));
        monthPreview.putExtra("monthName", monthName);
        monthPreview.putExtra("monthNumber", String.valueOf(month));
        monthPreview.putExtra("dayString", dayString);
        monthPreview.putExtra("dayNumber", String.valueOf(day));
        monthPreview.putExtra("name", userName);


        startActivity(monthPreview);
    }

    // Function to return day number
    private int getDateDay(String date) {

        String[] cutMonthFromRest = date.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

        int dayNumber = Integer.parseInt(dayString.trim());

        return dayNumber;
    }

    // Function to return month number
    private int getDateMonth(String date) {

        String[] cutMonthFromRest = date.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

        int monthNumber = getMonthNumber(monthName.trim());

        return monthNumber;
    }

    // Function to return year number
    private int getDateYear(String date) {

        String[] cutMonthFromRest = date.split("\\s+");
        String monthName = cutMonthFromRest[0];
        String dayAndYearString = cutMonthFromRest[1];

        String[] cut2ndPhase = dayAndYearString.split(",");
        String dayString = cut2ndPhase[0];
        String yearString = cut2ndPhase[1];

        int yearNumber = Integer.parseInt(yearString.trim());

        return yearNumber;
    }

    // Function to return hour number
    private int getTimeHour(String time) {

        String[] cutHourFromRest = time.split(":");
        String hourString = cutHourFromRest[0];
        String minutesAndXm = cutHourFromRest[1];

        String[] cut2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = cut2ndPhase[0];
        String am_pm = cut2ndPhase[1];

        int hour = Integer.parseInt(hourString.trim());
        int hourNumber = 0;
        if (am_pm.equals("am")) {
            hourNumber = hour;
        }
        if (am_pm.equals("pm")) {
            hourNumber = hour + 12;
        }

        return hourNumber;
    }

    // Function to return minute number
    private int getTimeMinute(String time) {

        String timeFromCurrentString = time;
        String[] cutHourFromRest = timeFromCurrentString.split(":");
        String hourString = cutHourFromRest[0];
        String minutesAndXm = cutHourFromRest[1];

        String[] cut2ndPhase = minutesAndXm.split("\\s+");
        String minutesString = cut2ndPhase[0];
        String am_pm = cut2ndPhase[1];

        int minuteNumber = Integer.parseInt(minutesString.trim());

        return minuteNumber;
    }

    // Function to convert month number into month name
    private String getMonthName(int month) {

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

        return monthName;
    }

    // Function to convert month name into month number
    private int getMonthNumber(String monthName) {

        int monthNumber = 0;

        switch (monthName) {
            case "January":
                monthNumber = 1;
                break;
            case ("February"):
                monthNumber = 2;
                break;
            case ("March"):
                monthNumber = 3;
                break;
            case ("April"):
                monthNumber = 4;
                break;
            case ("May"):
                monthNumber = 5;
                break;
            case ("June"):
                monthNumber = 6;
                break;
            case ("July"):
                monthNumber = 7;
                break;
            case ("August"):
                monthNumber = 8;
                break;
            case ("September"):
                monthNumber = 9;
                break;
            case ("October"):
                monthNumber = 10;
                break;
            case ("November"):
                monthNumber = 11;
                break;
            case ("December"):
                monthNumber = 12;
                break;
        }
        return monthNumber;
    }

}
