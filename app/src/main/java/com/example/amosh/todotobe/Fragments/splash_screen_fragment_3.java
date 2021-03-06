package com.example.amosh.todotobe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amosh.todotobe.MainScreenActivity;
import com.example.amosh.todotobe.R;

public class splash_screen_fragment_3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.splash_screen_3, null);


        TextView begin = (TextView) rootView.findViewById(R.id.splash_3_begin);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startMainScreenActivity();
            }
        });

        return rootView;


    }

    private void startMainScreenActivity() {
        splash_screen_activity splash_screen_activity = (com.example.amosh.todotobe.Fragments.splash_screen_activity) getActivity();
        String userName = splash_screen_activity.getUserName();
        Intent begin = new Intent(getActivity(), MainScreenActivity.class);
        begin.putExtra("name", userName);
        startActivity(begin);
    }
}