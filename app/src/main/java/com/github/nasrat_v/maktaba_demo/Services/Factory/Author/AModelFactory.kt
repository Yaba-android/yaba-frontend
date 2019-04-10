package com.github.nasrat_v.maktaba_demo.Services.Factory.Author

import android.content.Context
import com.github.nasrat_v.maktaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.maktaba_demo.Listable.Author.AModel
import com.github.nasrat_v.maktaba_demo.R

class AModelFactory(private var context: Context, private var languageCode: String) {

    fun getEmptyInstance(): AModel {
        return (AModel(
            getPicture(),
            "", ""
        ))
    }

    fun getInstance(index: Int): AModel {
        return (AModel(
            getPicture(),
            getName(index),
            getDescription(index)
        ))
    }

    private fun getPicture(): Int {
        return R.drawable.author_round
    }

    private fun getName(index: Int): String {
        val nameArray = context.resources.getStringArray(R.array.name_authors_books)

        return nameArray[index]
    }

    private fun getDescription(index: Int): String {
        val descArray = if (languageCode == StringLocaleResolver.ARABIC_LANGUAGE_CODE) {
            context.resources.getStringArray(R.array.desc_authors_books_arabic)
        } else {
            context.resources.getStringArray(R.array.desc_authors_books)
        }

        return descArray[index]
    }
}