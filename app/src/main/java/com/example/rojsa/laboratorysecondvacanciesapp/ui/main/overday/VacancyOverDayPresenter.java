package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.overday;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.rubrics.Rubrics;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacancyOverDayPresenter implements VacancyOverDayContract.Presenter {
    private RequestInterface mService;
    private List<VacanciesModel> mListVacancy;
    private SQLiteHelper mSQLiteHelper;
    private int mRefreshLimit = 1;
    private VacancyOverDayContract.View mView;
    private String mSalary = "", mTerm = "";
    private ArrayList<String> listViewed;
    private ArrayList<String> favoriteList;

    VacancyOverDayPresenter(RequestInterface service, SQLiteHelper sqLiteHelper) {
        mService = service;
        mSQLiteHelper = sqLiteHelper;
    }

    @Override
    public void getDataOverDay() {
        refreshViewedList();
        mService.getSearchVacancies("login", "f", "20", String.valueOf(mRefreshLimit), mSalary, mTerm)
                .enqueue(new Callback<List<VacanciesModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacanciesModel>> call, @NonNull Response<List<VacanciesModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mListVacancy.addAll(response.body());
                            mView.onSuccess(mListVacancy);
                            saveVacanciesOverDay(response.body());
                            mView.stopRefreshing();
                        } else {
                            mView.stopRefreshing();
                            mView.onError("Не найдено вакансий");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<VacanciesModel>> call, @NonNull Throwable t) {
                        mView.stopRefreshing();
                        mView.onError("Нет подключения к интернету");
                    }
                });
    }

    @Override
    public void dataFromSplashScreen(List<VacanciesModel> vacancyList) {
        if (vacancyList == null) {
            mView.onSuccess(mListVacancy = mSQLiteHelper.getAllVacanciesOverDay());
            mView.onError("Нет подключения к интернету");
        } else {
            deleteAllVacancies();
            mView.onSuccess(mListVacancy = vacancyList);
            saveVacanciesOverDay(mListVacancy);
        }
    }

    @Override
    public void filterGetData(ArrayList<String> list) {
        if (!list.isEmpty()) {
            mTerm = list.get(0);
            mSalary = list.get(1);
            mRefreshLimit = 1;
            mListVacancy.clear();
            mSQLiteHelper.deleteAllVacanciesOverDay();
            getDataOverDay();
        }
    }

    private void deleteAllVacancies() {
        mSQLiteHelper.deleteAllVacanciesOverDay();
    }

    @Override
    public void addToListVacancies() {
        mRefreshLimit += 1;
        getDataOverDay();
    }

    @Override
    public void saveVacancy(int position) {
        mSQLiteHelper.saveFavoriteVacancy(mListVacancy.get(position));
    }

    @Override
    public void deleteVacancy(int position) {
        mSQLiteHelper.deleteFavoriteVacancy(mListVacancy.get(position).getPid());
    }

    @Override
    public void refreshViewedList() {
        listViewed = new ArrayList<>();
        listViewed = mSQLiteHelper.getViewed();
    }

    @Override
    public int getViewed(String id) {
        for (int i = 0; i < listViewed.size(); i++) {
            if (id.equals(listViewed.get(i))) {
                return View.VISIBLE;
            }
        }
        return View.GONE;
    }

    @Override
    public boolean[] getFavoriteFromBase() {
        boolean[] mCheckedState = new boolean[mListVacancy.size()];
        ArrayList<VacanciesModel> list = (ArrayList<VacanciesModel>) mSQLiteHelper.getFavoriteVacancy();
        if (list != null) {
            for (int i = 0; i < mListVacancy.size(); i++) {
                mCheckedState[i] = false;
                for (int j = 0; j < list.size(); j++) {
                    if (mListVacancy.get(i).getPid().equals(list.get(j).getPid())) {
                        mCheckedState[i] = true;
                    }
                }

            }
        }
        return mCheckedState;
    }

    @Override
    public void bind(VacancyOverDayContract.View view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    private void saveVacanciesOverDay(final List<VacanciesModel> list) {
        mSQLiteHelper.saveAllVacanciesOverDay(list);

    }
}
