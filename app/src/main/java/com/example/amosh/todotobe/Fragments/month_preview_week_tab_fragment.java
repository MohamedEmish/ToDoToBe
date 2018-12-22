package com.example.amosh.todotobe.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.amosh.todotobe.Adapters.EventAdapter;
import com.example.amosh.todotobe.Adapters.EventDecorator;
import com.example.amosh.todotobe.AddRemainderActivity;
import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class month_preview_week_tab_fragment extends Fragment implements EventAdapter.EventClickListener {

    MonthPreviewActivity monthPreviewActivity;
    String username;
    FloatingActionButton fab;
    String day;
    String month;
    String year;
    RelativeLayout emptyView;
    ArrayList<CalendarDay> eventsDays;

    int dotColor;

    RecyclerView eventListView;
    EventAdapter eEventAdapter;
    MyUsersDbHelper usersDbHelper;

    MaterialCalendarView materialCalendarView;
    ImageView actionImage;

    Dialog dialog;

    FrameLayout completeAction, snoozeAction, overdueAction, editAcion, deleteAction, closeAction;

    List<Events> eventsList;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.month_preview_week_tab, null);

        // getting Data passed throw intent
        monthPreviewActivity = (MonthPreviewActivity) getActivity();
        username = monthPreviewActivity.getUserName();
        day = monthPreviewActivity.getDayString();
        month = monthPreviewActivity.getMonthNumberString();
        year = monthPreviewActivity.getYearString();

        // open DB
        usersDbHelper = new MyUsersDbHelper(view.getContext());

        // Add new event FAB
        fab = view.findViewById(R.id.month_preview_week_tab_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent(username);

            }
        });

        // list view components
        eventListView = view.findViewById(R.id.month_preview_week_tab_fragment_list);
        emptyView = view.findViewById(R.id.month_preview_week_tab_fragment_empty_view);
        eventListView.addItemDecoration(new DividerItemDecoration(eventListView.getContext(), DividerItemDecoration.VERTICAL));
        eventListView.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting data to list
        List<Events> list = usersDbHelper.readEventList(username, day, month, year);
        if (list.size() == 0) {
            eventListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            eventListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        eEventAdapter = new EventAdapter(getContext(), list);
        eEventAdapter.setClickListener(new EventAdapter.EventClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCustomActionsDialog(getContext(), position);
            }
        });
        eventListView.setAdapter(eEventAdapter);

        materialCalendarView = view.findViewById(R.id.month_periview_week_calender);
        materialCalendarView.setTopbarVisible(false);
        materialCalendarView.setSelectedDate(CalendarDay.from(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {

                int newDay = materialCalendarView.getSelectedDate().getDay();
                int newMonth = materialCalendarView.getSelectedDate().getMonth();
                int newYear = materialCalendarView.getSelectedDate().getYear();

                eventsList = usersDbHelper.readEventList(username, String.valueOf(newDay), String.valueOf(newMonth), String.valueOf(newYear));
                eEventAdapter = new EventAdapter(getContext(), eventsList);
                eventListView.setAdapter(eEventAdapter);
                if (eventsList.size() == 0) {
                    eventListView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    eventListView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }
        });

        // Decoration of days that have events
        Cursor eventsCursor = usersDbHelper.readEvent(username);
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
            materialCalendarView.addDecorator(new EventDecorator(dotColor, eventsDays));
        }


        return view;

    }

    private void addNewEvent(String name) {
        Intent ADDActivity = new Intent(getContext(), AddRemainderActivity.class);
        ADDActivity.putExtra("name", name);
        ADDActivity.putExtra("id", "");
        startActivity(ADDActivity);
    }

    @Override
    public void onItemClick(View view, int position) {
        showCustomActionsDialog(getContext(), position);

    }

    public void showCustomActionsDialog(final Context context, final int iPosition) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_event_action_dialog, null, false);

        /*HERE YOU CAN FIND YOU IDS AND SET TEXTS OR BUTTONS*/

        actionImage = (ImageView) view.findViewById(R.id.event_frame_image);
        if (!eventsList.get(iPosition).getImage().equals("")) {
            Uri imageUri = Uri.parse(eventsList.get(iPosition).getImage());
            actionImage.setImageURI(imageUri);
        } else {
            actionImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_cal_white));
        }

        closeAction = (FrameLayout) view.findViewById(R.id.event_frame_close);
        closeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        completeAction = (FrameLayout) view.findViewById(R.id.event_frame_completed);
        completeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersDbHelper.updateEventState(eventsList.get(iPosition).getUserName(),
                        eventsList.get(iPosition).getTitle(), EventsContract.EventsEntry.STATE_COMPLETED,
                        eventsList.get(iPosition).getDateFromDay(), eventsList.get(iPosition).getDateToDay());
                eventsList.get(iPosition).setState(EventsContract.EventsEntry.STATE_COMPLETED);
                eEventAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        snoozeAction = (FrameLayout) view.findViewById(R.id.event_frame_snoozed);
        snoozeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersDbHelper.updateEventState(eventsList.get(iPosition).getUserName(),
                        eventsList.get(iPosition).getTitle(), EventsContract.EventsEntry.STATE_SNOOZED,
                        eventsList.get(iPosition).getDateFromDay(), eventsList.get(iPosition).getDateToDay());
                eventsList.get(iPosition).setState(EventsContract.EventsEntry.STATE_SNOOZED);
                eEventAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        overdueAction = (FrameLayout) view.findViewById(R.id.event_frame_overdued);
        overdueAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersDbHelper.updateEventState(eventsList.get(iPosition).getUserName(),
                        eventsList.get(iPosition).getTitle(), EventsContract.EventsEntry.STATE_OVERDUE,
                        eventsList.get(iPosition).getDateFromDay(), eventsList.get(iPosition).getDateToDay());
                eventsList.get(iPosition).setState(EventsContract.EventsEntry.STATE_OVERDUE);
                eEventAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        deleteAction = (FrameLayout) view.findViewById(R.id.event_frame_delete);
        deleteAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersDbHelper.deleteEvent(eventsList.get(iPosition).getTitle(), eventsList.get(iPosition).getUserName(),
                        eventsList.get(iPosition).getDateFromDay(), eventsList.get(iPosition).getDateToDay());
                eventsList.remove(iPosition);
                eEventAdapter.notifyItemRemoved(iPosition);

                if (eventsList.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    eventListView.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }
        });


        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.trans);
        window.setGravity(Gravity.BOTTOM);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

}

