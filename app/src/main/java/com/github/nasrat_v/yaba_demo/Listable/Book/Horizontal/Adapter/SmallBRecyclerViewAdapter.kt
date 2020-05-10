package com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.nasrat_v.yaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Provider.ServerRoutesSingleton

class SmallBRecyclerViewAdapter(
    private var context: Context,
    private var list: ArrayList<BModel>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SmallBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.horizontal_recyclerview_small_book, container, false
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
        val url = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + model.imagePath)

        // load image asynchronously with cache and placeholder
        Glide.with(context).load(url).placeholder(R.drawable.empty_book).into(holder.mImage)
        holder.itemView.setOnClickListener {
            // envoyer le bon livre grace à position
            mBookClickCallback.bookEventButtonClicked(list[position])
        }
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var mImage = itemView.findViewById<ImageView>(R.id.horizontal_small_image)!!
    }
}
