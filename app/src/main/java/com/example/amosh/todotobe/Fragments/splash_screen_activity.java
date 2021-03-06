package com.example.amosh.todotobe.Fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.amosh.todotobe.Adapters.MyFragmentPageAdapter;
import com.example.amosh.todotobe.R;


public class splash_screen_activity extends AppCompatActivity {

    public static ViewPager viewPager;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userName = getIntent().getStringExtra("name");

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.splash_main);

        // Find the view pager that will allow the user to swipe between fragments

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page

        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

    }

    public String getUserName() {
        return userName;
    }

}