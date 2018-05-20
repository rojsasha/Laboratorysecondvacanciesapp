package com.example.rojsa.laboratorysecondvacanciesapp;

import android.app.Application;
import android.content.Context;

import com.example.rojsa.laboratorysecondvacanciesapp.data.ConnectInternet;
import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;


public class StartApplication extends Application {
    private RequestInterface service;
    private SQLiteHelper sqLiteHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        service = ConnectInternet.initRetrofit();
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
    }

    public static StartApplication get(Context context) {
        return (StartApplication) context.getApplicationContext();
    }

    public RequestInterface getService() {
        return service;
    }

    public SQLiteHelper getSqLiteHelper() {
        return sqLiteHelper;
    }
}
