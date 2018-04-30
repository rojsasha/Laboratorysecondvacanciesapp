package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.TabMain;
import com.example.rojsa.laboratorysecondvacanciesapp.data.FragmentCallBack;
import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface;
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements FragmentCallBack {
    private RequestInterface service;
    private List<AllDayModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        createTab();
        setSupportActionBar(toolbar);
        createDrawer(toolbar);
        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("listVacancy");

    }

    private void createTab() {
        ArrayList<TabMain> tabs = new ArrayList<>();
        tabs.add(new TabMain(new VacanciesOverDayFragment(), "Вакансии за сутки"));
        tabs.add(new TabMain(new SuitableFragment(), "Подходящие"));
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new TabStateAdapter(getSupportFragmentManager(), tabs));
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public List<AllDayModel> getAllVacancies() {
        return list;
    }
}
