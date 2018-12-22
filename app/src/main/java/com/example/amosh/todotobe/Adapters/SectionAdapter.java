package com.example.amosh.todotobe.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.R;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> implements EventAdapter.EventClickListener {

    private Context mContext;
    List<Events> eventsList;
    private MyUsersDbHelper usersDbHelper;
    private String myUsername;
    private EventAdapter eEventAdapter;
    Dialog dialog;
    FrameLayout completeAction, snoozeAction, overdueAction, editAction, deleteAction, closeAction;
    private List<Events> mEventsSectionList;


    public SectionAdapter(Context context, List<Events> list, String username) {

        mContext = context;
        mEventsSectionList = list;
        myUsername = username;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.section_custom_layout, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {

        int dateFromDay = mEventsSectionList.get(position).getDateFromDay();
        int dateFromMonth = mEventsSectionList.get(position).getDateFromMonth();
        int dateFromYear = mEventsSectionList.get(position).getDateFromYear();

        usersDbHelper = new MyUsersDbHelper(mContext);
        String day = String.valueOf(dateFromDay);
        String month = String.valueOf((dateFromMonth));
        String year = String.valueOf(dateFromYear);

        eventsList = usersDbHelper.readEventList(myUsername, day, month, year);
        Collections.reverse(eventsList);

        RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(layoutManager);

        eEventAdapter = new EventAdapter(mContext, eventsList);
        eEventAdapter.setClickListener(new EventAdapter.EventClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCustomActionsDialog(mContext, position);
            }
        });
        holder.recyclerView.addItemDecoration(new DividerItemDecoration(holder.recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        holder.recyclerView.setAdapter(eEventAdapter);


        Calendar calendar = Calendar.getInstance();
        calendar.set(dateFromYear, dateFromMonth - 1, dateFromDay);
        String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);

        holder.dayNameTextView.setText(dayName);
        holder.dayTextView.setText(day);
        holder.monthTextView.setText(monthName);
        holder.yearTextView.setText(year);
    }

    @Override
    public int getItemCount() {
        return mEventsSectionList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {


        TextView dayNameTextView;
        TextView dayTextView;
        TextView monthTextView;
        TextView yearTextView;
        RecyclerView recyclerView;

        public SectionViewHolder(View view) {
            super(view);

            dayNameTextView = (TextView) view.findViewById(R.id.section_day_name_label);
            dayTextView = (TextView) view.findViewById(R.id.section_day_label);
            monthTextView = (TextView) view.findViewById(R.id.section_month_label);
            yearTextView = (TextView) view.findViewById(R.id.section_year_label);
            recyclerView = (RecyclerView) view.findViewById(R.id.section_item_list_view);
        }
    }


    @Override
    public void onItemClick(View view, int position) {

        showCustomActionsDialog(mContext, position);

    }


    public void showCustomActionsDialog(final Context context, final int iPosition) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

                if (eventsList.size() == 0) {
                    mEventsSectionList.clear();
                    mEventsSectionList = usersDbHelper.readEventSectionList(myUsername);
                    notifyDataSetChanged();
                }
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

