package com.example.amosh.todotobe.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.amosh.todotobe.Adapters.EventAdapter;
import com.example.amosh.todotobe.AddRemainderActivity;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.R;

public class month_preview_day_tab_fragment extends Fragment {

    MonthPreviewActivity monthPreviewActivity;
    String username;
    FloatingActionButton fab;
    String day;
    String month;
    String year;
    RelativeLayout emptyView;

    RecyclerView eventListView;
    EventAdapter eEventAdapter;
    MyUsersDbHelper usersDbHelper;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.month_preview_day_tab, null);

        // getting Data passed throw intent
        monthPreviewActivity = (MonthPreviewActivity) getActivity();
        username = monthPreviewActivity.getUserName();
        day = monthPreviewActivity.getDayString();
        month = monthPreviewActivity.getMonthNumberString();
        year = monthPreviewActivity.getYearString();

        // open DB
        usersDbHelper = new MyUsersDbHelper(view.getContext());

        // Add new event FAB
        fab = view.findViewById(R.id.month_preview_day_tab_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent(username);

            }
        });

        eventListView = view.findViewById(R.id.month_preview_day_tab_fragment_list);
        emptyView = view.findViewById(R.id.month_preview_day_tab_fragment_empty_view);
        eventListView.addItemDecoration(new DividerItemDecoration(eventListView.getContext(), DividerItemDecoration.VERTICAL));
        eventListView.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting data to list
        Cursor cursor = usersDbHelper.readEvent(username, day, month, year);
        if (cursor.getCount() == 0) {
            eventListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            eventListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        eEventAdapter = new EventAdapter(getContext(), cursor);
        eventListView.setAdapter(eEventAdapter);

        return view;
    }

    private void addNewEvent(String name) {
        Intent ADDActivity = new Intent(getContext(), AddRemainderActivity.class);
        ADDActivity.putExtra("name", name);
        startActivity(ADDActivity);
    }

}
