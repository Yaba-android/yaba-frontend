package com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.nasrat_v.maktaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_demo.ICallback.IGroupClickCallback
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_demo.R

class GroupsBRecyclerViewAdapter(private var context: Context, private var list: ArrayList<GroupBModel>) :
    RecyclerView.Adapter<GroupsBRecyclerViewAdapter.ViewHolder>() {

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

        setSecondAndThirdImage(holder, books)
        holder.mFirstImage.setImageResource(firstBook.image)
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
                holder.mSecondImage.setImageResource(books[1].image)
                holder.mThirdImage.setImageResource(books[2].image)
            }
            books.size > 1 -> {
                holder.mThirdImage.setImageResource(books[1].image)
                holder.mSecondCardView.visibility = View.INVISIBLE
            }
            else -> {
                holder.mSecondCardView.visibility = View.INVISIBLE
                holder.mThirdCardView.visibility = View.INVISIBLE
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mFirstCardView = itemView.findViewById<CardView>(R.id.cardview_first_group)!!
        var mSecondCardView = itemView.findViewById<CardView>(R.id.cardview_second_group)!!
        var mThirdCardView = itemView.findViewById<CardView>(R.id.cardview_third_group)!!
        var mFirstImage = itemView.findViewById<ImageView>(R.id.horizontal_first_image)!!
        var mSecondImage = itemView.findViewById<ImageView>(R.id.horizontal_second_image)!!
        var mThirdImage = itemView.findViewById<ImageView>(R.id.horizontal_third_image)!!
        var mGenreName = itemView.findViewById<TextView>(R.id.text_genre_group)!!
    }
}
