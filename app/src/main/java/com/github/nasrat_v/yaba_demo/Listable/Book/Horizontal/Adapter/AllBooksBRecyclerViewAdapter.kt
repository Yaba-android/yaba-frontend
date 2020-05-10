package com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.nasrat_v.yaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Provider.ServerRoutesSingleton

class AllBooksBRecyclerViewAdapter(
    private var context: Context,
    private var downloadedBooks: ArrayList<DownloadListBModel>,
    private var list: ArrayList<BModel>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AllBooksBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_big_library_book, container, false
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

        if (isBookAlreadyDownloaded(model))
            holder.mImageDownload.visibility = View.INVISIBLE
        // load image asynchronously with cache and placeholder
        Glide.with(context).load(url).placeholder(R.drawable.empty_book).into(holder.mImage)
        holder.itemView.setOnClickListener {
            mBookClickCallback.bookEventButtonClicked(list[position])
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    private fun isBookAlreadyDownloaded(book: BModel): Boolean {
        downloadedBooks.forEach {
            if (it.bookModels.find { dlBook -> dlBook.book == book } != null)
                return true
        }
        return false
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
        var mImageDownload = itemView.findViewById<ImageView>(R.id.download_book_library_image)!!
    }
}
