package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel

class NoTitleListBModelFactory {

    fun getEmptyInstance(): NoTitleListBModel {
        return (NoTitleListBModel(
            arrayListOf()
        ))
    }
}