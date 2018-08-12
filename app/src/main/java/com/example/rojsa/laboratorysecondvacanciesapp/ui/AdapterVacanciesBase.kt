package com.example.rojsa.laboratorysecondvacanciesapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.rojsa.laboratorysecondvacanciesapp.R
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

abstract class AdapterVacanciesBase(private val listVacanciesModel: List<VacanciesModel>
                                    , val context: Context,
                                    private val mClickListener: RecyclerViewClickListener) :
        RecyclerView.Adapter<AdapterVacanciesBase.ViewHolder>() {
    private var lastPosition: Int = -1
    private var mCheckedState: BooleanArray = BooleanArray(listVacanciesModel.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val view = layoutInflater.inflate(R.layout.items_cardview, null, false)
        view.layoutParams = lp

        return ViewHolder(view)
    }

    protected abstract fun onCheckboxClicked(isChecked: Boolean, position: Int)

    protected abstract fun setVisibilityViewedLayout(id: String): Int

    protected abstract fun onFavoriteList(): BooleanArray

    override fun getItemCount(): Int {
        return listVacanciesModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        setAnimation(holder.layoutParent, position)
        val vacancyModel: VacanciesModel = listVacanciesModel[position]
        holder.tvDate.text = formatData(vacancyModel.data)
        Log.d("alex", "sdssdf" + listVacanciesModel.size)

        holder.tvTitleCardView.text = vacancyModel.header
        holder.tvJob.text = vacancyModel.header
        holder.checkBox.setOnClickListener {
            onCheckboxClicked(holder.checkBox.isChecked, position)
        }
        if (vacancyModel.salary != "")
            holder.tvSalary.text = vacancyModel.salary

        holder.layoutParent.setOnClickListener { mClickListener.onItemClick(position) }

        holder.checkBox.setOnCheckedChangeListener { _, b ->
            mCheckedState[position] = b
        }
        mCheckedState = onFavoriteList()
        holder.checkBox.isChecked = mCheckedState[position]

        holder.layoutViewed.visibility = setVisibilityViewedLayout(vacancyModel.pid)
    }

    private fun formatData(data: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "HH:mm dd MMM yyyy"
        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        val date: Date
        var str: String? = null
        try {
            date = inputFormat.parse(data)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    private fun setAnimation(layoutParent: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
            layoutParent.startAnimation(animation)
            lastPosition = position
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate = itemView.findViewById(R.id.tvDate) as TextView
        val tvTitleCardView = itemView.findViewById(R.id.tvTitleCardView) as TextView
        val tvSalary = itemView.findViewById(R.id.tvSalary) as TextView
        val tvJob = itemView.findViewById(R.id.tvJob) as TextView
        var checkBox = itemView.findViewById(R.id.checkbox) as CheckBox
        val layoutViewed = itemView.findViewById(R.id.layoutViewed) as LinearLayout
        val layoutParent = itemView.findViewById(R.id.layoutParent) as LinearLayout
    }
}