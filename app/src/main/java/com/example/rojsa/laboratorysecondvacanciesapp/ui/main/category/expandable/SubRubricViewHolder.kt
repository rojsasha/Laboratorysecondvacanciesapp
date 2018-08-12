package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable

import android.view.View
import android.widget.TextView
import com.example.rojsa.laboratorysecondvacanciesapp.R
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class SubRubricViewHolder(view: View) : ChildViewHolder(view) {
     var tvSubRubric: TextView? = null

    init {
        tvSubRubric = view.findViewById(R.id.tvChildList)
    }

    fun setSubrubric(name: String) {
        tvSubRubric?.text = name
    }
}