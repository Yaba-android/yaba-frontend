package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Browse

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBrowseBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBRecyclerViewBottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.MainContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class BrowseFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private lateinit var mAdditionalClickCallback: MainContainerFragment.AdditionalClickCallback
    private var mDataset = arrayListOf<ListBModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        mockDataset()
        initVerticalRecyclerView(rootView, container!!)
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

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_browse)
        val adapterBookVertical = ListBrowseBRecyclerViewAdapter(container.context, mDataset, mTabFragmentClickCallback)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_browse)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            ListBRecyclerViewBottomOffsetDecoration(container.context, R.dimen.bottom_browse_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDataset() {
        val hmodels = arrayListOf<BModel>()

        hmodels.add(BModel(R.drawable.forest_small, "The Forest", "Lombok Indonesia", 4f, 102))
        hmodels.add(BModel(R.drawable.kohlarn_small, "Beach", "Koh Larn", 5f, 28))
        hmodels.add(BModel(R.drawable.forest_small, "The Waterfall", "Water", 4.5f, 356))
        hmodels.add(BModel(R.drawable.kohlarn_small, "View Point", "Thailand", 3.5f, 188))
        hmodels.add(BModel(R.drawable.forest_small, "Monkey forest", "Indonesia Traveler", 4f, 9))
        hmodels.add(BModel(R.drawable.kohlarn_small, "Sea and beach", "Next Pattaya", 3f, 42))
        mDataset.add(ListBModel("All Books", hmodels))
        mDataset.add(ListBModel("", hmodels))
    }
}