package com.example.amosh.todotobe.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.EventsContract;
import com.example.amosh.todotobe.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public EventAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String title = mCursor.getString(mCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TITLE));
        String people = mCursor.getString(mCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_PEOPLE));
        int timeFrom = mCursor.getInt(mCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TIME_FROM_HOUR));
        int timeTo = mCursor.getInt(mCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_TIME_TO_HOUR));
        String location = mCursor.getString(mCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_LOCATION));
        String imageUriString = mCursor.getString(mCursor.getColumnIndex(EventsContract.EventsEntry.COLUMN_IMAGE));

        if (!imageUriString.equals("")) {
            Uri imageUri = Uri.parse(imageUriString);
            holder.eImage.setImageURI(imageUri);
        } else {
            holder.eImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_calendar_no_eimage));
        }


        if (timeFrom > 12) {
            holder.timeFromTextView.setText(String.valueOf(timeFrom - 12));
        } else {
            holder.timeFromTextView.setText(String.valueOf(timeFrom));
        }

        if (timeTo > 12) {
            holder.timeToTextView.setText(String.valueOf(timeTo - 12));
        } else {
            holder.timeToTextView.setText(String.valueOf(timeTo));
        }

        if (people.equals("")) {
            holder.withTextView.setVisibility(View.GONE);
        }

        holder.titleTextView.setText(title);
        holder.peopleTextView.setText(people);
        holder.locationTextView.setText(location);

        if (timeTo >= 12) {
            holder.am_pmTextView.setText("pm");
        }
        if (timeTo < 12) {
            holder.am_pmTextView.setText("am");
        }

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();

        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView peopleTextView;
        public TextView timeFromTextView;
        public TextView timeToTextView;
        public TextView am_pmTextView;
        public TextView locationTextView;
        public TextView withTextView;
        public ImageView eImage;
        public LinearLayout stateColor;

        public EventViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.list_event_title);
            peopleTextView = itemView.findViewById(R.id.list_event_people);
            timeFromTextView = itemView.findViewById(R.id.list_event_time_from);
            timeToTextView = itemView.findViewById(R.id.list_event_time_to);
            am_pmTextView = itemView.findViewById(R.id.list_event_time_to_am_pm);
            locationTextView = itemView.findViewById(R.id.list_event_location);
            withTextView = itemView.findViewById(R.id.list_event_with);
            eImage = itemView.findViewById(R.id.list_event_image);
            stateColor = itemView.findViewById(R.id.list_event_state_color);
        }
    }
}