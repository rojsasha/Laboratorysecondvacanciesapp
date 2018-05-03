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
import android.widget.Toast;


import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;


import java.util.ArrayList;
import java.util.List;

public class DetailsVacancyActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvTitleDetails, tvJob, tvDate, tvSalary, tvSite, tvPhoneNumber, tvDetailVacancy;
    private List<AllDayModel> mListVacancy;
    private int mPos;
    private AppCompatButton btnCall;
    private AllDayModel modelVacancy;
    private LinearLayout btnPrev, btnNext;

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
//        modelVacancy = intent.getParcelableExtra("modelVacancy");
        mPos = intent.getIntExtra("position", 0);

        tvTitleDetails = findViewById(R.id.tvTitleDetails);
        tvJob = findViewById(R.id.tvJob);
        tvDate = findViewById(R.id.tvDate);
        tvSalary = findViewById(R.id.tvSalary);
        tvSite = findViewById(R.id.tvSite);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvDetailVacancy = findViewById(R.id.tvDetailVacancy);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnCall = findViewById(R.id.btnCall);

        btnCall.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        getAllDayVacancies();

    }


    private void writeData() {
        modelVacancy = mListVacancy.get(mPos);
        tvTitleDetails.setText(modelVacancy.getHeader());
        tvJob.setText(modelVacancy.getProfile());
        tvDate.setText(modelVacancy.getData());
        tvSalary.setText(modelVacancy.getSalary());
        tvSite.setText(modelVacancy.getSiteAddress());
        tvDetailVacancy.setText(modelVacancy.getBody());

        if (modelVacancy.getProfile().equals("")) tvJob.setText(R.string.no_phone_textview);

        if (modelVacancy.getSalary().equals("")) {
            tvSalary.setText(R.string.no_salary);
        } else {
            tvSalary.setText(modelVacancy.getSalary());
        }

        if (modelVacancy.getTelephone().equals("")) {
            btnCall.setVisibility(View.GONE);
            tvPhoneNumber.setText(R.string.no_phone_textview);
        } else {
            btnCall.setVisibility(View.VISIBLE);

            tvPhoneNumber.setText(modelVacancy.getTelephone());
        }

        disableButton();
        saveIdVacancy(modelVacancy);
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
            btnPrev.setVisibility(View.INVISIBLE);

        } else if (mListVacancy.size() - 1 == mPos) {
            btnNext.setVisibility(View.INVISIBLE);
        }
    }

    private void prevVacancy() {
        mPos -= 1;
        disableButton();
        btnNext.setVisibility(View.VISIBLE);
        writeData();
        saveIdVacancy(mListVacancy.get(mPos));
    }

    private void nextVacancy() {
        mPos += 1;
        disableButton();
        btnPrev.setVisibility(View.VISIBLE);
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
