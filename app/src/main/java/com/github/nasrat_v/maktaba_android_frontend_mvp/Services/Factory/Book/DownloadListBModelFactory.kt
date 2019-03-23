package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel

class DownloadListBModelFactory {

    fun getEmptyInstance(): DownloadListBModel {
        return (DownloadListBModel(
            arrayListOf()
        ))
    }
}