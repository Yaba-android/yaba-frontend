package com.github.nasrat_v.yaba_demo.Services.Factory.Book

import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel

class DownloadListBModelFactory {

    fun getEmptyInstance(): DownloadListBModel {
        return (DownloadListBModel(
            arrayListOf()
        ))
    }
}