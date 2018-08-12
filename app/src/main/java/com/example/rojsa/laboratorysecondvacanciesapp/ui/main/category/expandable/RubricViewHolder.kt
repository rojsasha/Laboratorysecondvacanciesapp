package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable

import android.view.View
import android.widget.TextView
import com.example.rojsa.laboratorysecondvacanciesapp.R
import com.example.rojsa.laboratorysecondvacanciesapp.ui.RecyclerViewClickListener
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class RubricViewHolder(view: View) : GroupViewHolder(view) {

    var tvRubrics: TextView? = null
    private var listener: RecyclerViewClickListener? = null

    init {
        tvRubrics = view.findViewById(R.id.tvListParent)
        view.setOnClickListener {
            if (listener!= null)
                listener!!.onItemClick(adapterPosition)
        }
    }

    fun setRubricTitle(group: ExpandableGroup<*>) {
        tvRubrics?.text = group.title
    }

    fun setOnGroupClickListener(listener: RecyclerViewClickListener) {
        this.listener = listener
    }


}