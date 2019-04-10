package com.github.nasrat_v.maktaba_demo.Services.Factory.Author

import android.content.Context
import com.github.nasrat_v.maktaba_demo.Listable.Author.AModel
import com.github.nasrat_v.maktaba_demo.R

class AModelRandomFactory(private var context: Context) {

    fun getRandomInstance(): AModel {
        return (AModel(
            getRandomPicture(),
            getRandomName(),
            getRandomDescription()
        ))
    }

    private fun getRandomPicture(): Int {
        return R.drawable.author_round
    }

    private fun getRandomName(): String {
        val nameArray = context.resources.getStringArray(R.array.name_authors_books)

        return nameArray[(0..(nameArray.size - 1)).random()]
    }

    private fun getRandomDescription(): String {
        val descArray = context.resources.getStringArray(R.array.desc_authors_books)

        return descArray[(0..(descArray.size - 1)).random()]
    }
}