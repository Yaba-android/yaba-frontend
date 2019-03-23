package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book.BModelRandomFactory
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import kotlin.collections.ArrayList

class BModelRandomProvider(private var context: Context) {

    fun getRandomsInstances(nb: Int): ArrayList<BModel> {
        val randomFactory =
            BModelRandomFactory(context)
        val listModel = arrayListOf<BModel>()
        var book: BModel

        for (index in 0..(nb - 1)) {
            book = randomFactory.getRandomInstance()
            while (listModel.find { it == book } != null) {
                book = randomFactory.getRandomInstance()
            }
            listModel.add(book)
        }
        return listModel
    }

    fun getRandomsInstancesFromList(
        nb: Int,
        books: ArrayList<BModel>,
        selectedIndex: ArrayList<Int>
    ): ArrayList<BModel> {

        val selectedBooks = arrayListOf<BModel>()
        val tmpList = arrayListOf<BModel>()
        var randomIndex: Int

        tmpList.addAll(books)
        for (index in 0..(nb - 1)) {
            randomIndex = (0..(tmpList.size - 1)).random()
            selectedIndex.add(randomIndex)
            selectedBooks.add(tmpList[randomIndex])
            tmpList.removeAt(randomIndex)
        }
        return selectedBooks
    }

    fun getRandomsInstancesFromListToListBModel(
        title: String,
        nbPerColumns: Int,
        nbPerRow: Int,
        allBooks: ArrayList<BModel>
    ): ArrayList<ListBModel> {

        val selectedIndex = arrayListOf<Int>()
        val tmpList = arrayListOf<BModel>()
        val dataset = arrayListOf<ListBModel>()

        tmpList.addAll(allBooks)
        for (index in 0..(nbPerColumns - 1)) {
            selectedIndex.clear()
            dataset.add(
                ListBModel(
                    title,
                    getRandomsInstancesFromList(
                        nbPerRow,
                        tmpList,
                        selectedIndex
                    )
                )
            )
            removeIndexFromTmpList(selectedIndex, tmpList)
        }
        return dataset
    }

    fun getRandomsInstancesFromListToNoTitleListBModel(
        nbPerColumns: Int,
        nbPerRow: Int,
        allBooks: ArrayList<BModel>
    ): ArrayList<NoTitleListBModel> {

        val selectedIndex = arrayListOf<Int>()
        val tmpList = arrayListOf<BModel>()
        val dataset = arrayListOf<NoTitleListBModel>()

        tmpList.addAll(allBooks)
        for (index in 0..(nbPerColumns - 1)) {
            selectedIndex.clear()
            dataset.add(
                NoTitleListBModel(
                    getRandomsInstancesFromList(
                        nbPerRow,
                        tmpList,
                        selectedIndex
                    )
                )
            )
            removeIndexFromTmpList(selectedIndex, tmpList)
        }
        return dataset
    }

    private fun removeIndexFromTmpList(selectedIndex: ArrayList<Int>, tmpList: ArrayList<BModel>) {
        selectedIndex.forEach {
            tmpList.removeAt(it)
        }
    }
}