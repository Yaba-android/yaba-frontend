package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class VerticalRecyclerViewAdapter(var context: Context, var list: ArrayList<VerticalItemModel>) : RecyclerView.Adapter<VerticalRecyclerViewAdapter.ViewHolder>() {

    var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView =  LayoutInflater.from(container.context).inflate(R.layout.item_vertical, container, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        val title = model.title

        holder.mTitle.text = title
        holder.horizontalRecyclerView.setRecycledViewPool(viewPool)
        holder.horizontalRecyclerView.setHasFixedSize(true)
        //holder.horizontalRecyclerView.isNestedScrollingEnabled = false
        holder.horizontalRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.horizontalRecyclerView.adapter = HorizontalRecyclerViewAdapter(context, model.items)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTitle = itemView.findViewById<TextView>(R.id.vertical_title)
        var horizontalRecyclerView = itemView.findViewById<RecyclerView>(R.id.horizontal_recyclerview)
    }
}
