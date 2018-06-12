package com.example.rojsa.laboratorysecondvacanciesapp.ui.details;


import android.content.DialogInterface;
import android.content.Intent;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.base.BaseActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailsVacancyActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvTitleDetails, mTvJob, mTvDate, mTvSalary, mTvSite, mTvPhoneNumber, mTvDetailVacancy;
    private CheckBox mCheckBox;
    private List<VacanciesModel> mListVacancy;
    private int mPos;
    private AppCompatButton mBtnCall;
    private LinearLayout mBtnPrev, mBtnNext;
    private SQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_vacancy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Вакансии");
        createDrawer(toolbar, false);

        mSQLiteHelper = StartApplication.get(this).getSqLiteHelper();
        initViewElement();
        writeData();
    }

    private void initViewElement() {
        Intent intent = getIntent();

        if (intent.getBooleanExtra("flag", true)) {
            getAllDayVacancies();
            mPos = intent.getIntExtra("position", 0);
        } else {
            getAllFavoriteVacancies();
            mPos = intent.getIntExtra("position", 0);
        }


        mTvTitleDetails = findViewById(R.id.tvTitleDetails);
        mTvJob = findViewById(R.id.tvJob);
        mTvDate = findViewById(R.id.tvDate);
        mTvSalary = findViewById(R.id.tvSalary);
        mTvSite = findViewById(R.id.tvSite);
        mTvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        mTvDetailVacancy = findViewById(R.id.tvDetailVacancy);
        mCheckBox = findViewById(R.id.checkbox);

        mBtnPrev = findViewById(R.id.btnPrev);
        mBtnNext = findViewById(R.id.btnNext);
        mBtnCall = findViewById(R.id.btnCall);

        mBtnCall.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);


    }

    private void writeData() {

        VacanciesModel modelVacancy = mListVacancy.get(mPos);
        mTvTitleDetails.setText(modelVacancy.getHeader());

        if (!modelVacancy.getProfile().equals(""))
            mTvJob.setText(modelVacancy.getProfile());

        mTvDate.setText(formatData(modelVacancy.getData()));

        mTvSalary.setText(modelVacancy.getSalary());
        mTvSite.setText(modelVacancy.getSiteAddress());
        mTvDetailVacancy.setText(modelVacancy.getBody());

        mCheckBox.setChecked(getFavoriteVacancies(modelVacancy));

        if (!modelVacancy.getSalary().equals(""))
            mTvSalary.setText(modelVacancy.getSalary());

        if (modelVacancy.getTelephone().equals("")) {
            mBtnCall.setVisibility(View.GONE);
            mTvPhoneNumber.setText(R.string.no_phone_textview);
        } else {
            mBtnCall.setVisibility(View.VISIBLE);
            mTvPhoneNumber.setText(modelVacancy.getTelephone());
        }
        disableButton();
        saveIdVacancy(modelVacancy);
    }

    private void saveIdVacancy(final VacanciesModel model) {
        mSQLiteHelper.saveViewed(model.getPid());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCall:
                String tel = mListVacancy.get(mPos).getTelephone();
                if (tel.contains(";")) {
                    showDialog(tel);

                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel: " + tel.replace(".", "")));
                    startActivity(intent);
                }
                break;
            case R.id.btnPrev:
                prevVacancy();
                break;
            case R.id.btnNext:
                nextVacancy();
                break;
            case R.id.checkbox:
                if (mCheckBox.isChecked()) {
                    Toast.makeText(getApplicationContext(), "добавлено в избранные", Toast.LENGTH_LONG).show();
                    saveVacancy(mListVacancy.get(mPos));
                } else {
                    Toast.makeText(getApplicationContext(), "удалено из избранных", Toast.LENGTH_LONG).show();
                    deleteVacancy(mListVacancy.get(mPos));
                }
                break;
        }
    }

    private void disableButton() {
        if (mListVacancy.size() == 1) {
            mBtnNext.setVisibility(View.INVISIBLE);
            mBtnPrev.setVisibility(View.INVISIBLE);

        } else if (mListVacancy.size() - 1 == mPos) {
            mBtnNext.setVisibility(View.INVISIBLE);
        } else if (mPos == 0) {
            mBtnPrev.setVisibility(View.INVISIBLE);
        }
    }

    private void prevVacancy() {
        mPos -= 1;
        disableButton();
        mBtnNext.setVisibility(View.VISIBLE);
        writeData();
        saveIdVacancy(mListVacancy.get(mPos));
    }

    private String formatData(String data) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "HH:mm dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
        Date date;
        String str = null;
        try {
            date = inputFormat.parse(data);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void nextVacancy() {
        mPos += 1;
        disableButton();
        mBtnPrev.setVisibility(View.VISIBLE);
        writeData();
        saveIdVacancy(mListVacancy.get(mPos));
    }

    private void showDialog(String tel) {
        tel = tel.replace(" ", "");
        final String[] mTelephoneList = tel.split(";");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите телефон")
                .setItems(mTelephoneList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel: " + mTelephoneList[i]));
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveVacancy(final VacanciesModel model) {
        mSQLiteHelper.saveFavoriteVacancy(model);
    }

    private void deleteVacancy(final VacanciesModel model) {
        mSQLiteHelper.deleteFavoriteVacancy(model.getPid());
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

    private void getAllDayVacancies() {
        mListVacancy = mSQLiteHelper.getAllVacanciesOverDay();
    }

    private void getAllFavoriteVacancies() {
        mListVacancy = mSQLiteHelper.getFavoriteVacancy();
    }


}
