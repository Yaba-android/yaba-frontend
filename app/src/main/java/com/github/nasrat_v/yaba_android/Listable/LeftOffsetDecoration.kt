package com.github.nasrat_v.yaba_android.Listable

import android.content.Context
import android.graphics.Rect
import androidx.annotation.DimenRes
import android.view.View

class LeftOffsetDecoration(private val mItemOffset: Int)
    : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int)
            : this(context.resources.getDimensionPixelSize(itemOffsetId))

    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) != (parent.adapter!!.itemCount - 1)) { // not last item
            outRect.set(mItemOffset, 0, 0, 0)
        }
    }
}