package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Author

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Author.AModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class AModelFactory(private var context: Context) {

    fun getEmptyInstance(): AModel {
        return (AModel(
            R.drawable.author_round, "",
            context.getString(R.string.author_description)
        ))
    }
}