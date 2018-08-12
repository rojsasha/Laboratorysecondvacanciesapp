package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable

import com.example.rojsa.laboratorysecondvacanciesapp.data.model.rubrics.Subrubric
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

data class RubricsModel(val titles: String, val subRubric: List<Subrubric>) : ExpandableGroup<Subrubric>(titles, subRubric)


