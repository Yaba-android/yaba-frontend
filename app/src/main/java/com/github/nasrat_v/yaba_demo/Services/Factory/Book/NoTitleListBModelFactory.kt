package com.github.nasrat_v.yaba_demo.Services.Factory.Book

import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel

class NoTitleListBModelFactory {

    fun getEmptyInstance(): NoTitleListBModel {
        return (NoTitleListBModel(
            arrayListOf()
        ))
    }
}