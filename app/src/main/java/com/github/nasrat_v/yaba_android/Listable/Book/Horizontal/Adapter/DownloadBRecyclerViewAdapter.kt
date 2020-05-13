package com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.nasrat_v.yaba_android.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.yaba_android.R
import com.github.nasrat_v.yaba_android.Services.Provider.ServerRoutesSingleton

class DownloadBRecyclerViewAdapter(
    private var context: Context,
    private var list: ArrayList<DownloadBModel>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<DownloadBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_big_book_image, container, false
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
        val url = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + model.book.imagePath)

        // load image asynchronously with cache and placeholder
        Glide.with(context).load(url).placeholder(R.drawable.empty_book).into(holder.mImage)
        holder.itemView.setOnClickListener {
            mBookClickCallback.bookEventButtonClicked(list[position].book)
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
    }
}
