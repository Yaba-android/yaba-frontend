package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Model.BookDetailsBRModel

class BookDetailsBRModelFactory {

    fun getEmptyInstance(): BookDetailsBRModel {
        return (BookDetailsBRModel(
            arrayListOf(),
            arrayListOf()
        ))
    }
}