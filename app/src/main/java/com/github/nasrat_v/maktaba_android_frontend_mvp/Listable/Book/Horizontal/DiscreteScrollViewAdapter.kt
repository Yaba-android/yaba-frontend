package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class DiscreteScrollViewAdapter(private var context: Context, private var list: ArrayList<BModel>)
    : RecyclerView.Adapter<DiscreteScrollViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private var mLastClickTime: Long = 0

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView =  LayoutInflater.from(container.context).inflate(R.layout.horizontal_discretescrollview_no_text_book, container, false)

        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.mImage.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, model.title, Toast.LENGTH_SHORT).show()
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                // envoyer le bon livre grace à position
                mBookClickCallback.bookEventButtonClicked(list[position])
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
    }
}
