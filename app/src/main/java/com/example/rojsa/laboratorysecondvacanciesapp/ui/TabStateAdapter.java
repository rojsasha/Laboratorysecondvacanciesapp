package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by rojsa on 15.04.2018.
 */

public class TabStateAdapter extends FragmentStatePagerAdapter {
    ArrayList<TabMain> tabs;

    public TabStateAdapter(FragmentManager fm, ArrayList<TabMain> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

}
