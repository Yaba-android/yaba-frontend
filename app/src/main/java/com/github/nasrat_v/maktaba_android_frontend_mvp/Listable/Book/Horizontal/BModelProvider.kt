package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel

class BModelProvider(private var context: Context) {

    companion object {
        const val NB_ALL_BOOKS_DATABASE = 50 // check max size in db before
    }

    fun getAllBooksFromDatabase(): ArrayList<BModel> {
        val allBooksDb = arrayListOf<BModel>()

        for (index in 0..(NB_ALL_BOOKS_DATABASE - 1)) {
            allBooksDb.add(BModelFactory(context)
                .getInstance(
                    index
                )
            )
        }
        return allBooksDb
    }
}