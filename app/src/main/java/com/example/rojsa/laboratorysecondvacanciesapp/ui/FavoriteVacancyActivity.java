package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;

import java.util.List;

public class FavoriteVacancyActivity extends BaseActivity {
    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vacancies_over_day);
        sqLiteHelper = StartApplication.get(getApplicationContext()).getSqLiteHelper();
        getFavoriteData();
    }



    private void getFavoriteData(){
        ListView listView = findViewById(R.id.recycleView);
        List<AllDayModel> list = sqLiteHelper.getFavoriteVacancy();
        if (list.isEmpty())return;
        RecycleViewAdapter adapter = new RecycleViewAdapter(getApplicationContext(),list);
        listView.setAdapter(adapter);
    }
}
