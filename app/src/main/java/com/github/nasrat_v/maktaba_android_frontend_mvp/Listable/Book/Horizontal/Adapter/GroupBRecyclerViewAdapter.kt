package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class GroupBRecyclerViewAdapter(private var context: Context, private var list: ArrayList<GroupBModel>)
    : RecyclerView.Adapter<GroupBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_group_book, container, false
        )
        return ViewHolder(
            rootView
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val books = list[position].bookModels
        val firstBook = books.first()
        val genre = list[position].genre

        setSecondAndThirdImage(holder, books)
        holder.mFirstImage.setImageResource(firstBook.image)
        holder.mGenreName.text = (genre.name + " (" + books.size + ")")
        holder.itemView.setOnClickListener {
            Toast.makeText(context, genre.name, Toast.LENGTH_SHORT).show()
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    private fun setSecondAndThirdImage(holder: ViewHolder, books: ArrayList<BModel>) {
        when {
            books.size > 2 -> {
                holder.mSecondImage.setImageResource(books[1].image)
                holder.mThirdImage.setImageResource(books[2].image)
            }
            books.size > 1 -> {
                holder.mSecondImage.setImageResource(books[1].image)
                holder.mThirdLayout.visibility = View.INVISIBLE
            }
            else -> {
                holder.mSecondLayout.visibility = View.INVISIBLE
                holder.mThirdLayout.visibility = View.INVISIBLE
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val mFirstLayout = itemView.findViewById<RelativeLayout>(R.id.horizontal_first_image_layout_group)!!
        val mSecondLayout = itemView.findViewById<RelativeLayout>(R.id.horizontal_second_image_layout_group)!!
        val mThirdLayout = itemView.findViewById<RelativeLayout>(R.id.horizontal_third_image_layout_group)!!

        var mFirstImage = mFirstLayout.findViewById<ImageView>(R.id.horizontal_image)!!
        var mSecondImage = mSecondLayout.findViewById<ImageView>(R.id.horizontal_image)!!
        var mThirdImage = mThirdLayout.findViewById<ImageView>(R.id.horizontal_image)!!
        var mGenreName = itemView.findViewById<TextView>(R.id.text_genre_group)!!
    }
}
