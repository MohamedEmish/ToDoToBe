package com.example.amosh.todotobe.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.Events;
import com.example.amosh.todotobe.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mContext;
    private List<Events> mEventsArrayList;
    private EventClickListener mClickListener;
    private EventLongClickListener mLongClickListener;

    public EventAdapter(Context context, List<Events> arrayList) {
        mContext = context;
        mEventsArrayList = arrayList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        int state = mEventsArrayList.get(position).getState();
        switch (state) {
            case 0:
                holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                break;
            case 1:
                holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
            case 2:
                holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                break;
            case 3:
                holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.light_purple));
                break;
        }
        String imageUriString = mEventsArrayList.get(position).getImage();

        if (!imageUriString.equals("")) {
            Uri imageUri = Uri.parse(imageUriString);
            holder.eImage.setImageURI(imageUri);
        } else {
            holder.eImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_calendar_no_eimage));
        }

        int timeFrom = mEventsArrayList.get(position).getTimeFromHour();
        if (timeFrom > 12) {
            holder.timeFromTextView.setText(String.valueOf(timeFrom - 12));
        } else {
            holder.timeFromTextView.setText(String.valueOf(timeFrom));
        }

        int timeTo = mEventsArrayList.get(position).getTimeToHour();
        if (timeTo > 12) {
            holder.timeToTextView.setText(String.valueOf(timeTo - 12));
        } else {
            holder.timeToTextView.setText(String.valueOf(timeTo));
        }

        String people = mEventsArrayList.get(position).getPeople();
        if (people.equals("")) {
            holder.withTextView.setVisibility(View.GONE);
        }

        holder.titleTextView.setText(mEventsArrayList.get(position).getTitle());
        holder.peopleTextView.setText(people);
        holder.locationTextView.setText(mEventsArrayList.get(position).getLocation());

        if (timeTo >= 12) {
            holder.am_pmTextView.setText("pm");
        }
        if (timeTo < 12) {
            holder.am_pmTextView.setText("am");
        }

    }

    @Override
    public int getItemCount() {
        return mEventsArrayList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(EventClickListener eventClickListener) {
        this.mClickListener = eventClickListener;
    }

    public void setLongClickListener(EventLongClickListener eventLongClickListener) {
        this.mLongClickListener = eventLongClickListener;
    }

    // return data
    public String getItemName(int id) {
        return mEventsArrayList.get(id).getTitle();
    }

    // parent activity will implement this method to respond to click events
    public interface EventClickListener {
        void onItemClick(View view, int position);
    }

    public interface EventLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


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
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            } else {

                return false;
            }
        }
    }
}