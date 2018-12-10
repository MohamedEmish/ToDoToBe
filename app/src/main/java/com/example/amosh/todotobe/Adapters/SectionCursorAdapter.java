package com.example.amosh.todotobe.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.R;

import java.util.Calendar;
import java.util.Locale;


public class SectionCursorAdapter extends CursorAdapter {

    MyUsersDbHelper usersDbHelper;
    EventCursorAdapter eCursorAdapter;
    String myUsername;

    public SectionCursorAdapter(Context context, Cursor c, String username) {
        super(context, c);
        myUsername = username;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.section_custom_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView dayNameTextView = (TextView) view.findViewById(R.id.section_day_name_label);
        TextView dayTextView = (TextView) view.findViewById(R.id.section_day_label);
        TextView monthTextView = (TextView) view.findViewById(R.id.section_month_label);
        TextView yearTextView = (TextView) view.findViewById(R.id.section_year_label);
        ListView listView = (ListView) view.findViewById(R.id.section_item_list_view);

        int dateFromDay = cursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_DAY));
        int dateFromMonth = cursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_MONTH));
        int dateFromYear = cursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_DATE_FROM_YEAR));

        usersDbHelper = new MyUsersDbHelper(context);
        String day = String.valueOf(dateFromDay);
        String month = String.valueOf((dateFromMonth));
        String year = String.valueOf(dateFromYear);


        Cursor listCursor = usersDbHelper.readEvent(myUsername, day, month, year);
        listCursor.moveToFirst();
//        for (listCursor.moveToFirst(); !listCursor.isAfterLast(); listCursor.moveToNext()) {
//            setListViewHeightBasedOnChildren(listView);
//            eCursorAdapter = new EventCursorAdapter(context, listCursor);
//            listView.setAdapter(eCursorAdapter);
//
//        }

//        setListViewHeightBasedOnChildren(listView);

        eCursorAdapter = new EventCursorAdapter(context, listCursor);
        listView.setAdapter(eCursorAdapter);


        Calendar calendar = Calendar.getInstance();
        calendar.set(dateFromYear, dateFromMonth - 1, dateFromDay);
        String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);

        dayNameTextView.setText(dayName);
        dayTextView.setText(day);
        monthTextView.setText(monthName);
        yearTextView.setText(year);

    }
}
