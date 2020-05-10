package com.github.nasrat_v.yaba_demo.Listable.Genre.Horizontal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.yaba_demo.ICallback.IRecommendedAdditionalClickCallback
import com.github.nasrat_v.yaba_demo.R

class GPSRecyclerViewAdapter(
    private var context: Context, private var list: ArrayList<GModel>,
    private var mAdditionalClickCallback: IRecommendedAdditionalClickCallback
) : androidx.recyclerview.widget.RecyclerView.Adapter<GPSRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_popular_genre, container, false
        )
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mName.text = model.name
        holder.itemView.setOnClickListener {
            // envoyer le bon livre grace à position
            mAdditionalClickCallback.popularSpeciesEventButtonClicked(list[position])
        }
    }

    class ViewHolder(genreView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(genreView) {
        var mName = genreView.findViewById<TextView>(R.id.genre_name)!!
    }
}