package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.support.v4.app.Fragment;


public class TabMain {
    private final Fragment fragment;
    private final CharSequence title;

    public TabMain(Fragment fragment, CharSequence title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }
    public CharSequence getTitle(){
        return title;
    }
}
