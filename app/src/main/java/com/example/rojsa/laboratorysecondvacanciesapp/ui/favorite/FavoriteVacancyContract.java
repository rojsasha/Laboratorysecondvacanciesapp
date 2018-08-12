package com.example.rojsa.laboratorysecondvacanciesapp.ui.favorite;

import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.Lifecycle;

import java.util.List;

public interface FavoriteVacancyContract {

    interface View {
        void onSuccess(List<VacanciesModel> list);
    }

    interface Presenter extends Lifecycle<View> {
        void getSavedList();
        void deleteFavoriteVacancy(int position);
        boolean[] setCheckedFavouriteList();

    }
}
