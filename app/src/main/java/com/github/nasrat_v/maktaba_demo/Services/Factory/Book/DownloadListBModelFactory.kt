package com.github.nasrat_v.maktaba_demo.Services.Factory.Book

import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel

class DownloadListBModelFactory {

    fun getEmptyInstance(): DownloadListBModel {
        return (DownloadListBModel(
            arrayListOf()
        ))
    }
}