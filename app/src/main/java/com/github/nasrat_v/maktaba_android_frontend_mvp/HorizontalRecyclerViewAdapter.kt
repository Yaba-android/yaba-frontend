package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class HorizontalRecyclerViewAdapter(var context: Context, var list: ArrayList<HorizontalItemModel>) : RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView =  LayoutInflater.from(container.context).inflate(R.layout.item_horizontal, container, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mTitle.text = model.title
        holder.itemView.setOnClickListener {
            Toast.makeText(context, model.title, Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTitle = itemView.findViewById<TextView>(R.id.horizontal_title)
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)
    }
}
