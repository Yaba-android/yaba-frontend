package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Model.BrowseBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel

class BrowseBModelFactory {

    fun getEmptyInstance(): BrowseBModel {
        return (BrowseBModel(
            arrayListOf(),
            arrayListOf()
        ))
    }
}