package com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.nasrat_v.maktaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_demo.R

class CarouselBRecyclerViewAdapter(private var context: Context, private var list: ArrayList<BModel>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CarouselBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_carousel_book_image, container, false
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

        holder.mImage.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            // envoyer le bon livre grace à position
            mBookClickCallback.bookEventButtonClicked(list[position])
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_image)!!
    }
}
