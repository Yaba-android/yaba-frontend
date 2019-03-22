package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import java.util.*
import kotlin.collections.ArrayList

class GModelProvider(var context: Context) {

    fun getAllGenres(): ArrayList<GModel> {
        val hmodels = arrayListOf<GModel>()
        val genreArray = context.resources.getStringArray(R.array.genres)
        val genreNumberArray = context.resources.getIntArray(R.array.genres_numbers)
        val genrePopularArray = context.resources.getIntArray(R.array.genres_popular)

        for (index in 0..(genreArray.size - 1)) {
            hmodels.add(
                GModel(
                    genreArray[index],
                    genreNumberArray[index],
                    genrePopularArray[index]
                )
            )
        }
        return (hmodels)
    }

    fun getPopularGenres(): ArrayList<GModel> {
        val hmodels = getAllGenres()
        val popularList = arrayListOf<GModel>()

        hmodels.forEach {
            if (it.popular != 0) { // get only popular genre
                popularList.add(it)
            }
        }
        return (popularList)
    }

    fun getNbGenre(genreName: String): Int {
        val hmodels = getAllGenres()
        val genre = hmodels.find {
            it.name == genreName
        }

        if (genre != null)
            return (genre.nb)
        return -1 // error
    }

    fun getListAllBooksFromGenre(nbPerRow: Int, genre: GModel): ArrayList<NoTitleListBModel> {
        val allbooks = BModelProvider(context).getAllBooksFromDatabase()
        val filteredList = ArrayList(
            allbooks.filter {
                it.genre == genre
            }
        )

        if (filteredList.isNotEmpty()) {
            return simpleListToNoTitleList(nbPerRow, filteredList)
        }
        return arrayListOf() // no books from genre
    }

    private fun simpleListToNoTitleList(nbPerRow: Int, filteredList: ArrayList<BModel>): ArrayList<NoTitleListBModel> {
        val noTitleList = arrayListOf<NoTitleListBModel>()

        while (filteredList.isNotEmpty()) {
            fillListWithNextBook(nbPerRow, filteredList, noTitleList)
        }
        return noTitleList
    }

    private fun fillListWithNextBook(
        nbPerRow: Int,
        filteredList: ArrayList<BModel>,
        noTitleList: ArrayList<NoTitleListBModel>
    ) {
        val books = arrayListOf<BModel>()

        for (index in 0..(nbPerRow - 1)) {
            if (filteredList.isEmpty()) {
                noTitleList.add(NoTitleListBModel(books))
                return
            }
            books.add(filteredList.first())
            filteredList.remove(filteredList.first())
        }
        noTitleList.add(NoTitleListBModel(books))
    }
}