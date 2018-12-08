package com.example.amosh.todotobe.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.R;

public class EventCursorAdapter extends CursorAdapter {


    public EventCursorAdapter(Context context, Cursor c) {
        super(context, c);
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
        TextView withTextView = (TextView) view.findViewById(R.id.list_event_with);
        ImageView eImage = (ImageView) view.findViewById(R.id.list_event_image);
        LinearLayout stateColor = (LinearLayout) view.findViewById(R.id.list_event_state_color);

        String title = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TITLE));
        String people = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_PEOPLE));
        int timeFrom = cursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR));
        int timeTo = cursor.getInt(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR));
        String location = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_LOCATION));
        String imageUriString = cursor.getString(cursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_IMAGE));

        if (!imageUriString.equals("")) {
            Uri imageUri = Uri.parse(imageUriString);
            eImage.setImageURI(imageUri);
        } else {
            eImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_calendar_no_eimage));
        }


        if (timeFrom > 12) {
            timeFromTextView.setText(String.valueOf(timeFrom - 12));
        } else {
            timeFromTextView.setText(String.valueOf(timeFrom));
        }

        if (timeTo > 12) {
            timeToTextView.setText(String.valueOf(timeTo - 12));
        } else {
            timeToTextView.setText(String.valueOf(timeTo));
        }

        if (people.equals("")) {
            withTextView.setVisibility(View.GONE);
        }

        titleTextView.setText(title);
        peopleTextView.setText(people);
        locationTextView.setText(location);

        if (timeTo >= 12) {
            am_pmTextView.setText("pm");
        }
        if (timeTo < 12) {
            am_pmTextView.setText("am");
        }


//        if (state.equals("1")){stateColor.setBackgroundColor(context.getResources().getColor(R.color.green)); }
//        else if (state.equals("2")){stateColor.setBackgroundColor(context.getResources().getColor(R.color.orange)); }
//        else if (state.equals("3")){stateColor.setBackgroundColor(context.getResources().getColor(R.color.light_purple)); }
//        else {stateColor.setBackgroundColor(context.getResources().getColor(R.color.white_color)); }


//        long id = cursor.getLong(cursor.getColumnIndex(EventsContract.EventsEntry._ID));

    }
}
