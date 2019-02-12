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
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.Model
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListRecyclerViewBottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.MainContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class BrowseFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private lateinit var mAdditionalClickCallback: MainContainerFragment.AdditionalClickCallback
    private var mDataset = arrayListOf<ListModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        mockDataset()

        val linearLayout = rootView.findViewById<LinearLayout>(R.id.root_linear_layout_browse)
        val adapterBookVertical =
            ListRecyclerViewAdapter(
                container!!.context,
                mDataset,
                mTabFragmentClickCallback
            )
        val verticalRecyclerView = rootView.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_browse)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            ListRecyclerViewBottomOffsetDecoration(
                container.context,
                R.dimen.book_vertical_recycler_view
            )
        )
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
        val hmodels = arrayListOf<Model>()
        hmodels.add(
            Model(
                R.drawable.forest_small,
                "The Forest",
                "Lombok Indonesia"
            )
        )
        hmodels.add(
            Model(
                R.drawable.kohlarn_small,
                "Beach",
                "Koh Larn"
            )
        )
        hmodels.add(
            Model(
                R.drawable.forest_small,
                "The Waterfall",
                "Water"
            )
        )
        hmodels.add(
            Model(
                R.drawable.kohlarn_small,
                "View Point",
                "Thailand"
            )
        )
        hmodels.add(
            Model(
                R.drawable.forest_small,
                "Monkey forest",
                "Indonesia Traveler"
            )
        )
        hmodels.add(
            Model(
                R.drawable.kohlarn_small,
                "Sea and beach",
                "Next Pattaya"
            )
        )
        mDataset.add(
            ListModel(
                "All Books",
                hmodels
            )
        )
        mDataset.add(ListModel("", hmodels))
    }
}