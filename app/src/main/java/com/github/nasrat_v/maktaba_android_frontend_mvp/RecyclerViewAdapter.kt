package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecyclerViewAdapter(var mDataset: ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView =  LayoutInflater.from(container.context).inflate(R.layout.cardview_item, container, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTitle.text = mDataset[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTitle = itemView.findViewById<TextView>(R.id.row_title)
    }
}
