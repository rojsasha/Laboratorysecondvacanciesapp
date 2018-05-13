package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.rojsa.laboratorysecondvacanciesapp.R;

public class SearchDialog extends DialogFragment {
    private RadioButton mRbModeAny, mRbModeFullDay, mRbModeFlexible, mRbModeRemotely, mRbModeNight;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        mRbModeAny = view.findViewById(R.id.rbModeAny);
        mRbModeFullDay = view.findViewById(R.id.rbModeFullDay);
        mRbModeFlexible = view.findViewById(R.id.rbModeFlexible);
        mRbModeRemotely = view.findViewById(R.id.rbModeRemotely);
        mRbModeNight = view.findViewById(R.id.rbModeNight);
        RadioGroup radioGroup = new RadioGroup(getContext());
        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
