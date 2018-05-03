package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.support.v4.app.Fragment;


public class TabMain {
    private final Fragment mFragment;
    private final CharSequence mTitle;

    public TabMain(Fragment mFragment, CharSequence title) {
        this.mFragment = mFragment;
        this.mTitle = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }
    public CharSequence getTitle(){
        return mTitle;
    }
}
