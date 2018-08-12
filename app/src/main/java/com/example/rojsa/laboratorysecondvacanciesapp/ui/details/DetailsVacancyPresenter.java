package com.example.rojsa.laboratorysecondvacanciesapp.ui.details;

import android.util.Log;
import android.view.View;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.StringResources;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailsVacancyPresenter implements DetailVacancyContract.Presenter {
    private List<VacanciesModel> mListVacancy;
    private SQLiteHelper mSQLiteHelper;
    private StringResources mGetString;
    private DetailVacancyContract.View mView;
    private int mPos;

    DetailsVacancyPresenter(SQLiteHelper sqLiteHelper,
                            StringResources getString) {
        mSQLiteHelper = sqLiteHelper;
        mGetString = getString;
    }

    @Override
    public void getVacancy(int id) {
        boolean isFavorite;
        int isShowingPhone;
        VacanciesModel modelVacancy = mListVacancy.get(id);


        if (modelVacancy.getProfile().equals(""))
            modelVacancy.setProfile(mGetString.getString(R.string.no_phone_textview));


        isFavorite = getFavoriteVacancies(modelVacancy);

        if (modelVacancy.getSalary().equals(""))
            modelVacancy.setSalary(mGetString.getString(R.string.no_salary));

        if (modelVacancy.getTelephone().equals("")) {
            modelVacancy.setTelephone(mGetString.getString(R.string.no_phone_textview));
            isShowingPhone = View.GONE;
        } else {
            isShowingPhone = View.VISIBLE;
        }
        disableButton();
        saveIdVacancy(modelVacancy);
        mView.onSuccess(modelVacancy, isFavorite, isShowingPhone);

    }

    @Override
    public void getDataForDialogCall() {
        String tel = mListVacancy.get(mPos).getTelephone();
        if (tel.contains(";")) {
            tel = tel.replace(" ", "");
            mView.getCall(tel.split(";"));
        } else {
            mView.getCall(tel.replace(".", ""));
        }
    }

    @Override
    public void nextVacancy() {
        mPos += 1;
        disableButton();
        getVacancy(mPos);
        saveIdVacancy(mListVacancy.get(mPos));
    }

    @Override
    public void previousVacancy() {
        mPos -= 1;
        disableButton();
        getVacancy(mPos);
        saveIdVacancy(mListVacancy.get(mPos));
    }

    @Override
    public void selectSavedDataOnStart(boolean flag, int position) {
        mPos = position;
        if (flag) {
            getAllDayVacancies();
        } else {
            getAllFavoriteVacancies();
        }
        Log.d("position deta", "position" + position + " " + flag);


    }

    @Override
    public void saveDeleteFavoriteVacancies(boolean isChecked) {
        if (isChecked) {
            saveVacancy(mListVacancy.get(mPos));
        } else {
            deleteVacancy(mListVacancy.get(mPos));
        }
    }

    private void disableButton() {
        if (mListVacancy.size() == 1) {
            mView.disableButton(View.INVISIBLE, View.INVISIBLE);
        } else if (mListVacancy.size() - 1 == mPos) {
            mView.disableButton(View.VISIBLE, View.INVISIBLE);
        } else if (mPos == 0) {
            mView.disableButton(View.INVISIBLE, View.VISIBLE);
        } else {
            mView.disableButton(View.VISIBLE, View.VISIBLE);
        }
    }

    private boolean getFavoriteVacancies(VacanciesModel model) {
        ArrayList<VacanciesModel> list = (ArrayList<VacanciesModel>) mSQLiteHelper.getFavoriteVacancy();
        if (list == null) return false;
        for (int i = 0; i < list.size(); i++) {
            if (model.getPid().equals(list.get(i).getPid()))
                return true;
        }
        return false;
    }

    @Override
    public void bind(DetailVacancyContract.View view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    private void saveIdVacancy(final VacanciesModel model) {
        mSQLiteHelper.saveViewed(model.getPid());
    }


    private void formatData(List<VacanciesModel> listVacancy) {
        for (int i = 0; i < listVacancy.size(); i++) {
            String data = listVacancy.get(i).getData();
            Log.d("DetailsVacancyPresenter", "startformatData: " + data);
            String inputPattern = "yyyy-MM-dd HH:mm:ss";
            String outputPattern = "HH:mm dd MMM yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
            Date date;
            String str;
            try {
                date = inputFormat.parse(data);
                str = outputFormat.format(date);
                listVacancy.get(i).setData(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mListVacancy = listVacancy;
        getVacancy(mPos);
    }

    private void saveVacancy(final VacanciesModel model) {
        mSQLiteHelper.saveFavoriteVacancy(model);
    }

    private void deleteVacancy(final VacanciesModel model) {
        mSQLiteHelper.deleteFavoriteVacancy(model.getPid());
    }

    private void getAllDayVacancies() {
        mListVacancy = mSQLiteHelper.getAllVacanciesOverDay();
        formatData(mListVacancy);
    }

    private void getAllFavoriteVacancies() {
        mListVacancy = mSQLiteHelper.getFavoriteVacancy();
        formatData(mListVacancy);
    }

    private boolean isViewAttached() {
        return mView != null;
    }

}
