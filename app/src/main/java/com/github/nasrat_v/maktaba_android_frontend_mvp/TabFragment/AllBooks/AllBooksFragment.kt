package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.AllBooks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.AllBooksListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class AllBooksFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mAdapterBookVertical: AllBooksListBRecyclerViewAdapter
    private lateinit var mDisplayMetrics: DisplayMetrics
    private var mDataset = arrayListOf<NoTitleListBModel>()
    private var mDownloadedBooks = arrayListOf<DownloadListBModel>()

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

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_double_recyclerview)

        mAdapterBookVertical =
            AllBooksListBRecyclerViewAdapter(
                container.context,
                mDataset,
                mDownloadedBooks,
                mBookClickCallback
            )
        mAdapterBookVertical.setDisplayMetrics(mDisplayMetrics)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_all_books_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}