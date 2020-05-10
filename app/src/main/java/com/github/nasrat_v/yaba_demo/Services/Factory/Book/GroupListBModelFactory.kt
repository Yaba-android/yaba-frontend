package com.github.nasrat_v.yaba_demo.Services.Factory.Book

import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel

class GroupListBModelFactory {

    fun getEmptyInstance(): GroupListBModel {
        return (GroupListBModel(
            arrayListOf()
        ))
    }
}