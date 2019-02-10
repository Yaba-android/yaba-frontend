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

    private lateinit var mClickCallback: MainContainerFragment.ClickCallback
    private lateinit var rootView: View
    private lateinit var verticalRecyclerView: RecyclerView
    private lateinit var adapter: VerticalRecyclerViewAdapter
    private var mDataset = arrayListOf<VerticalItemModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        mockDataset()

        adapter = VerticalRecyclerViewAdapter(container!!.context, mDataset, mClickCallback)
        verticalRecyclerView = rootView.findViewById(R.id.vertical_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapter
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonGenre = rootView.findViewById<Button>(R.id.button_genre_nav)

        buttonGenre.setOnClickListener {
            mClickCallback.genreNavigationEventButtonClicked() // l'event click est envoyé à l'activity parent grâce à l'interface
        }
    }

    fun setClickCallback(clickCallback: MainContainerFragment.ClickCallback) {
        mClickCallback = clickCallback
    }

    private fun mockDataset() {
        var hmodels: ArrayList<HorizontalItemModel>

        for (i in 1..3) {
            hmodels = arrayListOf<HorizontalItemModel>()
            for (n in 1..5) {
                hmodels.add(HorizontalItemModel("Book $n", "A lire $n"))
            }
            mDataset.add(VerticalItemModel("Section $i", hmodels))
        }
    }
}