package com.example.rojsa.laboratorysecondvacanciesapp.ui.details;

import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.Lifecycle;

public interface DetailVacancyContract {
    interface View {
        void onSuccess(VacanciesModel modelVacancy, boolean isChecked, int isShowingButton);

        void disableButton(int prev, int next);

        void getCall(String[] telList);
        void getCall(String uri);

        void checkboxToast(String msg);

        void onError(String msg);

    }

    interface Presenter extends Lifecycle<View> {
        void getVacancy(int id);

        void nextVacancy();

        void getDataForDialogCall();

        void previousVacancy();

        void selectSavedDataOnStart(boolean flag, int position);

        void saveDeleteFavoriteVacancies(boolean isChecked);


    }
}
