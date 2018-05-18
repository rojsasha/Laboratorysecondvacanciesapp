package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rojsa on 15.04.2018.
 */

public class VacanciesOverDayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener {
    private ListView mListView;
    private SwipeRefreshLayout mRefreshLayout;
    private List<VacanciesModel> mListVacancy;
    private int mRefreshLimit = 1;
    private SQLiteHelper mSQLiteHelper;
    private FragmentCallBack mCallBack;
    private ListViewAdapter mAdapter;
    private String mSalary = "", mTerm = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacancies_over_day, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        mListView = view.findViewById(R.id.listView);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mSQLiteHelper = StartApplication.get(getContext()).getSqLiteHelper();
        mRefreshLayout.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mCallBack.getAllVacancies() == null) {
            mListVacancy = mSQLiteHelper.getAllVacanciesOverDay();
            mAdapter = new ListViewAdapter(getContext(), mListVacancy);
            mListView.setAdapter(mAdapter);
            Toast.makeText(getContext(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
        } else {
            mListVacancy = mCallBack.getAllVacancies();
            mAdapter = new ListViewAdapter(getContext(), mListVacancy);
            mListView.setAdapter(mAdapter);
            saveVacanciesOverDay();

        }
    }

    private void getData() {
        RequestInterface mService = StartApplication.get(getContext()).getService();
        mService.getSearchVacancies("au", "get_post_by_filter", "20", String.valueOf(mRefreshLimit), mSalary,mTerm)
                .enqueue(new Callback<List<VacanciesModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacanciesModel>> call, @NonNull Response<List<VacanciesModel>> response) {
                        if (response.body() != null && response.isSuccessful()) {

                            mListVacancy.addAll(response.body());
                            Toast.makeText(getContext(), "rerererer", Toast.LENGTH_SHORT).show();
                            mAdapter = new ListViewAdapter(getContext(), mListVacancy);
                            mListView.setAdapter(mAdapter);
                            mRefreshLayout.setRefreshing(false);
                            saveVacanciesOverDay();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<VacanciesModel>> call, @NonNull Throwable t) {
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onRefresh() {
        mRefreshLimit += 1;
        getData();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void filterListener(ArrayList<String> list) {
        if (!list.isEmpty()) {
            mTerm = list.get(0);
            mSalary = list.get(1);

            Toast.makeText(getContext(), mSalary + " " + mTerm, Toast.LENGTH_SHORT).show();
            mListVacancy.clear();
            getData();
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "onPause", Toast.LENGTH_SHORT).show();
    }

    private void saveVacanciesOverDay() {
        mSQLiteHelper.saveAllVacanciesOverDay(mListVacancy);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getContext(), DetailsVacancyActivity.class);
        intent.putExtra("modelVacancy", mListVacancy.get(position));
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (FragmentCallBack) context;


    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Toast.makeText(getContext(), "onAttachFragment", Toast.LENGTH_SHORT).show();
    }
}
