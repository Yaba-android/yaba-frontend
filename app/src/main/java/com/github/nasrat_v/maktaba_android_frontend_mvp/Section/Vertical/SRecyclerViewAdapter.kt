package com.github.nasrat_v.maktaba_android_frontend_mvp.Section.Vertical

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IRecommendedAdditionalClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class SRecyclerViewAdapter(private var context: Context, private var list: ArrayList<SModel>,
                           private var mAdditionalClickCallback: IRecommendedAdditionalClickCallback)
    : RecyclerView.Adapter<SRecyclerViewAdapter.ViewHolder>() {

    private var mLastClickTime: Long = 0

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(R.layout.vertical_recyclerview_section, container, false)
        return ViewHolder(
            rootView
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mSectionButton.text = (model.name + " (" + model.nb + ')')
        holder.mSectionButton.setOnClickListener {
            Toast.makeText(context, model.name, Toast.LENGTH_SHORT).show()
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                // envoyer le bon livre grace à position
                mAdditionalClickCallback.sectionEventButtonClicked(list[position])
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    class ViewHolder(genreView: View) : RecyclerView.ViewHolder(genreView) {
        var mSectionButton = genreView.findViewById<TextView>(R.id.button_section)!!
    }
}