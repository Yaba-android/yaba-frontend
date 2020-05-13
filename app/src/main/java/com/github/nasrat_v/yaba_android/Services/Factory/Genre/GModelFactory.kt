package com.github.nasrat_v.yaba_android.Services.Factory.Genre

import android.content.Context
import com.github.nasrat_v.yaba_android.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_android.Listable.Genre.GModel
import com.github.nasrat_v.yaba_android.Services.Provider.Genre.GModelProvider
import com.github.nasrat_v.yaba_android.R
import org.json.JSONObject

class GModelFactory(private var context: Context, private var languageCode: String) {

    companion object {
        const val JSON_GMODEL_GENRE = "Genre"
    }

    fun getInstanceFromJsonObject(jsonObject: JSONObject): GModel {
        return GModel(
            jsonToString(jsonObject, JSON_GMODEL_GENRE),
            0,
            0
        )
    }

    fun getEmptyInstance(): GModel {
        return GModel("", 0, 0)
    }

    fun getInstance(index: Int): GModel {
        val genreList = if (languageCode == StringLocaleResolver.FRENCH_LANGUAGE_CODE) {
            context.resources.getStringArray(R.array.genres_books_arabic)
        } else {
            context.resources.getStringArray(R.array.genres_books)
        }
        val provider = GModelProvider(context, languageCode)
        val genrePopularList = provider.getPopularGenres()
        val name = genreList[index]
        val nb = provider.getNbGenre(name)

        if (genrePopularList.find { it.name == name } != null) {
            return GModel(name, nb, 1)
        }
        return (GModel(name, nb, 0))
    }

    private fun jsonToString(jsonObject: JSONObject, key: String): String {
        return jsonObject.get(key).toString()
    }
}