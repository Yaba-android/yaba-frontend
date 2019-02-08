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

    lateinit var mClickCallback: ContainerFragment.GenreNavigationClickCallback
    lateinit var rootView: View

    lateinit var verticalRecyclerView: RecyclerView
    lateinit var adapter: VerticalRecyclerViewAdapter

    var mDataset = arrayListOf<VerticalItemModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        var hmodels: ArrayList<HorizontalItemModel>

        for (i in 1..10) {
            hmodels = arrayListOf<HorizontalItemModel>()
            for (n in 1..5) {
                hmodels.add(HorizontalItemModel("Brochure #$n", "A lire #$n"))
            }
            mDataset.add(VerticalItemModel("Section #$i", hmodels))
        }

        adapter = VerticalRecyclerViewAdapter(container!!.context, mDataset)
        verticalRecyclerView = rootView.findViewById(R.id.vertical_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapter

        return rootView
    }

    fun setGenreNavigationClickCallback(clickCallback: ContainerFragment.GenreNavigationClickCallback) {
        mClickCallback = clickCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtonGenreNav()
    }

    private fun initButtonGenreNav() {
        val buttonGenre = rootView.findViewById<Button>(R.id.button_genre_nav)

        buttonGenre.setOnClickListener {
            mClickCallback.eventButtonClicked() // l'event click est envoyé à l'activity parent grâce à l'interface
        }
    }
}