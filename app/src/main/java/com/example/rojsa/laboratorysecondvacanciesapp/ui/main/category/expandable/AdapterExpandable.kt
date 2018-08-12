package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.rojsa.laboratorysecondvacanciesapp.R
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.rubrics.Subrubric
import com.example.rojsa.laboratorysecondvacanciesapp.ui.RecyclerViewClickListener
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class AdapterExpandable(listRubrics: ArrayList<RubricsModel>, private val clickListener: RecyclerViewClickListener) : ExpandableRecyclerViewAdapter<RubricViewHolder, SubRubricViewHolder>(listRubrics) {


    var rubricModel: List<Subrubric>? = null
    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): RubricViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_parent, parent, false)

        return RubricViewHolder(view)
    }


    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): SubRubricViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_child, parent, false)
        return SubRubricViewHolder(view)
    }

    override fun onBindChildViewHolder(holder: SubRubricViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>, childIndex: Int) {
        rubricModel = group.items as List<Subrubric>?
        holder.tvSubRubric?.text = rubricModel?.get(childIndex)!!.subrubric


        holder.tvSubRubric?.setOnClickListener {
            Log.e("onclick", "sfsdfdsf")

        }
    }

    override fun onBindGroupViewHolder(holder: RubricViewHolder, flatPosition: Int,
                                       group: ExpandableGroup<*>) {
        holder.setRubricTitle(group)
    }
}
