package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Adapter.AllBooksBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IDownloadBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.LeftOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class AllBooksListBRecyclerViewAdapter(
    private var context: Context,
    private var listAllBooks: ArrayList<NoTitleListBModel>,
    private var listDownloadedBooks: ArrayList<DownloadListBModel>,
    private var mBookClickCallback: IBookClickCallback,
    private var mDownloadBookClickCallback: IDownloadBookClickCallback
) : RecyclerView.Adapter<AllBooksListBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mHorizontalRecyclerViewAdapter: AllBooksBRecyclerViewAdapter
    private lateinit var mHorizontalRecyclerView: RecyclerView
    private lateinit var mLeftOffestDecoration: RecyclerView.ItemDecoration
    private var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.vertical_generic_recyclerview_book, container, false
        )
        return ViewHolder(
            rootView
        )
    }

    override fun getItemCount(): Int {
        return listAllBooks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = listAllBooks[position]

        mLeftOffestDecoration = LeftOffsetDecoration(context, R.dimen.left_big_book_horizontal_recycler_view)
        mHorizontalRecyclerViewAdapter =
            AllBooksBRecyclerViewAdapter(
                context,
                listDownloadedBooks,
                model.bookModels
            )
        mHorizontalRecyclerViewAdapter.setTabFragmentClickCallback(mBookClickCallback)
        mHorizontalRecyclerViewAdapter.setDownloadBookClickCallback(mDownloadBookClickCallback)
        holder.horizontalRecyclerView.setRecycledViewPool(viewPool)
        holder.horizontalRecyclerView.setHasFixedSize(true)
        holder.horizontalRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.horizontalRecyclerView.adapter = mHorizontalRecyclerViewAdapter
        holder.horizontalRecyclerView.addItemDecoration(mLeftOffestDecoration)
        mHorizontalRecyclerView = holder.horizontalRecyclerView
    }

    fun notifyDataSetChangedDownloadList() {
        mHorizontalRecyclerView.removeItemDecoration(mLeftOffestDecoration)
        mHorizontalRecyclerViewAdapter.notifyDataSetChanged()
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var horizontalRecyclerView = itemView.findViewById<RecyclerView>(R.id.horizontal_recyclerview)!!
    }
}
