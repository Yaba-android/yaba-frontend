package com.github.nasrat_v.maktaba_demo.TabFragment.Download

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListAdapter.DownloadListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_demo.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_demo.R

class DownloadFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mAdapterBookVertical: DownloadListBRecyclerViewAdapter
    private lateinit var mDisplayMetrics: DisplayMetrics
    private var mDataset = arrayListOf<DownloadListBModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE

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
        mDataset.clear()
        mDataset.addAll(dataset)
    }

    fun notifyHorizontalDataSetChanged() {
        mAdapterBookVertical.notifyDataSetChangedDownloadList()
    }

    fun notifyVerticalDataSetChanged() {
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
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_double_recyclerview)
        val sortButton = view.findViewById<Button>(R.id.sort_button)

        mAdapterBookVertical =
            DownloadListBRecyclerViewAdapter(
                container.context,
                mDataset,
                mBookClickCallback
            )
        sortButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.sort))
        mAdapterBookVertical.setDisplayMetrics(mDisplayMetrics)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_download_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}