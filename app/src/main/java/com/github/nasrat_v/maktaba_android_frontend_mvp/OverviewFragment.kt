package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class OverviewFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var verticalRecyclerView: RecyclerView
    private lateinit var adapterBookVertical: BookVerticalRecyclerViewAdapter
    private var mDataset = arrayListOf<BookVerticalModel>()
    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_overview, container, false)

        mockDataset()

        adapterBookVertical = BookVerticalRecyclerViewAdapter(container!!.context, mDataset, mTabFragmentClickCallback)
        verticalRecyclerView = rootView.findViewById(R.id.book_vertical_recyclerview_overview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(BookVerticalRecyclerViewBottomOffsetDecoration(container.context, R.dimen.book_vertical_recycler_view))
        return rootView
    }

    fun setTabFragmentClickCallback(tabFragmentClickCallback: ITabFragmentClickCallback) {
        mTabFragmentClickCallback = tabFragmentClickCallback
    }

    private fun mockDataset() {
        val hmodels = arrayListOf<BookHorizontalModel>()

        for (n in 1..5) {
            hmodels.add(BookHorizontalModel("Book $n", "A lire $n"))
        }
        mDataset.add(BookVerticalModel("More Books from this Author", hmodels))
    }
}