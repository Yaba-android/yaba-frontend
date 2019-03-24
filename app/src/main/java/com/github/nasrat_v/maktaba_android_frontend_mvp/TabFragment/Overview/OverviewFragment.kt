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
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.RecommendedActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.ListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.BookDetailsContainerFragment

class OverviewFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mSelectedBook: BModel
    private lateinit var mAdapterBookVertical: ListBRecyclerViewAdapter
    private val mDataset = arrayListOf<ListBModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_overview, container, false)

        initAuthorAttributes(rootView)
        initBookDetailsAttributes(rootView)
        initVerticalRecyclerView(rootView, container!!)
        return rootView
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setSelectedBook(book: BModel) {
        mSelectedBook = book
    }

    fun setBookVerticalRecyclerView(books: ArrayList<ListBModel>) {
        mDataset.clear()
        mDataset.addAll(books)
    }

    fun notifyVerticalDataSetChanged() {
        mAdapterBookVertical.notifyDataSetChanged()
    }

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_overview)
        val verticalRecyclerView =
            view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_review_overview_footer)

        mAdapterBookVertical =
            ListBRecyclerViewAdapter(
                container.context,
                mDataset,
                mBookClickCallback
            )
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initAuthorAttributes(view: View) {
        val authorTitle = view.findViewById<TextView>(R.id.author_desc_review_overview)
        val authorDesc = view.findViewById<TextView>(R.id.author_desc_review_overview)
        val authorPicture = view.findViewById<ImageView>(R.id.author_picture_review_overview)

        authorTitle.text = mSelectedBook.author.name
        authorDesc.text = mSelectedBook.author.desc
        authorPicture.setImageResource(mSelectedBook.author.picture)
    }

    @SuppressLint("SetTextI18n")
    private fun initBookDetailsAttributes(view: View) { // faire passer le selected book au fragment
        val resumeBook = view.findViewById<TextView>(R.id.resume_book_overview)

        resumeBook.text = mSelectedBook.resume
        initBookDetailsInfoAttributes(view, R.id.overview_category_length, "Length", mSelectedBook.length.toString())
        initBookDetailsInfoAttributes(view, R.id.overview_category_file_size, "File size", mSelectedBook.fileSize)
        initBookDetailsInfoAttributes(
            view,
            R.id.overview_category_date_publication,
            "Date of publication",
            mSelectedBook.datePublication
        )
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
}