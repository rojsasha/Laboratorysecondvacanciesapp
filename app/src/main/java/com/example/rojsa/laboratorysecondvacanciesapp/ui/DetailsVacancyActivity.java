package com.example.rojsa.laboratorysecondvacanciesapp.ui;


import android.content.DialogInterface;
import android.content.Intent;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;


import java.util.List;

public class DetailsVacancyActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvTitleDetails, mTvJob, mTvDate, mTvSalary, mTvSite, mTvPhoneNumber, mTvDetailVacancy;
    private List<AllDayModel> mListVacancy;
    private int mPos;
    private AppCompatButton mBtnCall;
    private AllDayModel mModelVacancy;
    private LinearLayout mBtnPrev, mBtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_vacancy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createDrawer(toolbar);

        initViewElement();
        writeData();
    }

    private void initViewElement() {
        Intent intent = getIntent();
//        mModelVacancy = intent.getParcelableExtra("mModelVacancy");
        mPos = intent.getIntExtra("position", 0);

        mTvTitleDetails = findViewById(R.id.tvTitleDetails);
        mTvJob = findViewById(R.id.tvJob);
        mTvDate = findViewById(R.id.tvDate);
        mTvSalary = findViewById(R.id.tvSalary);
        mTvSite = findViewById(R.id.tvSite);
        mTvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        mTvDetailVacancy = findViewById(R.id.tvDetailVacancy);

        mBtnPrev = findViewById(R.id.btnPrev);
        mBtnNext = findViewById(R.id.btnNext);
        mBtnCall = findViewById(R.id.btnCall);

        mBtnCall.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        getAllDayVacancies();

    }


    private void writeData() {
        mModelVacancy = mListVacancy.get(mPos);
        mTvTitleDetails.setText(mModelVacancy.getHeader());
        mTvJob.setText(mModelVacancy.getProfile());
        mTvDate.setText(mModelVacancy.getData());
        mTvSalary.setText(mModelVacancy.getSalary());
        mTvSite.setText(mModelVacancy.getSiteAddress());
        mTvDetailVacancy.setText(mModelVacancy.getBody());

        if (mModelVacancy.getProfile().equals("")) mTvJob.setText(R.string.no_phone_textview);

        if (mModelVacancy.getSalary().equals("")) {
            mTvSalary.setText(R.string.no_salary);
        } else {
            mTvSalary.setText(mModelVacancy.getSalary());
        }

        if (mModelVacancy.getTelephone().equals("")) {
            mBtnCall.setVisibility(View.GONE);
            mTvPhoneNumber.setText(R.string.no_phone_textview);
        } else {
            mBtnCall.setVisibility(View.VISIBLE);

            mTvPhoneNumber.setText(mModelVacancy.getTelephone());
        }

        disableButton();
        saveIdVacancy(mModelVacancy);
    }

    private void saveIdVacancy(final AllDayModel model) {

                SQLiteHelper saveID = StartApplication.get(getApplicationContext()).getSqLiteHelper();
                saveID.saveViewed(model.getPid());

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
                    intent.setData(Uri.parse("tel: " + tel.replace(".","")));
                    startActivity(intent);
                }
                break;
            case R.id.btnPrev:
                prevVacancy();
                break;
            case R.id.btnNext:
                nextVacancy();
                break;
        }
    }

    private void disableButton() {
        if (mPos == 0) {
            mBtnPrev.setVisibility(View.INVISIBLE);

        } else if (mListVacancy.size() - 1 == mPos) {
            mBtnNext.setVisibility(View.INVISIBLE);
        }
    }

    private void prevVacancy() {
        mPos -= 1;
        disableButton();
        mBtnNext.setVisibility(View.VISIBLE);
        writeData();
        saveIdVacancy(mListVacancy.get(mPos));
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
    private void getAllDayVacancies(){
        SQLiteHelper sqLiteHelper = StartApplication.get(getApplicationContext()).getSqLiteHelper();
        mListVacancy = sqLiteHelper.getAllVacanciesOverDay();
    }


}
