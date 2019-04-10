package com.github.nasrat_v.maktaba_demo.Services.Factory.Book

import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel

class GroupListBModelFactory {

    fun getEmptyInstance(): GroupListBModel {
        return (GroupListBModel(
            arrayListOf()
        ))
    }
}