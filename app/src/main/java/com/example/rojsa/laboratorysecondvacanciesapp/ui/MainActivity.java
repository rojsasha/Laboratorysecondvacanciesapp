package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements FragmentCallBack, View.OnClickListener {
    private List<VacanciesModel> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        createTab();
        ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        setSupportActionBar(toolbar);
        createDrawer(toolbar, true);
        Intent intent = getIntent();
        mList = intent.getParcelableArrayListExtra("listVacancy");
        ArrayList<String> mSalary = new ArrayList<>();

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
    public List<VacanciesModel> getAllVacancies() {
        return mList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "resume main activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        SearchDialog dialog = new SearchDialog();
        dialog.show(getSupportFragmentManager(), "search");
    }

}
