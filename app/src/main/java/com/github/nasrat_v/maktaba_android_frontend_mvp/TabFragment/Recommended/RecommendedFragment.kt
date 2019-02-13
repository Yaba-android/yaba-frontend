package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Recommended

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextSwitcher
import android.widget.TextView
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.DiscreteScrollViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.DiscreteScrollViewScrollStateChangeListener
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.Model
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListRecyclerViewBottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class RecommendedFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private var mDataset = arrayListOf<ListModel>()
    private var hmodels = arrayListOf<Model>()

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
        val title = view.findViewById<TextView>(R.id.title_book_discretescrollview)
        val author = view.findViewById<TextView>(R.id.author_book_discretescrollview)
        val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar_book_discretescrollview)
        val numberRating = view.findViewById<TextView>(R.id.number_rating_book_discretescrollview)
        val fadeIn = AnimationUtils.loadAnimation(container.context, R.anim.fade_in);
        val listener = DiscreteScrollViewScrollStateChangeListener(title, author, ratingBar, numberRating, fadeIn, hmodels)

        discreteRecyclerViewAdapter.setTabFragmentClickCallback(mTabFragmentClickCallback)
        discreteScrollView.setHasFixedSize(true)
        discreteScrollView.adapter = discreteRecyclerViewAdapter
        discreteScrollView.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.75f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build()
        )
        discreteScrollView.addOnItemChangedListener { _, adapterPosition -> // initialisation carousel
            title.text = hmodels[adapterPosition].title
            author.text = hmodels[adapterPosition].author
            ratingBar.rating = hmodels[adapterPosition].rating
            numberRating.text = ("(" + hmodels[adapterPosition].numberRating + ")")
        }
        discreteScrollView.addScrollStateChangeListener(listener) // scroll event carousel
    }

    private fun initVerticalRecycler(view: View, container: ViewGroup) {
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_recommended)
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical = ListRecyclerViewAdapter(container.context, mDataset, mTabFragmentClickCallback)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(ListRecyclerViewBottomOffsetDecoration(container.context, R.dimen.book_vertical_recycler_view))
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDataset() {
        hmodels = arrayListOf<Model>()

        hmodels.add(Model(R.drawable.forest_small, "The Forest", "Lombok Indonesia", 4f, 102))
        hmodels.add(Model(R.drawable.kohlarn_small, "Beach", "Koh Larn", 5f, 28))
        hmodels.add(Model(R.drawable.forest_small, "The Waterfall", "Water", 4.5f, 356))
        hmodels.add(Model(R.drawable.kohlarn_small, "View Point", "Thailand", 3.5f, 188))
        hmodels.add(Model(R.drawable.forest_small, "Monkey forest", "Indonesia Traveler", 4f, 9))
        hmodels.add(Model(R.drawable.kohlarn_small, "Sea and beach", "Next Pattaya", 3f, 42))
        mDataset.add(ListModel("Authors recommended for you", hmodels))
        mDataset.add(ListModel("Recommended for you", hmodels))
    }
}