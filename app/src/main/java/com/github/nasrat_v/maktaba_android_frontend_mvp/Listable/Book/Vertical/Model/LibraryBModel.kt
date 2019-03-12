package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel

data class LibraryBModel(var downloadBooks: ArrayList<DownloadListBModel>,
                    var groupBooks: ArrayList<GroupListBModel>,
                    var allBooks: ArrayList<NoTitleListBModel>) {
}