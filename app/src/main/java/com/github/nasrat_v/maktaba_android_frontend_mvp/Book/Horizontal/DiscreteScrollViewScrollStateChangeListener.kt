package com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal

import android.view.animation.Animation
import android.widget.RatingBar
import android.widget.TextView
import com.yarolegovich.discretescrollview.DiscreteScrollView

class DiscreteScrollViewScrollStateChangeListener(private var title: TextView,
                                                  private var author: TextView,
                                                  private var ratingBar: RatingBar,
                                                  private var numberRating: TextView,
                                                  private var fadeIn: Animation,
                                                  private var list: ArrayList<BModel>)
    : DiscreteScrollView.ScrollStateChangeListener<DiscreteScrollViewAdapter.ViewHolder> {

    override fun onScrollStart(currentItemHolder: DiscreteScrollViewAdapter.ViewHolder,
                               adapterPosition: Int) {
    }

    override fun onScrollEnd(currentItemHolder: DiscreteScrollViewAdapter.ViewHolder,
                             adapterPosition: Int) {
        title.text = list[adapterPosition].title
        author.text = list[adapterPosition].author
        ratingBar.rating = list[adapterPosition].rating
        numberRating.text = ("(" + list[adapterPosition].numberRating + ")")
    }

    override fun onScroll(scrollPosition: Float, currentPosition: Int, newPosition: Int,
                          currentHolder: DiscreteScrollViewAdapter.ViewHolder?,
                          newCurrent: DiscreteScrollViewAdapter.ViewHolder?) {
        if ((scrollPosition in 0.2f..0.4f) || (scrollPosition in -0.4f..-0.2f)) {
            title.text = list[newPosition].title
            author.text = list[newPosition].author
            ratingBar.rating = list[newPosition].rating
            numberRating.text = ("(" + list[newPosition].numberRating + ")")
            title.startAnimation(fadeIn)
            author.startAnimation(fadeIn)
            ratingBar.startAnimation(fadeIn)
            numberRating.startAnimation(fadeIn)
        }
    }
}



