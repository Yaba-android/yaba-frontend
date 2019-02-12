package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

class BrowseFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private lateinit var mAdditionalClickCallback: MainContainerFragment.AdditionalClickCallback
    private var mDataset = arrayListOf<BookVerticalModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        mockDataset()

        val linearLayout = rootView.findViewById<LinearLayout>(R.id.root_linear_layout_browse)
        val adapterBookVertical = BookVerticalRecyclerViewAdapter(container!!.context, mDataset, mTabFragmentClickCallback)
        val verticalRecyclerView = rootView.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_browse)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(BookVerticalRecyclerViewBottomOffsetDecoration(container.context, R.dimen.book_vertical_recycler_view))
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonOpenGenre = view.findViewById<Button>(R.id.button_open_nav_genre)

        // l'event click est envoyé à l'activity parent grâce à l'interface
        buttonOpenGenre.setOnClickListener {
            mAdditionalClickCallback.genreNavigationEventButtonClicked()
        }
    }

    fun setTabFragmentClickCallback(tabFragmentClickCallback: ITabFragmentClickCallback) {
        mTabFragmentClickCallback = tabFragmentClickCallback
    }

    fun setAdditionalClickCallback(additionalClickCallback: MainContainerFragment.AdditionalClickCallback) {
        mAdditionalClickCallback = additionalClickCallback
    }

    private fun mockDataset() {
        var hmodels: ArrayList<BookHorizontalModel>

        for (i in 1..3) {
            hmodels = arrayListOf<BookHorizontalModel>()
            for (n in 1..5) {
                hmodels.add(BookHorizontalModel("Book $n", "A lire $n"))
            }
            mDataset.add(BookVerticalModel("Section $i", hmodels))
        }
    }
}