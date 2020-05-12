package com.github.nasrat_v.yaba_demo.Services.Factory.Author

import android.content.Context
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Author.AModel
import com.github.nasrat_v.yaba_demo.R
import org.json.JSONArray
import org.json.JSONObject

class AModelFactory(private var context: Context, private var languageCode: String) {

    companion object {
        private const val JSON_AMODEL_AUTHORID = "AuthorId"
        private const val JSON_AMODEL_REMOTEID = "RemoteId"
        private const val JSON_AMODEL_IMAGEPATH = "ImagePath"
        private const val JSON_AMODEL_AUTHORNAME = "AuthorName"
        private const val JSON_AMODEL_NAME = "Name"
        private const val JSON_AMODEL_DESC = "Desc"
        private const val JSON_AMODEL_BOOKSID = "BooksId"
    }

    fun getInstanceFromJsonObject(jsonObject: JSONObject): AModel {
        return (AModel(
            jsonToString(jsonObject, JSON_AMODEL_REMOTEID),
            jsonToString(jsonObject, JSON_AMODEL_IMAGEPATH),
            jsonToString(jsonObject, JSON_AMODEL_NAME),
            jsonToString(jsonObject, JSON_AMODEL_DESC),
            getBooksIdFromJsonArray(jsonObject.getJSONArray(JSON_AMODEL_BOOKSID))
        ))
    }

    private fun getBooksIdFromJsonArray(jsonArray: JSONArray): ArrayList<String> {
        val booksId = arrayListOf<String>()

        for (i in 0 until jsonArray.length()) {
            val str = jsonArray.getString(i)
            booksId.add(str)
        }
        return booksId
    }

    fun getInstanceFromBModelJsonObject(jsonObject: JSONObject): AModel {
        return (AModel(
            jsonToString(jsonObject, JSON_AMODEL_AUTHORID),
            "",
            jsonToString(jsonObject, JSON_AMODEL_AUTHORNAME),
            "",
            arrayListOf()
        ))
    }

    fun getEmptyInstance(): AModel {
        return (AModel(
            "", "",
            "", "",
            arrayListOf()
        ))
    }

    /*fun getInstance(index: Int): AModel {
        return (AModel(
            getPicture(),
            getName(index),
            getDescription(index)
        ))
    }*/

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