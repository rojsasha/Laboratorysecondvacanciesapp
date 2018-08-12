package com.example.rojsa.laboratorysecondvacanciesapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.FragmentCallBack;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.CategoryFragment;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.main.overday.VacanciesOverDayFragment;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.search.SearchDialog;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.SearchDialogCallback;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements FragmentCallBack, View.OnClickListener, SearchDialogCallback {
    private List<VacanciesModel> mList;

    private ViewPager mViewPager;
    private TabStateAdapter mStateAdapter;

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

    }

    private void createTab() {
        ArrayList<TabMain> tabs = new ArrayList<>();
        tabs.add(new TabMain(new VacanciesOverDayFragment(), "Вакансии за сутки"));
        tabs.add(new TabMain(new CategoryFragment(), "Подходящие"));
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mStateAdapter = new TabStateAdapter(getSupportFragmentManager(), tabs));
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
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

    @Override
    public void searchByFilter(ArrayList<String> arrayList) {
        Fragment fragment = mStateAdapter.getFirstFragment();
        if (fragment instanceof VacanciesOverDayFragment) {
            ((VacanciesOverDayFragment) fragment).filterListener(arrayList);
        }
    }
}
