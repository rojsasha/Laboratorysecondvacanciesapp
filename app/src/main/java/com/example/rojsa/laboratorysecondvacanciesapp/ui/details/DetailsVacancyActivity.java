package com.example.rojsa.laboratorysecondvacanciesapp.ui.details;


import android.content.DialogInterface;
import android.content.Intent;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.StringResources;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.base.BaseActivity;


import java.util.List;

public class DetailsVacancyActivity extends BaseActivity implements View.OnClickListener, DetailVacancyContract.View {
    private TextView mTvTitleDetails, mTvJob, mTvDate, mTvSalary, mTvSite, mTvPhoneNumber, mTvDetailVacancy;
    private CheckBox mCheckBox;
    private List<VacanciesModel> mListVacancy;
    private int mPos;
    private AppCompatButton mBtnCall;
    private LinearLayout mBtnPrev, mBtnNext;


    private DetailsVacancyPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_vacancy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mPresenter = new DetailsVacancyPresenter(StartApplication.get(this).getSqLiteHelper(),
                new StringResources(this));
        mPresenter.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Вакансии");
        createDrawer(toolbar, false);

        initViewElement();
    }

    private void initViewElement() {
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

        mPresenter.selectSavedDataOnStart(getIntent().getBooleanExtra("flag", true),
                getIntent().getIntExtra("position", 0));


        mBtnCall.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCall:
                mPresenter.getDataForDialogCall();
                break;
            case R.id.btnPrev:
                mPresenter.previousVacancy();
                break;
            case R.id.btnNext:
                mPresenter.nextVacancy();
                break;
            case R.id.checkbox:
                mPresenter.saveDeleteFavoriteVacancies(mCheckBox.isChecked());
                break;
        }
    }

    @Override
    public void onSuccess(VacanciesModel modelVacancy, boolean isChecked, int isShowing) {
        mTvTitleDetails.setText(modelVacancy.getHeader());
        mTvJob.setText(modelVacancy.getProfile());
        mTvDate.setText(modelVacancy.getData());
        mTvSalary.setText(modelVacancy.getSalary());
        mTvSite.setText(modelVacancy.getSiteAddress());
        mTvDetailVacancy.setText(modelVacancy.getBody());
        mCheckBox.setChecked(isChecked);
        mTvSalary.setText(modelVacancy.getSalary());
        mBtnCall.setVisibility(isShowing);
        mTvPhoneNumber.setText(modelVacancy.getTelephone());
    }

    @Override
    public void disableButton(int prev, int next) {
        mBtnPrev.setVisibility(prev);
        mBtnNext.setVisibility(next);
    }

    @Override
    public void getCall(final String[] telList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.text_dialog_call)
                .setItems(telList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel: " + telList[i]));
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void getCall(String uri) {
        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel: " + uri)));
    }

    @Override
    public void checkboxToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
    }
}
