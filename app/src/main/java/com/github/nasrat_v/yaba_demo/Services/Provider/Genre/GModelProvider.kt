package com.github.nasrat_v.yaba_demo.Services.Provider.Genre

import android.content.Context
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelProvider
import kotlin.collections.ArrayList

class GModelProvider(
    var context: Context,
    private var languageCode: String
) {

    fun getAllGenres(): ArrayList<GModel> {
        val hmodels = arrayListOf<GModel>()
        val genreNumberArray = context.resources.getIntArray(R.array.genres_numbers)
        val genrePopularArray = context.resources.getIntArray(R.array.genres_popular)
        val genreArray = if (languageCode == StringLocaleResolver.FRENCH_LANGUAGE_CODE) {
            context.resources.getStringArray(R.array.genres_french)
        } else {
            context.resources.getStringArray(R.array.genres)
        }

        for (index in genreArray.indices) {
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

    fun getAllBooksFromGenre(genre: GModel): ArrayList<BModel> {
        val allbooks = BModelProvider(context, languageCode).getAllBooksFromResource()

        return ArrayList(
            allbooks.filter {
                it.genre == genre
            }
        )
    }

    fun getListAllBooksFromGenre(nbPerRow: Int, genre: GModel): ArrayList<NoTitleListBModel> {
        val filteredList = getAllBooksFromGenre(genre)

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

        for (index in 0 until nbPerRow) {
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