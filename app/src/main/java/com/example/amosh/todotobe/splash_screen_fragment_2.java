package com.example.amosh.todotobe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class splash_screen_fragment_2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.splash_screen_2, null);

        TextView next = (TextView) rootView.findViewById(R.id.splash_screen_2_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                splash_screen_activity.viewPager.setCurrentItem(2);
            }
        });

        return rootView;
    }
}