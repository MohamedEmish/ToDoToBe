package com.example.amosh.todotobe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amosh.todotobe.Adapters.EventCursorAdapter;
import com.example.amosh.todotobe.Adapters.SectionCursorAdapter;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    String username;
    EditText searchEditText;
    ImageView searchIcon;
    String searchText;

    ListView listView;
    SectionCursorAdapter sCursorAdapter;
    EventCursorAdapter eCursorAdapter;
    MyUsersDbHelper usersDbHelper;
    ArrayList<String> eventsDays;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);

        username = getIntent().getStringExtra("name");
        usersDbHelper = new MyUsersDbHelper(this);

        listView = (ListView) findViewById(R.id.timeline_list);
        Cursor cursor = usersDbHelper.readEventSection(username);
        sCursorAdapter = new SectionCursorAdapter(this, cursor, username);
        listView.setAdapter(sCursorAdapter);

        // Search TextView and Function
        searchEditText = (EditText) findViewById(R.id.timeline_search_text);
        searchIcon = (ImageView) findViewById(R.id.timeline_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchEditText.getVisibility() == View.GONE) {
                    searchEditText.setVisibility(View.VISIBLE);
                } else {
                    searchText = searchEditText.getText().toString().trim();
                    if (searchText.length() == 0) {
                        searchEditText.setVisibility(View.GONE);
                    } else {
                        searchForEvent(searchText);
                    }
                }
            }
        });
    }

    // search function
    private void searchForEvent(String searchText) {
        // TODO : // search method
        Toast.makeText(TimelineActivity.this, "SEARCHING FOR " + searchText, Toast.LENGTH_SHORT).show();
    }

    public String getUserName() {
        return username;
    }

}
