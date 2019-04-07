package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Groups

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IGroupClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Language.StringLocaleResolver
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.GroupsListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class GroupsFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mGroupClickCallback: IGroupClickCallback
    private lateinit var mAdapterBookVertical: GroupsListBRecyclerViewAdapter
    private var mDataset = arrayListOf<GroupListBModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_groups, container, false)

        initVerticalRecyclerView(rootView, container!!)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // click event button peut etre geré ici
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setGroupClickCallback(groupClickCallback: IGroupClickCallback) {
        mGroupClickCallback = groupClickCallback
    }

    fun setDatasetVerticalRecyclerView(dataset: ArrayList<GroupListBModel>) {
        mDataset.clear()
        mDataset.addAll(dataset)
    }

    fun notifyVerticalDataSetChanged() {
        mAdapterBookVertical.notifyDataSetChanged()
    }

    fun setLanguageCode(languageCode: String) {
        mLanguage = languageCode
    }

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_double_recyclerview)
        val sortButton = view.findViewById<Button>(R.id.sort_button)

        mAdapterBookVertical =
            GroupsListBRecyclerViewAdapter(
                container.context,
                mDataset,
                mBookClickCallback,
                mGroupClickCallback
            )
        sortButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.sort))
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_groups_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}