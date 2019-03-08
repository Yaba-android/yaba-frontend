package com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Genre.GModelRandomFactory
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import java.util.*

class BModelRandomFactory(private var context: Context) {

    fun getRandomsInstances(nb: Int) : ArrayList<BModel> {
        val listModel = arrayListOf<BModel>()

        for (index in 0..nb) {
            listModel.add(getRandomInstance())
        }
        return listModel
    }

    fun getRandomsInstancesDiscreteScrollView(nb: Int) : ArrayList<BModel> {
        val listModel = arrayListOf<BModel>()

        for (index in 0..nb) {
            listModel.add(getRandomInstanceDiscreteScrollView())
        }
        return listModel
    }

    private fun getRandomInstance() : BModel {
        return (BModel(getRandomImage(), getRandomTitle(),
                getRandomAuthor(), getRandomRating(),
                getRandomNumberRating(), getRandomPrice(),
                getRandomLength(), getRandomGenre(),
                getRandomFileSize(), getRandomCountry(),
                getRandomDatePublication(), getRandomPublisher()))
    }

    private fun getRandomInstanceDiscreteScrollView() : BModel {
        return (BModel(getRandomImageCarousel(), getRandomTitle(),
                getRandomAuthor(), getRandomRating(),
                getRandomNumberRating(), getRandomPrice(),
                getRandomLength(), getRandomGenre(),
                getRandomFileSize(), getRandomCountry(),
                getRandomDatePublication(), getRandomPublisher()))
    }

    private fun getRandomImage() : Int {
        val imgArray = context.resources.obtainTypedArray(R.array.images_books)
        val img = imgArray.getResourceId((0..(imgArray.length() - 1)).random(), -1)

        imgArray.recycle()
        return img
    }

    private fun getRandomImageCarousel() : Int {
        val imgArray = context.resources.obtainTypedArray(R.array.images_books_carousel)
        val img = imgArray.getResourceId((0..(imgArray.length() - 1)).random(), -1)

        imgArray.recycle()
        return img
    }

    private fun getRandomTitle() : String {
        val titleArray = context.resources.getStringArray(R.array.titles_books)

        return titleArray[(0..(titleArray.size - 1)).random()]
    }

    private fun getRandomAuthor() : String {
        val authorArray = context.resources.getStringArray(R.array.authors_books)

        return authorArray[(0..(authorArray.size - 1)).random()]
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
        val genreList = GModelRandomFactory(context).getAllGenres()

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