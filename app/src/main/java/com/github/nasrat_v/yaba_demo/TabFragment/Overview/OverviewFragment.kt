package com.github.nasrat_v.yaba_demo.TabFragment.Overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListAdapter.NoTitleListBRecyclerViewAdapter
import com.github.nasrat_v.yaba_demo.Listable.BottomOffsetDecoration
import com.github.nasrat_v.yaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Provider.ServerRoutesSingleton

class OverviewFragment : androidx.fragment.app.Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mSelectedBook: BModel
    private lateinit var mAdapterBookVertical: NoTitleListBRecyclerViewAdapter
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
    private lateinit var mContext: Context
    private val mDataset = arrayListOf<NoTitleListBModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_overview, container, false)

        mContext = container!!.context
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

        initAuthorButtonText(rootView)
        initVerticalRecyclerView(rootView, container)
        return rootView
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setSelectedBook(book: BModel) {
        mSelectedBook = book
    }

    fun setBookVerticalRecyclerView(books: ArrayList<NoTitleListBModel>) {
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

    fun setLanguageCode(languageCode: String) {
        mLanguage = languageCode
    }

    private fun initAuthorAttributes() {
        val url = (ServerRoutesSingleton.ROUTE_SRV_AUTHORS + mSelectedBook.author.imagePath)

        // load image asynchronously with cache and placeholder
        Glide.with(mContext).load(url).placeholder(R.drawable.empty_author).into(mAuthorPicture)
        mAuthorTitle.text = mSelectedBook.author.name
        mAuthorDesc.text = mSelectedBook.author.desc
    }

    private fun initAuthorButtonText(view: View) {
        val button = view.findViewById<Button>(R.id.button_read_more_author_review_overview)

        button.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.read_more))
    }

    private fun initBookDetailsAttributes() { // faire passer le selected book au fragment
        mResumeBook.text = mSelectedBook.resume
        initBookDetailsInfoAttributes(
            mLayoutLength,
            getString(StringLocaleResolver(mLanguage).getRes(R.string.length)),
            mSelectedBook.length.toString()
        )
        initBookDetailsInfoAttributes(
            mLayoutFileSize,
            getString(StringLocaleResolver(mLanguage).getRes(R.string.file_size)),
            mSelectedBook.fileSize
        )
        initBookDetailsInfoAttributes(
            mLayoutDatePublication,
            getString(StringLocaleResolver(mLanguage).getRes(R.string.date_publication)),
            mSelectedBook.datePublication
        )
        initBookDetailsInfoAttributes(
            mLayoutGenre,
            getString(StringLocaleResolver(mLanguage).getRes(R.string.genre)),
            mSelectedBook.genre.name
        )
        initBookDetailsInfoAttributes(
            mLayoutCountry,
            getString(StringLocaleResolver(mLanguage).getRes(R.string.country)),
            mSelectedBook.country
        )
        initBookDetailsInfoAttributes(
            mLayoutPublisher,
            getString(StringLocaleResolver(mLanguage).getRes(R.string.publisher)),
            mSelectedBook.publisher
        )
    }

    private fun initBookDetailsInfoAttributes(layout: LinearLayout, title: String, content: String) {
        val titleCategory = layout.findViewById<TextView>(R.id.title_category)
        val contentCategory = layout.findViewById<TextView>(R.id.content_category)

        titleCategory.text = title
        contentCategory.text = content
    }

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val layoutTitle = view.findViewById<RelativeLayout>(R.id.title_layout_recyclerview)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val viewAllButton = layoutTitle.findViewById<Button>(R.id.view_all_button)
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_overview)
        val verticalRecyclerView =
            view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.book_vertical_recyclerview_review_overview_footer)

        mAdapterBookVertical =
            NoTitleListBRecyclerViewAdapter(
                container.context,
                mDataset,
                mBookClickCallback,
                mLanguage
            )
        title.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.title_review_overview_recyclerview))
        viewAllButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.view_all))
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            container.context,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}