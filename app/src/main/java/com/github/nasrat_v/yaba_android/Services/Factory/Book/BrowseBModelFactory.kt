package com.github.nasrat_v.yaba_android.Services.Factory.Book

import com.github.nasrat_v.yaba_android.Listable.Book.Model.BrowseBModel

class BrowseBModelFactory {

    fun getEmptyInstance(): BrowseBModel {
        return (BrowseBModel(
            arrayListOf(),
            arrayListOf()
        ))
    }
}