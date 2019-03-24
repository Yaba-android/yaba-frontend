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
    private lateinit var mAuthorTitle: TextView
    private lateinit var mAuthorDesc: TextView
    private lateinit var mAuthorPicture: ImageView
    private lateinit var mResumeBook: TextView
    private lateinit var mLayoutLength: LinearLayout
    private lateinit var mLayoutFileSize: LinearLayout
    private lateinit var mLayoutDatePublication: LinearLayout
    private lateinit var mLayoutGenre: LinearLayout
    private lateinit var mLayoutCountry: LinearLayout
    private lateinit var mLayoutPublisher: LinearLayout

    private val mDataset = arrayListOf<ListBModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_overview, container, false)

        mAuthorTitle = rootView.findViewById(R.id.author_title_review_overview)
        mAuthorDesc = rootView.findViewById(R.id.author_desc_review_overview)
        mAuthorPicture = rootView.findViewById(R.id.author_picture_review_overview)
        mResumeBook = rootView.findViewById(R.id.resume_book_overview)
        mLayoutLength = rootView.findViewById(R.id.overview_category_length)
        mLayoutFileSize = rootView.findViewById(R.id.overview_category_file_size)
        mLayoutDatePublication = rootView.findViewById(R.id.overview_category_date_publication)
        mLayoutGenre = rootView.findViewById(R.id.overview_category_genre)
        mLayoutCountry = rootView.findViewById(R.id.overview_category_country)
        mLayoutPublisher = rootView.findViewById(R.id.overview_category_publisher)

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

    fun initBookAttributes() {
        initAuthorAttributes()
        initBookDetailsAttributes()
    }

    fun notifyVerticalDataSetChanged() {
        mAdapterBookVertical.notifyDataSetChanged()
    }

    private fun initAuthorAttributes() {
        mAuthorTitle.text = mSelectedBook.author.name
        mAuthorDesc.text = mSelectedBook.author.desc
        mAuthorPicture.setImageResource(mSelectedBook.author.picture)
    }

    @SuppressLint("SetTextI18n")
    private fun initBookDetailsAttributes() { // faire passer le selected book au fragment
        mResumeBook.text = mSelectedBook.resume
        initBookDetailsInfoAttributes(mLayoutLength, "Length", mSelectedBook.length.toString())
        initBookDetailsInfoAttributes(mLayoutFileSize, "File size", mSelectedBook.fileSize)
        initBookDetailsInfoAttributes(mLayoutDatePublication, "Date of publication", mSelectedBook.datePublication)
        initBookDetailsInfoAttributes(mLayoutGenre, "Genre", mSelectedBook.genre.name)
        initBookDetailsInfoAttributes(mLayoutCountry, "Country", mSelectedBook.country)
        initBookDetailsInfoAttributes(mLayoutPublisher, "Publisher", mSelectedBook.publisher)
    }

    private fun initBookDetailsInfoAttributes(layout: LinearLayout, title: String, content: String) {
        val titleCategory = layout.findViewById<TextView>(R.id.title_category)
        val contentCategory = layout.findViewById<TextView>(R.id.content_category)

        titleCategory.text = title
        contentCategory.text = content
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
}