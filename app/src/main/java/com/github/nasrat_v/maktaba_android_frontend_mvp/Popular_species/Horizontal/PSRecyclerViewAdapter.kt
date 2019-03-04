package com.github.nasrat_v.maktaba_android_frontend_mvp.Popular_species.Horizontal

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class PSRecyclerViewAdapter(private var context: Context, private var list: ArrayList<PSModel>,
                            private var mTabFragmentClickCallback: ITabFragmentClickCallback)
    : RecyclerView.Adapter<PSRecyclerViewAdapter.ViewHolder>() {

    private var mLastClickTime: Long = 0

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(R.layout.horizontal_recyclerview_store_popular_species, container, false)
        return ViewHolder(
            rootView
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mName.text = model.name
        holder.itemView.setOnClickListener {
            Toast.makeText(context, model.name, Toast.LENGTH_SHORT).show()
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                // envoyer le bon livre grace à position
                mTabFragmentClickCallback.popularSpeciesEventButtonClicked(list[position])
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    class ViewHolder(genreView: View) : RecyclerView.ViewHolder(genreView) {
        var mName = genreView.findViewById<TextView>(R.id.genre_name)!!
    }
}