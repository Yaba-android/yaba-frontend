package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.LayoutManager

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class CarouselLinearLayoutManager(context: Context, direction: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, direction, reverseLayout) {

    private val mShrinkAmount = 0.30f
    private val mShrinkDistance = 1f

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scrollHorizontallyBy(0, recycler, state)
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        val scalePoint = (width / 2f)
        val d0 = 0f
        val d1 = (mShrinkDistance * scalePoint)
        val s0 = 1f
        val s1 = (1f - mShrinkAmount)

        for (index in 0..(childCount - 1)) {
            val child = getChildAt(index)
            val childMidPoint = ((getDecoratedLeft(child!!) + getDecoratedRight(child)) / 2f)
            val d = Math.min(d1, Math.abs(scalePoint - childMidPoint))
            val scale = (s0 + (s1 - s0) * (d - d0) / (d1 - d0))

            child.scaleX = scale
            child.scaleY = scale
            //child.pivotY = (getDecoratedBottom(child) * 1f)
        }
        return scrolled
    }

    fun initFirstScrollPosition(index: Int) {
        scrollToPositionWithOffset(index, 90)
    }
}