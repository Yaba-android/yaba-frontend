package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Download

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.DownloadListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class DownloadFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private var mDataset = arrayListOf<DownloadListBModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_download, container, false)

        initVerticalRecyclerView(rootView, container!!)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // click event button peut etre geré ici
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setDatasetVerticalRecyclerView(dataset: ArrayList<DownloadListBModel>) {
        mDataset = dataset
    }

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val adapterBookVertical =
            DownloadListBRecyclerViewAdapter(
                container.context,
                mDataset,
                mBookClickCallback
            )
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_double_recyclerview)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_all_books_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}