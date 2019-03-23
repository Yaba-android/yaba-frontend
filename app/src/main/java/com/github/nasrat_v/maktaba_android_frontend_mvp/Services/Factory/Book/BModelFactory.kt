package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Genre.GModelFactory

class BModelFactory(private var context: Context) {

    fun getEmptyInstance(): BModel {
        return (BModel(
            getEmptyImage(), "",
            "", 0f,
            0, 0f,
            0, GModelFactory().getEmptyInstance(),
            "", "",
            "", ""
        ))
    }

    fun getInstance(index: Int): BModel {
        return (BModel(
            getImage(index), getTitle(index),
            getAuthor(index), getRating(index),
            getNumberRating(index), getPrice(index),
            getLength(index), getGenre(index),
            getFileSize(index), getCountry(index),
            getDatePublication(index), getPublisher(index)
        ))
    }

    private fun getEmptyImage(): Int {
        val imgArray = context.resources.obtainTypedArray(R.array.empty_image_book)
        val img = imgArray.getResourceId(0, -1)

        imgArray.recycle()
        return img
    }

    private fun getImage(index: Int): Int {
        val imgArray = context.resources.obtainTypedArray(R.array.images_books)
        val img = imgArray.getResourceId(index, -1)

        imgArray.recycle()
        return img
    }

    private fun getTitle(index: Int): String {
        val titleArray = context.resources.getStringArray(R.array.titles_books)

        return titleArray[index]
    }

    private fun getAuthor(index: Int): String {
        val authorArray = context.resources.getStringArray(R.array.authors_books)

        return authorArray[index]
    }

    private fun getRating(index: Int): Float {
        val ratingArray = context.resources.getStringArray(R.array.ratings_books)

        return ratingArray[index].toFloat()
    }

    private fun getNumberRating(index: Int): Int {
        val numberRatingArray = context.resources.getIntArray(R.array.numbers_ratings_books)

        return numberRatingArray[index]
    }

    private fun getPrice(index: Int): Float {
        val priceArray = context.resources.getStringArray(R.array.prices_books)

        return priceArray[index].toFloat()
    }

    private fun getLength(index: Int): Int {
        val lengthArray = context.resources.getIntArray(R.array.lengths_books)

        return lengthArray[index]
    }

    private fun getGenre(index: Int): GModel {
        val genreList = context.resources.getStringArray(R.array.genres_books)
        val provider = GModelProvider(context)
        val genrePopularList = provider.getPopularGenres()
        val name = genreList[index]
        val nb = provider.getNbGenre(name)

        if (genrePopularList.find { it.name == name } != null) {
            return GModel(name, nb, 1)
        }
        return (GModel(genreList[index], nb, 0))
    }

    private fun getFileSize(index: Int): String {
        val fileSizeArray = context.resources.getStringArray(R.array.filesizes_books)

        return fileSizeArray[index]
    }

    private fun getCountry(index: Int): String {
        val countryArray = context.resources.getStringArray(R.array.countries_books)

        return countryArray[index]
    }

    private fun getDatePublication(index: Int): String {
        val datePublicationArray = context.resources.getStringArray(R.array.datepublications_books)

        return datePublicationArray[index]
    }

    private fun getPublisher(index: Int): String {
        val datePublicationArray = context.resources.getStringArray(R.array.publishers_books)

        return datePublicationArray[index]
    }
}