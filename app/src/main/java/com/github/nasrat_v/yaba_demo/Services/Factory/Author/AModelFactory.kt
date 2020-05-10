package com.github.nasrat_v.yaba_demo.Services.Factory.Author

import android.content.Context
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Author.AModel
import com.github.nasrat_v.yaba_demo.R
import org.json.JSONObject

class AModelFactory(private var context: Context, private var languageCode: String) {

    companion object {
        const val JSON_AMODEL_AUTHOR = "Author"
    }

    fun getInstanceFromJsonObject(jsonObject: JSONObject): AModel {
        return (AModel(
            getPicture(),
            jsonToString(jsonObject, JSON_AMODEL_AUTHOR),
            getDescription(1)
        ))
    }

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

    private fun jsonToString(jsonObject: JSONObject, key: String): String {
        return jsonObject.get(key).toString()
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