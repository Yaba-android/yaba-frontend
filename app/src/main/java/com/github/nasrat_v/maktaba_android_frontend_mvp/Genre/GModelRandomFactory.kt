package com.github.nasrat_v.maktaba_android_frontend_mvp.Genre

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class GModelRandomFactory(var context: Context) {

    fun getAllGenres() : ArrayList<GModel> {
        val hmodels = arrayListOf<GModel>()
        val genreArray = context.resources.getStringArray(R.array.genres)
        val genreNumberArray = context.resources.getIntArray(R.array.genres_numbers)
        val genrePopularArray = context.resources.getIntArray(R.array.genres_popular)

        for (index in 0..(genreArray.size - 1)) {
            hmodels.add(GModel(genreArray[index], genreNumberArray[index], genrePopularArray[index]))
        }
        return (hmodels)
    }

    fun getPopularGenres() : ArrayList<GModel> {
        val hmodels = getAllGenres()
        val popularList = arrayListOf<GModel>()

        hmodels.forEach {
            if (it.popular != 0) { // get only popular genre
                popularList.add(it)
            }
        }
        return (popularList)
    }
}