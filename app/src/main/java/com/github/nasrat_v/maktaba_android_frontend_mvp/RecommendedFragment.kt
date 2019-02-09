package com.github.nasrat_v.maktaba_android_frontend_mvp

import `in`.goodiebag.carouselpicker.CarouselPicker
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RecommendedFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var verticalRecyclerView: RecyclerView
    private lateinit var adapter: VerticalRecyclerViewAdapter
    private var mDataset = arrayListOf<VerticalItemModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_recommended, container, false)

        val carouselPicker = rootView.findViewById<CarouselPicker>(R.id.carousel)

        val imageItems = arrayListOf<CarouselPicker.PickerItem>()
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher_round))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
        imageItems.add(CarouselPicker.DrawableItem(R.mipmap.ic_launcher))
        val imageAdapter = CarouselPicker.CarouselViewAdapter(container!!.context, imageItems, 0)
        carouselPicker.adapter = imageAdapter

        mockDataset()

        adapter = VerticalRecyclerViewAdapter(container.context, mDataset)
        verticalRecyclerView = rootView.findViewById(R.id.vertical_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapter
        return rootView
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