package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel

class GroupListBModelFactory {

    fun getEmptyInstance(): GroupListBModel {
        return (GroupListBModel(
            arrayListOf()
        ))
    }
}