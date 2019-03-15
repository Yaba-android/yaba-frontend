package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import kotlin.collections.ArrayList

class BModelRandomProvider(private var context: Context) {

    fun getRandomsInstances(nb: Int): ArrayList<BModel> {
        val randomFactory = BModelRandomFactory(context)
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

    fun getRandomsInstancesFromList(nb: Int, books: ArrayList<BModel>): ArrayList<BModel> {
        val selectedBooks = arrayListOf<BModel>()
        val tmpList = arrayListOf<BModel>()
        var randomIndex: Int

        tmpList.addAll(books)
        for (index in 0..(nb - 1)) {
            randomIndex = (0..(tmpList.size - 1)).random()
            selectedBooks.add(tmpList[randomIndex])
            tmpList.removeAt(randomIndex)
        }
        return selectedBooks
    }
}