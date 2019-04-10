package com.github.nasrat_v.maktaba_demo.Services.Factory.Book

import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.Model.LibraryBModel

class LibraryBModelFactory {

    fun getEmptyInstance(): LibraryBModel {
        return (LibraryBModel(
            arrayListOf(),
            arrayListOf(),
            arrayListOf()
        ))
    }
}