package com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.nasrat_v.yaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Provider.ServerRoutesSingleton

class BigBRecyclerViewAdapter(
    private var context: Context,
    private var languageCode: String,
    private var list: ArrayList<BModel>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<BigBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_big_book, container, false
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
        val url = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + model.imagePath)

        // load image asynchronously with cache and placeholder
        Glide.with(context).load(url).placeholder(R.drawable.empty_book).into(holder.mImage)
        holder.mTitle.text = model.title
        holder.mAuthor.text = model.author.name
        holder.mRating.rating = model.rating
        holder.mNumberRating.text = ("(" + model.numberRating + ")")
        if (languageCode == StringLocaleResolver.ARABIC_LANGUAGE_CODE) {
            holder.mPrice.text = ("$" + model.price)
        } else {
            holder.mPrice.text = (model.price.toString() + "$")
        }
        holder.itemView.setOnClickListener {
            mBookClickCallback.bookEventButtonClicked(list[position])
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var mTitle = itemView.findViewById<TextView>(R.id.horizontal_title)!!
        var mAuthor = itemView.findViewById<TextView>(R.id.horizontal_author)!!
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
        var mRating = itemView.findViewById<RatingBar>(R.id.horizontal_rating_bar)!!
        var mNumberRating = itemView.findViewById<TextView>(R.id.horizontal_number_rating)!!
        var mPrice = itemView.findViewById<TextView>(R.id.horizontal_price)!!
    }
}
