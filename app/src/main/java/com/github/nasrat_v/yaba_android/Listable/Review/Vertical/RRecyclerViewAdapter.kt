package com.github.nasrat_v.yaba_android.Listable.Review.Vertical

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.github.nasrat_v.yaba_android.R

class RRecyclerViewAdapter(private var context: Context, private var list: ArrayList<RModel>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<RRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(container.context).inflate(
                R.layout.vertical_recyclerview_review, container, false
            )
        return ViewHolder(
            rootView
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mImage.setImageResource(model.image)
        holder.mAuthor.text = model.author
        holder.mComment.text = model.comment
        holder.mRating.rating = model.rating
    }

    class ViewHolder(reviewView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(reviewView) {
        var mImage = reviewView.findViewById<ImageView>(R.id.profile_picture_review)!!
        var mAuthor = reviewView.findViewById<TextView>(R.id.author_review)!!
        var mComment = reviewView.findViewById<TextView>(R.id.comment_review)!!
        var mRating = reviewView.findViewById<RatingBar>(R.id.rating_bar_review)!!
    }
}