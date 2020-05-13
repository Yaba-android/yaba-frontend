package com.github.nasrat_v.yaba_android.Services.Factory.Book

import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.GroupListBModel

class GroupListBModelFactory {

    fun getEmptyInstance(): GroupListBModel {
        return (GroupListBModel(
            arrayListOf()
        ))
    }
}