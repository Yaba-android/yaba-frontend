package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.annotation.DimenRes
import android.view.View

class RightOffsetDecoration(private val mItemOffset: Int)
    : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int)
            : this(context.resources.getDimensionPixelSize(itemOffsetId))

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) != (parent.adapter!!.itemCount - 1)) { // not last item
            outRect.set(0, 0, mItemOffset, 0)
        }
    }
}