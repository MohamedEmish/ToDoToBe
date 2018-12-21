package com.example.amosh.todotobe.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RelativeLayout;

import com.example.amosh.todotobe.Adapters.EventAdapter;
import com.example.amosh.todotobe.AddRemainderActivity;
import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.R;

import java.util.List;

public class month_preview_day_tab_fragment extends Fragment implements EventAdapter.EventClickListener {

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

    Dialog dialog;

    FrameLayout completeAction, snoozeAction, overdueAction, editAcion, deleteAction, closeAction;

    List<Events> eventsList;
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
        eventsList = usersDbHelper.readEventList(username, day, month, year);
        if (eventsList.size() == 0) {
            eventListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            eventListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        eEventAdapter = new EventAdapter(getContext(), eventsList);
        eEventAdapter.setClickListener(new EventAdapter.EventClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCustomActionsDialog(getContext(), position);
            }
        });
        eventListView.setAdapter(eEventAdapter);

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

                dialog.dismiss();
            }
        });


        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.trans);
        window.setGravity(Gravity.BOTTOM);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

}
