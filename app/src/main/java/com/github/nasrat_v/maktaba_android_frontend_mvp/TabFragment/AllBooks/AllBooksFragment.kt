package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.AllBooks

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.BigNoTextListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.LibraryContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class AllBooksFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_allbooks, container, false)

        initVerticalRecyclerView(rootView, container!!)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // click event button peut etre geré ici
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    private fun initVerticalRecyclerView(view: View, container: ViewGroup) {
        val mDataset = arrayListOf<NoTitleListBModel>()

        mockDatasetVerticalRecyclerView(mDataset)

        val linearLayout = view.findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val adapterBookVertical = BigNoTextListBRecyclerViewAdapter(container.context, mDataset, mBookClickCallback)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_double_recyclerview)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        /*verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(container.context, R.dimen.bottom_browse_book_vertical_recycler_view)
        )*/
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetVerticalRecyclerView(mDataset: ArrayList<NoTitleListBModel>) {
        val hmodelsOne = arrayListOf<BModel>()
        val hmodelsTwo = arrayListOf<BModel>()
        val hmodelsThree = arrayListOf<BModel>()
        val hmodelsFour = arrayListOf<BModel>()
        val hmodelsFive = arrayListOf<BModel>()
        val hmodelsSix = arrayListOf<BModel>()
        val hmodelsSeven = arrayListOf<BModel>()
        val hmodelsHeigth = arrayListOf<BModel>()

        hmodelsOne.add(BModel(R.drawable.book6, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsOne.add(BModel(R.drawable.book4, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsTwo.add(BModel(R.drawable.book2, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsTwo.add(BModel(R.drawable.book1, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsThree.add(BModel(R.drawable.book3, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsThree.add(BModel(R.drawable.book2, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsFour.add(BModel(R.drawable.book8, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsFour.add(BModel(R.drawable.book7, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsFive.add(BModel(R.drawable.book3, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsFive.add(BModel(R.drawable.book5, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsSix.add(BModel(R.drawable.book4, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsSix.add(BModel(R.drawable.book1, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsSeven.add(BModel(R.drawable.book8, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsSeven.add(BModel(R.drawable.book1, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsHeigth.add(BModel(R.drawable.book3, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsHeigth.add(BModel(R.drawable.book6, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        mDataset.add(NoTitleListBModel(hmodelsOne))
        mDataset.add(NoTitleListBModel(hmodelsTwo))
        mDataset.add(NoTitleListBModel(hmodelsThree))
        mDataset.add(NoTitleListBModel(hmodelsFour))
        mDataset.add(NoTitleListBModel(hmodelsFive))
        mDataset.add(NoTitleListBModel(hmodelsSix))
        mDataset.add(NoTitleListBModel(hmodelsSeven))
        mDataset.add(NoTitleListBModel(hmodelsHeigth))
    }
}