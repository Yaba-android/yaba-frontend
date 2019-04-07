package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.NoTitleListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Language.StringLocaleResolver
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RRecyclerViewAdapter
import com.mcxiaoke.koi.ext.find

class ReviewFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mSelectedBook: BModel
    private lateinit var mAdapterBookVertical: NoTitleListBRecyclerViewAdapter
    private lateinit var mAdapterReviewVertical: RRecyclerViewAdapter
    private lateinit var mAuthorTitle: TextView
    private lateinit var mAuthorDesc: TextView
    private lateinit var mAuthorPicture: ImageView
    private val mDatasetBooks = arrayListOf<NoTitleListBModel>()
    private val mDatasetReviews = arrayListOf<RModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_review, container, false)

        mAuthorTitle = rootView.findViewById(R.id.author_title_review_overview)
        mAuthorDesc = rootView.findViewById(R.id.author_desc_review_overview)
        mAuthorPicture = rootView.findViewById(R.id.author_picture_review_overview)

        initAuthorButtonText(rootView)
        initReviewButtonText(rootView)
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

    fun setBookVerticalRecyclerView(books: ArrayList<NoTitleListBModel>) {
        mDatasetBooks.clear()
        mDatasetBooks.addAll(books)
    }

    fun setReviewVerticalRecyclerView(reviews: ArrayList<RModel>) {
        mDatasetReviews.clear()
        mDatasetReviews.addAll(reviews)
    }

    fun notifyVerticalBookDataSetChanged() {
        mAdapterBookVertical.notifyDataSetChanged()
    }

    fun notifyVerticalReviewDataSetChanged() {
        mAdapterReviewVertical.notifyDataSetChanged()
    }

    fun initBookAttributes() {
        initAuthorAttributes()
    }

    fun setLanguageCode(languageCode: String) {
        mLanguage = languageCode
    }

    private fun initAuthorAttributes() {
        mAuthorTitle.text = mSelectedBook.author.name
        mAuthorDesc.text = mSelectedBook.author.desc
        mAuthorPicture.setImageResource(mSelectedBook.author.picture)
    }

    private fun initAuthorButtonText(view: View) {
        val button = view.findViewById<Button>(R.id.button_read_more_author_review_overview)

        button.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.read_more))
    }

    private fun initReviewButtonText(view: View) {
        val button = view.findViewById<Button>(R.id.button_more_review)

        button.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.view_all_reviews))
    }

    private fun initReviewVerticalRecyclerView(view: View, container: ViewGroup) {
        val reviewVerticalRecyclerView = view.findViewById<RecyclerView>(R.id.review_vertical_recyclerview)

        mAdapterReviewVertical = RRecyclerViewAdapter(container.context, mDatasetReviews)
        reviewVerticalRecyclerView.setHasFixedSize(true)
        reviewVerticalRecyclerView.layoutManager =
            LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        reviewVerticalRecyclerView.adapter = mAdapterReviewVertical
        reviewVerticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_review_vertical_recycler_view)
        )
    }

    private fun initBookVerticalRecyclerView(view: View, container: ViewGroup) {
        val layoutTitle = view.findViewById<RelativeLayout>(R.id.title_layout_recyclerview)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val viewAllButton = layoutTitle.findViewById<Button>(R.id.view_all_button)
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_review)
        val bookVerticalRecyclerView =
            view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_review_overview_footer)

        mAdapterBookVertical =
            NoTitleListBRecyclerViewAdapter(
                container.context,
                mDatasetBooks,
                mBookClickCallback,
                mLanguage
            )
        title.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.title_review_overview_recyclerview))
        viewAllButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.view_all))
        bookVerticalRecyclerView.setHasFixedSize(true)
        bookVerticalRecyclerView.layoutManager =
            LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        bookVerticalRecyclerView.adapter = mAdapterBookVertical
        bookVerticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_book_vertical_recycler_view)
        )
        bookVerticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}