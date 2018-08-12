package com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rojsa.laboratorysecondvacanciesapp.R
import com.example.rojsa.laboratorysecondvacanciesapp.StartApplication
import com.example.rojsa.laboratorysecondvacanciesapp.ui.RecyclerViewClickListener
import com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable.AdapterExpandable
import com.example.rojsa.laboratorysecondvacanciesapp.ui.main.category.expandable.RubricsModel

class CategoryFragment : Fragment(), CategoryContract.View ,RecyclerViewClickListener{

    var recyclerView: RecyclerView? = null
    var adapter: AdapterExpandable? = null

    override fun onItemClick(position: Int) {
        Toast.makeText(context, "dfsdfdsf" + position,Toast.LENGTH_LONG).show()
    }

    override fun onStartSuccessExpanded(ListRubrics: ArrayList<RubricsModel>) {
        recyclerView?.adapter = AdapterExpandable(ListRubrics,this)
    }

    override fun onError(msg: String) {
        Toast.makeText(context, "adasdasdasd$msg", Toast.LENGTH_LONG).show()
    }

    private var mPresenter: CategoryContract.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vacancies_over_day, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.GONE

        mPresenter = CategoryPresenter(StartApplication.get(context!!).service,
                StartApplication.get(context!!).sqLiteHelper)
        initViewElement(view)

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView?.layoutManager = LinearLayoutManager(context)



        mPresenter?.bind(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter!!.downloadCategories()
    }

    private fun initViewElement(view: View) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unbind()
    }
}