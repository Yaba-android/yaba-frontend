package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class BigBRecyclerViewAdapter(private var context: Context, private var list: ArrayList<BModel>)
    : RecyclerView.Adapter<BigBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_big_book, container, false
        )
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mTitle.text = model.title
        holder.mAuthor.text = model.author
        holder.mImage.setImageResource(model.image)
        holder.mRating.rating = model.rating
        holder.mNumberRating.text = ("(" + model.numberRating + ")")
        holder.mPrice.text = ("$" + model.price)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, model.title, Toast.LENGTH_SHORT).show()
            mBookClickCallback.bookEventButtonClicked(list[position])
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mTitle = itemView.findViewById<TextView>(R.id.horizontal_title)!!
        var mAuthor = itemView.findViewById<TextView>(R.id.horizontal_author)!!
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
        var mRating = itemView.findViewById<RatingBar>(R.id.horizontal_rating_bar)!!
        var mNumberRating = itemView.findViewById<TextView>(R.id.horizontal_number_rating)!!
        var mPrice = itemView.findViewById<TextView>(R.id.horizontal_price)!!
    }
}
