package com.github.nasrat_v.maktaba_android_frontend_mvp

import `in`.goodiebag.carouselpicker.CarouselPicker
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.view.ViewPager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class RecommendedFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private var mDataset = arrayListOf<BookVerticalModel>()
    private var bookSelectedCarousel = 0
    private var mLastClickTime: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_recommended, container, false)

        initCarousel(rootView, container!!)
        initVerticalRecycler(rootView, container)
        return rootView
    }

    fun setTabFragmentClickCallback(tabFragmentClickCallback: ITabFragmentClickCallback) {
        mTabFragmentClickCallback = tabFragmentClickCallback
    }

    private fun initCarousel(view: View, container: ViewGroup) {
        val carouselPicker = view.findViewById<CarouselPicker>(R.id.carousel)

        val imageItems = arrayListOf<CarouselPicker.PickerItem>()
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        imageItems.add(CarouselPicker.DrawableItem(R.drawable.forest))
        val imageAdapter = CarouselPicker.CarouselViewAdapter(container.context, imageItems, 0)
        carouselPicker.adapter = imageAdapter

        carouselPicker.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bookSelectedCarousel = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        val buttonCarousel = view.findViewById<Button>(R.id.button_carousel)

        buttonCarousel.setOnClickListener {
            Toast.makeText(context, "Book Carousel", Toast.LENGTH_SHORT).show()
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                // envoyer le bon livre grace à bookSelectedCarousel
                mTabFragmentClickCallback.bookEventButtonClicked()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    private fun initVerticalRecycler(view: View, container: ViewGroup) {
        mockDataset()
        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical = BookVerticalRecyclerViewAdapter(container.context, mDataset, mTabFragmentClickCallback)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.book_vertical_recyclerview_recommended)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(BookVerticalRecyclerViewBottomOffsetDecoration(container.context, R.dimen.book_vertical_recycler_view))
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDataset() {
        var hmodels: ArrayList<BookHorizontalModel>

        for (i in 1..2) {
            hmodels = arrayListOf<BookHorizontalModel>()
            for (n in 1..5) {
                hmodels.add(BookHorizontalModel("Brochure $n", "A lire $n"))
            }
            mDataset.add(BookVerticalModel("Section $i", hmodels))
        }
    }
}