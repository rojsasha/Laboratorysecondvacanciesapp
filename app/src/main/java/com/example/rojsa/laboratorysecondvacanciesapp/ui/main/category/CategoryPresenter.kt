package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category

import com.example.rojsa.laboratorysecondvacanciesapp.data.RequestInterface
import com.example.rojsa.laboratorysecondvacanciesapp.data.SQLiteHelper
import com.example.rojsa.laboratorysecondvacanciesapp.data.model.rubrics.Rubrics
import com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable.RubricsModel
import retrofit2.Call
import retrofit2.Response

class CategoryPresenter(private val service: RequestInterface, val sqLiteHelper: SQLiteHelper) : CategoryContract.Presenter {

    override fun downloadCategories() {
        service.getRubrics("login", "f")
                .enqueue(object : retrofit2.Callback<List<Rubrics>> {
                    override fun onResponse(call: Call<List<Rubrics>>, response: Response<List<Rubrics>>) {
                        if (response.isSuccessful && response.body() != null) {
                            mView?.onError("asdasdasd" + response.body()?.size)
                            writeDataParse(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<List<Rubrics>>, t: Throwable) {

                    }
                })
    }

    private var mView: CategoryContract.View? = null

    fun writeDataParse(body: List<Rubrics>) {
        val rubricsModel: ArrayList<RubricsModel>? = java.util.ArrayList()
        for (i in 0 until body.size) {
            rubricsModel?.add(RubricsModel(body[i].rubric, body[i].subrubrics))
        }
        mView?.onStartSuccessExpanded(rubricsModel!!)
    }

    override fun bind(view: CategoryContract.View?) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}