package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel

class LibraryBModelFactory {

    fun getEmptyInstance(): LibraryBModel {
        return (LibraryBModel(
            arrayListOf(),
            arrayListOf(),
            arrayListOf()
        ))
    }
}