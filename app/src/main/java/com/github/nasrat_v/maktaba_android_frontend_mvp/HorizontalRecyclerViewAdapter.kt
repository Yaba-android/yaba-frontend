package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class HorizontalRecyclerViewAdapter(private var context: Context, private var list: ArrayList<HorizontalItemModel>)
    : RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mClickCallback: MainContainerFragment.ClickCallback
    private var mLastClickTime: Long = 0

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView =  LayoutInflater.from(container.context).inflate(R.layout.item_horizontal, container, false)
        return ViewHolder(rootView, context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mTitle.text = model.title
        holder.itemView.setOnClickListener {
            Toast.makeText(context, model.title, Toast.LENGTH_SHORT).show()
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                // envoyer le bon livre grace à position
                mClickCallback.bookEventButtonClicked()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    fun setClickCallback(clickCallback: MainContainerFragment.ClickCallback) {
        mClickCallback = clickCallback
    }

    class ViewHolder(itemView: View, var context: Context): RecyclerView.ViewHolder(itemView) {
        var mTitle = itemView.findViewById<TextView>(R.id.horizontal_title)!!
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
    }
}
