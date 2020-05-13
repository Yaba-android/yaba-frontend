package com.github.nasrat_v.yaba_android.Services.Provider.Book

import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.NoTitleListBModel

class LibraryBModelRandomProvider {

    fun getRandomDownloadedListBookFromList(nbY: Int, nbX: Int, allBooks: ArrayList<NoTitleListBModel>)
            : ArrayList<DownloadListBModel> {

        val simpleList = allBooksInSimpleList(allBooks)
        val indexSelected = getAllRandomIndex((nbY * nbX), simpleList.size)
        val listBooksSelected = arrayListOf<DownloadListBModel>()

        for (index in 0 until nbY) {
            listBooksSelected.add(
                DownloadListBModel(
                    getRandomDownloadedBookFromList(nbX, simpleList, indexSelected)
                )
            )
        }
        return listBooksSelected
    }

    private fun getRandomDownloadedBookFromList(nbX: Int, list: ArrayList<BModel>, indexSelected: ArrayList<Int>)
            : ArrayList<DownloadBModel> {

        val booksSelected = arrayListOf<DownloadBModel>()

        for (index in 0 until nbX) {
            booksSelected.add(
                DownloadBModel(
                    list[indexSelected.first()]
                )
            )
            indexSelected.remove(indexSelected.first())
        }
        return booksSelected
    }

    private fun getAllRandomIndex(nbAllIndex: Int, sizeList: Int): ArrayList<Int> {
        val indexSelected = arrayListOf<Int>()
        var randomIndex: Int

        for (index in 0 until nbAllIndex) {
            randomIndex = randomizeIndex(indexSelected, sizeList)
            indexSelected.add(randomIndex)
        }
        return indexSelected
    }

    private fun randomizeIndex(indexSelected: ArrayList<Int>, sizeList: Int): Int {
        var randomIndex = initRandomIndex(sizeList)

        while (indexSelected.find { it == randomIndex } != null)
            randomIndex = initRandomIndex(sizeList)
        return randomIndex
    }

    private fun allBooksInSimpleList(allBooks: ArrayList<NoTitleListBModel>): ArrayList<BModel> {
        val simpleList = arrayListOf<BModel>()

        allBooks.forEach {
            it.bookModels.forEach { book ->
                simpleList.add(book)
            }
        }
        return simpleList
    }

    private fun initRandomIndex(size: Int): Int {
        return (0 until size).random()
    }
}