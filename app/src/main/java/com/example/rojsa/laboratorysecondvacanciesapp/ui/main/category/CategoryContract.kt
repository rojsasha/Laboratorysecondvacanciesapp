package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category

import com.example.rojsa.laboratorysecondvacanciesapp.data.model.rubrics.Rubrics
import com.example.rojsa.laboratorysecondvacanciesapp.ui.Lifecycle
import com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable.RubricsModel

interface CategoryContract {
    interface View {
        fun onStartSuccessExpanded(ListRubrics: ArrayList<RubricsModel>)
        fun onError(msg: String)
    }

    interface Presenter : Lifecycle<View> {
        fun downloadCategories()
    }
}