package com.github.nasrat_v.yaba_android.Services.Factory.Author

import android.content.Context
import com.github.nasrat_v.yaba_android.Listable.Author.AModel
import com.github.nasrat_v.yaba_android.R

class AModelRandomFactory(private var context: Context) {

    fun getRandomInstance(): AModel {
        return (AModel(
            "",
            "",
            getRandomName(),
            getRandomDescription(),
            arrayListOf()
        ))
    }

    private fun getRandomPicture(): Int {
        return R.drawable.author_round
    }

    private fun getRandomName(): String {
        val nameArray = context.resources.getStringArray(R.array.name_authors_books)

        return nameArray[(nameArray.indices).random()]
    }

    private fun getRandomDescription(): String {
        val descArray = context.resources.getStringArray(R.array.desc_authors_books)

        return descArray[(descArray.indices).random()]
    }
}