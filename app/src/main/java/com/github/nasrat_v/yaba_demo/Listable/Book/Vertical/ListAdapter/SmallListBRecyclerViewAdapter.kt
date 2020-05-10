package com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListAdapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Adapter.SmallBRecyclerViewAdapter
import com.github.nasrat_v.yaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.R
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class SmallListBRecyclerViewAdapter(
    private var context: Context, private var listNoTitleListBModel: ArrayList<NoTitleListBModel>,
    private var bookClickCallback: IBookClickCallback
) : androidx.recyclerview.widget.RecyclerView.Adapter<SmallListBRecyclerViewAdapter.ViewHolder>() {

    private var viewPool = androidx.recyclerview.widget.RecyclerView.RecycledViewPool()

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
            SmallBRecyclerViewAdapter(
                context,
                model.bookModels
            )

        horizontalRecyclerViewAdapter.setTabFragmentClickCallback(bookClickCallback)
        holder.horizontalRecyclerView.setRecycledViewPool(viewPool)
        holder.horizontalRecyclerView.setHasFixedSize(true)
        holder.horizontalRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        holder.horizontalRecyclerView.adapter = horizontalRecyclerViewAdapter
        GravitySnapHelper(Gravity.END).attachToRecyclerView(holder.horizontalRecyclerView)
        /*holder.horizontalRecyclerView.addItemDecoration(
            LeftOffsetDecoration(
                context,
                R.dimen.left_small_book_vertical_recycler_view
            )
        )*/
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var horizontalRecyclerView = itemView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.horizontal_recyclerview)!!
    }
}
