package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RRecyclerViewAdapter

class ReviewFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mSelectedBook: BModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_review, container, false)

        initReviewVerticalRecyclerView(rootView, container!!)
        initBookVerticalRecyclerView(rootView, container)
        return rootView
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setSelectedBook(book: BModel) {
        mSelectedBook = book
    }

    private fun initReviewVerticalRecyclerView(view: View, container: ViewGroup) {
        val mDataset = RModelProvider(container.context).getAllReviews()
        val adapterReviewVertical = RRecyclerViewAdapter(container.context, mDataset)
        val reviewVerticalRecyclerView = view.findViewById<RecyclerView>(R.id.review_vertical_recyclerview)

        reviewVerticalRecyclerView.setHasFixedSize(true)
        reviewVerticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        reviewVerticalRecyclerView.adapter = adapterReviewVertical
        reviewVerticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_review_vertical_recycler_view)
        )
    }

    private fun initBookVerticalRecyclerView(view: View, container: ViewGroup) {
        val mDataset = arrayListOf<ListBModel>()

        mockDatasetBook(container, mDataset)

        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_review)
        val adapterBookVertical = ListBRecyclerViewAdapter(container.context, mDataset, mBookClickCallback)
        val bookVerticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_review_overview_footer)

        bookVerticalRecyclerView.setHasFixedSize(true)
        bookVerticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        bookVerticalRecyclerView.adapter = adapterBookVertical
        bookVerticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(
                container.context,
                R.dimen.bottom_book_vertical_recycler_view
            )
        )
        bookVerticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetBook(container: ViewGroup, mDataset: ArrayList<ListBModel>) {
        val factory = BModelRandomProvider(container.context)

        mDataset.add(ListBModel("More Books from this Authors", factory.getRandomsInstances(3)))
    }
}