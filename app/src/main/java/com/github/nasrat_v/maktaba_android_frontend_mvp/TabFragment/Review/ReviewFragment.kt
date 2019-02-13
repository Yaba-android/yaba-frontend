package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.Model
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListRecyclerViewBottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class ReviewFragment : Fragment() {

    private var mDatasetReview = arrayListOf<ReviewVerticalModel>()
    private var mDatasetBook = arrayListOf<ListModel>()
    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_review, container, false)

        mockDatasetReview()
        initReviewVerticalRecyclerView(rootView, container!!)
        mockDatasetBook()
        initBookVerticalRecyclerView(rootView, container)
        return rootView
    }

    fun setTabFragmentClickCallback(tabFragmentClickCallback: ITabFragmentClickCallback) {
        mTabFragmentClickCallback = tabFragmentClickCallback
    }

    private fun initReviewVerticalRecyclerView(view: View, container: ViewGroup) {
        val adapterReviewVertical = ReviewVerticalRecyclerViewAdapter(container.context, mDatasetReview)
        val reviewVerticalRecyclerView = view.findViewById<RecyclerView>(R.id.review_vertical_recyclerview)

        reviewVerticalRecyclerView.setHasFixedSize(true)
        reviewVerticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        reviewVerticalRecyclerView.adapter = adapterReviewVertical
    }

    private fun initBookVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_review)
        val adapterBookVertical = ListRecyclerViewAdapter(container.context, mDatasetBook, mTabFragmentClickCallback)
        val bookVerticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_review_overview_footer)

        bookVerticalRecyclerView.setHasFixedSize(true)
        bookVerticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        bookVerticalRecyclerView.adapter = adapterBookVertical
        bookVerticalRecyclerView.addItemDecoration(ListRecyclerViewBottomOffsetDecoration(container.context, R.dimen.book_vertical_recycler_view))
        bookVerticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetReview() {
        for (i in 1..3) {
            mDatasetReview.add(
                ReviewVerticalModel(
                    "Jane Bloggs", "This book was really cool, " +
                            "and I would definitely read more by this author"
                )
            )
        }
    }

    private fun mockDatasetBook() {
        val hmodels = arrayListOf<Model>()

        hmodels.add(Model(R.drawable.forest_small, "The Forest", "Lombok Indonesia"))
        hmodels.add(Model(R.drawable.kohlarn_small, "Beach", "Koh Larn"))
        hmodels.add(Model(R.drawable.forest_small, "The Waterfall", "Water"))
        hmodels.add(Model(R.drawable.kohlarn_small, "View Point", "Thailand"))
        hmodels.add(Model(R.drawable.forest_small, "Monkey forest", "Indonesia Traveler"))
        hmodels.add(Model(R.drawable.kohlarn_small, "Sea and beach", "Next Pattaya"))
        mDatasetBook.add(ListModel("More Books from this Author", hmodels))
    }
}