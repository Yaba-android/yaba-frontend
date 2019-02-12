package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class OverviewFragment : Fragment() {

    private var mDataset = arrayListOf<BookVerticalModel>()
    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_overview, container, false)

        mockDataset()

        val linearLayout = rootView.findViewById<LinearLayout>(R.id.root_linear_layout_overview)
        val adapterBookVertical = BookVerticalRecyclerViewAdapter(container!!.context, mDataset, mTabFragmentClickCallback)
        val verticalRecyclerView = rootView.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_overview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(BookVerticalRecyclerViewBottomOffsetDecoration(container.context, R.dimen.book_vertical_recycler_view))
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
        return rootView
    }

    fun setTabFragmentClickCallback(tabFragmentClickCallback: ITabFragmentClickCallback) {
        mTabFragmentClickCallback = tabFragmentClickCallback
    }

    private fun mockDataset() {
        val hmodels = arrayListOf<BookHorizontalModel>()

        hmodels.add(BookHorizontalModel(R.drawable.forest_small, "The Forest", "Lombok Indonesia"))
        hmodels.add(BookHorizontalModel(R.drawable.kohlarn_small, "Beach", "Koh Larn"))
        hmodels.add(BookHorizontalModel(R.drawable.forest_small, "The Waterfall", "Water"))
        hmodels.add(BookHorizontalModel(R.drawable.kohlarn_small, "View Point", "Thailand"))
        hmodels.add(BookHorizontalModel(R.drawable.forest_small, "Monkey forest", "Indonesia Traveler"))
        hmodels.add(BookHorizontalModel(R.drawable.kohlarn_small, "Sea and beach", "Next Pattaya"))
        mDataset.add(BookVerticalModel("More Books from this Author", hmodels))
    }
}