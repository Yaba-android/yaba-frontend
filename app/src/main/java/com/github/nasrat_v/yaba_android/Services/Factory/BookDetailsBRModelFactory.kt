package com.github.nasrat_v.yaba_android.Services.Factory

import com.github.nasrat_v.yaba_android.Listable.Model.BookDetailsBRModel

class BookDetailsBRModelFactory {

    fun getEmptyInstance(): BookDetailsBRModel {
        return (BookDetailsBRModel(
            arrayListOf(),
            arrayListOf()
        ))
    }
}