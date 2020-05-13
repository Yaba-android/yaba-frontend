package com.github.nasrat_v.yaba_android.Services.Factory.Book

import android.content.Context
import com.github.nasrat_v.yaba_android.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_android.Listable.Author.AModel
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_android.Listable.Genre.GModel
import com.github.nasrat_v.yaba_android.Services.Provider.Genre.GModelProvider
import com.github.nasrat_v.yaba_android.R
import com.github.nasrat_v.yaba_android.Services.Factory.Author.AModelRandomFactory

class BModelRandomFactory(private var context: Context, private var languageCode: String) {

    fun getRandomInstance() : BModel {
        return (BModel(
            "", "", getRandomTitle(),
            getRandomAuthor(), getRandomRating(),
            getRandomNumberRating(), getRandomPrice(),
            getRandomLength(), getRandomGenre(),
            getRandomFileSize(), getRandomCountry(),
            getRandomDatePublication(),
            getRandomPublisher(), getRandomResume(), ""
        ))
    }

    /*private fun getRandomImage() : Int {
        val imgArray = context.resources.obtainTypedArray(R.array.images_books)
        val img = imgArray.getResourceId((0..(imgArray.length() - 1)).random(), -1)

        imgArray.recycle()
        return img
    }*/

    private fun getRandomTitle() : String {
        val titleArray = context.resources.getStringArray(R.array.titles_books)

        return titleArray[(titleArray.indices).random()]
    }

    private fun getRandomAuthor() : AModel {
        return AModelRandomFactory(context).getRandomInstance()
    }

    private fun getRandomRating() : Float {
        val ratingArray = context.resources.getStringArray(R.array.ratings_books)

        return ratingArray[(ratingArray.indices).random()].toFloat()
    }

    private fun getRandomNumberRating() : Int {
        return (0..999).random()
    }

    private fun getRandomPrice() : Float {
        val priceArray = context.resources.getStringArray(R.array.prices_books)

        return priceArray[(priceArray.indices).random()].toFloat()
    }

    private fun getRandomLength() : Int {
        val lengthArray = context.resources.getIntArray(R.array.lengths_books)

        return lengthArray[(lengthArray.indices).random()]
    }

    private fun getRandomGenre() : GModel {
        val genreList = GModelProvider(context, languageCode).getAllGenres()

        return genreList[(0 until genreList.size).random()]
    }

    private fun getRandomFileSize() : String {
        val fileSizeArray = context.resources.getStringArray(R.array.filesizes_books)

        return fileSizeArray[(fileSizeArray.indices).random()]
    }

    private fun getRandomCountry() : String {
        val countryArray = context.resources.getStringArray(R.array.countries_books)

        return countryArray[(countryArray.indices).random()]
    }

    private fun getRandomDatePublication() : String {
        val datePublicationArray = context.resources.getStringArray(R.array.datepublications_books)

        return datePublicationArray[(datePublicationArray.indices).random()]
    }

    private fun getRandomPublisher() : String {
        val datePublicationArray = context.resources.getStringArray(R.array.publishers_books)

        return datePublicationArray[(datePublicationArray.indices).random()]
    }

    private fun getRandomResume(): String {
        val resumeArray =  if (languageCode == StringLocaleResolver.FRENCH_LANGUAGE_CODE) {
            context.resources.getStringArray(R.array.resume_books_arabic)
        } else {
            context.resources.getStringArray(R.array.resume_books)
        }

        return resumeArray[(resumeArray.indices).random()]
    }
}