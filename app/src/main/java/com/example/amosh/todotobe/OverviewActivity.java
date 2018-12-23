package com.example.amosh.todotobe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.widget.TextView;

import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.Data.UsersContract;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    String username;
    int currentMonth;
    MyUsersDbHelper usersDbHelper;
    TextView monthTxt;
    TextView yearTxt;
    int passableYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_layout);

        username = getIntent().getStringExtra("name");

        ImageView profilePic = (ImageView) findViewById(R.id.overview_user_image);
        usersDbHelper = new MyUsersDbHelper(OverviewActivity.this);
        SQLiteDatabase db = usersDbHelper.getReadableDatabase();
        Cursor cursor = usersDbHelper.readUser(username);
        if (cursor.moveToFirst()) {
            String imageUriString = cursor.getString(cursor.getColumnIndex(UsersContract.UsersEntry.COLUMN_IMAGE));
            if (imageUriString.equals("")) {
                profilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_8c5c19d99c_white));
            } else {
                Uri imageUri = Uri.parse(imageUriString);
                Picasso.with(OverviewActivity.this).load(imageUri).into(profilePic);
            }
        }

        Calendar calendar = Calendar.getInstance();

        monthTxt = (TextView) findViewById(R.id.overview_month_textview);
        yearTxt = (TextView) findViewById(R.id.overview_year_textview);

        yearTxt.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        monthTxt.setText(String.valueOf(getMonthName(calendar.get(Calendar.MONTH) + 1)));

        int year = calendar.get(Calendar.YEAR);
        setYear(year);

        currentMonth = calendar.get(Calendar.MONTH) + 1;
        updateUi(currentMonth, year);

        ImageView rightArrow = (ImageView) findViewById(R.id.overview_forward_month);
        ImageView leftArrow = (ImageView) findViewById(R.id.overview_backward_month);

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int passedYear = getYear();
                if (currentMonth != 0) {
                    currentMonth -= 1;
                }
                if (currentMonth == 0) {
                    currentMonth = 12;
                    passedYear -= 1;
                    setYear(passedYear);
                }
                updateUi(currentMonth, passedYear);
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int passedYear = getYear();
                if (currentMonth != 13) {
                    currentMonth += 1;
                }
                if (currentMonth == 13) {
                    currentMonth = 1;
                    passedYear += 1;
                    setYear(passedYear);
                }
                updateUi(currentMonth, passedYear);
            }
        });

        FloatingActionButton summary = (FloatingActionButton) findViewById(R.id.overview_fab);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent summary = new Intent(OverviewActivity.this, SummaryChartActivity.class);
                summary.putExtra("name", username);
                summary.putExtra("month", String.valueOf(currentMonth));
                summary.putExtra("year", String.valueOf(getYear()));
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

    private int getYear() {
        return passableYear;
    }

    private void setYear(int passedYear) {
        passableYear = passedYear;
    }

    private void updateUi(final int month, final int year) {

        monthTxt.setText(getMonthName(month));
        yearTxt.setText(String.valueOf(year));

        TextView snoozeTxt = (TextView) findViewById(R.id.overview_snoozed_number);
        TextView snoozeRatio = (TextView) findViewById(R.id.overview_snoozed_ration);

        TextView completeTxt = (TextView) findViewById(R.id.overview_completed_number);
        TextView completeRatio = (TextView) findViewById(R.id.overview_completed_ration);

        TextView overdueTxt = (TextView) findViewById(R.id.overview_overdued_number);
        TextView overdueRatio = (TextView) findViewById(R.id.overview_overdued_ration);

        TextView more_less = (TextView) findViewById(R.id.overview_more_less);
        TextView overMonthRatio = (TextView) findViewById(R.id.overview_overmonth_ratio);


        List<Events> complete;
        List<Events> overdue;
        List<Events> snooze;

        overdue = usersDbHelper.readEventListStateNoDay(username, String.valueOf(month), String.valueOf(year), String.valueOf(EventsContract.EventsEntry.STATE_OVERDUE));
        overdueTxt.setText(String.valueOf(overdue.size()));

        complete = usersDbHelper.readEventListStateNoDay(username, String.valueOf(month), String.valueOf(year), String.valueOf(EventsContract.EventsEntry.STATE_COMPLETED));
        completeTxt.setText(String.valueOf(complete.size()));

        snooze = usersDbHelper.readEventListStateNoDay(username, String.valueOf(month), String.valueOf(year), String.valueOf(EventsContract.EventsEntry.STATE_SNOOZED));
        snoozeTxt.setText(String.valueOf(snooze.size()));


        int total = overdue.size() + complete.size() + snooze.size();
        int snoozeRate = 0;
        int completeRate = 0;
        int overdueRate = 0;

        if (snooze.size() != 0) {
            snoozeRate = (snooze.size() * 100) / total;
        }
        if (complete.size() != 0) {
            completeRate = (complete.size() * 100) / total;
        }
        if (overdue.size() != 0) {
            overdueRate = (overdue.size() * 100) / total;
        }

        snoozeRatio.setText(String.valueOf(snoozeRate));
        completeRatio.setText(String.valueOf(completeRate));
        overdueRatio.setText(String.valueOf(overdueRate));
        if (month != 1) {
            List<Events> pastComplete = usersDbHelper.readEventListStateNoDay(username, String.valueOf(month - 1), String.valueOf(year), String.valueOf(EventsContract.EventsEntry.STATE_COMPLETED));

            int current = complete.size();
            int past = pastComplete.size();

            if (current >= past && past != 0) {
                int rate = current / past;
                more_less.setText("more");
                overMonthRatio.setText(String.valueOf(rate));
            } else if (current < past && current != 0) {
                int rate = current / past;
                more_less.setText("less");
                overMonthRatio.setText(String.valueOf(rate));
            } else {
                more_less.setText("more");
                overMonthRatio.setText(String.valueOf(100));
            }
        } else if (month == 1) {
            if (month != 1) {
                List<Events> pastComplete = usersDbHelper.readEventListStateNoDay(username, String.valueOf(month), String.valueOf(year - 1), String.valueOf(EventsContract.EventsEntry.STATE_COMPLETED));

                int current = complete.size();
                int past = pastComplete.size();

                if (current >= past && past != 0) {
                    int rate = current / past;
                    more_less.setText("more");
                    overMonthRatio.setText(String.valueOf(rate));
                } else if (current < past && current != 0) {
                    int rate = current / past;
                    more_less.setText("less");
                    overMonthRatio.setText(String.valueOf(rate));
                } else {
                    more_less.setText("more");
                    overMonthRatio.setText(String.valueOf(100));
                }
            }

        }
    }

    private String getMonthName(int thisMonth) {

        String monthName = "";

        if (thisMonth == 1) {
            monthName = "January";
        }
        if (thisMonth == 2) {
            monthName = "February";
        }
        if (thisMonth == 3) {
            monthName = "March";
        }
        if (thisMonth == 4) {
            monthName = "April";
        }
        if (thisMonth == 5) {
            monthName = "May";
        }
        if (thisMonth == 6) {
            monthName = "June";
        }
        if (thisMonth == 7) {
            monthName = "July";
        }
        if (thisMonth == 8) {
            monthName = "August";
        }
        if (thisMonth == 9) {
            monthName = "September";
        }
        if (thisMonth == 10) {
            monthName = "October";
        }
        if (thisMonth == 11) {
            monthName = "November";
        }
        if (thisMonth == 12) {
            monthName = "December";
        }

        return monthName;
    }
}
