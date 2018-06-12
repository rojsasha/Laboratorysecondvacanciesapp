package com.example.rojsa.laboratorysecondvacanciesapp.ui.search;

import android.content.Context;
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
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.ui.SearchDialogCallback;


import java.util.ArrayList;


public class SearchDialog extends DialogFragment implements View.OnClickListener {
    private RadioButton mRbModeAny, mRbModeFullDay, mRbModeFlexible, mRbModeRemotely, mRbModeNight, mRbSalaryAny, mRbSalaryFive, mRbSalaryTen, mRbSalaryThirty;
    private CompoundButton mPreviousModeCompoundButton, mPreviousSalaryCompoundButton;
    private String[] mSalaryArray, mModeArray;
    private int mModePosition, mSalaryPosition;

    private SearchDialogCallback mCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mRbModeAny = view.findViewById(R.id.rbModeAny);
        mRbModeFullDay = view.findViewById(R.id.rbModeFullDay);
        mRbModeFlexible = view.findViewById(R.id.rbModeFlexible);
        mRbModeRemotely = view.findViewById(R.id.rbModeRemotely);
        mRbModeNight = view.findViewById(R.id.rbModeNight);


        mModeArray = getResources().getStringArray(R.array.rbMode);
        mSalaryArray = getResources().getStringArray(R.array.rbSalary);

        mRbSalaryAny = view.findViewById(R.id.rbSalaryAny);
        mRbSalaryFive = view.findViewById(R.id.rbSalaryFive);
        mRbSalaryTen = view.findViewById(R.id.rbSalaryTen);
        mRbSalaryThirty = view.findViewById(R.id.rbSalaryThirty);

        Button btnSearch = view.findViewById(R.id.btnSearch);
        Button btnClear = view.findViewById(R.id.btnClear);

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
        setTagRadioButton();
        Toast.makeText(getContext(), mModeArray[1] + "", Toast.LENGTH_SHORT).show();


    }

    CompoundButton.OnCheckedChangeListener radioGroupMode = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (mPreviousModeCompoundButton != null) {

                Toast.makeText(getContext(), compoundButton.getTag() + "", Toast.LENGTH_SHORT).show();
                mPreviousModeCompoundButton.setChecked(false);
                mPreviousModeCompoundButton = compoundButton;
                mModePosition = (int) compoundButton.getTag();


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
                mSalaryPosition = (int) compoundButton.getTag();
            } else {
                mPreviousSalaryCompoundButton = compoundButton;

            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (SearchDialogCallback) context;
    }

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
                ArrayList<String> mSalaryModeList = new ArrayList<>();
                mSalaryModeList.add(mModeArray[mModePosition]);
                mSalaryModeList.add(mSalaryArray[mSalaryPosition]);
                mCallback.searchByFilter(mSalaryModeList);
                dismiss();
                break;
        }
    }

    private void setTagRadioButton() {
        mRbModeAny.setTag(0);
        mRbModeFullDay.setTag(1);
        mRbModeFlexible.setTag(2);
        mRbModeRemotely.setTag(3);
        mRbModeNight.setTag(4);

        mRbSalaryAny.setTag(0);
        mRbSalaryFive.setTag(1);
        mRbSalaryTen.setTag(2);
        mRbSalaryThirty.setTag(3);

    }
}
