package com.example.rojsa.laboratorysecondvacanciesapp.ui.favorite

import android.content.Context
import android.view.View
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel
import com.example.rojsa.laboratorysecondvacanciesapp.ui.AdapterVacanciesBase
import com.example.rojsa.laboratorysecondvacanciesapp.ui.RecyclerViewClickListener

class FavoriteVacanciesAdapter(listVacanciesModel: MutableList<out VacanciesModel>, context: Context, mClickListener:
RecyclerViewClickListener, val presenter: FavoriteVacancyPresenter) : AdapterVacanciesBase(listVacanciesModel,
        context, mClickListener) {
    override fun onFavoriteList(): BooleanArray {
        return presenter.setCheckedFavouriteList()
    }

    override fun setVisibilityViewedLayout(id: String): Int {
        return View.GONE
    }

    override fun onCheckboxClicked(isChecked: Boolean, position: Int) {
        if (!isChecked) {
            presenter.deleteFavoriteVacancy(position)
            presenter.getSavedList()
        }
    }
}