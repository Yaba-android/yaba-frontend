package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Adapter.BigBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.LeftOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.RightOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class BigListBRecyclerViewAdapter(
    private var context: Context, private var listNoTitleListBModel: ArrayList<NoTitleListBModel>,
    private var bookClickCallback: IBookClickCallback,
    private var languageCode: String
) : RecyclerView.Adapter<BigListBRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mDisplayMetrics: DisplayMetrics
    private var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.vertical_generic_recyclerview_book, container, false
        )
        return ViewHolder(
            rootView
        )
    }

    override fun getItemCount(): Int {
        return listNoTitleListBModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = listNoTitleListBModel[position]
        val horizontalRecyclerViewAdapter =
            BigBRecyclerViewAdapter(
                languageCode,
                model.bookModels
            )

        horizontalRecyclerViewAdapter.setTabFragmentClickCallback(bookClickCallback)
        holder.horizontalRecyclerView.setRecycledViewPool(viewPool)
        holder.horizontalRecyclerView.setHasFixedSize(true)
        holder.horizontalRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.horizontalRecyclerView.adapter = horizontalRecyclerViewAdapter
        if (needNegativeDivider()) {
            holder.horizontalRecyclerView.addItemDecoration(
                LeftOffsetDecoration(context, R.dimen.left_big_book_horizontal_recycler_view)
            )
        } else {
            for (index in 0..(getNbItemDecoration() - 1)) {
                holder.horizontalRecyclerView.addItemDecoration(
                    LeftOffsetDecoration(context, R.dimen.left_divider)
                )
            }
            for (index in 0..(getNbItemDecoration() - 1)) {
                holder.horizontalRecyclerView.addItemDecoration(
                    RightOffsetDecoration(context, R.dimen.left_divider)
                )
            }
        }
    }

    fun setDisplayMetrics(displayMetrics: DisplayMetrics) {
        mDisplayMetrics = displayMetrics
    }

    private fun getScreenWidth(): Int {
        return mDisplayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return mDisplayMetrics.heightPixels
    }

    private fun needNegativeDivider(): Boolean {
        return ((context.resources.getDimensionPixelOffset(R.dimen.width_image_big_book) * 2) > (getScreenWidth() * 0.80f))
    }

    private fun getNbItemDecoration(): Int {
        val sizeImage = context.resources.getDimensionPixelOffset(R.dimen.width_image_big_book)
        val size = ((getScreenWidth() / sizeImage) * 6f)
        return size.toInt()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var horizontalRecyclerView = itemView.findViewById<RecyclerView>(R.id.horizontal_recyclerview)!!
    }
}
