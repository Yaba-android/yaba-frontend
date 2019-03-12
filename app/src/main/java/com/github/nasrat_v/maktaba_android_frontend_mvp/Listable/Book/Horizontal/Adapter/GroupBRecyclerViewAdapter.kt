package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IDownloadBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class GroupBRecyclerViewAdapter(
    private var context: Context,
    private var downloadedBooks: ArrayList<DownloadListBModel>,
    private var list: ArrayList<BModel>
) : RecyclerView.Adapter<GroupBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mDownloadBookClickCallback: IDownloadBookClickCallback

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

        if (isBookAlreadyDownloaded(model))
            holder.mButtonDownload.visibility = View.INVISIBLE
        holder.mImage.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, model.title, Toast.LENGTH_SHORT).show()
            // envoyer le bon livre grace à position
            mBookClickCallback.bookEventButtonClicked(list[position])
        }
        holder.mButtonDownload.setOnClickListener {
            Toast.makeText(context, ("Downloading " + model.title + " ..."), Toast.LENGTH_SHORT).show()
            mDownloadBookClickCallback.downloadBookEventButtonClicked(list[position])
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setDownloadBookClickCallback(downloadBookClickCallback: IDownloadBookClickCallback) {
        mDownloadBookClickCallback = downloadBookClickCallback
    }

    private fun isBookAlreadyDownloaded(book: BModel): Boolean {
        downloadedBooks.forEach {
            if (it.bookModels.find { dlBook -> dlBook.book == book } != null)
                return true
        }
        return false
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
        var mButtonDownload = itemView.findViewById<Button>(R.id.button_download_book_library)!!
    }
}
