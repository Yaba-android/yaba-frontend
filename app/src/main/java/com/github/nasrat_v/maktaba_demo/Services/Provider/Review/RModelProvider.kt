package com.github.nasrat_v.maktaba_demo.Services.Provider.Review

import android.content.Context
import com.github.nasrat_v.maktaba_demo.Listable.Review.Vertical.RModel
import com.github.nasrat_v.maktaba_demo.R
import java.util.*

class RModelProvider(private var context: Context) {

    fun getAllReviews() : ArrayList<RModel> {
        val hmodels = arrayListOf<RModel>()
        val imgArray = context.resources.obtainTypedArray(R.array.images_reviews)
        val authorArray = context.resources.getStringArray(R.array.authors_reviews)
        val commentArray = context.resources.getStringArray(R.array.comments_reviews)
        val ratingArray = context.resources.getStringArray(R.array.ratings_reviews)

        for (index in 0..(authorArray.size - 1)) {
            hmodels.add(
                RModel(
                    imgArray.getResourceId(index, -1),
                    authorArray[index],
                    commentArray[index],
                    ratingArray[index].toFloat()
                )
            )
        }
        imgArray.recycle()
        return (hmodels)
    }

}