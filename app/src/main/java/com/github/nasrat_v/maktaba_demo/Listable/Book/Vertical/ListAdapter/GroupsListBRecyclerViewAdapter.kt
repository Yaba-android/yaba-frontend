package com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListAdapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nasrat_v.maktaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_demo.ICallback.IGroupClickCallback
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Adapter.GroupsBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_demo.Listable.LeftOffsetDecoration
import com.github.nasrat_v.maktaba_demo.R

class GroupsListBRecyclerViewAdapter(
    private var context: Context,
    private var listNoTitleListBModel: ArrayList<GroupListBModel>,
    private var bookClickCallback: IBookClickCallback,
    private var groupClickCallback: IGroupClickCallback
) : androidx.recyclerview.widget.RecyclerView.Adapter<GroupsListBRecyclerViewAdapter.ViewHolder>() {

    private var viewPool = androidx.recyclerview.widget.RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(
            R.layout.vertical_generic_recyclerview_book, container, false
        )
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return listNoTitleListBModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = listNoTitleListBModel[position]
        val horizontalRecyclerViewAdapter = GroupsBRecyclerViewAdapter(context, model.groupModels)

        horizontalRecyclerViewAdapter.setBookClickCallback(bookClickCallback)
        horizontalRecyclerViewAdapter.setGroupClickCallback(groupClickCallback)
        holder.horizontalRecyclerView.setRecycledViewPool(viewPool)
        holder.horizontalRecyclerView.setHasFixedSize(true)
        holder.horizontalRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        holder.horizontalRecyclerView.adapter = horizontalRecyclerViewAdapter
        holder.horizontalRecyclerView.addItemDecoration(
            LeftOffsetDecoration(context, R.dimen.left_group_book_horizontal_recycler_view)
        )
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var horizontalRecyclerView = itemView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.horizontal_recyclerview)!!
    }
}
