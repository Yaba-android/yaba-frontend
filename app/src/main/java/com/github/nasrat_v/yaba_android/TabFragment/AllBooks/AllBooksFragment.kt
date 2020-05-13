package com.github.nasrat_v.yaba_android.TabFragment.AllBooks

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListAdapter.AllBooksListBRecyclerViewAdapter
import com.github.nasrat_v.yaba_android.Listable.BottomOffsetDecoration
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_android.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_android.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_android.R

class AllBooksFragment : androidx.fragment.app.Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mAdapterBookVertical: AllBooksListBRecyclerViewAdapter
    private lateinit var mDisplayMetrics: DisplayMetrics
    private var mDataset = arrayListOf<NoTitleListBModel>()
    private var mDownloadedBooks = arrayListOf<DownloadListBModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_allbooks, container, false)

        initVerticalRecyclerView(rootView, container!!)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // click event button peut etre geré ici
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setDatasetVerticalRecyclerView(dataset: ArrayList<NoTitleListBModel>) {
        mDataset.clear()
        mDataset.addAll(dataset)
    }

    fun setDownloadedBooks(downloadedBooks: ArrayList<DownloadListBModel>) {
        mDownloadedBooks.clear()
        mDownloadedBooks.addAll(downloadedBooks)
    }

    fun notifyBothDataSetChanged() {
        mAdapterBookVertical.notifyDataSetChangedDownloadList()
        mAdapterBookVertical.notifyDataSetChanged()
    }

    fun setDisplayMetrics(displayMetrics: DisplayMetrics) {
        mDisplayMetrics = displayMetrics
    }

    fun setLanguageCode(languageCode: String) {
        mLanguage = languageCode
    }

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val verticalRecyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.vertical_double_recyclerview)
        val sortButton = view.findViewById<Button>(R.id.sort_button)

        mAdapterBookVertical =
            AllBooksListBRecyclerViewAdapter(
                container.context,
                mDataset,
                mDownloadedBooks,
                mBookClickCallback
            )
        sortButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.sort))
        mAdapterBookVertical.setDisplayMetrics(mDisplayMetrics)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            container.context,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_all_books_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}