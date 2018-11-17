package com.example.amosh.todotobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class splash_screen_fragment_1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.splash_screen_1, null);

        TextView next = (TextView) rootView.findViewById(R.id.splash_screen_1_next);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                splash_screen_activity.viewPager.setCurrentItem(1);
            }
        });

        return rootView;
    }
}