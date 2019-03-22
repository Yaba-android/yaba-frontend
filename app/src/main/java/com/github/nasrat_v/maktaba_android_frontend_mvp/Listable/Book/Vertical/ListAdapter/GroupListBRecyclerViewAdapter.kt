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
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Adapter.GroupBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.LeftOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class GroupListBRecyclerViewAdapter(
    private var context: Context,
    private var listAllBooks: ArrayList<NoTitleListBModel>,
    private var listDownloadedBooks: ArrayList<DownloadListBModel>,
    private var mBookClickCallback: IBookClickCallback,
    private var mDownloadBookClickCallback: IDownloadBookClickCallback
) : RecyclerView.Adapter<GroupListBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mHorizontalRecyclerViewAdapter: GroupBRecyclerViewAdapter
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

        mHorizontalRecyclerViewAdapter =
            GroupBRecyclerViewAdapter(
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
        holder.horizontalRecyclerView.addItemDecoration(
            LeftOffsetDecoration(context, R.dimen.left_big_book_horizontal_recycler_view)
        )
    }

    fun notifyDataSetChangedDownloadList() {
        this.notifyDataSetChanged()
        mHorizontalRecyclerViewAdapter.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var horizontalRecyclerView = itemView.findViewById<RecyclerView>(R.id.horizontal_recyclerview)!!
    }
}
