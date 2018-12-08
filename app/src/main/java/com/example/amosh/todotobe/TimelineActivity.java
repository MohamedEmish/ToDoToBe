package com.example.amosh.todotobe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TimelineActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);

        username = getIntent().getStringExtra("name");
    }
}
