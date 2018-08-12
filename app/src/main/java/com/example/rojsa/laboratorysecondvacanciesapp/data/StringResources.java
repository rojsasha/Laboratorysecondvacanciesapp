package com.example.rojsa.laboratorysecondvacanciesapp.data;

import android.content.Context;

public class StringResources {
    private Context mContext;

    public StringResources(Context context) {
        mContext = context;
    }

    public String getString(int resId) {
        return mContext.getString(resId);
    }
}
