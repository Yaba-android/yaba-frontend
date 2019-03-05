package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Review.Vertical.RModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Review.Vertical.RRecyclerViewAdapter

class ReviewFragment : Fragment() {

    private var mDatasetReview = arrayListOf<RModel>()
    private var mDatasetBook = arrayListOf<ListBModel>()
    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_review, container, false)

        mockDatasetReview()
        initReviewVerticalRecyclerView(rootView, container!!)
        mockDatasetBook()
        initBookVerticalRecyclerView(rootView, container)
        return rootView
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    private fun initReviewVerticalRecyclerView(view: View, container: ViewGroup) {
        val adapterReviewVertical = RRecyclerViewAdapter(container.context, mDatasetReview)
        val reviewVerticalRecyclerView = view.findViewById<RecyclerView>(R.id.review_vertical_recyclerview)

        reviewVerticalRecyclerView.setHasFixedSize(true)
        reviewVerticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        reviewVerticalRecyclerView.adapter = adapterReviewVertical
    }

    private fun initBookVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_review)
        val adapterBookVertical = ListBRecyclerViewAdapter(container.context, mDatasetBook, mBookClickCallback)
        val bookVerticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_review_overview_footer)

        bookVerticalRecyclerView.setHasFixedSize(true)
        bookVerticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        bookVerticalRecyclerView.adapter = adapterBookVertical
        bookVerticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_book_vertical_recycler_view)
        )
        bookVerticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetReview() {
        for (i in 1..3) {
            mDatasetReview.add(
                com.github.nasrat_v.maktaba_android_frontend_mvp.Review.Vertical.RModel(
                    "Vlad Poutin", "This book was really cool, " +
                            "and I would definitely read more by this author"
                )
            )
        }
    }

    private fun mockDatasetBook() {
        val hmodels = arrayListOf<BModel>()

        hmodels.add(BModel(R.drawable.forest_small, "The Forest", "Lombok Indonesia", 4f, 102))
        hmodels.add(BModel(R.drawable.kohlarn_small, "Beach", "Koh Larn", 5f, 28))
        hmodels.add(BModel(R.drawable.forest_small, "The Waterfall", "Water", 4.5f, 356))
        hmodels.add(BModel(R.drawable.kohlarn_small, "View Point", "Thailand", 3.5f, 188))
        hmodels.add(BModel(R.drawable.forest_small, "Monkey forest", "Indonesia Traveler", 4f, 9))
        hmodels.add(BModel(R.drawable.kohlarn_small, "Sea and beach", "Next Pattaya", 3f, 42))
        mDatasetBook.add(ListBModel("More Books from this Author", hmodels))
    }
}