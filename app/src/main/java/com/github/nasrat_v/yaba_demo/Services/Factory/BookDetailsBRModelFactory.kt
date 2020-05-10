package com.github.nasrat_v.yaba_demo.Services.Factory

import com.github.nasrat_v.yaba_demo.Listable.Model.BookDetailsBRModel

class BookDetailsBRModelFactory {

    fun getEmptyInstance(): BookDetailsBRModel {
        return (BookDetailsBRModel(
            arrayListOf(),
            arrayListOf()
        ))
    }
}