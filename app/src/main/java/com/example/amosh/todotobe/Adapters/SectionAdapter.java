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

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    private Context mContext;
    List<Events> eventsList;
    private MyUsersDbHelper usersDbHelper;
    private String myUsername;
    private EventAdapter eEventAdapter;
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
}

