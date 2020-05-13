package com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.LayoutManager

import android.content.Context

class CarouselLinearLayoutManager(context: Context, direction: Int, reverseLayout: Boolean) :
    androidx.recyclerview.widget.LinearLayoutManager(context, direction, reverseLayout) {

    private val mShrinkAmount = 0.32f
    private val mShrinkDistance = 2f

    override fun onLayoutChildren(recycler: androidx.recyclerview.widget.RecyclerView.Recycler?, state: androidx.recyclerview.widget.RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scrollHorizontallyBy(0, recycler, state)
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: androidx.recyclerview.widget.RecyclerView.Recycler?, state: androidx.recyclerview.widget.RecyclerView.State?): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        val scalePoint = (width / 5f)
        val d0 = 0f
        val d1 = (mShrinkDistance * scalePoint)
        val s0 = 1f
        val s1 = (1f - mShrinkAmount)

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            val childMidPoint = ((getDecoratedLeft(child!!) + getDecoratedRight(child)) / 2f)
            val d = Math.min(d1, Math.abs(scalePoint - childMidPoint))
            val scale = (s0 + (s1 - s0) * (d - d0) / (d1 - d0))

            child.scaleX = scale
            child.scaleY = scale
        }
        return scrolled
    }

    fun initFirstScrollPosition(index: Int) {
        scrollToPositionWithOffset(index, 90)
    }
}