package com.github.nasrat_v.yaba_android.ICallback

import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.GroupBModel

interface IGroupClickCallback {
    fun groupEventButtonClicked(group: GroupBModel)
}