package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.overday;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.FragmentCallBack;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.RecyclerViewClickListener;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.details.DetailsVacancyActivity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rojsa on 15.04.2018.
 */

public class VacanciesOverDayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener, VacancyOverDayContract.View, RecyclerViewClickListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private FragmentCallBack mCallBack;
    private VacanciesOverDayAdapter mAdapter;
    private ProgressBar mProgressBar;

    private VacancyOverDayContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacancies_over_day, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        mProgressBar = view.findViewById(R.id.progressBarSearchDialog);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnScrollListener(mScrollListener);

        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new VacancyOverDayPresenter(StartApplication.get(getContext()).getService(),
                StartApplication.get(getContext()).getSqLiteHelper());

        mPresenter.bind(this);

        mPresenter.dataFromSplashScreen(mCallBack.getAllVacancies());


    }

    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

        }
    };

    @Override
    public void onRefresh() {
        mPresenter.addToListVacancies();
    }

    public void filterListener(ArrayList<String> list) {
        mPresenter.filterGetData(list);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            mPresenter.refreshViewedList();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        startActivity(new Intent(getContext(), DetailsVacancyActivity.class)
                .putExtra("flag", true)
                .putExtra("position", position));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (FragmentCallBack) context;
    }

    @Override
    public void onSuccess(List<VacanciesModel> vacancyList) {
        mRecyclerView.setAdapter(mAdapter = new VacanciesOverDayAdapter(vacancyList, getContext(), this, mPresenter));
        Toast.makeText(getContext(), vacancyList.size() + " ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopRefreshing() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void adapterNotify() {
        mAdapter.notifyAll();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(getContext(), DetailsVacancyActivity.class)
                .putExtra("flag", true)
                .putExtra("position", position));
        Log.d("position", "position" + position);
    }
}
