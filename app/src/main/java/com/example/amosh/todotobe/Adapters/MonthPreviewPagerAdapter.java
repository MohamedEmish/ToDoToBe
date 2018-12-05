package com.example.amosh.todotobe.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MonthPreviewPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public MonthPreviewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new com.example.amosh.todotobe.Fragments.month_preview_day_tab_fragment();
        } else if (position == 1) {
            return new com.example.amosh.todotobe.Fragments.month_preview_week_tab_fragment();
        } else {
            return new com.example.amosh.todotobe.Fragments.month_preview_month_tab_fargment();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
