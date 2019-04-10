package com.github.nasrat_v.maktaba_demo.Listable

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.annotation.DimenRes
import android.view.View

class LeftOffsetDecoration(private val mItemOffset: Int)
    : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int)
            : this(context.resources.getDimensionPixelSize(itemOffsetId))

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) != (parent.adapter!!.itemCount - 1)) { // not last item
            outRect.set(mItemOffset, 0, 0, 0)
        }
    }
}