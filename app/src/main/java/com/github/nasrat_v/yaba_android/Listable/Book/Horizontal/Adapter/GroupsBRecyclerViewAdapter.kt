package com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.nasrat_v.yaba_android.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_android.ICallback.IGroupClickCallback
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.yaba_android.R
import com.github.nasrat_v.yaba_android.Services.Provider.ServerRoutesSingleton

class GroupsBRecyclerViewAdapter(
    private var context: Context,
    private var list: ArrayList<GroupBModel>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<GroupsBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mGroupClickCallback: IGroupClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_groups_book, container, false
        )
        return ViewHolder(
            rootView
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = list[position]
        val books = group.bookModels
        val firstBook = books.first()
        val genre = group.genre
        val urlFirstImage = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + firstBook.imagePath)

        // load image asynchronously with cache and placeholder
        Glide.with(context).load(urlFirstImage).placeholder(R.drawable.empty_book).into(holder.mFirstImage)
        setSecondAndThirdImage(holder, books)
        holder.mGenreName.text = (genre.name + " (" + books.size + ")")
        holder.itemView.setOnClickListener {
            mGroupClickCallback.groupEventButtonClicked(group)
        }
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setGroupClickCallback(groupClickCallback: IGroupClickCallback) {
        mGroupClickCallback = groupClickCallback
    }

    private fun setSecondAndThirdImage(holder: ViewHolder, books: ArrayList<BModel>) {
        when {
            books.size > 2 -> {
                val urlSecondImage = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + books[1].imagePath)
                val urlThirdImage = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + books[2].imagePath)

                // load image asynchronously with cache and placeholder
                Glide.with(context).load(urlSecondImage).placeholder(R.drawable.empty_book).into(holder.mSecondImage)
                Glide.with(context).load(urlThirdImage).placeholder(R.drawable.empty_book).into(holder.mThirdImage)
            }
            books.size > 1 -> {
                val urlSecondImage = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + books[1].imagePath)

                // load image asynchronously with cache and placeholder
                Glide.with(context).load(urlSecondImage).placeholder(R.drawable.empty_book).into(holder.mSecondImage)
                holder.mSecondCardView.visibility = View.INVISIBLE
            }
            else -> {
                holder.mSecondCardView.visibility = View.INVISIBLE
                holder.mThirdCardView.visibility = View.INVISIBLE
            }
        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var mFirstCardView = itemView.findViewById<androidx.cardview.widget.CardView>(R.id.cardview_first_group)!!
        var mSecondCardView = itemView.findViewById<androidx.cardview.widget.CardView>(R.id.cardview_second_group)!!
        var mThirdCardView = itemView.findViewById<androidx.cardview.widget.CardView>(R.id.cardview_third_group)!!
        var mFirstImage = itemView.findViewById<ImageView>(R.id.horizontal_first_image)!!
        var mSecondImage = itemView.findViewById<ImageView>(R.id.horizontal_second_image)!!
        var mThirdImage = itemView.findViewById<ImageView>(R.id.horizontal_third_image)!!
        var mGenreName = itemView.findViewById<TextView>(R.id.text_genre_group)!!
    }
}
