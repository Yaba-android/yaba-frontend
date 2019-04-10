package com.github.nasrat_v.maktaba_demo.Services.Factory

import com.github.nasrat_v.maktaba_demo.Listable.Model.BookDetailsBRModel

class BookDetailsBRModelFactory {

    fun getEmptyInstance(): BookDetailsBRModel {
        return (BookDetailsBRModel(
            arrayListOf(),
            arrayListOf()
        ))
    }
}