package com.example.amosh.todotobe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new splash_screen_fragment_1();
        } else if (position == 1){
            return new splash_screen_fragment_2();
        } else {
            return new splash_screen_fragment_3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}