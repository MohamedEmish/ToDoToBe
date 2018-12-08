package com.example.amosh.todotobe.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.amosh.todotobe.Adapters.EventCursorAdapter;
import com.example.amosh.todotobe.Adapters.EventDecorator;
import com.example.amosh.todotobe.AddRemainderActivity;
import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;

public class month_preview_month_tab_fargment extends Fragment {

    MonthPreviewActivity monthPreviewActivity;
    String username;
    FloatingActionButton fab;
    String day;
    String month;
    String year;
    ArrayList<CalendarDay> eventsDays;

    int dotColor;

    ListView eventListView;
    EventCursorAdapter eCursorAdapter;
    MyUsersDbHelper usersDbHelper;

    MaterialCalendarView materialCalendarView;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.month_preview_month_tab, null);

        // getting Data passed throw intent
        monthPreviewActivity = (MonthPreviewActivity) getActivity();
        username = monthPreviewActivity.getUserName();
        day = monthPreviewActivity.getDayString();
        month = monthPreviewActivity.getMonthNumberString();
        year = monthPreviewActivity.getYearString();

        // open DB
        usersDbHelper = new MyUsersDbHelper(view.getContext());

        // Add new event FAB
        fab = view.findViewById(R.id.month_preview_month_tab_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent(username);

            }
        });

        // list view components
        eventListView = view.findViewById(R.id.month_preview_month_tab_fragment_list);

        // if list is empty
        View emptyView = view.findViewById(R.id.month_preview_month_tab_fragment_empty_view);
        eventListView.setEmptyView(emptyView);

        // setting data to list
        Cursor cursor = usersDbHelper.readEvent(username, day, month, year);
        if (cursor.getCount() > 0) {
            eCursorAdapter = new EventCursorAdapter(getContext(), cursor);
            eventListView.setAdapter(eCursorAdapter);
        }

        materialCalendarView = view.findViewById(R.id.month_preview_month_calender);
        materialCalendarView.setTopbarVisible(false);
        materialCalendarView.setPagingEnabled(false);
        materialCalendarView.setSelectedDate(CalendarDay.from(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {

                int newDay = materialCalendarView.getSelectedDate().getDay();
                int newMonth = materialCalendarView.getSelectedDate().getMonth();
                int newYear = materialCalendarView.getSelectedDate().getYear();

                Cursor newCursor = usersDbHelper.readEvent(username, String.valueOf(newDay), String.valueOf(newMonth), String.valueOf(newYear));
                eCursorAdapter = new EventCursorAdapter(getContext(), newCursor);
                eventListView.setAdapter(eCursorAdapter);
            }
        });


        // Decoration of days that have events
        Cursor eventsCursor = usersDbHelper.readEvent(username);
        int length = eventsCursor.getCount();
        if (length > 0) {
            eventsDays = new ArrayList<>();
            for (eventsCursor.moveToFirst(); !eventsCursor.isAfterLast(); eventsCursor.moveToNext()) {
                int eventDayNu = eventsCursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY));
                int eventMonthNu = eventsCursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH));
                int eventYearNu = eventsCursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR));

                CalendarDay eventDay = CalendarDay.from(eventYearNu, eventMonthNu, eventDayNu);
                eventsDays.add(eventDay);
            }

            dotColor = getResources().getColor(R.color.colorAccent);
            materialCalendarView.addDecorator(new EventDecorator(dotColor, eventsDays));
        }


        return view;
    }

    private void addNewEvent(String name) {
        Intent ADDActivity = new Intent(getContext(), AddRemainderActivity.class);
        ADDActivity.putExtra("name", name);
        startActivity(ADDActivity);
    }
}
