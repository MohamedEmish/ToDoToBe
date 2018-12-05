package com.example.amosh.todotobe.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.MainScreenActivity;
import com.example.amosh.todotobe.R;
import com.squareup.picasso.Picasso;

public class EventCursorAdapter extends CursorAdapter {


    private final MainScreenActivity activity;

    public EventCursorAdapter(MainScreenActivity context, Cursor c) {
        super(context, c, 0);
        this.activity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView titleTextView = (TextView) view.findViewById(R.id.list_event_title);
        TextView peopleTextView = (TextView) view.findViewById(R.id.list_event_people);
        TextView timeFromTextView = (TextView) view.findViewById(R.id.list_event_time_from);
        TextView timeToTextView = (TextView) view.findViewById(R.id.list_event_time_to);
        TextView am_pmTextView = (TextView) view.findViewById(R.id.list_event_time_to_am_pm);
        TextView locationTextView = (TextView) view.findViewById(R.id.list_event_location);

        ImageView eImage = (ImageView) view.findViewById(R.id.list_event_image);

        String title = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TITLE));
        String people = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_PEOPLE));
        String timeFrom = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR));
        String timeTo = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR));
        String location = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_LOCATION));

        Uri imageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_IMAGE)));

        Picasso.with(context).load(imageUri).into(eImage);

        titleTextView.setText(title);
        peopleTextView.setText(people);
        timeFromTextView.setText(timeFrom);
        timeToTextView.setText(timeTo);
        locationTextView.setText(location);

        int hour = Integer.valueOf(timeTo);
        if (hour >= 12) {
            am_pmTextView.setText("pm");
        }
        if (hour < 12) {
            am_pmTextView.setText("am");
        }

        long id = cursor.getLong(cursor.getColumnIndex(EventsContract.EventsEntry._ID));

    }
}
