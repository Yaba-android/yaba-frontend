package com.github.nasrat_v.maktaba_demo.Services.Factory.Genre

import android.content.Context
import com.github.nasrat_v.maktaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.maktaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_demo.Services.Provider.Genre.GModelProvider
import com.github.nasrat_v.maktaba_demo.R

class GModelFactory(private var context: Context, private var languageCode: String) {

    fun getEmptyInstance(): GModel {
        return GModel("", 0, 0)
    }

    fun getInstance(index: Int): GModel {
        val genreList = if (languageCode == StringLocaleResolver.ARABIC_LANGUAGE_CODE) {
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
}