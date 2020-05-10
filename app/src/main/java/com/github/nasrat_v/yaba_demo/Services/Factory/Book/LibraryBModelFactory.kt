package com.github.nasrat_v.yaba_demo.Services.Factory.Book

import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.Model.LibraryBModel

class LibraryBModelFactory {

    fun getEmptyInstance(): LibraryBModel {
        return (LibraryBModel(
            arrayListOf(),
            arrayListOf(),
            arrayListOf()
        ))
    }
}