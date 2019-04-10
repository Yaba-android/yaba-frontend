package com.github.nasrat_v.maktaba_demo.Services.Factory.Book

import com.github.nasrat_v.maktaba_demo.Listable.Book.Model.BrowseBModel

class BrowseBModelFactory {

    fun getEmptyInstance(): BrowseBModel {
        return (BrowseBModel(
            arrayListOf(),
            arrayListOf()
        ))
    }
}