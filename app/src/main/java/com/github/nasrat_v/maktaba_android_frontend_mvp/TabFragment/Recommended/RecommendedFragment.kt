package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Recommended

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.DiscreteScrollViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.DiscreteScrollViewLeftOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBRecyclerViewBottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class RecommendedFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private var mDataset = arrayListOf<ListBModel>()
    private var hmodels = arrayListOf<BModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_recommended, container, false)

        mockDataset()
        initDiscreteScrollView(rootView, container!!)
        initVerticalRecycler(rootView, container)
        return rootView
    }

    fun setTabFragmentClickCallback(tabFragmentClickCallback: ITabFragmentClickCallback) {
        mTabFragmentClickCallback = tabFragmentClickCallback
    }

    private fun initDiscreteScrollView(view: View, container: ViewGroup) {
        val discreteScrollView = view.findViewById<DiscreteScrollView>(R.id.discrete_scroll_view)
        val discreteRecyclerViewAdapter = DiscreteScrollViewAdapter(container.context, hmodels)

        discreteRecyclerViewAdapter.setTabFragmentClickCallback(mTabFragmentClickCallback)
        discreteScrollView.setHasFixedSize(true)
        discreteScrollView.adapter = discreteRecyclerViewAdapter
        discreteScrollView.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.80f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.BOTTOM)
                .build()
        )
        /*discreteScrollView.addItemDecoration(
            DiscreteScrollViewLeftOffsetDecoration(container.context, R.dimen.left_book_horizontal_recycler_view)
        )*/
        if (hmodels.size > 0) {
            discreteScrollView.scrollToPosition((hmodels.size / 2))
        }
    }

    private fun initVerticalRecycler(view: View, container: ViewGroup) {
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_recommended)
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical = ListBRecyclerViewAdapter(container.context, mDataset, mTabFragmentClickCallback)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            ListBRecyclerViewBottomOffsetDecoration(container.context, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDataset() {
        hmodels = arrayListOf<BModel>()

        hmodels.add(BModel(R.drawable.forest_small, "The Forest", "Lombok Indonesia", 4f, 102))
        hmodels.add(BModel(R.drawable.kohlarn_small, "Beach", "Koh Larn", 5f, 28))
        hmodels.add(BModel(R.drawable.forest_small, "The Waterfall", "Water", 4.5f, 356))
        hmodels.add(BModel(R.drawable.kohlarn_small, "View Point", "Thailand", 3.5f, 188))
        hmodels.add(BModel(R.drawable.forest_small, "Monkey forest", "Indonesia Traveler", 4f, 9))
        hmodels.add(BModel(R.drawable.kohlarn_small, "Sea and beach", "Next Pattaya", 3f, 42))
        mDataset.add(ListBModel("Authors recommended for you", hmodels))
        mDataset.add(ListBModel("Recommended for you", hmodels))
    }
}