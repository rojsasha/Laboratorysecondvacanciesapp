package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication;
import com.example.rojsa.laboratorysecondvacanciesapp.model.AllDayModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rojsa on 18.04.2018.
 */

public class RecycleViewAdapter extends ArrayAdapter {
    private List<AllDayModel> list;
    private Context context;
    ArrayList<String> savedList = StartApplication.get(getContext()).getSqLiteHelper().getViewed();

    public RecycleViewAdapter(@NonNull Context context, List<AllDayModel> list) {
        super(context, 0, list);
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_cardview, parent, false);
            holder.tvDate = convertView.findViewById(R.id.tvDate);
            holder.tvTitleCardView = convertView.findViewById(R.id.tvTitleCardView);
            holder.tvSalary = convertView.findViewById(R.id.tvSalary);
            holder.tvJob = convertView.findViewById(R.id.tvJob);
            holder.layoutViewed = convertView.findViewById(R.id.layoutViewed);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AllDayModel model = (AllDayModel) getItem(position);
        holder.tvDate.setText(model.getData());
        holder.tvDate.setText(model.getData());
        holder.tvJob.setText(model.getHeader());
        holder.tvTitleCardView.setText(model.getProfession());
        holder.tvSalary.setText(model.getSalary());
        if (setViewed(model.getPid())) {
            holder.layoutViewed.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private boolean setViewed(String id) {

        for (int i = 0; i < savedList.size(); i++) {
            if (id.equals(savedList.get(i))) {
                return true;
            }


        }
        return false;
    }

    class ViewHolder {
        TextView tvDate, tvTitleCardView, tvSalary, tvJob;
        LinearLayout layoutViewed;

    }
}
