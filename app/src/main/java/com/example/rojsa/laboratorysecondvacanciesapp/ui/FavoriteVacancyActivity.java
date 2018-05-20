package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.util.List;

public class FavoriteVacancyActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private SQLiteHelper mSQLiteHelper;
    private List<VacanciesModel> list;
    private ListViewAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vacancies_over_day);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        getSupportActionBar().setTitle("Избранные вакансии");
        createDrawer(toolbar,false);
        mSQLiteHelper = StartApplication.get(getApplicationContext()).getSqLiteHelper();
        getFavoriteData();

    }

    private void getFavoriteData(){
        ListView listView = findViewById(R.id.listView);
        list = mSQLiteHelper.getFavoriteVacancy();
        if (list.isEmpty())return;
        mAdapter = new ListViewAdapter(getApplicationContext(),list,false);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null)
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this, DetailsVacancyActivity.class);
        intent.putExtra("flag", false);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
