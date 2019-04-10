package com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListAdapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Adapter.BRecyclerViewAdapter
import com.github.nasrat_v.maktaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_demo.ICallback.IDeleteBrowseBookClickCallback
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_demo.R
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class ListEraseBRecyclerViewAdapter(
    private var context: Context, private var list: ArrayList<ListBModel>,
    private var bookClickCallback: IBookClickCallback,
    private var deleteBrowseBookClickCallback: IDeleteBrowseBookClickCallback,
    private var languageCode: String
) : androidx.recyclerview.widget.RecyclerView.Adapter<ListEraseBRecyclerViewAdapter.ViewHolder>() {

    private var viewPool = androidx.recyclerview.widget.RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.vertical_recyclerview_book_erase, container, false
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
        val title = model.title
        val horizontalRecyclerViewAdapter =
            BRecyclerViewAdapter(
                languageCode,
                model.bookModels
            )

        horizontalRecyclerViewAdapter.setTabFragmentClickCallback(bookClickCallback)
        holder.mTitle.text = title
        holder.mHorizontalRecyclerView.setRecycledViewPool(viewPool)
        holder.mHorizontalRecyclerView.setHasFixedSize(true)
        holder.mHorizontalRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        holder.mHorizontalRecyclerView.adapter = horizontalRecyclerViewAdapter
        GravitySnapHelper(Gravity.START).attachToRecyclerView(holder.mHorizontalRecyclerView)
        holder.mEraseButton.setOnClickListener {
            deleteBrowseBookClickCallback.recyclerViewEraseEventButtonClicked(holder.adapterPosition)
        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var mTitle = itemView.findViewById<TextView>(R.id.vertical_title)!!
        var mHorizontalRecyclerView = itemView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.horizontal_recyclerview)!!
        var mEraseButton = itemView.findViewById<Button>(R.id.button_erase_recyclerview)!!
    }
}
