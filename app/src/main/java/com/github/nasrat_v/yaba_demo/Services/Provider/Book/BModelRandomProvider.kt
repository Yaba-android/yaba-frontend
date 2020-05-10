package com.github.nasrat_v.yaba_demo.Services.Provider.Book

import android.content.Context
import com.github.nasrat_v.yaba_demo.Services.Factory.Book.BModelRandomFactory
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import kotlin.collections.ArrayList

class BModelRandomProvider(private var context: Context, private var languageCode: String) {

    fun getRandomsInstancesFromList(
        nb: Int,
        books: ArrayList<BModel>
    ): ArrayList<BModel> {
        return getRandomsInstancesFromListWithSelectedIndex(
            nb, books, arrayListOf()
        )
    }

    fun getRandomsInstances(nb: Int): ArrayList<BModel> {
        val randomFactory =
            BModelRandomFactory(context, languageCode)
        val listModel = arrayListOf<BModel>()
        var book: BModel

        for (index in 0 until nb) {
            book = randomFactory.getRandomInstance()
            while (listModel.find { it == book } != null) {
                book = randomFactory.getRandomInstance()
            }
            listModel.add(book)
        }
        return listModel
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
        for (index in 0 until nbPerColumns) {
            selectedIndex.clear()
            dataset.add(
                ListBModel(
                    title,
                    getRandomsInstancesFromListWithSelectedIndex(
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
        for (index in 0 until nbPerColumns) {
            selectedIndex.clear()
            dataset.add(
                NoTitleListBModel(
                    getRandomsInstancesFromListWithSelectedIndex(
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

    private fun getRandomsInstancesFromListWithSelectedIndex(
        nb: Int,
        books: ArrayList<BModel>,
        selectedIndex: ArrayList<Int>
    ): ArrayList<BModel> {

        val selectedBooks = arrayListOf<BModel>()
        val tmpList = arrayListOf<BModel>()
        var randomIndex: Int

        tmpList.addAll(books)
        for (index in 0 until nb) {
            randomIndex = (0 until tmpList.size).random()
            selectedIndex.add(randomIndex)
            selectedBooks.add(tmpList[randomIndex])
            tmpList.removeAt(randomIndex)
        }
        return selectedBooks
    }

    private fun removeIndexFromTmpList(selectedIndex: ArrayList<Int>, tmpList: ArrayList<BModel>) {
        selectedIndex.forEach {
            tmpList.removeAt(it)
        }
    }
}