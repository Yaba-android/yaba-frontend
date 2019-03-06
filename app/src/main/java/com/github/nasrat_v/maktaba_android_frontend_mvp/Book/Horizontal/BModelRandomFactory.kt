package com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal

import android.content.Context
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
                getRandomNumberRating(), getRandomPrice()))
    }

    private fun getRandomInstanceDiscreteScrollView() : BModel {
        return (BModel(getRandomImageCarousel(), getRandomTitle(),
                getRandomAuthor(), getRandomRating(),
                getRandomNumberRating(), getRandomPrice()))
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
        return ((0..999).random())
    }

    private fun getRandomPrice() : Float {
        val priceArray = context.resources.getStringArray(R.array.prices_books)

        return priceArray[(0..(priceArray.size - 1)).random()].toFloat()
    }
}