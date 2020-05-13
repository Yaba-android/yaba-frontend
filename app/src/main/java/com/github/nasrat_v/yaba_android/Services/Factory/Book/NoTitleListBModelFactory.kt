package com.github.nasrat_v.yaba_android.Services.Factory.Book

import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.NoTitleListBModel

class NoTitleListBModelFactory {

    fun getEmptyInstance(): NoTitleListBModel {
        return (NoTitleListBModel(
            arrayListOf()
        ))
    }
}