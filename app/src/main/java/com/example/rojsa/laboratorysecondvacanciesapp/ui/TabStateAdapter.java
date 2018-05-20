package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by rojsa on 15.04.2018.
 */

public class TabStateAdapter extends FragmentStatePagerAdapter {
    private ArrayList<TabMain> mTabs;

    TabStateAdapter(FragmentManager fm, ArrayList<TabMain> tabs) {
        super(fm);
        this.mTabs = tabs;
    }

    public Fragment getFirstFragment() {
        return mTabs.get(0).getFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return mTabs.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }

}
