package com.github.nasrat_v.maktaba_android_frontend_mvp.Animation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class BrowseActivityAnimation(private var context: Context) {

    fun resetAnimationFirstRecyclerView(recycler: RecyclerView, list: ArrayList<BModel>) {
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)

        fadeOut.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                list.clear()
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        recycler.startAnimation(fadeOut)
    }

    fun resetAnimationSecondRecyclerView(recycler: RecyclerView, list: ArrayList<ListBModel>) {
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)

        fadeOut.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                list.clear()
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        recycler.startAnimation(fadeOut)
    }

    fun resetAnimationTextView(text: TextView) {
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)

        fadeOut.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                text.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        text.startAnimation(fadeOut)
    }
}