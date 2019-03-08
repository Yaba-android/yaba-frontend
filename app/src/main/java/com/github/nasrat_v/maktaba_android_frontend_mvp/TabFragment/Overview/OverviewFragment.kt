package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModelRandomFactory
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class OverviewFragment : Fragment() {

    private var mDataset = arrayListOf<ListBModel>()
    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mSelectedBook: BModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_overview, container, false)

        initBookDetailsAttributes(rootView)
        mockDataset(container!!)
        initVerticalRecyclerView(rootView, container)
        return rootView
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setSelectedBook(book: BModel) {
        mSelectedBook = book
    }

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_overview)
        val adapterBookVertical = ListBRecyclerViewAdapter(container.context, mDataset, mBookClickCallback)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_review_overview_footer)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    @SuppressLint("SetTextI18n")
    private fun initBookDetailsAttributes(view: View) { // faire passer le selected book au fragment
        initBookDetailsInfoAttributes(view, R.id.overview_category_length, "Length", mSelectedBook.length.toString())
        initBookDetailsInfoAttributes(view, R.id.overview_category_file_size, "File size", mSelectedBook.fileSize)
        initBookDetailsInfoAttributes(view, R.id.overview_category_date_publication, "Date of publication", mSelectedBook.datePublication)
        initBookDetailsInfoAttributes(view, R.id.overview_category_genre, "Genre", mSelectedBook.genre.name)
        initBookDetailsInfoAttributes(view, R.id.overview_category_country, "Country", mSelectedBook.country)
        initBookDetailsInfoAttributes(view, R.id.overview_category_publisher, "Publisher", mSelectedBook.publisher)
    }

    private fun initBookDetailsInfoAttributes(view: View, idLayout: Int, title: String, content: String) {
        val layout = view.findViewById<LinearLayout>(idLayout)
        val titleCategory = layout.findViewById<TextView>(R.id.title_category)
        val contentCategory = layout.findViewById<TextView>(R.id.content_category)

        titleCategory.text = title
        contentCategory.text = content
    }

    private fun mockDataset(container: ViewGroup) {
        val factory = BModelRandomFactory(container.context)

        mDataset.add(ListBModel("More Books from this Authors", factory.getRandomsInstances(3)))
    }
}