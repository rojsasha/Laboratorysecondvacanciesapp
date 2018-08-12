package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.overday

import android.content.Context
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel
import com.example.rojsa.laboratorysecondvacanciesapp.ui.AdapterVacanciesBase
import com.example.rojsa.laboratorysecondvacanciesapp.ui.RecyclerViewClickListener

class VacanciesOverDayAdapter(listVacanciesModel: MutableList<out VacanciesModel>,
                              context: Context,
                              mClickListener: RecyclerViewClickListener,
                              val presenter: VacancyOverDayContract.Presenter)
    : AdapterVacanciesBase(listVacanciesModel,
        context, mClickListener) {
    override fun onFavoriteList(): BooleanArray {
        return presenter.favoriteFromBase
    }

    override fun setVisibilityViewedLayout(id: String): Int {
        return presenter.getViewed(id)
    }

    override fun onCheckboxClicked(isChecked: Boolean, position: Int) {
        if (!isChecked){
            presenter.deleteVacancy(position)
        }else{
            presenter.saveVacancy(position)
        }
    }
}