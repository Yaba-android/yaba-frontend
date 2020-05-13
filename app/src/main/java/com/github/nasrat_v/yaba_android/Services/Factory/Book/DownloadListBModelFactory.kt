package com.github.nasrat_v.yaba_android.Services.Factory.Book

import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.DownloadListBModel

class DownloadListBModelFactory {

    fun getEmptyInstance(): DownloadListBModel {
        return (DownloadListBModel(
            arrayListOf()
        ))
    }
}