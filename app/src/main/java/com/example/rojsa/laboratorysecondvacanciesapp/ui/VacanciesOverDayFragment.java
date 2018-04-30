package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.FragmentCallBack;
import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rojsa on 15.04.2018.
 */


public class VacanciesOverDayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private RequestInterface service;
    private ListView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private List<AllDayModel> listVacancy;
    private int refreshLimit = 20;
    private SQLiteHelper sqLiteHelper;
    private FragmentCallBack mCallBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacancies_over_day, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recycleView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        sqLiteHelper = StartApplication.get(getContext()).getSqLiteHelper();
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mCallBack.getAllVacancies() == null) {
            listVacancy = sqLiteHelper.getAllVacanciesOverDay();
            RecycleViewAdapter adapter = new RecycleViewAdapter(getContext(), listVacancy);
            recyclerView.setAdapter(adapter);
            Toast.makeText(getContext(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
        } else {
            listVacancy = mCallBack.getAllVacancies();
            RecycleViewAdapter adapter = new RecycleViewAdapter(getContext(), listVacancy);
            recyclerView.setAdapter(adapter);
            saveVacanciesOverDay();
        }
    }

    private void getData() {

        service = StartApplication.get(getContext()).getService();
        service.getAllVacancies("au", "get_all_vacancies", String.valueOf(refreshLimit), "1")
                .enqueue(new Callback<List<AllDayModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<AllDayModel>> call, @NonNull Response<List<AllDayModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listVacancy = response.body();
                            RecycleViewAdapter adapter = new RecycleViewAdapter(getContext(), listVacancy);
                            recyclerView.setAdapter(adapter);
                            refreshLayout.setRefreshing(false);
                            saveVacanciesOverDay();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<AllDayModel>> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    public void onRefresh() {
        refreshLimit = refreshLimit + 20;
        getData();

    }

    private void saveVacanciesOverDay() {
        sqLiteHelper.saveAllVacanciesOverDay(listVacancy);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getContext(), DetailsVacancyActivity.class);
      intent.putExtra("modelVacancy",listVacancy.get(position));
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (FragmentCallBack) context;

    }
}
