package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Author.AModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Author.AModelRandomFactory

class BModelRandomFactory(private var context: Context) {

    fun getRandomInstance() : BModel {
        return (BModel(
            getRandomImage(), getRandomTitle(),
            getRandomAuthor(), getRandomRating(),
            getRandomNumberRating(), getRandomPrice(),
            getRandomLength(), getRandomGenre(),
            getRandomFileSize(), getRandomCountry(),
            getRandomDatePublication(),
            getRandomPublisher(), context.getString(R.string.resume_book)
        ))
    }

    private fun getRandomImage() : Int {
        val imgArray = context.resources.obtainTypedArray(R.array.images_books)
        val img = imgArray.getResourceId((0..(imgArray.length() - 1)).random(), -1)

        imgArray.recycle()
        return img
    }

    private fun getRandomTitle() : String {
        val titleArray = context.resources.getStringArray(R.array.titles_books)

        return titleArray[(0..(titleArray.size - 1)).random()]
    }

    private fun getRandomAuthor() : AModel {
        return AModelRandomFactory(context).getRandomInstance()
    }

    private fun getRandomRating() : Float {
        val ratingArray = context.resources.getStringArray(R.array.ratings_books)

        return ratingArray[(0..(ratingArray.size - 1)).random()].toFloat()
    }

    private fun getRandomNumberRating() : Int {
        return (0..999).random()
    }

    private fun getRandomPrice() : Float {
        val priceArray = context.resources.getStringArray(R.array.prices_books)

        return priceArray[(0..(priceArray.size - 1)).random()].toFloat()
    }

    private fun getRandomLength() : Int {
        val lengthArray = context.resources.getIntArray(R.array.lengths_books)

        return lengthArray[(0..(lengthArray.size - 1)).random()]
    }

    private fun getRandomGenre() : GModel {
        val genreList = GModelProvider(context).getAllGenres()

        return genreList[(0..(genreList.size - 1)).random()]
    }

    private fun getRandomFileSize() : String {
        val fileSizeArray = context.resources.getStringArray(R.array.filesizes_books)

        return fileSizeArray[(0..(fileSizeArray.size - 1)).random()]
    }

    private fun getRandomCountry() : String {
        val countryArray = context.resources.getStringArray(R.array.countries_books)

        return countryArray[(0..(countryArray.size - 1)).random()]
    }

    private fun getRandomDatePublication() : String {
        val datePublicationArray = context.resources.getStringArray(R.array.datepublications_books)

        return datePublicationArray[(0..(datePublicationArray.size - 1)).random()]
    }

    private fun getRandomPublisher() : String {
        val datePublicationArray = context.resources.getStringArray(R.array.publishers_books)

        return datePublicationArray[(0..(datePublicationArray.size - 1)).random()]
    }
}