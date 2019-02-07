package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tabbed_browse.view.*

class BrowseTabbedFragment : Fragment() {

    lateinit var mClickInterface: ClickInterface
    lateinit var viewPager: ViewPager
    lateinit var rootView: View
    private var models = arrayListOf<Model>()

    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    var mDataset = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_tabbed_browse, container, false)

        for (i in 0..50)
            mDataset.add("New Title #$i")

        mRecyclerView = rootView.findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager = mLayoutManager
        val mAdapter = RecyclerViewAdapter(mDataset)
        mRecyclerView.adapter = mAdapter

        return rootView
    }

    fun setClickInterface(cInterface: ClickInterface) {
        mClickInterface = cInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtonGenreNav()
    }

    private fun initButtonGenreNav() {
        val buttonGenre = rootView.findViewById<Button>(R.id.button_genre_nav)

        buttonGenre.setOnClickListener {
            mClickInterface.buttonClicked() // l'event click est envoyé à l'activity parent grâce à l'interface
        }
    }
}