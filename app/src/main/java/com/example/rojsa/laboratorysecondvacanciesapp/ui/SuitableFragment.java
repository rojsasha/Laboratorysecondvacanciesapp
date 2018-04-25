package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rojsa.laboratorysecondvacanciesapp.R;

/**
 * Created by rojsa on 15.04.2018.
 */

public class SuitableFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suitable,container,false);
        return view;
    }
}
