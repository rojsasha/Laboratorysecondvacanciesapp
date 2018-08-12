package com.example.rojsa.laboratorysecondvacanciesapp.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.AdapterVacanciesBase;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.RecyclerViewClickListener;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.base.BaseActivity;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.details.DetailsVacancyActivity;

import java.util.List;

public class FavoriteVacancyActivity extends BaseActivity implements AdapterView.OnItemClickListener, FavoriteVacancyContract.View
        , RecyclerViewClickListener {
    private ListView mListView;
    private FavoriteVacancyPresenter mPresenter;
    private AdapterVacanciesBase mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vacancies_over_day);
//        mListView = findViewById(R.id.listView);
//        mListView.setOnItemClickListener(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter = new FavoriteVacancyPresenter(
                StartApplication.get(getApplicationContext()).getSqLiteHelper()
        );
        mPresenter.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Избранные вакансии");
        createDrawer(toolbar, false);

        mPresenter.getSavedList();
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


    @Override
    public void onSuccess(List<VacanciesModel> list) {
//        mListView.setAdapter(new ListViewAdapter(this,list,1));
        mRecyclerView.setAdapter(mAdapter = new FavoriteVacanciesAdapter(list, getApplicationContext(), this, mPresenter));
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(getApplicationContext(), DetailsVacancyActivity.class)
                .putExtra("flag", false)
                .putExtra("position", position));
    }
}
