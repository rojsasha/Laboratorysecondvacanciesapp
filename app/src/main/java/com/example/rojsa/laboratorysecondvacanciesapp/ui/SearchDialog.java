package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.util.ArrayList;
import java.util.List;

public class SearchDialog extends DialogFragment implements View.OnClickListener {
    private RadioButton mRbModeAny, mRbModeFullDay, mRbModeFlexible, mRbModeRemotely, mRbModeNight, mRbSalaryAny, mRbSalaryFive, mRbSalaryTen, mRbSalaryThirty;
    private CompoundButton mPreviousModeCompoundButton, mPreviousSalaryCompoundButton;
    private Button btnClear, btnSearch;
    private List<VacanciesModel> mVacancieslList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mRbModeAny = view.findViewById(R.id.rbModeAny);
        mRbModeFullDay = view.findViewById(R.id.rbModeFullDay);
        mRbModeFlexible = view.findViewById(R.id.rbModeFlexible);
        mRbModeRemotely = view.findViewById(R.id.rbModeRemotely);
        mRbModeNight = view.findViewById(R.id.rbModeNight);

        mRbSalaryAny = view.findViewById(R.id.rbSalaryAny);
        mRbSalaryFive = view.findViewById(R.id.rbSalaryFive);
        mRbSalaryTen = view.findViewById(R.id.rbSalaryTen);
        mRbSalaryThirty = view.findViewById(R.id.rbSalaryThirty);

        btnSearch = view.findViewById(R.id.btnSearch);
        btnClear = view.findViewById(R.id.btnClear);

        btnSearch.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        mRbModeFullDay.setOnCheckedChangeListener(radioGroupMode);
        mRbModeAny.setOnCheckedChangeListener(radioGroupMode);
        mRbModeFlexible.setOnCheckedChangeListener(radioGroupMode);
        mRbModeRemotely.setOnCheckedChangeListener(radioGroupMode);
        mRbModeNight.setOnCheckedChangeListener(radioGroupMode);

        mRbSalaryAny.setOnCheckedChangeListener(radioGroupSalary);
        mRbSalaryFive.setOnCheckedChangeListener(radioGroupSalary);
        mRbSalaryTen.setOnCheckedChangeListener(radioGroupSalary);
        mRbSalaryThirty.setOnCheckedChangeListener(radioGroupSalary);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDefaultRadioButtons();

    }

    CompoundButton.OnCheckedChangeListener radioGroupMode = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (mPreviousModeCompoundButton != null) {
                mPreviousModeCompoundButton.setChecked(false);
                mPreviousModeCompoundButton = compoundButton;
            } else {
                mPreviousModeCompoundButton = compoundButton;
            }
        }
    };
    CompoundButton.OnCheckedChangeListener radioGroupSalary = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (mPreviousSalaryCompoundButton != null) {
                mPreviousSalaryCompoundButton.setChecked(false);
                mPreviousSalaryCompoundButton = compoundButton;
            } else {
                mPreviousSalaryCompoundButton = compoundButton;
            }
        }
    };

    private void setDefaultRadioButtons() {
        mRbModeAny.setChecked(true);
        mRbSalaryAny.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                setDefaultRadioButtons();
                break;
            case R.id.btnSearch:

                break;
        }
    }
    private void getData(){

    }
}
