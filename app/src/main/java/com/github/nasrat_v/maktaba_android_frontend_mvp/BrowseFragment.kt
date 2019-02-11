package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class BrowseFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private lateinit var mAdditionalClickCallback: MainContainerFragment.AdditionalClickCallback
    private lateinit var rootView: View
    private lateinit var verticalRecyclerView: RecyclerView
    private lateinit var adapterBookVertical: BookVerticalRecyclerViewAdapter
    private var mDataset = arrayListOf<BookVerticalModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        mockDataset()

        adapterBookVertical = BookVerticalRecyclerViewAdapter(container!!.context, mDataset, mTabFragmentClickCallback)
        verticalRecyclerView = rootView.findViewById(R.id.book_vertical_recyclerview_browse)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(BookVerticalRecyclerViewBottomOffsetDecoration(container.context, R.dimen.book_vertical_recycler_view))
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonGenre = rootView.findViewById<Button>(R.id.button_genre_nav)

        buttonGenre.setOnClickListener {
            mAdditionalClickCallback.genreNavigationEventButtonClicked() // l'event click est envoyé à l'activity parent grâce à l'interface
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