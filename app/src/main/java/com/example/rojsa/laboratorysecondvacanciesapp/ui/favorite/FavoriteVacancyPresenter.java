package com.example.rojsa.laboratorysecondvacanciesapp.ui.favorite;

import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.util.List;

public class FavoriteVacancyPresenter implements FavoriteVacancyContract.Presenter {
    private SQLiteHelper mSQLiteHelper;
    private FavoriteVacancyContract.View mView;
    private List<VacanciesModel> mListVacancy;

    public FavoriteVacancyPresenter(SQLiteHelper sqLiteHelper) {
        mSQLiteHelper = sqLiteHelper;
    }

    @Override
    public void getSavedList() {
        mView.onSuccess(mListVacancy = mSQLiteHelper.getFavoriteVacancy());
    }

    @Override
    public void deleteFavoriteVacancy(int position) {
        mSQLiteHelper.deleteFavoriteVacancy(mListVacancy.get(position).getPid());
    }

    @Override
    public boolean[] setCheckedFavouriteList() {
        boolean[] b = new boolean[mListVacancy.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = true;
        }
        return b;
    }

    @Override
    public void bind(FavoriteVacancyContract.View view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;

    }
}
