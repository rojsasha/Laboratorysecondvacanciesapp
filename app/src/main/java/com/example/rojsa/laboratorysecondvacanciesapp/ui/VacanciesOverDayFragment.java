package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface;
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rojsa on 15.04.2018.
 */


public class VacanciesOverDayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener {
    private RequestInterface service;
    private ListView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private List<AllDayModel> listVacancy;
    int refreshLimit = 20;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacancies_over_day, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(this);
        recyclerView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData() {
        service = StartApplication.get(getContext()).getService();
        service.getAllVacancies("au", "get_all_vacancies", String.valueOf(refreshLimit), "1")
                .enqueue(new Callback<List<AllDayModel>>() {
                    @Override
                    public void onResponse(Call<List<AllDayModel>> call, Response<List<AllDayModel>> response) {
                        listVacancy = response.body();
                        RecycleViewAdapter adapter = new RecycleViewAdapter(getContext(),listVacancy);
                        recyclerView.setAdapter(adapter);


                    }

                    @Override
                    public void onFailure(Call<List<AllDayModel>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onRefresh() {
        refreshLimit = refreshLimit + 20;
        getData();
        refreshLayout.setRefreshing(false);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getContext(),DetailsVacancyActivity.class);
        intent.putExtra("listVacancy", (Serializable) listVacancy);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
