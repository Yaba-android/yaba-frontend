package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class ReviewFragment : Fragment() {

    private var mDataset = arrayListOf<ReviewVerticalModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_review, container, false)

        mockDataset()

        val adapterReviewVertical =
            ReviewVerticalRecyclerViewAdapter(
                container!!.context,
                mDataset
            )
        val verticalRecyclerView = rootView.findViewById<RecyclerView>(R.id.review_vertical_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterReviewVertical
        return rootView
    }

    private fun mockDataset() {
        for (i in 1..3) {
            mDataset.add(
                ReviewVerticalModel(
                    "Jane Bloggs", "This book was really cool, " +
                            "and I would definitely read more by this author"
                )
            )
        }
    }
}