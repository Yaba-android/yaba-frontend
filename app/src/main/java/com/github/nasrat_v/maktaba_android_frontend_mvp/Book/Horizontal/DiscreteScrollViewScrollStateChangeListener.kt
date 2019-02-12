package com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal

import android.widget.TextView
import com.yarolegovich.discretescrollview.DiscreteScrollView

class DiscreteScrollViewScrollStateChangeListener(private var title: TextView,
                                                  private var author: TextView,
                                                  private var list: ArrayList<Model>)
    : DiscreteScrollView.ScrollStateChangeListener<DiscreteScrollViewAdapter.ViewHolder> {

    override fun onScrollStart(currentItemHolder: DiscreteScrollViewAdapter.ViewHolder,
                               adapterPosition: Int) {
        title.text = list[adapterPosition].title
        author.text = list[adapterPosition].author
    }

    override fun onScrollEnd(currentItemHolder: DiscreteScrollViewAdapter.ViewHolder,
                             adapterPosition: Int) {
        title.text = list[adapterPosition].title
        author.text = list[adapterPosition].author
    }

    override fun onScroll(scrollPosition: Float, currentPosition: Int, newPosition: Int,
                          currentHolder: DiscreteScrollViewAdapter.ViewHolder?,
                          newCurrent: DiscreteScrollViewAdapter.ViewHolder?) {
        if ((scrollPosition >= 0.7f) || (scrollPosition <= -0.7f)) {
            title.text = list[newPosition].title
            author.text = list[newPosition].author
        }
    }
}



