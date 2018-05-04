package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper;
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rojsa on 18.04.2018.
 */

public class ListViewAdapter extends ArrayAdapter {
    private SQLiteHelper mSqLiteHelper = StartApplication.get(getContext()).getSqLiteHelper();
    private boolean[] mCheckedState;
     private List<VacanciesModel> mList;

    ListViewAdapter(@NonNull Context context, List<VacanciesModel> list) {
        super(context, 0, list);
        mCheckedState = new boolean[list.size()];
        this.mList = list;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        getFavoriteVacancy();
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_cardview, parent, false);
            holder.tvDate = convertView.findViewById(R.id.tvDate);
            holder.tvTitleCardView = convertView.findViewById(R.id.tvTitleCardView);
            holder.tvSalary = convertView.findViewById(R.id.tvSalary);
            holder.tvJob = convertView.findViewById(R.id.tvJob);
            holder.checkBox = convertView.findViewById(R.id.checkbox);
            holder.layoutViewed = convertView.findViewById(R.id.layoutViewed);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final VacanciesModel model = (VacanciesModel) getItem(position);

        holder.tvDate.setText(formatData(model.getData()));
        holder.tvJob.setText(model.getHeader());
        holder.tvTitleCardView.setText(model.getProfession());
        holder.tvSalary.setText(model.getSalary());


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCheckedState[position] = b;
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckedState[position]) {
                    Toast.makeText(getContext(), "Сохранено в избранные " + mCheckedState[position] + model.getHeader(), Toast.LENGTH_SHORT).show();
                    saveVacancy(model);
                } else {
                    deleteVacancy(model);
                    Toast.makeText(getContext(), "Удалено из избранных " + mCheckedState[position] + model.getHeader(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        getFavoriteVacancy();
        holder.checkBox.setChecked(mCheckedState[position]);
        if (model.getSalary().equals("")) holder.tvSalary.setText(R.string.no_salary);
        if (setViewed(model.getPid())) {
            holder.layoutViewed.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private void saveVacancy(final VacanciesModel model) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mSqLiteHelper.saveFavoriteVacancy(model);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void deleteVacancy(final VacanciesModel model) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mSqLiteHelper.deleteFavoriteVacancy(model.getPid());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
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

    private boolean setViewed(String id) {
        ArrayList<String> savedList = mSqLiteHelper.getViewed();
        for (int i = 0; i < savedList.size(); i++) {
            if (id.equals(savedList.get(i))) {
                return true;
            }
        }
        return false;
    }

    private void getFavoriteVacancy(){
        ArrayList<VacanciesModel> list = (ArrayList<VacanciesModel>) mSqLiteHelper.getFavoriteVacancy();
        if (list!= null){
            for (int i = 0; i < mList.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (mList.get(i).getPid().equals(list.get(j).getPid())){
                        mCheckedState[i] = true;
                    }
                }

            }
        }
    }


    class ViewHolder {
        TextView tvDate, tvTitleCardView, tvSalary, tvJob;
        CheckBox checkBox;
        LinearLayout layoutViewed;

    }
}
