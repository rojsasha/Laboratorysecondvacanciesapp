package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.overday;

import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.Lifecycle;

import java.util.ArrayList;
import java.util.List;

public interface VacancyOverDayContract {
     interface View  {
         void onSuccess(List<VacanciesModel> vacancyList);
         void stopRefreshing();
         void adapterNotify();
         void onError(String msg);

     }

     interface Presenter extends Lifecycle<View> {
         void getDataOverDay();

         void dataFromSplashScreen(List<VacanciesModel> vacancyList);

         void filterGetData(ArrayList<String> list);

         void addToListVacancies();

         void saveVacancy(int position);
         void deleteVacancy(int position);
         void refreshViewedList();
         int getViewed(String id);
         boolean[] getFavoriteFromBase();



     }
}
