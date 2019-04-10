package com.github.nasrat_v.maktaba_demo.Services.Provider.Book

import android.content.Context
import com.github.nasrat_v.maktaba_demo.Services.Factory.Book.BModelFactory
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_demo.R

class BModelProvider(private var context: Context, private var languageCode: String) {

    fun getAllBooksFromDatabase(): ArrayList<BModel> {
        val allBooksDb = arrayListOf<BModel>()

        for (index in 0..(getDatabaseSize() - 1)) {
            allBooksDb.add(
                BModelFactory(context, languageCode)
                    .getInstance(
                        index
                    )
            )
        }
        return allBooksDb
    }

    private fun getDatabaseSize(): Int {
        return context.resources.getIntArray(R.array.db_size).first()
    }
}