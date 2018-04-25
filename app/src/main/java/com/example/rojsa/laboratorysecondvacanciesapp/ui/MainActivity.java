package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.TabMain;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        createTab();
        setSupportActionBar(toolbar);
        createDrawer(toolbar);

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
}
