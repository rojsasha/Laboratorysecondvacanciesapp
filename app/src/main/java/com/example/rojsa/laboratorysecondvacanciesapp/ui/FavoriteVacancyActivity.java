package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.util.List;

public class FavoriteVacancyActivity extends BaseActivity {
    SQLiteHelper mSQLiteHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vacancies_over_day);
        Toolbar toolbar = findViewById(R.id.toolbar);
        createDrawer(toolbar);
        mSQLiteHelper = StartApplication.get(getApplicationContext()).getSqLiteHelper();
        getFavoriteData();
    }



    private void getFavoriteData(){
        ListView listView = findViewById(R.id.recycleView);
        List<VacanciesModel> list = mSQLiteHelper.getFavoriteVacancy();
        if (list.isEmpty())return;
        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(),list);
        listView.setAdapter(adapter);
    }
}
