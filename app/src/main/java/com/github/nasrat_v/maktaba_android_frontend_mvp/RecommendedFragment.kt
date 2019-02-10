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
import android.widget.Toast

class RecommendedFragment : Fragment() {

    private lateinit var mClickCallback: MainContainerFragment.ClickCallback
    private lateinit var rootView: View
    private lateinit var verticalRecyclerView: RecyclerView
    private lateinit var adapter: VerticalRecyclerViewAdapter
    private var mDataset = arrayListOf<VerticalItemModel>()
    private var bookSelectedCarousel = 0
    private var mLastClickTime: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_recommended, container, false)

        initCarousel(container!!)
        initVerticalRecycler(container)
        return rootView
    }

    fun setClickCallback(clickCallback: MainContainerFragment.ClickCallback) {
        mClickCallback = clickCallback
    }

    private fun initCarousel(container: ViewGroup) {
        val carouselPicker = rootView.findViewById<CarouselPicker>(R.id.carousel)

        val imageItems = arrayListOf<CarouselPicker.PickerItem>()
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher_round))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
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

        val buttonCarousel = rootView.findViewById<Button>(R.id.button_carousel)

        buttonCarousel.setOnClickListener {
            Toast.makeText(context, "Book Carousel", Toast.LENGTH_SHORT).show()
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                // envoyer le bon livre grace à bookSelectedCarousel
                mClickCallback.bookEventButtonClicked()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    private fun initVerticalRecycler(container: ViewGroup) {
        mockDataset()
        adapter = VerticalRecyclerViewAdapter(container.context, mDataset, mClickCallback)
        verticalRecyclerView = rootView.findViewById(R.id.vertical_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapter
    }

    private fun mockDataset() {
        var hmodels: ArrayList<HorizontalItemModel>

        for (i in 1..2) {
            hmodels = arrayListOf<HorizontalItemModel>()
            for (n in 1..5) {
                hmodels.add(HorizontalItemModel("Brochure $n", "A lire $n"))
            }
            mDataset.add(VerticalItemModel("Section $i", hmodels))
        }
    }
}